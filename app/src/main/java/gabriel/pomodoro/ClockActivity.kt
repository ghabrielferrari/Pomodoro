package gabriel.pomodoro

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import gabriel.pomodoro.databinding.ActivityClockBinding

class ClockActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityClockBinding.inflate(layoutInflater)
    }

    private var timeRemaining: Long = 0
    private lateinit var countDownTimer: CountDownTimer
    private var isTimerRunning: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val selectedTime = intent.getIntExtra("selected_time", 0)
        timeRemaining = selectedTime * 60000L

        setupCountDownTimer()

        binding.btnFinish.setOnClickListener {
            showFinishConfirmationDialog()
        }

        binding.btnStop.setOnClickListener {
            showStopConfirmationDialog()
        }
    }

    private fun setupCountDownTimer() {
        countDownTimer = object : CountDownTimer(timeRemaining, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemaining = millisUntilFinished
                updateTimerUI()
            }

            override fun onFinish() {
                isTimerRunning = false
                showResumeButton()
            }
        }
        countDownTimer.start()
        isTimerRunning = true
    }

    private fun updateTimerUI() {
        val minutes = (timeRemaining / 60000).toInt()
        val seconds = ((timeRemaining % 60000) / 1000).toInt()
        val timerText = String.format("%02d:%02d", minutes, seconds)
        binding.timerTextView.text = timerText
    }

    private fun showFinishConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Finish Timer")
            .setMessage("Are you sure you want to finish the timer?")
            .setPositiveButton("Yes") { dialogInterface: DialogInterface, _: Int ->
                stopTimer()
                startActivity(Intent(this, MainActivity::class.java))
                dialogInterface.dismiss()
            }
            .setNegativeButton("No") { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }
            .create()
            .show()
    }

    private fun showStopConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Stop Timer")
            .setMessage("Are you sure you want to stop the timer?")
            .setPositiveButton("Yes") { dialogInterface: DialogInterface, _: Int ->
                stopTimer()
                dialogInterface.dismiss()
            }
            .setNegativeButton("No") { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }
            .create()
            .show()
    }

    private fun stopTimer() {
        countDownTimer.cancel()
        isTimerRunning = false
        showResumeButton()
        Toast.makeText(this, "Timer finished", Toast.LENGTH_SHORT).show()
    }

    private fun showResumeButton() {
        if (isTimerRunning) return

        binding.btnResume.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                resumeTimer()
                visibility = View.GONE
            }
        }
    }

    private fun resumeTimer() {
        setupCountDownTimer()
    }
}
