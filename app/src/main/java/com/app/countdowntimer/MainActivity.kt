package com.app.countdowntimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    val start_timer_in_millies : Long = 1 * 60 * 1000
    var remainingTime :Long = start_timer_in_millies
    lateinit var title_tv:TextView
    lateinit var time_tv :TextView
    lateinit var reset_tv:TextView
    lateinit var start_btn:Button
    lateinit var progressbar :ProgressBar
    var timer : CountDownTimer? =null
    var isTimmerRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title_tv = findViewById(R.id.title_textView)
        time_tv = findViewById(R.id.timer_textView)
        progressbar = findViewById(R.id.progressBar)
        reset_tv = findViewById(R.id.rest_textView)
        start_btn = findViewById(R.id.start_button)
        start_btn.setOnClickListener{
            if (!isTimmerRunning) {
                StartTimer(start_timer_in_millies)
                title_tv.text = resources.getText(R.string.keep_going)
            }
        }
        reset_tv.setOnClickListener{
            ResetTimer()
        }
    }

    private fun StartTimer(startTime : Long) {
         timer = object : CountDownTimer(startTime, 1000) {
            override fun onTick(timeleft: Long) {
                remainingTime = timeleft
                updateTimerText()
                progressbar.progress = remainingTime.toDouble().div(start_timer_in_millies.toDouble()).times(100).toInt()
                time_tv.setText(timeleft.toString())
            }

            override fun onFinish() {
                Toast.makeText(this@MainActivity, "Finish !! ", Toast.LENGTH_SHORT).show()
                isTimmerRunning = false
            }

        }.start()
        isTimmerRunning = true
    }

    private fun ResetTimer(){
        timer?.cancel()
        remainingTime = start_timer_in_millies
        updateTimerText()
        title_tv.text = resources.getText(R.string.take_pomodoro)
        isTimmerRunning = false
        progressbar.progress = 100
    }

    private fun updateTimerText(){
        val minute = remainingTime.div(1000).div(60)
        val second = remainingTime.div(1000) % 60
        val formatedTime = String.format("%02d:%02d " ,minute  ,second)
       time_tv.text = formatedTime
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putLong("remaining_Time",remainingTime)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val savedTime = savedInstanceState.getLong("remaining_Time")
        if (savedTime != start_timer_in_millies)
            StartTimer(savedTime)
    }
    }


