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

SpeedFast es una empresa de reparto a domicilio que ofrece tres tipos de servicio, cada uno con criterios distintos para la asignación de repartidor:

- **Comida** (restaurantes): requiere repartidor con mochila térmica.
- **Encomiendas** (documentos o paquetes): requiere validación de peso y embalaje.
- **Compras Express** (supermercado o farmacia): debe asignarse al repartidor más cercano con disponibilidad inmediata.

El sistema implementado en Java modela esta lógica mediante un método `asignarRepartidor()` que se comporta de manera diferenciada según el tipo de pedido. El proyecto aplica principios de Programación Orientada a Objetos como encapsulamiento, **herencia**, **abstracción**, **sobrescritura de métodos (overriding)**, **sobrecarga de métodos (overloading)** e **interfaces**, delegando la lógica específica de cada tipo de pedido a sus respectivas clases hijas y desacoplando las operaciones de despacho, cancelación e historial mediante contratos independientes.

`Pedido` es una **clase abstracta**: no puede instanciarse directamente y define `calcularTiempoEntrega()` como método abstracto, obligando a cada subclase a implementar su propia fórmula de tiempo estimado según el tipo de servicio. Además, `Pedido` implementa las interfaces `Despachable` y `Cancelable`, y la clase `ControladorDeEnvios` implementa `Rastreable`, gestionando el historial de entregas del sistema.

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
│   └── ControladorDeEnvios.java # Implementa Rastreable; orquesta despacharPedido()/cancelarPedido() y mantiene el historial de entregas (ArrayList<Pedido>)
└── Main.java                    # Punto de entrada; simula asignación manual y automática, cálculo de tiempo, despacho, cancelación y visualización del historial
```

### Jerarquía de herencia — Pedido

`Pedido` es la clase base (abstracta) y contiene los atributos comunes a todo pedido (`idPedido` generado con `UUID`, `direccionEntrega`, `tipoPedido`, `distanciaKm`, `repartidorAsignado`, `estado`). Las tres subclases extienden estos atributos con información específica de cada tipo de servicio, implementan `getNombreTipo()` y `calcularTiempoEntrega()`, y sobrescriben `asignarRepartidor()` (en sus dos versiones sobrecargadas) para aplicar la validación correspondiente antes de asignar al repartidor.

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

`ControladorDeEnvios` centraliza las operaciones funcionales sobre los pedidos:

- `despacharPedido(Pedido)`: invoca `despachar()` sobre el pedido y, si su estado queda en `"Despachado"`, lo agrega al historial.
- `cancelarPedido(Pedido)`: invoca `cancelar()` sobre el pedido.
- `verHistorial()`: recorre un `ArrayList<Pedido>` interno e imprime cada entrega realizada junto al repartidor que la efectuó.

### Punto de entrada

`Main.java` crea una instancia de cada tipo de pedido (`PedidoComida`, `PedidoEncomienda`, `PedidoExpress`) y simula el flujo completo del sistema: muestra el resumen (`mostrarResumen()`), asigna repartidor de forma **manual** (Comida y Encomienda, con nombre) y **automática** (Express, sin parámetros), calcula el tiempo estimado (`calcularTiempoEntrega()`), despacha los pedidos de Comida y Encomienda a través del `ControladorDeEnvios`, cancela el pedido Express y finalmente muestra el historial de entregas realizadas.

---

## Instrucciones para ejecutar el proyecto

### Opción 1 — Desde IntelliJ IDEA

1. Clona el repositorio:
```bash
git clone https://github.com/yerkodigo/speedfast.git
```
2. Abre el proyecto en IntelliJ IDEA.
3. Ejecuta la clase `Main.java` ubicada en el paquete `com.speedfast`.

### Opción 2 — Con Maven

```bash
mvn compile exec:java -Dexec.mainClass="com.speedfast.Main"
```

---

**Repositorio GitHub:** https://github.com/yerkodigo/speedfast

---

© Duoc UC | Escuela de Informática y Telecomunicaciones | Desarrollo Orientado a Objetos 2 - Semana 03
