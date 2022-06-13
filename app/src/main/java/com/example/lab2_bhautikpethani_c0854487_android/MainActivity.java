package com.example.lab2_bhautikpethani_c0854487_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.lab2_bhautikpethani_c0854487_android.databinding.ActivityMainBinding;
import com.example.lab2_bhautikpethani_c0854487_android.service.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        dbHelper = new DatabaseHelper(this);
    }


}