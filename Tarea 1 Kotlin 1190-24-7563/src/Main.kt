
// Ejercicio 1 calcular indice de masa corporal


fun ejercicio1() {
    println("       Ejercicio 1: IMC      ")

    val peso   = 80.0
    val altura = 1.80

    val imc = peso / (altura * altura)

    val categoria = when {
        imc < 18.5  -> "Bajo peso"
        imc < 25.0  -> "Normal"
        imc < 30.0  -> "Sobrepeso"
        else        -> "Obesidad"
    }

    println("Peso:    $peso kg")
    println("Altura:  $altura m")
    println("IMC:     ${String.format("%.2f", imc)}")
    println("Categoría: $categoria")
    println()
}


// Ejercicio 2 Conversor de temperaturas


fun celsiusAFahrenheit(c: Double): Double = c * 9.0 / 5.0 + 32.0
fun fahrenheitACelsius(f: Double): Double = (f - 32.0) * 5.0 / 9.0
fun celsiusAKelvin(c: Double): Double     = c + 273.15
fun kelvinACelsius(k: Double): Double     = k - 273.15
fun fahrenheitAKelvin(f: Double): Double  = celsiusAKelvin(fahrenheitACelsius(f))
fun kelvinAFahrenheit(k: Double): Double  = celsiusAFahrenheit(kelvinACelsius(k))

fun convertir(valor: Double, desde: String, hasta: String): Double =
    when ("$desde->$hasta") {
        "C->F" -> celsiusAFahrenheit(valor)
        "F->C" -> fahrenheitACelsius(valor)
        "C->K" -> celsiusAKelvin(valor)
        "K->C" -> kelvinACelsius(valor)
        "F->K" -> fahrenheitAKelvin(valor)
        "K->F" -> kelvinAFahrenheit(valor)
        else   -> valor
    }

fun ejercicio2() {
    println("      Ejercicio 2 Conversor de temperatura      ")

    println("100°C  → Fahrenheit : ${convertir(100.0, "C", "F")}°F   (esperado: 212.0)")
    println("0°C    → Kelvin     : ${convertir(0.0,   "C", "K")}K    (esperado: 273.15)")
    println("32°F   → Celsius    : ${convertir(32.0,  "F", "C")}°C   (esperado: 0.0)")
    println()
}



// Ejercicio 3 : Modelo de estudiantes con Data Class

data class Estudiante(
    val nombre: String,
    val grado: Int,
    val notas: List<Double>
) {
    val promedio: Double get() = if (notas.isEmpty()) 0.0 else notas.average()
}

fun ejercicio3() {
    println("  Ejercicio 3 : Modelo de estudiantes con Data Class  ")

    val estudiantes = listOf(
        Estudiante("Diego Alejandro",    10, listOf(85.0, 90.0, 78.0, 90.0)),
        Estudiante("Juan Pérez",     9, listOf(60.0, 62.0, 70.0, 48.0)),
        Estudiante("Valeria Cristina",   11, listOf(95.0, 88.0, 91.0, 97.0)),
        Estudiante("Carlos Ruiz",   10, listOf(72.0, 68.0, 75.0, 80.0)),
        Estudiante("Sofía Mendoza", 12, listOf(40.0, 55.0, 60.0, 50.0)),
        Estudiante("Andrés Felipe",   9, listOf(88.0, 76.0, 82.0, 90.0))
    )

    val aprobados = estudiantes
        .filter { it.promedio >= 70.0 }
        .sortedByDescending { it.promedio }

    println("Top 3 estudiantes aprobados:")
    aprobados.take(3).forEachIndexed { i, est ->
        println("  ${i + 1}. ${est.nombre}  –  promedio: ${String.format("%.1f", est.promedio)}")
    }
    println()
}



// Ejercicio 4 Directorio de contactos

data class Contacto(
    val nombre: String,
    val telefono: String?,
    val email: String?
)

val directorio: Map<String, Contacto?> = mapOf(
    "C001" to Contacto("Ana García",  "+502 1234-5678", "ana@email.com"),
    "C002" to Contacto("Luis Pérez",  null,             "luis@email.com"),
    "C003" to Contacto("María López", "+502 8765-4321", null),
    "C004" to null,                                           // contacto inexistente
    "C005" to Contacto("Pedro Castro",null,             null)
)

fun buscarContacto(id: String): Contacto? = directorio[id]

fun ejercicio4() {
    println("   Ejercicio 4 Directorio de contactos   ")

    val ids = listOf("C001", "C002", "C003", "C004", "C005", "C999")

    for (id in ids) {
        println("── Buscando id: $id")
        val contacto = buscarContacto(id)

        contacto?.let { c ->
            println("   Nombre   : ${c.nombre}")
            println("   Teléfono : ${c.telefono ?: "No disponible"}")
            println("   Email    : ${c.email    ?: "No disponible"}")
        } ?: println("   Contacto no encontrado o registro nulo")
    }
    println()
}



// Ejercicio 5 Modelo de inventario con operaciones CRUD

data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Double,
    var stock: Int
)

sealed class OpInventario {
    data class Exito(val datos: Any)      : OpInventario()
    data class Error(val mensaje: String) : OpInventario()
    object SinStock                       : OpInventario()
}

val inventario: MutableList<Producto> = mutableListOf()

fun agregarProducto(producto: Producto): OpInventario {
    return if (inventario.any { it.id == producto.id }) {
        OpInventario.Error("Ya existe un producto con id ${producto.id}")
    } else {
        inventario.add(producto)
        OpInventario.Exito(producto)
    }
}

fun buscarPorId(id: Int): Producto? = inventario.find { it.id == id }

fun actualizarStock(id: Int, cantidad: Int): OpInventario {
    val producto = buscarPorId(id)
        ?: return OpInventario.Error("Producto con id $id no encontrado")

    return if (producto.stock + cantidad < 0) {
        OpInventario.SinStock
    } else {
        producto.stock += cantidad
        OpInventario.Exito(producto)
    }
}

fun listarDisponibles(): List<Producto> = inventario.filter { it.stock > 0 }

fun List<Producto>.valorTotal(): Double =
    sumOf { it.precio * it.stock }

fun List<Producto>.buscarPorNombre(query: String): List<Producto> =
    filter { it.nombre.contains(query, ignoreCase = true) }

fun ejercicio5() {
    println(" Ejercicio 5 Modelo de inventario con operaciones CRUD ")

    val productos = listOf(
        Producto(1, "Laptop Pro",      15_999.99, 10),
        Producto(2, "Mouse",  299.99, 50),
        Producto(3, "Teclado Mecánico",   899.99,  0),  // sin stock
        Producto(4, "Monitor 27\"",      4_499.99, 5),
        Producto(5, "Auriculares BT",     749.99, 20)
    )

    println("   Agregando productos   ")
    productos.forEach { p ->
        when (val op = agregarProducto(p)) {
            is OpInventario.Exito   -> println("  ✓ Agregado: ${(op.datos as Producto).nombre}")
            is OpInventario.Error   -> println("  ✗ Error: ${op.mensaje}")
            is OpInventario.SinStock -> println("  ✗ Sin stock")
        }
    }

    when (val op = agregarProducto(Producto(1, "Duplicado", 0.0, 0))) {
        is OpInventario.Error -> println("  ✗ Error esperado: ${op.mensaje}")
        else -> {}
    }

    println("\n   Búsqueda por id   ")
    println("  id=3 → ${buscarPorId(3)?.nombre ?: "No encontrado"}")
    println("  id=9 → ${buscarPorId(9)?.nombre ?: "No encontrado"}")


    println("\n   Actualizar stock   ")
    when (val op = actualizarStock(2, -5)) {
        is OpInventario.Exito   -> println("  ✓ Stock actualizado: ${(op.datos as Producto).nombre} → stock ${op.datos.stock}")
        is OpInventario.Error   -> println("  ✗ ${op.mensaje}")
        is OpInventario.SinStock -> println("  ✗ Sin stock suficiente")
    }
    when (val op = actualizarStock(3, -1)) {   // teclado sin stock
        is OpInventario.SinStock -> println("  ✗ Sin stock suficiente (esperado para Teclado)")
        else -> {}
    }

    println("\n   Búsqueda parcial por nombre ('pro')   ")
    inventario.buscarPorNombre("pro").forEach { println("  → ${it.nombre}") }

    println("\n   Productos disponibles   ")
    listarDisponibles().forEach { p ->
        println("  ${p.nombre.padEnd(22)} precio: ${String.format("%8.2f", p.precio)}  stock: ${p.stock}")
    }

    println("\n   Valor total del inventario   ")
    println("  Total: Q${String.format("%.2f", inventario.valorTotal())}")
    println()
}

fun main() {
    println("        MENÚ DE EJERCICIOS KOTLIN       ")
    println("")
    println("1 → IMC")
    println("2 → Conversor de Temperaturas")
    println("3 → Estudiantes")
    println("4 → Contactos")
    println("5 → Inventario")
    println("0 → Ejecutar todos")
    print("Elige un ejercicio: ")

    val opcion = readLine()?.trim()?.toIntOrNull()

    when (opcion) {
        1    -> ejercicio1()
        2    -> ejercicio2()
        3    -> ejercicio3()
        4    -> ejercicio4()
        5    -> ejercicio5()
        0    -> { ejercicio1(); ejercicio2(); ejercicio3(); ejercicio4(); ejercicio5() }
        else -> println("Opción inválida. Elige un número del 0 al 5.")
    }
}