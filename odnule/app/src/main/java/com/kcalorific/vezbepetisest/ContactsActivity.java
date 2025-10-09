package com.kcalorific.vezbepetisest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * ContactsActivity - ekran za prikaz kontakata sa uređaja
 * Učitava podatke koristeći ContentProvider (ContactsContract)
 * Demonstrira korišćenje ContentProvider-a za pristup sistemskim podacima
 */
public class ContactsActivity extends AppCompatActivity {

    private static final String TAG = "ContactsActivity";
    private static final int PERMISSION_REQUEST_CODE = 100;

    private ListView lvContacts;
    private List<String> contactsList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        // Inicijalizacija komponenti
        lvContacts = findViewById(R.id.lvContacts);
        contactsList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactsList);
        lvContacts.setAdapter(adapter);

        // Provera i traženje dozvole za čitanje kontakata
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            // Traženje dozvole
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    PERMISSION_REQUEST_CODE);
        } else {
            // Dozvola već dobijena, učitavanje kontakata
            loadContacts();
        }
    }

    /**
     * Učitavanje kontakata koristeći ContentProvider
     * Koristi ContactsContract.Contacts kao izvor podataka
     */
    private void loadContacts() {
        contactsList.clear();

        // Definisanje kolona koje želimo da učitamo
        String[] projection = {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.HAS_PHONE_NUMBER
        };

        // Cursor za čitanje podataka iz ContentProvider-a
        Cursor cursor = getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,  // URI ContentProvider-a
                projection,                              // Kolone
                null,                                    // Selection
                null,                                    // Selection args
                ContactsContract.Contacts.DISPLAY_NAME + " ASC"  // Sortiranje
        );

        if (cursor != null && cursor.getCount() > 0) {
            int count = 0;
            while (cursor.moveToNext() && count < 20) {  // Ograničenje na 20 kontakata
                // Dohvatanje ID-a i imena
                String id = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                int hasPhone = cursor.getInt(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                String contactInfo = name;

                // Ako kontakt ima broj telefona, dohvatamo ga
                if (hasPhone > 0) {
                    String phoneNumber = getPhoneNumber(id);
                    if (phoneNumber != null) {
                        contactInfo += "\n" + phoneNumber;
                    }
                }

                contactsList.add(contactInfo);
                count++;
            }
            cursor.close();

            // Ažuriranje adapter-a
            adapter.notifyDataSetChanged();
            
            Log.d(TAG, "Učitano " + contactsList.size() + " kontakata");
            Toast.makeText(this, "Učitano " + contactsList.size() + " kontakata", Toast.LENGTH_SHORT).show();
        } else {
            contactsList.add("Nema dostupnih kontakata");
            adapter.notifyDataSetChanged();
            Log.d(TAG, "Nema kontakata na uređaju");
        }

        if (cursor != null) {
            cursor.close();
        }
    }

    /**
     * Dohvatanje broja telefona za specifični kontakt
     * @param contactId ID kontakta
     * @return Broj telefona ili null
     */
    private String getPhoneNumber(String contactId) {
        String phoneNumber = null;

        Cursor phoneCursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                new String[]{contactId},
                null
        );

        if (phoneCursor != null && phoneCursor.moveToFirst()) {
            phoneNumber = phoneCursor.getString(
                    phoneCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)
            );
            phoneCursor.close();
        }

        return phoneNumber;
    }

    /**
     * Obrada rezultata traženja dozvole
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Dozvola dobijena, učitavanje kontakata
                Toast.makeText(this, "Dozvola za kontakte dobijena", Toast.LENGTH_SHORT).show();
                loadContacts();
            } else {
                // Dozvola odbijena
                Toast.makeText(this, "Dozvola za kontakte odbijena", Toast.LENGTH_SHORT).show();
                contactsList.add("Dozvola za pristup kontaktima nije dobijena");
                adapter.notifyDataSetChanged();
            }
        }
    }
}

