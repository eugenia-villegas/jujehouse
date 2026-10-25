# Noche de Leyendas

> Título provisional: cambialo por el nombre definitivo del juego.

Juego de defensa por turnos ambientado en el folklore argentino. Criaturas de la tradición oral amenazan una estancia y el jugador debe protegerla con los remedios que cuenta la propia tradición.

Proyecto de la cátedra **Programación III** (Licenciatura en Sistemas de Información, Facultad de Informática y Diseño, Universidad Champagnat).

**Autor:** _completar_
**Ciclo:** 2025

---

## Tecnologías

| Herramienta | Uso |
|---|---|
| Java | Lenguaje principal (programación orientada a objetos) |
| Swing | Interfaz gráfica |
| SQLite (`sqlite-jdbc`) | Persistencia de jugadores, partidas y fichas de leyendas |
| Maven | Gestión de dependencias y build |
| Apache NetBeans | IDE |
| Git / GitHub | Control de versiones |

## Cómo ejecutarlo

1. Clonar el repositorio.
2. Abrirlo en Apache NetBeans como proyecto Maven.
3. Ejecutar *Clean and Build* (Maven descarga el driver de SQLite solo).
4. Ejecutar la clase principal.

El archivo de base de datos (`.db`) se crea automáticamente la primera vez que corre el programa. No hace falta instalar SQLite.

---

## Cómo se juega

1. Antes de cada oleada podés **comprar remedios** con tus pesos.
2. Aparecen las **criaturas** de la oleada.
3. Asignás uno o más remedios a cada criatura. Los daños se suman.
4. Se resuelve el combate:
   - Si el daño total alcanza la vida de la criatura, queda **neutralizada** y ganás su recompensa.
   - Si no, **llega a la estancia** y le resta tranquilidad.
5. Cada remedio se puede usar **una sola vez por oleada**, pero se conserva para las siguientes.
6. **Ganás** si sobrevivís las 5 oleadas. **Perdés** si la tranquilidad de la estancia llega a 0.

**Valores iniciales:** 20 puntos de tranquilidad y 60 pesos.

### Criaturas

| Criatura | Vida | Daño a la estancia | Recompensa |
|---|---|---|---|
| Luz Mala | 30 | 2 | 10 |
| Pombero | 40 | 4 | 20 |
| Lobizón | 60 | 8 | 50 |

### Remedios

| Remedio | Poder | Costo | Efecto especial |
|---|---|---|---|
| Cuchillo | 40 | 30 | Ninguno |
| Rezo (cruz y oración) | 30 | 20 | Si no neutraliza a la criatura, reduce a la mitad el daño que hace a la estancia |
| Ofrenda (tabaco, miel y caña) | 20 | 15 | Ninguno |

### Regla de combate

`daño efectivo = poder del remedio × multiplicador según la criatura`

| | Luz Mala | Pombero | Lobizón |
|---|---|---|---|
| Cuchillo | ×1.5 | ×0.5 | ×1 |
| Rezo | ×1 | ×1 | ×1.5 |
| Ofrenda | ×0.5 | ×2 | ×0.5 |

Se suman los daños efectivos de todos los remedios asignados a una criatura. Si el total es mayor o igual a su vida, queda neutralizada.

Ejemplos:
- Cuchillo contra Luz Mala: 60 ≥ 30, neutralizada.
- Ofrenda contra Pombero: 40 ≥ 40, neutralizado.
- Lobizón: ningún remedio solo alcanza 60. Con Cuchillo + Rezo suman 85, neutralizado.

### Oleadas (borrador, a ajustar jugando)

| Oleada | Criaturas |
|---|---|
| 1 | 3 Luz Mala |
| 2 | 3 Luz Mala + 1 Pombero |
| 3 | 2 Luz Mala + 2 Pombero |
| 4 | 2 Luz Mala + 2 Pombero + 1 Lobizón |
| 5 | 3 Luz Mala + 2 Pombero + 2 Lobizón |

**Puntaje final:** suma de recompensas obtenidas + bonus por tranquilidad restante.

> Los valores numéricos son de balance de juego y se ajustan probando. Lo que se apoya en la tradición es qué remedio funciona contra cada criatura.

---

## Arquitectura (MVC)

```
paquete raíz
├── modelo          Lógica del juego, sin Swing ni SQL
├── vista           Pantallas Swing, sin lógica de juego
├── controlador     Conecta modelo y vista, maneja el flujo de la partida
├── persistencia    Acceso a SQLite (DAOs)
└── excepciones     Excepciones propias
```

### Modelo
- `Criatura` (abstracta) → `LuzMala`, `Pombero`, `Lobizon`
- `TipoRemedio` (enum con nombre, poder, costo y efecto de cada remedio)
- `Remedio` (clase concreta: guarda su `TipoRemedio` y si ya se usó en la oleada)
- `Oleada`, `Partida`

El **polimorfismo** vive en el multiplicador: cada subclase de `Criatura` sobreescribe cómo le afecta cada remedio.

### Vista
- Ventana principal
- Pantalla de juego (criaturas de la oleada, remedios, datos de la partida)
- Pantalla de ranking
- Pantalla de fichas de leyendas

### Controlador
- Controlador de juego: compra de remedios, asignación, resolución de oleadas, fin de partida
- Controlador de persistencia: guardar partidas y leer ranking y fichas

### Persistencia
- `ConexionSQLite`: abre la conexión, activa las claves foráneas (`PRAGMA foreign_keys = ON`) y crea las tablas si no existen
- `JugadorDAO`, `PartidaDAO`, `LeyendaDAO`

### Excepciones propias
- `FondosInsuficientesException`: comprar un remedio sin pesos suficientes
- `RemedioYaUtilizadoException`: reasignar un remedio ya usado en la oleada
- `PersistenciaException`: envuelve las `SQLException` para que la vista nunca vea errores de JDBC

---

## Base de datos

```
jugadores (1) ────< partidas >──── (0..1) leyendas
```

### `jugadores`
| Columna | Tipo | Notas |
|---|---|---|
| id | INTEGER | Clave primaria |
| nombre | TEXT | No nulo, único |

### `leyendas`
| Columna | Tipo | Notas |
|---|---|---|
| id | INTEGER | Clave primaria |
| nombre | TEXT | No nulo, único |
| region | TEXT | Dónde se cuenta la leyenda |
| descripcion | TEXT | Ficha mostrada en el juego |
| como_se_enfrenta | TEXT | Qué dice la tradición |
| fuente | TEXT | Origen del dato |

Se carga con las tres leyendas la primera vez que corre el programa.

### `partidas`
| Columna | Tipo | Notas |
|---|---|---|
| id | INTEGER | Clave primaria |
| jugador_id | INTEGER | No nulo, FK → `jugadores.id` |
| puntaje | INTEGER | No nulo |
| oleada_alcanzada | INTEGER | No nulo |
| victoria | INTEGER | 0 o 1 |
| derrotado_por_id | INTEGER | Puede ser nulo, FK → `leyendas.id` |
| fecha | TEXT | Formato `YYYY-MM-DD HH:MM` |

**Decisión de diseño:** las estadísticas de las criaturas (vida, daño, multiplicadores) viven en el código Java para aprovechar el polimorfismo. La base guarda solo el contenido informativo.

---

## Etapas de desarrollo

- [x] Proyecto Maven creado en NetBeans
- [x] Dependencia `sqlite-jdbc` agregada
- [x] Repositorio en GitHub
- [ ] Paquetes creados
- [ ] Prueba de conexión a SQLite
- [ ] Modelo: `Criatura` y subclases
- [ ] Modelo: `TipoRemedio` (enum) y `Remedio`
- [ ] Lógica de combate probada por consola
- [ ] `Partida` y `Oleada`
- [ ] Ventana básica en Swing
- [ ] Pantalla de juego conectada a la lógica
- [ ] Ciclo completo de 5 oleadas con victoria y derrota
- [ ] Excepciones integradas
- [ ] Tablas SQLite y DAOs
- [ ] Guardado de partida y pantalla de ranking
- [ ] Fichas de leyendas en el juego
- [ ] Arte pixel art
- [ ] Pruebas de punta a punta

---

## Fuentes del folklore

Las leyendas varían según la región y la fuente. Este proyecto toma como referencia:

- Luz Mala: [Billiken](https://billiken.lat/entretenimiento/como-y-por-que-aparece-la-luz-mala-uno-de-los-mitos-argentinos-mas-famosos/)
- Pombero: [Billiken](https://billiken.lat/interesante/el-pombero-el-duende-mitologico-que-no-te-querras-cruzar-nunca/)
- Lobizón: [Billiken](https://billiken.lat/interesante/quien-o-que-es-el-lobizon-uno-de-los-mas-famosos-mitos-argentinos/)

Para reforzar las fichas conviene sumar alguna fuente académica o de recopilación folklórica.