package com.example.kolokvijum1;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kolokvijum1.database.OsobaRepository;

/**
 * MainActivity - glavna aktivnost aplikacije
 * Sadrži Checkbox za lokaciju i Button za prelazak na drugu aktivnost
 */
public class MainActivity extends AppCompatActivity {

    private CheckBox checkboxLocation;
    private Button btnDugme;
    private OsobaRepository osobaRepository;
    private static final int DEFAULT_GODISTE = 2000; // Promenite na vaše godište

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicijalizujemo repository
        osobaRepository = new OsobaRepository(this);

        // Pronalazimo view elemente
        checkboxLocation = findViewById(R.id.checkboxLocation);
        btnDugme = findViewById(R.id.btnDugme);

        // Postavljamo click listener na dugme
        btnDugme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Proveravamo da li je checkbox čekiran
                if (checkboxLocation.isChecked()) {
                    // Ako jeste, otvaramo dialog
                    showDialog();
                } else {
                    // Ako nije, prikazujemo Toast poruku
                    Toast.makeText(MainActivity.this, 
                        "Morate dozvoliti lokaciju!", 
                        Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Prikazuje dialog sa EditText poljem za unos imena
     */
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        
        // Kreiramo EditText za unos imena
        View dialogView = LayoutInflater.from(this).inflate(
            android.R.layout.simple_list_item_1, null);
        final EditText editText = new EditText(this);
        editText.setHint("Unesite ime");
        
        builder.setView(editText);
        builder.setTitle("Unos imena");
        
        builder.setPositiveButton("OK", (dialog, which) -> {
            String ime = editText.getText().toString().trim();
            
            if (!ime.isEmpty()) {
                // Čuvamo u bazu sa default godištem
                long id = osobaRepository.insert(ime, DEFAULT_GODISTE);
                Toast.makeText(MainActivity.this, 
                    "Sačuvano: " + ime + " (ID: " + id + ")", 
                    Toast.LENGTH_SHORT).show();
                
                // Prelazimo na SecondActivity
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, 
                    "Ime ne može biti prazno!", 
                    Toast.LENGTH_SHORT).show();
            }
        });
        
        builder.setNegativeButton("Otkaži", null);
        builder.show();
    }
}
