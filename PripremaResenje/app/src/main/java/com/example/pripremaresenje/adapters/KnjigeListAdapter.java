package com.example.pripremaresenje.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.pripremaresenje.R;
import com.example.pripremaresenje.Utils.DatabaseConstants;
import com.example.pripremaresenje.database.KnjigeRepository;
import com.example.pripremaresenje.models.Knjiga;

import java.util.ArrayList;

/*
 * Adapteri unutar Android-a sluze da prikazu unapred nedefinisanu kolicinu podataka
 * pristigle sa interneta ili ucitane iz baze ili filesystem-a uredjaja.
 * Da bi napravili adapter treba da napraivmo klasu, koja nasledjuje neki od postojecih adaptera.
 * Za potrebe ovih vezbi koristicemo ArrayAdapter koji kao izvor podataka iskoristi listu ili niz.
 * Nasledjivanjem bilo kog adaptera, dobicemo
 * nekolko metoda koje moramo da referinisemo da bi adapter ispravno radio.
 * */
public class KnjigeListAdapter extends ArrayAdapter<Knjiga> {
    private ArrayList<Knjiga> listKnjige;
    private KnjigeRepository knjigeRepository;

    public KnjigeListAdapter(Context context, ArrayList<Knjiga> knjige){
        super(context, R.layout.knjiga_card, knjige);
        listKnjige = knjige;
    }
    /*
     * Ova metoda vraca ukupan broj elemenata u listi koje treba prikazati
     * */
    @Override
    public int getCount() {
        return listKnjige.size();
    }

    /*
     * Ova metoda vraca pojedinacan element na osnovu pozicije
     * */
    @Nullable
    @Override
    public Knjiga getItem(int position) {
        return listKnjige.get(position);
    }

    /*
     * Ova metoda vraca jedinstveni identifikator, za adaptere koji prikazuju
     * listu ili niz, pozicija je dovoljno dobra. Naravno mozemo iskoristiti i
     * jedinstveni identifikator objekta, ako on postoji.
     * */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        knjigeRepository = new KnjigeRepository(getContext());

        Knjiga knjiga = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.knjiga_card,
                    parent, false);
        }
        TextView naslov = convertView.findViewById(R.id.naslov);
        TextView brStranica = convertView.findViewById(R.id.brStranica);
        TextView povez = convertView.findViewById(R.id.povez);
        TextView zanr = convertView.findViewById(R.id.zanr);
        TextView autor = convertView.findViewById(R.id.autor);

        Button obrisi = convertView.findViewById(R.id.delButton);
        obrisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("OBRISI", Long.toString(getItem(position).getID()));
                knjigeRepository.deleteData(getItem(position).getID());
                listKnjige.remove(position);
                notifyDataSetChanged();
            }
        });

        if(knjiga != null){
            naslov.setText("Naslov: " + knjiga.getNaslov());
            brStranica.setText("Broj stranica: " + Integer.toString(knjiga.getBrStranica()));
            povez.setText("Povez: " + knjiga.getPovez());
            zanr.setText("Zanr: " + knjiga.getZanr());
            autor.setText("Autor: " + knjiga.getAutor().getImeIPrezime());

            autor.setOnClickListener(v -> {
                // Handle click on the item at 'position'
                String whereClause = DatabaseConstants.COLUMN_AUTOR + " = ?";
                String[] whereArgs = {String.valueOf(knjiga.getAutor().getID())};
                Log.i("KNJIGE: ", Integer.toString(knjiga.getAutor().getID()));

                Cursor cursor = knjigeRepository.getData(new String[]{DatabaseConstants.COLUMN_NASLOV}, whereClause, whereArgs, null);
                String imenaAutora = "";
                while (cursor.moveToNext()){
                    imenaAutora += cursor.getString(0) + "\r\n";
                }
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setMessage(imenaAutora)
                        .setCancelable(false)
                        .setPositiveButton("U redu", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                AlertDialog alert = dialog.create();
                alert.show();
            });
        }

        return convertView;
    }
}
