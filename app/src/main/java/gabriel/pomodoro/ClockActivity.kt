package gabriel.pomodoro

import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import gabriel.pomodoro.R
import gabriel.pomodoro.databinding.ActivityClockBinding

class ClockActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityClockBinding.inflate(layoutInflater)
    }

    private var timeRemaining: Long = 0
    private lateinit var countDownTimer: CountDownTimer
    private var isTimerRunning: Boolean = false
    private lateinit var resumeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val selectedTime = intent.getIntExtra("selected_time", 0)
        timeRemaining = selectedTime * 60000L // Converter minutos para milissegundos

        countDownTimer = object : CountDownTimer(timeRemaining, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemaining = millisUntilFinished
                updateTimerUI()
            }

            override fun onFinish() {
                // O cronômetro chegou ao fim
                isTimerRunning = false
                showResumeButton()
            }
        }

        countDownTimer.start()
        isTimerRunning = true

        binding.btnStop.setOnClickListener {
            showStopConfirmationDialog()
        }
    }

    private fun updateTimerUI() {
        val minutes = (timeRemaining / 60000).toInt()
        val seconds = ((timeRemaining % 60000) / 1000).toInt()

        val timerText = String.format("%02d:%02d", minutes, seconds)
        binding.timerTextView.text = timerText
    }

    private fun showStopConfirmationDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Parar Cronômetro")
        alertDialogBuilder.setMessage("Tem certeza que deseja parar o cronômetro?")
        alertDialogBuilder.setPositiveButton("Sim") { dialogInterface: DialogInterface, _: Int ->
            stopTimer()
            dialogInterface.dismiss()
        }
        alertDialogBuilder.setNegativeButton("Não") { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun stopTimer() {
        countDownTimer.cancel()
        isTimerRunning = false
        showResumeButton()
        Toast.makeText(this, "Cronômetro parado", Toast.LENGTH_SHORT).show()
    }

    private fun showResumeButton() {
        if (isTimerRunning) return // Já exibido o botão de Resume

        resumeButton = Button(this)
        resumeButton.text = "Resume"
        val params = binding.btnStop.layoutParams as ViewGroup.MarginLayoutParams
        params.topMargin = resources.getDimensionPixelSize(R.dimen.margin_medium)
        binding.root.addView(resumeButton, params)

        resumeButton.setOnClickListener {
            resumeTimer()
            binding.root.removeView(resumeButton)
        }
    }

    private fun resumeTimer() {
        countDownTimer.start()
        isTimerRunning = true
    }
}
