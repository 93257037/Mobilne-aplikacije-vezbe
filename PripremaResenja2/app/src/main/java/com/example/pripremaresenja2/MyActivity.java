package com.example.pripremaresenja2;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends AppCompatActivity {
    private String permission = Manifest.permission.CAMERA;
    private ActivityResultLauncher<String> mPermissionResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        mPermissionResult = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                result -> {
                    if (result) {
                        FragmentTransition.to(KorisniciFragment.newInstance("S","s"), this, false, R.id.kontejner);
                    } else {
                        TextView textView = findViewById(R.id.prikaz);
                        textView.setText("“Nije dozvoljena kamera!”");
                    }
                });

        Button traziDozvolu = findViewById(R.id.prikaziDozvolu);
        traziDozvolu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int getPermission = ContextCompat.checkSelfPermission(getApplicationContext(), permission);
                if (getPermission != PackageManager.PERMISSION_GRANTED) {
                    mPermissionResult.launch(permission);
                } else {
                    FragmentTransition.to(KorisniciFragment.newInstance("S","s"), MyActivity.this, false, R.id.kontejner);
                }
            }
        });

        Button obojiIIspisi = findViewById(R.id.prikaziBoju);
        obojiIIspisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ConstraintLayout)findViewById(R.id.aktivnost_kontejner)).setBackgroundColor(Color.RED);
                TextView textView = findViewById(R.id.prikaz);
                String ulogovani = getSharedPreferences("pref_file", MODE_PRIVATE).getString("pref_email", "default");
                String[] niz = ulogovani.split("\r\n");
                textView.setText(niz[niz.length-1]);
            }
        });
    }
}