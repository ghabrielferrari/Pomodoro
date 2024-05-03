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

        setupSlider()

        binding.btnStart.setOnClickListener {
            val selectedTime = slider.value.toInt()
            startTimer(selectedTime)
        }
    }

    private fun setupSlider() {
        slider = binding.slider

        slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {}
            override fun onStopTrackingTouch(slider: Slider) {}
        })

        slider.addOnChangeListener { _, _, _ ->
            // Responds to changes in slider value
        }
    }

    private fun startTimer(selectedTime: Int) {
        val intent = Intent(this@MainActivity, ClockActivity::class.java)
        intent.putExtra("selected_time", selectedTime)
        startActivity(intent)
    }
}