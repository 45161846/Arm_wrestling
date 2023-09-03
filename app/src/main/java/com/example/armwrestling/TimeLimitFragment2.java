package com.example.armwrestling;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.armwrestling.databinding.TimeFragmentBinding;

import java.util.Objects;

public class TimeLimitFragment2 extends Fragment {

    TimeFragmentBinding binding;
    MyViewModel viewModel;
    TextView input;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = TimeFragmentBinding.inflate(inflater);

        viewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        input.setText((int) viewModel.getSTART_TIME());

        Toast.makeText(getContext(), "abc", Toast.LENGTH_SHORT).show();

        return binding.getRoot();
    }
}
