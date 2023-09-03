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
import com.example.armwrestling.databinding.DefaultTimeFragmentBinding
import com.example.armwrestling.databinding.TimeFragmentBinding

class ConstantTimeFragment:Fragment(R.layout.default_time_fragment) {

    lateinit var binding: DefaultTimeFragmentBinding
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var input: EditText
    lateinit var viewModel: MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DefaultTimeFragmentBinding.inflate(inflater)
        sharedPreferences =
            activity?.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)!!
        editor = sharedPreferences.edit()

        viewModel = ViewModelProvider(requireActivity())[MyViewModel::class.java]

        input = binding.constantTimeEt

        input.setText((viewModel.CONSTANT.toInt()/1000).toString())

        input.inputType = InputType.TYPE_CLASS_NUMBER
        input.setOnFocusChangeListener { _, _ ->
            if(!input.isFocused){
                input.setText((viewModel.CONSTANT.toInt()/1000).toString())
            }
        }
        input.doAfterTextChanged {
            if(input.text.toString() != "") {
                editor.putLong("CONSTANT",input.text.toString().toLong()*1000).apply()
                viewModel.CONSTANT = input.text.toString().toLong()*1000

            }
        }

        return binding.root
    }
}