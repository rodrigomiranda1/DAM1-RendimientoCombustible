package com.example.examenpractica

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import androidx.core.widget.doOnTextChanged
import com.example.examenpractica.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

            configurarOyentes()

    }

    private fun configurarOyentes(){
        binding.etNombre.doOnTextChanged { _, _, _, _ -> binding.tilNombre.error = null }
        binding.etDistancia.doOnTextChanged { _, _, _, _ -> binding.etDistancia.error = null }
        binding.etCombustible.doOnTextChanged { _, _, _, _ -> binding.etCombustible.error = null }

        binding.btnCalcular.setOnClickListener {
            validarYCalcular()
        }
    }

    private fun validarYCalcular(){
        val nombreTexto = binding.etNombre.text?.toString() ?: "" //recibe nom de form
        val nombreLimpio = nombreTexto.trim() //elimina espacios de inicio y final

        var esValido = true

        if(nombreLimpio.isEmpty()){
            binding.tilNombre.error = getString(R.string.error_nombre_vacio) //PREGUNTAR DONDE SE ASGINA EL TIL O ET Y XQ
            esValido = false
        }

        val distancia = parseDoubleInput(binding.etDistancia.text?.toString())
        if(distancia == null ||distancia < 0 || distancia >= 1000000){
            binding.etDistancia.error = getString(R.string.error_numero_invalido)
            esValido = false
        }

        val combustible = parseDoubleInput(binding.etCombustible.text?.toString())
        if(combustible == null || combustible <0 || combustible >= 1000000){
            binding.etCombustible.error = getString(R.string.error_numero_invalido)
            esValido = false
        }
        if(!esValido) return

        val rendimiento = distancia!! / combustible!!

        if(!rendimiento.isFinite()){
            binding.tilCombustible.error = getString(R.string.error_numero_invalido) //porque tilcombustible
            return
        }

        val clasficacion = when{
            rendimiento > 0.0 && rendimiento < 8.0 -> getString(R.string.class_low)
            rendimiento >= 8.0 && rendimiento < 12.0 -> getString(R.string.class_medium)
            rendimiento >= 12 && rendimiento < 16 -> getString(R.string.class_good)
            rendimiento >= 16 -> getString(R.string.class_excellent)
            else -> getString(R.string.class_low)
        }

        val intent = Intent(this, ResultadoActivity::class.java).apply {
            putExtra("EXTRA_NOMBRE", nombreLimpio)
            putExtra("EXTRA_RESULTADO", rendimiento)
            putExtra("EXTRA_CLASIFICACION", clasficacion)
        }
        startActivity(intent)
    }

    private fun parseDoubleInput(input: String?): Double? {
        if (input.isNullOrBlank()) return null
        val normalizado = input.trim().replace(',', '.')
        val valor = normalizado.toDoubleOrNull() ?: return null
        return if(valor.isFinite()) valor else null
    }

}