package com.example.pripremaresenja2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter za RecyclerView koji prikazuje listu beleški.
 * Koristi ViewHolder pattern za efikasno prikazivanje velikog broja stavki.
 */
public class BeleskeAdapter extends RecyclerView.Adapter<BeleskeAdapter.BeleskaViewHolder> {

    private List<Beleska> beleske;

    // Konstruktor prima listu beleški
    public BeleskeAdapter(List<Beleska> beleske) {
        this.beleske = beleske != null ? beleske : new ArrayList<>();
    }

    @NonNull
    @Override
    public BeleskaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Kreiranje View objekta iz item_beleska.xml layout-a
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_beleska, parent, false);
        return new BeleskaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BeleskaViewHolder holder, int position) {
        // Vezivanje podataka za ViewHolder
        Beleska beleska = beleske.get(position);
        holder.bind(beleska);
    }

    @Override
    public int getItemCount() {
        return beleske.size();
    }

    /**
     * Metoda za ažuriranje liste beleški (koristi se pri filtriranju i dodavanju novih beleški)
     */
    public void azurirajBeleske(List<Beleska> noveBeleske) {
        this.beleske = noveBeleske != null ? noveBeleske : new ArrayList<>();
        notifyDataSetChanged(); // Obaveštava RecyclerView da osvezi prikaz
    }

    /**
     * ViewHolder klasa koja drži reference na UI elemente za jednu stavku liste
     */
    static class BeleskaViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewNaslov;
        private TextView textViewDatum;
        private TextView textViewTekst;

        public BeleskaViewHolder(@NonNull View itemView) {
            super(itemView);
            // Pronalaženje TextView elemenata iz item_beleska.xml layout-a
            textViewNaslov = itemView.findViewById(R.id.textViewNaslov);
            textViewDatum = itemView.findViewById(R.id.textViewDatum);
            textViewTekst = itemView.findViewById(R.id.textViewTekst);
        }

        /**
         * Metoda koja postavlja podatke beleške u odgovarajuće TextView elemente
         */
        public void bind(Beleska beleska) {
            textViewNaslov.setText(beleska.getNaslov());
            textViewDatum.setText(beleska.getDatum());
            textViewTekst.setText(beleska.getTekst());
        }
    }
}

