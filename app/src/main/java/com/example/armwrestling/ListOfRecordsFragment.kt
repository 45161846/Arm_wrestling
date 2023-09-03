package com.example.armwrestling

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.example.armwrestling.databinding.FragmentListOfRecordsBinding

class ListOfRecordsFragment: Fragment(R.layout.fragment_list_of_records) {

    private lateinit var binding: FragmentListOfRecordsBinding
    private lateinit var recordButton: AppCompatButton
    private val stack = "stack"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentListOfRecordsBinding.inflate(layoutInflater)

        recordButton = binding.addRecordBt

//        recordButton.setOnClickListener{
//            parentFragmentManager
//                .beginTransaction()
//                .replace(R.id.audio_container,CreateAudioFragment())
//                .addToBackStack(stack)
//                .commit()
//        }

    }
}