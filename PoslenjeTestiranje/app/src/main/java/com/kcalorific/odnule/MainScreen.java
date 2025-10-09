package com.kcalorific.odnule;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kcalorific.odnule.models.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * MainScreen - Fragment sa prikazom svih proizvoda
 * Omogućava pretragu, filtriranje i prikaz proizvoda
 */
public class MainScreen extends Fragment {

    private EditText etSearch;
    private Spinner spinnerFilter;
    private ListView lvProducts;
    
    private List<Product> allProducts;
    private List<Product> filteredProducts;
    private ProductAdapter productAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // Inicijalizacija view komponenti
        initializeViews(view);

        // Učitavanje mokap podataka
        loadMockProducts();

        // Postavljanje adapter-a za ListView
        setupProductsList();

        // Postavljanje pretrage
        setupSearch();

        // Postavljanje filtriranja
        setupFilter();

        return view;
    }

    /**
     * Inicijalizacija view komponenti
     */
    private void initializeViews(View view) {
        etSearch = view.findViewById(R.id.etSearch);
        spinnerFilter = view.findViewById(R.id.spinnerFilter);
        lvProducts = view.findViewById(R.id.lvProducts);
    }

    /**
     * Učitavanje mokap podataka za proizvode
     */
    private void loadMockProducts() {
        allProducts = new ArrayList<>();
        allProducts.add(new Product(1, "Laptop Dell XPS 15", "Visokokvalitetan laptop sa Intel i7 procesorom, 16GB RAM, 512GB SSD", 150000));
        allProducts.add(new Product(2, "Smartphone Samsung Galaxy S23", "Android telefon sa 128GB memorije, AMOLED ekranom", 80000));
        allProducts.add(new Product(3, "Slušalice Sony WH-1000XM5", "Bežične slušalice sa aktivnom redukcijom buke", 35000));
        allProducts.add(new Product(4, "Tastatura Logitech MX Keys", "Mehanička tastatura sa pozadinskim osvetljenjem", 12000));
        allProducts.add(new Product(5, "Miš Logitech MX Master 3", "Ergonomski bežični miš za profesionalce", 9000));
        allProducts.add(new Product(6, "Monitor LG UltraWide 34''", "34-inčni ultraširoki monitor, 3440x1440 rezolucija", 55000));
        allProducts.add(new Product(7, "Webcam Logitech C920", "Full HD web kamera za video pozive", 8000));
        allProducts.add(new Product(8, "Eksterni HDD Seagate 2TB", "Prenosni eksterni hard disk 2TB", 7000));
        allProducts.add(new Product(9, "USB-C Hub Anker", "7-u-1 USB-C hub sa HDMI i USB portovima", 4500));
        allProducts.add(new Product(10, "Tablet Apple iPad Air", "10.9'' tablet sa M1 čipom, 256GB", 75000));

        // Kopiraj sve proizvode u filtrirane proizvode
        filteredProducts = new ArrayList<>(allProducts);
    }

    /**
     * Postavljanje liste proizvoda
     */
    private void setupProductsList() {
        productAdapter = new ProductAdapter();
        lvProducts.setAdapter(productAdapter);

        // Klik na proizvod
        lvProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = filteredProducts.get(position);
                openProductDetails(product);
            }
        });
    }

    /**
     * Postavljanje funkcionalnosti pretrage
     */
    private void setupSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterProducts(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * Postavljanje filtriranja po ceni
     */
    private void setupFilter() {
        String[] filterOptions = {"Svi proizvodi", "Do 10,000 RSD", "10,000 - 50,000 RSD", "50,000+ RSD"};
        ArrayAdapter<String> filterAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, filterOptions);
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilter.setAdapter(filterAdapter);

        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                applyPriceFilter(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * Filtriranje proizvoda po nazivu
     */
    private void filterProducts(String query) {
        filteredProducts.clear();
        
        if (query.isEmpty()) {
            filteredProducts.addAll(allProducts);
        } else {
            for (Product product : allProducts) {
                if (product.getName().toLowerCase().contains(query.toLowerCase()) ||
                    product.getDescription().toLowerCase().contains(query.toLowerCase())) {
                    filteredProducts.add(product);
                }
            }
        }
        
        productAdapter.notifyDataSetChanged();
    }

    /**
     * Filtriranje proizvoda po ceni
     */
    private void applyPriceFilter(int filterPosition) {
        String searchQuery = etSearch.getText().toString();
        filteredProducts.clear();

        for (Product product : allProducts) {
            boolean matchesSearch = searchQuery.isEmpty() || 
                                  product.getName().toLowerCase().contains(searchQuery.toLowerCase()) ||
                                  product.getDescription().toLowerCase().contains(searchQuery.toLowerCase());

            boolean matchesPrice = false;
            switch (filterPosition) {
                case 0: // Svi proizvodi
                    matchesPrice = true;
                    break;
                case 1: // Do 10,000
                    matchesPrice = product.getPrice() < 10000;
                    break;
                case 2: // 10,000 - 50,000
                    matchesPrice = product.getPrice() >= 10000 && product.getPrice() <= 50000;
                    break;
                case 3: // 50,000+
                    matchesPrice = product.getPrice() > 50000;
                    break;
            }

            if (matchesSearch && matchesPrice) {
                filteredProducts.add(product);
            }
        }

        productAdapter.notifyDataSetChanged();
    }

    /**
     * Otvaranje detalja proizvoda
     */
    private void openProductDetails(Product product) {
        Intent intent = new Intent(getActivity(), ProductDetailScreen.class);
        intent.putExtra("PRODUCT_ID", product.getId());
        intent.putExtra("PRODUCT_NAME", product.getName());
        intent.putExtra("PRODUCT_DESCRIPTION", product.getDescription());
        intent.putExtra("PRODUCT_PRICE", product.getPrice());
        startActivity(intent);
    }

    /**
     * Custom adapter za prikaz proizvoda u ListView-u
     */
    private class ProductAdapter extends ArrayAdapter<Product> {

        public ProductAdapter() {
            super(requireContext(), R.layout.item_product, filteredProducts);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_product, parent, false);
            }

            Product product = filteredProducts.get(position);

            TextView tvProductName = convertView.findViewById(R.id.tvProductName);
            TextView tvProductDescription = convertView.findViewById(R.id.tvProductDescription);
            TextView tvProductPrice = convertView.findViewById(R.id.tvProductPrice);

            tvProductName.setText(product.getName());
            tvProductDescription.setText(product.getDescription());
            tvProductPrice.setText(product.getFormattedPrice());

            return convertView;
        }

        @Override
        public int getCount() {
            return filteredProducts.size();
        }
    }
}

