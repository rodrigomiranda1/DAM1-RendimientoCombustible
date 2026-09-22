package com.example.examenpractica

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.examenpractica.databinding.ActivityResultadoBinding
import java.util.Locale


class ResultadoActivity : AppCompatActivity() {

private lateinit var binding: ActivityResultadoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nombre = intent.getStringExtra("EXTRA_NOMBRE") ?: ""
        val resultado = intent.getDoubleExtra("EXTRA_RESULTADO", 0.0)
        val clasificacion = intent.getStringExtra("EXTRA_CLASIFICACION") ?: ""

        binding.tvResultadoNombre.text = getString(R.string.prefijo_resultado_nombre, nombre)
        binding.tvValorRendimiento.text = String.format(Locale.getDefault(), getString(R.string.formato_rendimiento), resultado)
        binding.tvClasificacion.text = clasificacion

        binding.btnRecalcular.setOnClickListener {
            finish()
        }
    }
}