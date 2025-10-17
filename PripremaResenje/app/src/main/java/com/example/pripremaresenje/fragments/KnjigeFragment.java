package com.example.pripremaresenje.fragments;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.pripremaresenje.Utils.DatabaseConstants;
import com.example.pripremaresenje.Utils.FragmentTransition;
import com.example.pripremaresenje.R;
import com.example.pripremaresenje.adapters.KnjigeListAdapter;
import com.example.pripremaresenje.database.AutorRepository;
import com.example.pripremaresenje.database.KnjigeRepository;
import com.example.pripremaresenje.models.Autor;
import com.example.pripremaresenje.models.Knjiga;
import java.util.ArrayList;

public class KnjigeFragment extends ListFragment {
    private KnjigeRepository knjigeRepository;
    private KnjigeListAdapter knjigeListAdapter;

    public KnjigeFragment() {
        // Required empty public constructor
    }
    public static KnjigeFragment newInstance() {
        return new KnjigeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        knjigeRepository = new KnjigeRepository(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_knjige, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadKnjige();

        Button dodaj = view.findViewById(R.id.dodaj);
        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransition.to(NovaKnjigaFragment.newInstance(), requireActivity(), true, R.id.fragmentContainer);
            }
        });
    }

    @SuppressLint("Range")
    private void loadKnjige() {
        String[] projection = { DatabaseConstants.COLUMN_ID, DatabaseConstants.COLUMN_NASLOV, DatabaseConstants.COLUMN_BRSTRANICA,
                DatabaseConstants.COLUMN_POVEZ, DatabaseConstants.COLUMN_ZANR, DatabaseConstants.COLUMN_AUTOR};
        Cursor cursor = knjigeRepository.getData(projection, null, null, null);

        ArrayList<Knjiga> knjige = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Knjiga row = new Knjiga();
                row.setID(Integer.parseInt(cursor.getString(0)));
                row.setNaslov(cursor.getString(1));
                row.setBrStranica(cursor.getInt(2));
                row.setPovez(cursor.getString(3));
                row.setZanr(cursor.getString(4));

                AutorRepository autorRepository = new AutorRepository(getContext());
                Cursor cursor_a = autorRepository.getEntity(cursor.getInt(5));
                cursor_a.moveToNext();
                Autor autor = new Autor(cursor_a.getInt(0),
                        cursor_a.getString(1), cursor_a.getInt(2),
                        cursor_a.getString(3));
                row.setAutor(autor);

                knjige.add(row);
            }
            knjigeListAdapter = new KnjigeListAdapter(getActivity(), knjige);
            setListAdapter(knjigeListAdapter);
        }
        knjigeRepository.DBCLose();
    }

}