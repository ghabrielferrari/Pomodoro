package gabriel.pomodoro

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.slider.Slider
import gabriel.pomodoro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var slider: Slider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        sliderConfig()

        binding.btnStart.setOnClickListener {
            val selectedTime = slider.value.toInt() // Obtém o tempo selecionado
            val intent = Intent(this@MainActivity, ClockActivity::class.java)
            intent.putExtra("selected_time", selectedTime) // Passa o tempo selecionado como um extra
            startActivity(intent) // Inicia a activity do cronômetro
        }
    }

    private fun sliderConfig() {
        slider = binding.slider

        slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
                // Não é necessário implementar nada aqui
            }

            override fun onStopTrackingTouch(slider: Slider) {
                // Não é necessário implementar nada aqui
            }
        })

        slider.addOnChangeListener { slider, value, fromUser ->
            // Responde a mudanças no valor do slider
            // Você pode atualizar dinamicamente a exibição do tempo selecionado aqui
        }
    }
}