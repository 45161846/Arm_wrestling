package com.example.armwrestling

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentManager
import com.example.armwrestling.databinding.ActivitySecondBinding

class SecondActivity: AppCompatActivity() {

    lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        supportActionBar?.hide()

        Log.d("mLog_second_activity","started")

        binding = ActivitySecondBinding.inflate(layoutInflater)

//        val listFragment = ListOfRecordsFragment()
//
//        supportFragmentManager.beginTransaction().add(R.id.audio_container,listFragment).commit()

        setContentView(binding.root)

    }
}