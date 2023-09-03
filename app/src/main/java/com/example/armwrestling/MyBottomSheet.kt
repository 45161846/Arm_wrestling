package com.example.armwrestling

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModelProvider
import com.example.armwrestling.databinding.ActivitySecondBinding
import com.example.armwrestling.databinding.SettingsBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MyBottomSheet() : BottomSheetDialogFragment() {
    private lateinit var binding: SettingsBottomSheetBinding
    lateinit var editor: SharedPreferences.Editor

    lateinit var time: AppCompatRadioButton
    lateinit var repetitions: AppCompatRadioButton

    lateinit var random: SwitchCompat

    lateinit var changeVoice: AppCompatButton

    lateinit var sharedPreferences: SharedPreferences

    lateinit var viewModel: MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SettingsBottomSheetBinding.inflate(inflater)

        time = binding.timeRb
        repetitions = binding.repetitionRb
        random = binding.isRandomSw
        changeVoice = binding.changeVoice

        sharedPreferences = activity?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)!!
        editor = sharedPreferences.edit()

        viewModel = ViewModelProvider(requireActivity())[MyViewModel::class.java]

        if(sharedPreferences.getBoolean("random",false)){
            childFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_from_bottom_to_top,R.anim.slide_from_top_to_bottom)
                .replace(R.id.min_max_time_setting_fragment, MinMaxTimeFragment(),"MinMaxTimeFragment")
                .addToBackStack(null)
                .commit()
        }else{
            childFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_from_left_to_right,R.anim.slide_from_left_to_right_exit)
                .replace(R.id.min_max_time_setting_fragment, ConstantTimeFragment())
                .addToBackStack(null)
                .commit()

        }

        if(sharedPreferences.getBoolean("time",true)) {
            time.isChecked = true
            repetitions.isChecked = false
            val timeLimitFragment = TimeLimitFragment()
            childFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_from_left_to_right,R.anim.slide_from_left_to_right_exit)
                .replace(R.id.mode_setting_fragment, timeLimitFragment)
                .addToBackStack(null)
                .commit()
        }else{
            time.isChecked = false
            repetitions.isChecked = true
            val repetitionFragment = RepetitionFragment()
            childFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_from_right_to_left,R.anim.slide_from_right_to_left_exit)
                .replace(R.id.mode_setting_fragment, repetitionFragment)
                .addToBackStack(null)
                .commit()
        }
        random.isChecked = sharedPreferences.getBoolean("random",false)
        
        time.setOnCheckedChangeListener { _, _ ->
            editor.putBoolean("time", time.isChecked).apply()
            viewModel.mode_of_end = !time.isChecked
            if(time.isChecked) {
                childFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_from_left_to_right,R.anim.slide_from_left_to_right_exit)
                    .replace(R.id.mode_setting_fragment, TimeLimitFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }
        repetitions.setOnCheckedChangeListener { _, _ ->
            if(repetitions.isChecked) {
                childFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_from_right_to_left,R.anim.slide_from_right_to_left_exit)
                    .replace(R.id.mode_setting_fragment, RepetitionFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }

        random.setOnCheckedChangeListener { _, _ ->
            editor.putBoolean("random", random.isChecked).apply()
            viewModel.mode_of_training = random.isChecked
            if(random.isChecked){
                childFragmentManager
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_from_right_to_left,R.anim.slide_from_right_to_left_exit)
                    .replace(R.id.min_max_time_setting_fragment, MinMaxTimeFragment(),)
                    .addToBackStack(null)
                    .commit()
            }else{
                childFragmentManager
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_from_left_to_right,R.anim.slide_from_left_to_right_exit)
                    .replace(R.id.min_max_time_setting_fragment, ConstantTimeFragment())
                    .addToBackStack(null)
                    .commit()

            }
        }

        changeVoice.setOnClickListener{
            Log.d("mLog_second_activity","started")
            startActivity(Intent(activity, SecondActivity::class.java))
        }

        return binding.root
    }
}