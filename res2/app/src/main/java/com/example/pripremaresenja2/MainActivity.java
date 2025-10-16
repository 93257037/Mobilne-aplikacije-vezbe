package com.example.pripremaresenja2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("pref_file", Context.MODE_PRIVATE);
        EditText email = findViewById(R.id.username);
        EditText lozinka = findViewById(R.id.password);
        Button login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor sp_editor = sharedPreferences.edit();

                if (sharedPreferences.contains("pref_email")){
                    String emailPref = sharedPreferences.getString("pref_email", "default");
                    sp_editor.putString("pref_email", emailPref + "\r\n" + email.getText().toString());
                    sp_editor.apply();
                } else {
                    sp_editor.putString("pref_email", email.getText().toString());
                    sp_editor.apply();
                }

                Intent intent = new Intent(MainActivity.this, MyActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}