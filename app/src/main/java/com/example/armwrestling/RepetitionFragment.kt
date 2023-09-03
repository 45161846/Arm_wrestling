package com.example.armwrestling

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.armwrestling.databinding.RepetitionFragmentBinding
import com.example.armwrestling.databinding.TimeFragmentBinding

class RepetitionFragment: Fragment(R.layout.repetition_fragment) {

    lateinit var binding: RepetitionFragmentBinding
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var input: EditText
    lateinit var viewModel: MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = RepetitionFragmentBinding.inflate(inflater)
        sharedPreferences =
            activity?.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)!!
        editor = sharedPreferences.edit()

        viewModel = ViewModelProvider(requireActivity())[MyViewModel::class.java]

        input = binding.repetitionEt

        input.setText((viewModel.REPETITIONS).toString())

        input.inputType = InputType.TYPE_CLASS_NUMBER
        input.setOnFocusChangeListener { _, _ ->
            if(!input.isFocused){

                input.setText((viewModel.REPETITIONS).toString())
            }
        }
        input.doAfterTextChanged {
            if(input.text.toString() != "") {
                editor.putInt("REPETITIONS",input.text.toString().toInt()).apply()
                viewModel.REPETITIONS = input.text.toString().toInt()
            }
        }


        return binding.root
    }
}