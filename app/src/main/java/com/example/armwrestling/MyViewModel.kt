package com.example.armwrestling

import android.content.Context
import android.content.SharedPreferences
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.coroutines.coroutineContext

class MyViewModel : ViewModel() {

    var START_TIME = 13L // starting countdown time
    var WAITING_TIME: Long // RANDOM time until new sound command/rep. *Used only in (mode_of_training = TRUE)
    var CONSTANT = 0L // CONSTANT time until new sound command/rep. *Used only in (mode_of_training = FALSE)

    var MIN_TIME = 0L //MINIMUM delay between reps
    var MAX_TIME = 0L //MAXIMUM delay between reps

    var timesSpokenCounter = 0 //local counter to number of times which has been spoken
    var REPETITIONS = 0 //
    var isTicking = false

    var mode_of_training = false //random(true) or constant time(false)
    var mode_of_end = false  //end by time(false) or number of repetition(true)

    var TICK = 10L


    val currentTime = MutableLiveData<Long>()
    val trainingFinished = MutableLiveData<Boolean>()
    val timesSpoken = MutableLiveData<Int>()

    lateinit var timer:CountDownTimer

    init {

        timesSpoken.value = 0
        trainingFinished.value = false

        WAITING_TIME = if(mode_of_training) {
            randomTime()
        }else{
            CONSTANT
        }

    }

    fun setValues(start: Long, min: Long, max:Long,const:Long, rep: Int, mode_of_end: Boolean, mode_of_training: Boolean){
        this.START_TIME = start
        this.MIN_TIME = min
        this.MAX_TIME = max
        this.CONSTANT = const
        this.REPETITIONS = rep
        timesSpoken.value = 0
        timesSpokenCounter = 0
        this.mode_of_end = mode_of_end
        this.mode_of_training = mode_of_training

    }
    fun startTimer(){
        if(!isTicking){
            isTicking = true
            timer = if(mode_of_end){
                startRepetitionTimer()
            }else{
                startLimitedTimer()
            }
            timer.start()
        }

    }

    private fun startLimitedTimer():CountDownTimer{ //creating timer for mode of training when you end by timer, not number of repetitions
        WAITING_TIME = if(mode_of_training) { // random time
            randomTime()
        }else{
            CONSTANT
        }
        var LAST_UPDATE = START_TIME
        var UPDATE_TIME = LAST_UPDATE - WAITING_TIME

        return object : CountDownTimer(START_TIME, TICK){ //object = timer
                override fun onTick(current: Long) {
                    currentTime.value = current

                    if (UPDATE_TIME >= currentTime.value!!) {
                        LAST_UPDATE = currentTime.value!!
                        WAITING_TIME = if(mode_of_training) {
                            randomTime()
                        }else{
                            CONSTANT
                        }
                        timesSpokenCounter++
                        timesSpoken.value = timesSpokenCounter
                        UPDATE_TIME = LAST_UPDATE - WAITING_TIME
                    }

                }
                override fun onFinish() {
                    endOfWork()
                }
            }
    }

    private fun startRepetitionTimer():CountDownTimer{
        WAITING_TIME = if(mode_of_training) { // random time
            randomTime()
        }else{
            CONSTANT
        }
        var LAST_UPDATE = Long.MAX_VALUE
        var UPDATE_TIME = LAST_UPDATE - WAITING_TIME

        return object : CountDownTimer(START_TIME, TICK){ //object = timer
            override fun onTick(current: Long) {
                currentTime.value = current

                if(timesSpokenCounter>=REPETITIONS){
                    onFinish()
                }
                else if (UPDATE_TIME >= currentTime.value!!) {
                    LAST_UPDATE = currentTime.value!!

                    WAITING_TIME = if(mode_of_training) {
                        randomTime()
                    }else{
                        CONSTANT
                    }

                    timesSpokenCounter++
                    timesSpoken.value = timesSpokenCounter
                    UPDATE_TIME = LAST_UPDATE - WAITING_TIME

                }

            }
            override fun onFinish() {
                endOfWork()
            }
        }

    }
    fun randomTime():Long{
        return (Math.random()*(MAX_TIME-MIN_TIME).toDouble()+MIN_TIME).toLong()
    }
    fun endOfWork(){
        trainingFinished.value = true
        isTicking = false
        Log.d("mTimer","Finish")
    }
}