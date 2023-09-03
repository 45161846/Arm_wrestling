package com.example.armwrestling

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.armwrestling.databinding.RandomTimeFragmentBinding
import com.example.armwrestling.databinding.RepetitionFragmentBinding

class MinMaxTimeFragment: Fragment(R.layout.random_time_fragment) {

    lateinit var binding: RandomTimeFragmentBinding
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var inputMin: EditText
    lateinit var inputMax: EditText
    lateinit var viewModel: MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RandomTimeFragmentBinding.inflate(inflater)
        sharedPreferences =
            activity?.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)!!
        editor = sharedPreferences.edit()

        viewModel = ViewModelProvider(requireActivity())[MyViewModel::class.java]

        inputMin = binding.minTimeEt
        inputMax = binding.maxTimeEt

        inputMin.setText((viewModel.MIN_TIME/1000).toString())
        inputMax.setText((viewModel.MAX_TIME/1000).toString())

        inputMin.inputType = InputType.TYPE_CLASS_NUMBER
        inputMax.inputType = InputType.TYPE_CLASS_NUMBER

        inputMin.setOnFocusChangeListener { _, _ ->
            if(!inputMin.isFocused){

                inputMin.setText((viewModel.MIN_TIME/1000).toString())
            }
        }
        inputMin.doAfterTextChanged {
            if(inputMin.text.toString() != "") {
                editor.putLong("MIN_TIME",inputMin.text.toString().toLong()*1000).apply()
                Log.d("mTag","min_time")
                viewModel.MIN_TIME = inputMin.text.toString().toLong()*1000
            }
        }

        inputMax.setOnFocusChangeListener { _, _ ->
            if(!inputMax.isFocused){

                inputMax.setText((viewModel.MAX_TIME/1000).toString())
            }
        }
        inputMax.doAfterTextChanged {
            if(inputMax.text.toString() != "") {
                editor.putLong("MAX_TIME",inputMax.text.toString().toLong()*1000).apply()
                Log.d("mTag","max_time")
                viewModel.MAX_TIME = inputMax.text.toString().toLong()*1000
            }
        }


        return binding.root
    }
}