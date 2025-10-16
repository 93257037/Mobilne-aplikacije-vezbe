package com.kcalorific.testtttttt.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kcalorific.testtttttt.R;

public class FirstFragment extends Fragment {
    private Button button;
    private Button button2;

    private SharedPreferences sharedPreferences;


    public static FirstFragment newInstance(){
        return new FirstFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        button = view.findViewById(R.id.button);
        button2 = view.findViewById(R.id.button2);

        button.setOnClickListener(v -> {
            // Toggle (prebacivanje) enabled stanja dugmeta "Ispiši"
            // !btnIspisi.isEnabled() - logički NOT operator
            // Ako je dugme disabled (false), postavlja se enabled (true)
            // Ako je dugme enabled (true), postavlja se disabled (false)
            button2.setEnabled(!button2.isEnabled());
        });

        return view;
        }
}
