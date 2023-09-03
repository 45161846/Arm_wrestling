package com.example.armwrestling

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import com.example.armwrestling.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var startButton: AppCompatButton
    private lateinit var stopButton: AppCompatButton
    private lateinit var settings: AppCompatButton

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MyViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()


        //todo tools init
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = this.getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE)
        mediaPlayer = MediaPlayer.create(this,R.raw.beep)

        //todo buttons init
        startButton = binding.startButton
        stopButton = binding.stopButton
        settings = binding.settingsButton




        //todo viewModel init
        viewModel = ViewModelProvider(this)[MyViewModel::class.java]

        viewModel.trainingFinished.observe(this) {
            if(it) {
                end()
            }
        }
        viewModel.currentTime.observe(this) {
            if(!viewModel.mode_of_end) {
                startButton.text = (it / 1000).toString()
            }else{
                startButton.text = viewModel.timesSpokenCounter.toString()
                Log.d("mLog_timesSpokenCounter",viewModel.timesSpokenCounter.toString())
            }
        }
        viewModel.timesSpoken.observe(this){
            if(it>0) playSound(it)
        }
        loadSettings()



        //todo buttons' clickListeners init
        startButton.setOnClickListener {
            startButton.visibility = View.INVISIBLE

            viewModel.startTimer()

            startAnimation()

            stopButton.visibility = View.VISIBLE

        }
        stopButton.setOnClickListener {
            end()
        }

        settings.setOnClickListener {
            val bottomSheet = MyBottomSheet()
            bottomSheet.show(supportFragmentManager,"TAG")
        }
    }


    private fun startAnimation() {
        startButton.visibility = View.INVISIBLE
        stopButton.visibility = View.INVISIBLE
        stopButton.animate().apply {
            duration = 0
            translationX(-800f)
        }.withEndAction{
            startButton.animate().apply {
                duration = 0
                translationY(370f)

            }.withEndAction {
                startButton.visibility = View.VISIBLE
                stopButton.visibility = View.VISIBLE
                stopButton.animate().apply {
                    duration = 0
                    alpha(1f)
                }
                startButton.animate().apply {
                    duration = 250
                    translationY(-50f)
                }.withEndAction {

                    stopButton.animate().apply {
                        duration = 450
                        translationX(0f)
                    }.withEndAction {
                        startButton.animate().apply {
                            duration = 250
                            translationY(70f)
                        }
                    }

                }
            }
        }
    }

    private fun stopAnimation(){
        startButton.animate().apply {
            duration = 150
            alpha(0f)
        }.withEndAction{
            stopButton.animate().apply {
                duration = 150
                alpha(0f)

            }.withEndAction{
                stopButton.visibility = View.GONE
                startButton.animate().apply {
                    duration = 150
                    alpha(1f)
                    stopButton.visibility = View.GONE
                }
            }
        }
    }

    private fun playSound(text: Int) {
        mediaPlayer = MediaPlayer.create(this,R.raw.beep)
        mediaPlayer.start()
    }

    private fun loadSettings(){
        viewModel.setValues(sharedPreferences.getLong("START_TIME", 25000L),
            sharedPreferences.getLong("MIN_TIME", 2000L),
            sharedPreferences.getLong("MAX_TIME", 5000L),
            sharedPreferences.getLong("CONSTANT", 3000L),
            sharedPreferences.getInt("REPETITIONS", 15),
            !sharedPreferences.getBoolean("time",false),
            sharedPreferences.getBoolean("random", false))



    }

    private fun end() {
        viewModel.timer.cancel()
        viewModel.isTicking = false
        viewModel.timesSpoken.value = 0
        viewModel.timesSpokenCounter = 0
        startButton.text = getString(R.string.start_button)
        Log.d("mTimer", "END")
        stopAnimation()
    }

    override fun onStop() {

        if(mediaPlayer.isPlaying){
            mediaPlayer.release()
        }
        mediaPlayer.stop()
        super.onStop()
    }

}