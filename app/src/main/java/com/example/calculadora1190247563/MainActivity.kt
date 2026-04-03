package com.example.calculadora1190247563

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadora1190247563.ui.theme.Calculadora1190247563Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Calculadora1190247563Theme {
                CalculadosApp()
            }
        }
    }
}

@Composable
fun CalculadosApp() {
    var pantalla by remember { mutableStateOf("0") }
    var numero1 by remember { mutableStateOf<Double?>(null) }
    var operacion by remember { mutableStateOf<String?>(null) }
    var nuevoNumero by remember { mutableStateOf(false) }

    fun presionarNumero(numero: String) {
        pantalla = if (pantalla == "0" || nuevoNumero) {
            nuevoNumero = false
            numero
        } else {
            pantalla + numero
        }
    }

    fun presionarOperador(op: String) {
        numero1 = pantalla.toDoubleOrNull()
        operacion = op
        nuevoNumero = true
    }

    fun calcularResultado() {
        val numero2 = pantalla.toDoubleOrNull()
        if (numero1 != null && numero2 != null && operacion != null) {
            val resultado = when (operacion) {
                "+" -> numero1!! + numero2
                "-" -> numero1!! - numero2
                "×" -> numero1!! * numero2
                "÷" -> {
                    if (numero2 == 0.0) {
                        pantalla = "Error"
                        numero1 = null
                        operacion = null
                        nuevoNumero = true
                        return
                    } else {
                        numero1!! / numero2
                    }
                }
                else -> return
            }
            pantalla = if (resultado % 1.0 == 0.0) {
                resultado.toInt().toString()
            } else {
                resultado.toString()
            }
            numero1 = null
            operacion = null
            nuevoNumero = true
        }
    }

    fun limpiarTodo() {
        pantalla = "0"
        numero1 = null
        operacion = null
        nuevoNumero = false
    }

    fun agregarPunto() {
        if (nuevoNumero) {
            pantalla = "0."
            nuevoNumero = false
        } else if (!pantalla.contains(".")) {
            pantalla += "."
        }
    }

    fun cambiarSigno() {
        val valor = pantalla.toDoubleOrNull() ?: return
        pantalla = if (valor % 1.0 == 0.0) {
            (valor.toInt() * -1).toString()
        } else {
            (valor * -1).toString()
        }
    }

    fun calcularPorcentaje() {
        val valor = pantalla.toDoubleOrNull() ?: return
        pantalla = (valor / 100).toString()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010))
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.BottomEnd
        ) {
            Text(
                text = pantalla,
                color = Color.White,
                fontSize = 48.sp,
                fontWeight = FontWeight.Light
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                BotonCalculadora("C", Color(0xFFA5A5A5), Color.Black, Modifier.weight(1f)) { limpiarTodo() }
                BotonCalculadora("+/-", Color(0xFFA5A5A5), Color.Black, Modifier.weight(1f)) { cambiarSigno() }
                BotonCalculadora("%", Color(0xFFA5A5A5), Color.Black, Modifier.weight(1f)) { calcularPorcentaje() }
                BotonCalculadora("÷", Color(0xFFFF9F0A), Color.White, Modifier.weight(1f)) { presionarOperador("÷") }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                BotonCalculadora("7", Color(0xFF333333), Color.White, Modifier.weight(1f)) { presionarNumero("7") }
                BotonCalculadora("8", Color(0xFF333333), Color.White, Modifier.weight(1f)) { presionarNumero("8") }
                BotonCalculadora("9", Color(0xFF333333), Color.White, Modifier.weight(1f)) { presionarNumero("9") }
                BotonCalculadora("×", Color(0xFFFF9F0A), Color.White, Modifier.weight(1f)) { presionarOperador("×") }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                BotonCalculadora("4", Color(0xFF333333), Color.White, Modifier.weight(1f)) { presionarNumero("4") }
                BotonCalculadora("5", Color(0xFF333333), Color.White, Modifier.weight(1f)) { presionarNumero("5") }
                BotonCalculadora("6", Color(0xFF333333), Color.White, Modifier.weight(1f)) { presionarNumero("6") }
                BotonCalculadora("-", Color(0xFFFF9F0A), Color.White, Modifier.weight(1f)) { presionarOperador("-") }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                BotonCalculadora("1", Color(0xFF333333), Color.White, Modifier.weight(1f)) { presionarNumero("1") }
                BotonCalculadora("2", Color(0xFF333333), Color.White, Modifier.weight(1f)) { presionarNumero("2") }
                BotonCalculadora("3", Color(0xFF333333), Color.White, Modifier.weight(1f)) { presionarNumero("3") }
                BotonCalculadora("+", Color(0xFFFF9F0A), Color.White, Modifier.weight(1f)) { presionarOperador("+") }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                BotonCalculadora("0", Color(0xFF333333), Color.White, Modifier.weight(1f)) { presionarNumero("0") }
                BotonCalculadora(".", Color(0xFF333333), Color.White, Modifier.weight(1f)) { agregarPunto() }
                BotonCalculadora("=", Color(0xFFFF9F0A), Color.White, Modifier.weight(1f)) { calcularResultado() }
            }
        }
    }
}

@Composable
fun BotonCalculadora(
    texto: String,
    colorFondo: Color,
    colorTexto: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.aspectRatio(1f),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorFondo,
            contentColor = colorTexto
        )
    ) {
        Text(text = texto, fontSize = 24.sp)
    }
}