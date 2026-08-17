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

El sistema implementado en Java modela esta lógica mediante un método `asignarRepartidor()` que se comporta de manera diferenciada según el tipo de pedido. El proyecto aplica principios de Programación Orientada a Objetos como encapsulamiento, **herencia** y **sobrescritura de métodos (overriding)**, además de **sobrecarga de métodos (overloading)**, delegando la lógica específica de cada tipo de pedido a sus respectivas clases hijas.

---

## Paquetes y clases implementadas

```plaintext
src/main/java/com/speedfast/
├── model/
│   ├── Pedido.java              # Clase base; id, direccionEntrega y tipoPedido; define asignarRepartidor() sobrecargado
│   ├── PedidoComida.java        # Subclase; agrega mochilaTermica; sobreescribe asignarRepartidor() validando la mochila térmica
│   ├── PedidoEncomienda.java    # Subclase; agrega peso; sobreescribe asignarRepartidor() validando el peso máximo
│   └── PedidoExpress.java       # Subclase (Compra Express); sobreescribe asignarRepartidor() con asignación inmediata al repartidor más cercano
└── Main.java                    # Punto de entrada; crea instancias de cada tipo de pedido y prueba la asignación de repartidores
```

### Jerarquía de herencia — Pedido

`Pedido` es la clase base y contiene los atributos comunes a todo pedido (`idPedido`, `direccionEntrega`, `tipoPedido`). Las tres subclases extienden estos atributos con información específica de cada tipo de servicio y sobrescriben `asignarRepartidor()` (en sus dos versiones sobrecargadas) para aplicar la validación correspondiente antes de asignar al repartidor.

```
Pedido
 ├── PedidoComida       → mochilaTermica: boolean
 ├── PedidoEncomienda   → peso: float
 └── PedidoExpress      (sin atributos adicionales)
```

### Sobrecarga y sobrescritura de `asignarRepartidor()`

`Pedido` define dos versiones sobrecargadas de `asignarRepartidor()`: una sin parámetros y otra que recibe el nombre del repartidor. Cada subclase sobrescribe ambas versiones para mover la lógica de validación al lugar correcto según el tipo de pedido:

- **PedidoComida**: valida que exista mochila térmica antes de confirmar la asignación.
- **PedidoEncomienda**: valida que el peso sea menor a 60 kg antes de confirmar la asignación.
- **PedidoExpress** (Compra Express): asigna de inmediato al repartidor más cercano con disponibilidad.

### Punto de entrada

`Main.java` crea una instancia de cada tipo de pedido (`PedidoComida`, `PedidoEncomienda`, `PedidoExpress`) y llama a ambas versiones de `asignarRepartidor()` sobre cada una, mostrando por consola el comportamiento polimórfico de cada subclase.

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

© Duoc UC | Escuela de Informática y Telecomunicaciones | Desarrollo Orientado a Objetos 2 - Semana 01
