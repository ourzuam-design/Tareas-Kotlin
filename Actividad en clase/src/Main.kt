//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
data class Producto(
    val nombre: String,
    var precio: Double,
    var cantidad: Int,
    val cantidadMinima: Int,
) {

    fun estaEnStockCritico(): Boolean = cantidad <= cantidadMinima
    fun calcularValorTotal(): Double = precio * cantidad
    fun abastecer(unidades: Int) {
        if (unidades > 0) {
            cantidad += unidades
            println("Se han añadido $unidades unidades de $nombre.")
        }
    }

    fun vender(unidades: Int): Boolean {
        return if (unidades <= cantidad) {
            cantidad -= unidades
            true
        } else {
            false
        }
    }
}

class Inventario {
    private val listaProductos = mutableListOf<Producto>()

    fun agregarProducto(producto: Producto) {
        listaProductos.add(producto)
    }
    fun productosEnAlerta(): List<Producto> {
        return listaProductos.filter { it.estaEnStockCritico() }
    }
    fun valorTotalInventario(): Double {
        return listaProductos.sumOf { it.calcularValorTotal() }
    }

    fun reportar() {
        println("\n  Reporte del inventario")
        listaProductos.forEach { p ->
            val alerta = if (p.estaEnStockCritico()) "[Stock bajo]" else "[Stock aceptable]"
            println("${p.nombre.padEnd(15)} | Stock: ${p.cantidad} | Valor: $${p.calcularValorTotal()} $alerta")
        }
        println("Valor total del inventario: $${valorTotalInventario()}")
    }
}



fun main() {
    val miTienda = Inventario()
    val p1 = Producto("Laptop", 1200.0, 5, 2)
    val p2 = Producto("Mouse", 25.0, 10, 3)
    val p3 = Producto("Teclado.", 80.0, 2, 5)

    miTienda.agregarProducto(p1)
    miTienda.agregarProducto(p2)
    miTienda.agregarProducto(p3)

    println("Vendiendo 4 laptops: ${if (p1.vender(4)) "Éxito" else "Sin stock"}")
    p2.abastecer(5)
    miTienda.reportar()

    println("Productos que requieren reabastecimiento urgente:")
    miTienda.productosEnAlerta().forEach { println("- ${it.nombre} (Quedan ${it.cantidad})") }
}