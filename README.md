![Duoc UC](https://www.duoc.cl/wp-content/uploads/2022/09/logo-0.png)
# SpeedFast

---

## Autor del proyecto
- **Nombre completo:** Yerko Cortes Baeza
- **Sección:** II_003A
- **Carrera:** Analista Programador Computacional
- **Sede:** Online

---

## Descripción general del sistema

SpeedFast es una empresa de reparto a domicilio. El proyecto modela dos aspectos del negocio:

- La **asignación y despacho de pedidos** según su tipo de servicio (Comida, Encomienda, Compra Express), aplicando principios de Programación Orientada a Objetos: encapsulamiento, **herencia**, **abstracción**, **sobrescritura de métodos (overriding)**, **sobrecarga de métodos (overloading)** e **interfaces**.
- La **coordinación concurrente de entregas**: repartidores en su propio hilo despachan pedidos en paralelo, aplicando **sincronización** (`synchronized`) para evitar condiciones de carrera.
- Una **interfaz gráfica de escritorio (Java Swing)** que permite registrar pedidos, visualizarlos en una tabla y asignar un repartidor para simular el inicio de la entrega, todo compartiendo los datos a través de un mismo `ControladorDeEnvios`.

El punto de entrada del proyecto es `Main.java` (paquete `com.speedfast.main`), que abre la `VentanaPrincipal` de la interfaz gráfica.

---

## Paquetes y clases implementadas

```plaintext
src/main/java/com/speedfast/
├── model/
│   ├── Pedido.java              # Clase abstracta base; implementa Despachable y Cancelable; id (UUID), direccionEntrega, distanciaKm, repartidorAsignado y estado; define asignarRepartidor() sobrecargado, mostrarResumen(), despachar(), cancelar() y el método abstracto calcularTiempoEntrega()
│   ├── PedidoComida.java        # Subclase; agrega mochilaTermica; implementa calcularTiempoEntrega() y sobreescribe asignarRepartidor() validando la mochila térmica
│   ├── PedidoEncomienda.java    # Subclase; agrega peso; implementa calcularTiempoEntrega() y sobreescribe asignarRepartidor() validando el peso máximo
│   └── PedidoExpress.java       # Subclase (Compra Express); implementa calcularTiempoEntrega() y sobreescribe asignarRepartidor() con asignación inmediata al repartidor más cercano
├── interfaces/
│   ├── Despachable.java         # Contrato con el método despachar()
│   ├── Cancelable.java          # Contrato con el método cancelar()
│   └── Rastreable.java          # Contrato con el método verHistorial()
├── controlador/
│   └── ControladorDeEnvios.java # Implementa Rastreable; guarda la lista de pedidos registrados y el historial de entregas; expone agregarPedido(), getPedidos(), getPedidosPendientes(), despacharPedido() (sincronizado) y cancelarPedido()
├── vista/
│   ├── VentanaPrincipal.java        # JFrame inicial; crea el ControladorDeEnvios compartido y, con botones (GridLayout), abre las otras tres ventanas pasándoles ese mismo controlador
│   ├── VentanaRegistroPedido.java   # JFrame con formulario (ID, Dirección, Tipo, Distancia y campo extra según el tipo); valida los datos, crea el Pedido correspondiente y lo agrega al controlador
│   ├── VentanaListaPedidos.java     # JFrame con JTable + DefaultTableModel; botón Refrescar que vuelve a leer los pedidos desde el controlador
│   └── VentanaAsignarRepartidor.java # JFrame que lista los pedidos pendientes en un JComboBox, recibe el nombre del repartidor y lanza un hilo Repartidor para simular el despacho
├── concurrencia/
│   ├── Repartidor.java              # Implementa Runnable; nombre y lista de pedidos asignados; recorre y entrega sus pedidos de forma secuencial simulando tiempos con Thread.sleep(); usado tanto por la simulación de consola como por VentanaAsignarRepartidor
│   └── sincronizacion/
│       ├── Pedido.java          # id (int), direccionEntrega (String), estado (EstadoPedido); getters/setters, setEstado(String) y setEstado(EstadoPedido), toString()
│       ├── EstadoPedido.java    # Enum: PENDIENTE, EN_REPARTO, ENTREGADO
│       ├── ZonaDeCarga.java     # Recurso compartido; lista interna de pedidos pendientes protegida con métodos synchronized: agregarPedido(Pedido) y retirarPedido()
│       └── Repartidor.java      # Implementa Runnable; nombre y referencia a la ZonaDeCarga compartida; retira pedidos de a uno, simula la entrega con Thread.sleep() y actualiza su estado
└── main/
    └── Main.java                    # Punto de entrada; SwingUtilities.invokeLater(VentanaPrincipal::new) abre la interfaz gráfica
```

### Jerarquía de herencia — Pedido

`Pedido` (paquete `model`) es la clase base abstracta y contiene los atributos comunes a todo pedido (`idPedido` generado con `UUID`, `direccionEntrega`, `tipoPedido`, `distanciaKm`, `repartidorAsignado`, `estado`). Las tres subclases extienden estos atributos con información específica de cada tipo de servicio, implementan `getNombreTipo()` y `calcularTiempoEntrega()`, y sobrescriben `asignarRepartidor()` (en sus dos versiones sobrecargadas) para aplicar la validación correspondiente antes de asignar al repartidor.

```
Pedido (abstracta)
 ├── PedidoComida       → mochilaTermica: boolean
 ├── PedidoEncomienda   → peso: float
 └── PedidoExpress      (sin atributos adicionales)
```

### Abstracción — `calcularTiempoEntrega()`

`Pedido` declara `calcularTiempoEntrega()` como método abstracto, sin implementación propia, forzando a cada subclase a definir su propia fórmula de tiempo estimado en función de `distanciaKm`:

- **PedidoComida**: `15 + (2 * distanciaKm)` minutos.
- **PedidoEncomienda**: `20 + Math.round(1.5 * distanciaKm)` minutos.
- **PedidoExpress**: `10` minutos base, `+5` adicionales si `distanciaKm > 5`.

Además, `Pedido` provee `mostrarResumen()` (dirección y distancia) como comportamiento común heredado sin necesidad de sobrescritura.

### Sobrecarga y sobrescritura de `asignarRepartidor()`

`Pedido` define dos versiones sobrecargadas de `asignarRepartidor()`: una sin parámetros y otra que recibe el nombre del repartidor. Cada subclase sobrescribe ambas versiones para mover la lógica de validación al lugar correcto según el tipo de pedido:

- **PedidoComida**: valida que exista mochila térmica antes de confirmar la asignación.
- **PedidoEncomienda**: valida que el peso sea menor a 60 kg antes de confirmar la asignación.
- **PedidoExpress** (Compra Express): asigna de inmediato al repartidor más cercano con disponibilidad.

### Interfaces — desacoplamiento de responsabilidades

Se definen tres interfaces en el paquete `interfaces/`, cada una con un único método:

- **`Despachable`** → `despachar()`
- **`Cancelable`** → `cancelar()`
- **`Rastreable`** → `verHistorial()`

`Pedido` implementa `Despachable` y `Cancelable`, ya que despachar y cancelar son acciones propias de cada pedido individual. `ControladorDeEnvios` implementa `Rastreable`, porque el historial es una responsabilidad del sistema (observa el conjunto de pedidos), no de un pedido en particular. Esta separación evita que `Pedido` dependa de la lógica de seguimiento global y permite agregar nuevos tipos de pedido sin modificar el controlador.

### ControladorDeEnvios

`ControladorDeEnvios` centraliza las operaciones sobre los pedidos y es el punto de datos compartido entre las ventanas:

- `agregarPedido(Pedido)` / `getPedidos()` / `getPedidosPendientes()`: manejan la lista de pedidos registrados.
- `despacharPedido(Pedido)`: despacha el pedido y lo agrega al historial (método `synchronized` por seguridad ante varios repartidores a la vez).
- `cancelarPedido(Pedido)` y `verHistorial()`: cancelan un pedido y muestran el historial de entregas.

### Coordinación concurrente — `concurrencia/sincronizacion`

Este paquete modela la coordinación de entregas mediante una **zona de carga compartida**: los pedidos llegan a ella y múltiples repartidores, cada uno en su propio hilo, los retiran en paralelo.

- **`Pedido`**: clase simple con `id`, `direccionEntrega` y `estado` (tipado con el enum `EstadoPedido`). El uso de `enum` en lugar de `String` evita errores de tipeo y mejora la legibilidad. Expone `setEstado(String nuevoEstado)` (convierte el texto al valor del enum mediante `EstadoPedido.valueOf(...)`) además de una sobrecarga `setEstado(EstadoPedido nuevoEstado)` para uso interno.
- **`EstadoPedido`**: enum con los estados `PENDIENTE`, `EN_REPARTO` y `ENTREGADO`.
- **`ZonaDeCarga`**: recurso compartido que almacena los pedidos pendientes en una `List<Pedido>` interna. Sus dos operaciones (`agregarPedido` y `retirarPedido`) son `synchronized`, por lo que solo un hilo a la vez puede modificar la lista, garantizando que `retirarPedido()` nunca entregue el mismo pedido a dos repartidores distintos.
- **`Repartidor`**: implementa `Runnable`. En su `run()`, retira pedidos de la `ZonaDeCarga` de a uno mientras existan, cambia el estado a `EN_REPARTO`, simula el tiempo de entrega con `Thread.sleep()` y finalmente marca el pedido como `ENTREGADO`. Cuando la zona de carga queda vacía (`retirarPedido()` retorna `null`), el repartidor finaliza su ejecución de forma segura.

### Interfaz gráfica — paquete `vista`

Interfaz Swing que comparte un mismo `ControladorDeEnvios` entre sus tres ventanas:

- **`VentanaPrincipal`**: ventana inicial con botones para abrir las otras tres.
- **`VentanaRegistroPedido`**: formulario (ID, Dirección, Tipo, Distancia) que valida los datos y agrega el pedido al controlador.
- **`VentanaListaPedidos`**: `JTable` con los pedidos registrados; botón *Refrescar* para actualizarla.
- **`VentanaAsignarRepartidor`**: asigna un repartidor a un pedido pendiente y simula la entrega en un `Thread` aparte.

### Punto de entrada

`Main.java` (paquete `com.speedfast.main`) abre la interfaz con `SwingUtilities.invokeLater(VentanaPrincipal::new)`.

---

## Instrucciones para ejecutar el proyecto

### Opción 1 — Desde IntelliJ IDEA

1. Clona el repositorio:
```bash
git clone https://github.com/yerkodigo/speedfast.git
```
2. Abre el proyecto en IntelliJ IDEA.
3. Ejecuta la clase `Main.java` ubicada en el paquete `com.speedfast.main`. Se abrirá la `VentanaPrincipal` de la interfaz gráfica.

### Opción 2 — Con Maven

```bash
mvn compile exec:java -Dexec.mainClass="com.speedfast.main.Main"
```

---

**Repositorio GitHub:** https://github.com/yerkodigo/speedfast

---

© Duoc UC | Escuela de Informática y Telecomunicaciones | Desarrollo Orientado a Objetos 2
