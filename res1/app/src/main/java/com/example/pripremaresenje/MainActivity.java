package com.example.pripremaresenje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.pripremaresenje.Utils.FragmentTransition;
import com.example.pripremaresenje.Utils.InitDB;
import com.example.pripremaresenje.database.SQLiteHelper;
import com.example.pripremaresenje.fragments.AutorFragment;
import com.example.pripremaresenje.fragments.KnjigeFragment;
import com.example.pripremaresenje.fragments.KomentariFragment;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        InitDB.initDB(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch ((Objects.requireNonNull(item.getTitle())).toString()) {
            case "Knjige":
                // User chooses the "Settings" item. Show the app settings UI.
                FragmentTransition.to(KnjigeFragment.newInstance(), MainActivity.this, true, R.id.fragmentContainer);
                return true;

            case "Autori":
                FragmentTransition.to(AutorFragment.newInstance(), MainActivity.this, true, R.id.fragmentContainer);

                return true;
            case "Komentari":
                FragmentTransition.to(KomentariFragment.newInstance(), MainActivity.this, true, R.id.fragmentContainer);

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}