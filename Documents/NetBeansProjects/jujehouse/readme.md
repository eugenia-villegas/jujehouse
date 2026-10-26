# Noche de Leyendas

> Título provisional: cambialo por el nombre definitivo del juego.

Juego de defensa por turnos ambientado en el folklore argentino. Criaturas de la tradición oral amenazan un pueblo y el jugador debe protegerlo con los remedios que cuenta la propia tradición.

Proyecto de la cátedra **Programación III** (Licenciatura en Sistemas de Información, Facultad de Informática y Diseño, Universidad Champagnat).

**Autor:** _completar_
**Ciclo:** 2025

---

## Tecnologías

| Herramienta | Uso |
|---|---|
| Java | Lenguaje principal (programación orientada a objetos) |
| Swing | Interfaz gráfica, diseñada con el editor visual de NetBeans |
| SQLite (`sqlite-jdbc`) | Persistencia de jugadores y partidas |
| Maven | Gestión de dependencias y build |
| Apache NetBeans | IDE |
| Git / GitHub | Control de versiones |

## Cómo ejecutarlo

1. Clonar el repositorio.
2. Abrirlo en Apache NetBeans como proyecto Maven.
3. Ejecutar *Clean and Build* (Maven descarga el driver de SQLite solo).
4. Ejecutar la clase `Jujehouse` (paquete `com.mycompany.jujehouse`).

El archivo de base de datos (`jujehouse.db`) se crea automáticamente la primera vez, junto con sus tablas. No hace falta instalar SQLite.

---

## Cómo se juega

1. Al abrir el juego se pide el nombre del jugador.
2. En cada oleada aparecen criaturas. Seleccionás una en la lista y le asignás un remedio con los botones.
3. Cada remedio se puede usar **una sola vez por oleada**; se reinician al empezar la siguiente.
4. Podés asignar más de un remedio a la misma criatura: sus daños se suman.
5. Con **Resolver** se calcula el resultado:
   - Si el daño total alcanza la vida de la criatura, queda **neutralizada** y suma su recompensa al puntaje.
   - Si no, **llega al pueblo** y le resta tranquilidad.
6. **Ganás** si sobrevivís las 3 oleadas. **Perdés** si la tranquilidad del pueblo llega a 0.
7. Al terminar, la partida se guarda y se muestra el ranking.

**Valores iniciales:** 10 puntos de tranquilidad y 0 puntos.

### Criaturas

| Criatura | Vida | Daño al pueblo | Recompensa |
|---|---|---|---|
| Luz Mala | 30 | 2 | 10 |
| Pomberito (inspirado en el Pombero) | 40 | 4 | 20 |
| Lobizón | 60 | 8 | 50 |

### Remedios

| Remedio | Poder | Costo | Efecto especial |
|---|---|---|---|
| Cuchillo | 40 | 30 | Ninguno |
| Rezo (cruz y oración) | 30 | 20 | Si no neutraliza a la criatura, reduce a la mitad el daño que hace al pueblo |
| Ofrenda (tabaco, miel y caña) | 20 | 15 | Ninguno |

El costo está definido en el enum, pero la versión actual no tiene tienda: el jugador recibe los tres remedios en cada oleada.

### Regla de combate

`daño efectivo = poder del remedio × multiplicador según la criatura`

| | Luz Mala | Pomberito | Lobizón |
|---|---|---|---|
| Cuchillo | ×1.5 | ×0.5 | ×1 |
| Rezo | ×1 | ×1 | ×1.5 |
| Ofrenda | ×0.5 | ×2 | ×0.5 |

Se suman los daños efectivos de todos los remedios asignados a una criatura. Si el total es mayor o igual a su vida, queda neutralizada.

Ejemplos:
- Cuchillo contra Luz Mala: 60 ≥ 30, neutralizada.
- Ofrenda contra Pomberito: 40 ≥ 40, neutralizado.
- Lobizón: ningún remedio solo alcanza 60. Cuchillo + Rezo suman 85, neutralizado.

### Oleadas

| Oleada | Criaturas |
|---|---|
| 1 | 2 Luz Mala |
| 2 | 2 Luz Mala + Pomberito |
| 3 | Lobizón + Pomberito + Luz Mala |

**Puntaje final:** suma de las recompensas de las criaturas neutralizadas.

> Los valores numéricos son de balance de juego y se ajustan probando. Lo que se apoya en la tradición es qué remedio funciona contra cada criatura.

---

## Arquitectura (MVC)

```
com.mycompany.jujehouse   Clase principal (main)
modelo                    Lógica del juego, sin Swing ni SQL
vista                     Pantalla Swing, sin lógica de juego
controlador               Conecta modelo y vista
persistencia              Acceso a SQLite
excepciones               Excepciones propias
```

### Modelo
- `Criatura` (abstracta) → `LuzMala`, `Pomberito`, `Lobizon`
- `TipoRemedio` (enum con nombre, poder, costo y efecto de cada remedio)
- `Remedio` (clase concreta: guarda su `TipoRemedio` y si ya se usó en la oleada)
- `Partida` (estado del juego: tranquilidad, puntaje, oleada, criaturas, remedios y asignaciones)

El **polimorfismo** vive en `getMultiplicador`: cada subclase de `Criatura` lo sobreescribe con sus propios valores. El cálculo del daño, escrito una sola vez en `Criatura`, funciona para las tres.

### Vista
- `VentanaJuego`: lista de criaturas, botones de remedios, botón de resolver, estado de la partida e informe. No tiene lógica: avisa al controlador de cada clic y muestra lo que se le pide.

### Controlador
- `ControladorJuego`: recibe las acciones de la vista, usa `Partida`, decide cuándo guardar la partida y cómo informar los errores.

### Persistencia
- `ConexionSQLite`: abre la conexión, activa las claves foráneas (`PRAGMA foreign_keys = ON`) y crea las tablas si no existen.
- `PartidaDAO`: guarda una partida y lee el ranking.

### Excepciones propias
- `RemedioYaUtilizadoException`: usar un remedio que ya se usó en la oleada. La lanza `Remedio.usar()` y la maneja el controlador.
- `PersistenciaException`: envuelve las `SQLException` para que la vista y el controlador nunca vean errores de JDBC.

---

## Base de datos

```
jugadores (1) ────< partidas
```

### `jugadores`
| Columna | Tipo | Notas |
|---|---|---|
| id | INTEGER | Clave primaria, autoincremental |
| nombre | TEXT | No nulo, único |

### `partidas`
| Columna | Tipo | Notas |
|---|---|---|
| id | INTEGER | Clave primaria, autoincremental |
| jugador_id | INTEGER | No nulo, clave foránea → `jugadores.id` |
| puntaje | INTEGER | No nulo |
| oleada_alcanzada | INTEGER | No nulo |
| victoria | INTEGER | 0 o 1 |
| fecha | TEXT | Formato `YYYY-MM-DD HH:MM` |

**Ranking:** consulta con `JOIN` entre las dos tablas, ordenada por puntaje descendente (top 5).

**Decisión de diseño:** las estadísticas de las criaturas (vida, daño, multiplicadores) viven en el código Java para aprovechar el polimorfismo. La base guarda solo jugadores y resultados.

---

## Etapas de desarrollo

- [x] Proyecto Maven creado en NetBeans
- [x] Dependencia `sqlite-jdbc` agregada
- [x] Repositorio en GitHub
- [x] Paquetes creados
- [x] Prueba de conexión a SQLite
- [x] Modelo: `TipoRemedio` (enum) y `Remedio`
- [x] Modelo: `Criatura` y subclases
- [x] Lógica de combate probada por consola
- [x] Excepción `RemedioYaUtilizadoException`
- [x] `Partida`
- [x] Ventana Swing y controlador
- [ ] Tablas SQLite, DAO y guardado de partida
- [ ] Ranking al finalizar la partida
- [ ] Pruebas de punta a punta

## Mejoras futuras

- Tienda de remedios con dinero.
- Más oleadas y más criaturas del folklore (el Familiar, la Llorona).
- Fichas de leyendas dentro del juego, guardadas en la base de datos.
- Arte pixel art.

---

## Fuentes del folklore

Las leyendas varían según la región y la fuente. Este proyecto toma como referencia:

- Luz Mala: [Billiken](https://billiken.lat/entretenimiento/como-y-por-que-aparece-la-luz-mala-uno-de-los-mitos-argentinos-mas-famosos/)
- Pombero: [Billiken](https://billiken.lat/interesante/el-pombero-el-duende-mitologico-que-no-te-querras-cruzar-nunca/)
- Lobizón: [Billiken](https://billiken.lat/interesante/quien-o-que-es-el-lobizon-uno-de-los-mas-famosos-mitos-argentinos/)