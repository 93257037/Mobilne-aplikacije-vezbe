package com.kcalorific.odnule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * NotificationsScreen - Fragment za prikaz obaveštenja
 * Prikazuje listu obaveštenja za korisnika
 */
public class NotificationsScreen extends Fragment {

    private ListView lvNotifications;
    private List<String> notifications;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        // Inicijalizacija view komponenti
        initializeViews(view);

        // Učitavanje mokap obaveštenja
        loadMockNotifications();

        // Postavljanje adapter-a
        setupNotificationsList();

        return view;
    }

    /**
     * Inicijalizacija view komponenti
     */
    private void initializeViews(View view) {
        lvNotifications = view.findViewById(R.id.lvNotifications);
    }

    /**
     * Učitavanje mokap obaveštenja
     */
    private void loadMockNotifications() {
        notifications = new ArrayList<>();
        notifications.add("Dobrodošli u aplikaciju! Započnite kupovinu.");
        notifications.add("Novi proizvod: Laptop Dell XPS 15 je sada dostupan.");
        notifications.add("Specijalna ponuda: 20% popusta na sve slušalice!");
        notifications.add("Vaša porudžbina je poslata i stiže za 2-3 dana.");
        notifications.add("Novi komentar na proizvod koji ste pregledali.");
        notifications.add("Ažuriranje: Nova verzija aplikacije je dostupna.");
        notifications.add("Podsetnik: Proizvod u korpi čeka na vas.");
        notifications.add("Flash sale: Samo danas! Do 50% popusta.");
    }

    /**
     * Postavljanje liste obaveštenja
     */
    private void setupNotificationsList() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                notifications
        );
        lvNotifications.setAdapter(adapter);
    }
}

