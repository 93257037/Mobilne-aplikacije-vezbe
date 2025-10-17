package com.example.pripremaresenje;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pripremaresenje.database.PersonRepository;
import com.example.pripremaresenje.models.Person;

public class MainActivity extends AppCompatActivity {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    
    private CheckBox locationCheckBox;
    private Button button;
    private PersonRepository personRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        locationCheckBox = findViewById(R.id.locationCheckBox);
        button = findViewById(R.id.button);
        
        personRepository = new PersonRepository(this);
        personRepository.open();
        
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
        
        locationCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (locationCheckBox.isChecked()) {
                    checkLocationPermission();
                }
            }
        });
    }
    
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) 
                == PackageManager.PERMISSION_GRANTED) {
            // Permission granted, show dialog
            showNameDialog();
        } else {
            // Permission not granted, show toast
            Toast.makeText(this, "Morate dozvoliti lokaciju!", Toast.LENGTH_SHORT).show();
            locationCheckBox.setChecked(false);
        }
    }
    
    private void showNameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Unesite ime");
        
        final EditText input = new EditText(this);
        builder.setView(input);
        
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = input.getText().toString().trim();
                if (!name.isEmpty()) {
                    // Save to database with default birth year (2024)
                    Person person = new Person(name, 2024);
                    long result = personRepository.insertPerson(person);
                    if (result != -1) {
                        Toast.makeText(MainActivity.this, "Osoba sačuvana u bazi", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Greška pri čuvanju", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        
        builder.setNegativeButton("Otkaži", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                locationCheckBox.setChecked(false);
            }
        });
        
        builder.show();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (personRepository != null) {
            personRepository.close();
        }
    }
}