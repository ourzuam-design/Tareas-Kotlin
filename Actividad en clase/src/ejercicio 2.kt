class Estudiante(val nombre: String) {
    val notas: MutableList<Double> = mutableListOf()

    fun agregarNota(nota: Double) {
        if (nota in 0.0..100.0) {
            notas.add(nota)
        } else {
            println("Error: La nota $nota no es válida para $nombre.")
        }
    }

    fun promedio(): Double = if (notas.isEmpty()) 0.0 else notas.average()

    fun notaMasAlta(): Double = notas.maxOrNull() ?: 0.0

    fun notaMasBaja(): Double = notas.minOrNull() ?: 0.0

    fun obtenerNivel(): String {
        val p = promedio()
        return when {
            p >= 90 -> "Excelente"
            p >= 60 -> "Aprobado"
            else -> "Reprobado"
        }
    }
}


class Curso(val nombre: String) {
    val estudiantes: MutableList<Estudiante> = mutableListOf()

    fun agregarEstudiante(estudiante: Estudiante) = estudiantes.add(estudiante)

    fun promedioGeneral(): Double = if (estudiantes.isEmpty()) 0.0 else estudiantes.sumOf { it.promedio() } / estudiantes.size

    fun estudiantesAprobados(): List<Estudiante> = estudiantes.filter { it.promedio() >= 60 }

    fun estudiantesReprobados(): List<Estudiante> = estudiantes.filter { it.promedio() < 60 }

    fun mejorEstudiante(): Estudiante? = estudiantes.maxByOrNull { it.promedio() }

    fun generarReporte() {
        println("Reporte del curso: ${nombre.uppercase()}")

        estudiantes.forEach { e ->
            val stats = "Promedio: ${"%.2f".format(e.promedio())} | Máx: ${e.notaMasAlta()} | Mín: ${e.notaMasBaja()}"
            println("${e.nombre.padEnd(12)} -> $stats | Nivel: ${e.obtenerNivel()}")
        }

        println("Estadisticas generales")
        println("Promedio General del Curso: ${"%.2f".format(promedioGeneral())}")
        println("Total Aprobados: ${estudiantesAprobados().size}")
        println("Total Reprobados: ${estudiantesReprobados().size}")

        mejorEstudiante()?.let {
            println("Mejor Estudiante: ${it.nombre} con ${"%.2f".format(it.promedio())}")
        }
        println("\n")
    }
}

fun main() {
    val cursoKotlin = Curso("Programación I")


    val e1 = Estudiante("Carlos López").apply {
        agregarNota(95.0); agregarNota(88.0); agregarNota(92.0)
    }
    val e2 = Estudiante("María García").apply {
        agregarNota(65.0); agregarNota(70.0); agregarNota(55.0)
    }
    val e3 = Estudiante("Ana Martínez").apply {
        agregarNota(40.0); agregarNota(30.0); agregarNota(50.0)
    }
    val e4 = Estudiante("Juan Pérez").apply {
        agregarNota(100.0); agregarNota(98.0); agregarNota(100.0)
    }

    cursoKotlin.agregarEstudiante(e1)
    cursoKotlin.agregarEstudiante(e2)
    cursoKotlin.agregarEstudiante(e3)
    cursoKotlin.agregarEstudiante(e4)

    cursoKotlin.generarReporte()
}