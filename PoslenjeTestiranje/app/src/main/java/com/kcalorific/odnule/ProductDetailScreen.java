package com.kcalorific.odnule;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.kcalorific.odnule.models.Product;

/**
 * ProductDetailScreen - Ekran sa detaljnim informacijama o proizvodu
 * Prikazuje naziv, opis i cenu proizvoda
 */
public class ProductDetailScreen extends AppCompatActivity {

    // Tag za logovanje
    private static final String TAG = "ProductDetailScreen";

    // UI komponente
    private Toolbar toolbar;
    private TextView tvProductDetailName;
    private TextView tvProductDetailDescription;
    private TextView tvProductDetailPrice;
    private Button btnAddToCart;

    private Product product;

    /**
     * onCreate - Inicijalizacija ekrana sa detaljima proizvoda
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Log.d(TAG, "onCreate: ProductDetailScreen kreiran");

        // Inicijalizacija UI komponenti
        initializeViews();

        // Postavljanje Toolbar-a
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Učitavanje podataka o proizvodu
        loadProductData();

        // Prikazivanje podataka
        displayProductData();

        // Postavljanje akcije za dugme "Dodaj u korpu"
        btnAddToCart.setOnClickListener(v -> addToCart());
    }

    /**
     * Inicijalizacija view komponenti
     */
    private void initializeViews() {
        toolbar = findViewById(R.id.toolbarProductDetail);
        tvProductDetailName = findViewById(R.id.tvProductDetailName);
        tvProductDetailDescription = findViewById(R.id.tvProductDetailDescription);
        tvProductDetailPrice = findViewById(R.id.tvProductDetailPrice);
        btnAddToCart = findViewById(R.id.btnAddToCart);
    }

    /**
     * Učitavanje podataka o proizvodu iz Intent-a
     */
    private void loadProductData() {
        Intent intent = getIntent();
        int productId = intent.getIntExtra("PRODUCT_ID", 0);
        String productName = intent.getStringExtra("PRODUCT_NAME");
        String productDescription = intent.getStringExtra("PRODUCT_DESCRIPTION");
        double productPrice = intent.getDoubleExtra("PRODUCT_PRICE", 0.0);

        product = new Product(productId, productName, productDescription, productPrice);
    }

    /**
     * Prikazivanje podataka o proizvodu
     */
    private void displayProductData() {
        if (product != null) {
            tvProductDetailName.setText(product.getName());
            tvProductDetailDescription.setText(product.getDescription());
            tvProductDetailPrice.setText(product.getFormattedPrice());

            // Postavljanje naslova toolbar-a
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(product.getName());
            }
        }
    }

    /**
     * Dodavanje proizvoda u korpu
     */
    private void addToCart() {
        Toast.makeText(this, getString(R.string.product_added_to_cart, product.getName()), Toast.LENGTH_SHORT).show();
    }

    /**
     * Obrada klika na "nazad" dugme u toolbar-u
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ProductDetailScreen postao vidljiv");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ProductDetailScreen se restartuje");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ProductDetailScreen u prvom planu");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ProductDetailScreen pauziran");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ProductDetailScreen zaustavljen");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ProductDetailScreen uništen");
    }
}

