package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final int REQUEST_LOCATION_PERMISSION = 100;
    private static final int REQUEST_CAMERA_PERMISSION = 101;
    
    private ActivityResultLauncher<Intent> cameraLauncher;

    private TextView textViewInfo;
    private ImageButton imageButtonCamera;
    private ImageView imageViewPhoto;
    private Switch switchMusic;
    private Button buttonProduct;

    private LocationManager locationManager;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor gyroscope;

    private float accelX, accelY, accelZ;
    private float gyroX, gyroY, gyroZ;

    private MediaPlayer mediaPlayer;
    private int switchOnCount = 0;

    private Proizvod firstProduct;
    private Proizvod secondProduct;
    
    // Retrofit API services
    private ApiService apiService;
    private ApiService beeceptorApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        initLocation();
        initSensors();
        initMediaPlayer();
        initRetrofit();
        initDummyProducts();
        initCameraLauncher();
        setupListeners();

        requestLocationIfNeeded();
    }

    private void initViews() {
        textViewInfo = findViewById(R.id.textViewInfo);
        imageButtonCamera = findViewById(R.id.imageButtonCamera);
        imageViewPhoto = findViewById(R.id.imageViewPhoto);
        switchMusic = findViewById(R.id.switchMusic);
        buttonProduct = findViewById(R.id.buttonProduct);
    }

    private void initLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    private void initSensors() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        }
    }

    private void initMediaPlayer() {
        mediaPlayer = MediaPlayer.create(this, R.raw.fortnite_default_dance);
    }

    // Task 7: Initialize Retrofit clients
    private void initRetrofit() {
        apiService = RetrofitClient.getApiService();
        beeceptorApiService = RetrofitClient.getBeeceptorApiService();
    }

    private void initDummyProducts() {
        // Placeholder products - will be replaced by API calls
        firstProduct = new Proizvod(1, "Naslov 1", "Opis prvog proizvoda");
        secondProduct = new Proizvod(2, "Naslov 2", "Opis drugog proizvoda");
    }

    private void initCameraLauncher() {
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            Bitmap imageBitmap = (Bitmap) extras.get("data");
                            if (imageBitmap != null) {
                                imageViewPhoto.setImageBitmap(imageBitmap);
                                // Each time the image is replaced, show gyroscope readings in a Toast
                                String msg = "Gyro X: " + String.format("%.2f", gyroX) 
                                        + ", Y: " + String.format("%.2f", gyroY) 
                                        + ", Z: " + String.format("%.2f", gyroZ);
                                Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(this, "Greška: Slika nije dobijena", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "Greška: Podaci nisu dobijeni", Toast.LENGTH_SHORT).show();
                        }
                    } else if (result.getResultCode() == RESULT_CANCELED) {
                        Toast.makeText(this, "Snimanje otkazano", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Greška pri snimanju", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void setupListeners() {
        imageButtonCamera.setOnClickListener(v -> openCamera());

        buttonProduct.setOnClickListener(v -> {
            // Task 8: Fetch all products and update TextView with first product attributes
            fetchAllProducts();
        });

        switchMusic.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                switchOnCount++;
                startMusic();

                // Task 10: On second time switch is turned on, fetch from beeceptor and
                // replace TextView content with title of second post
                if (switchOnCount == 2) {
                    fetchDummyJsonFromBeeceptor();
                }
            } else {
                stopMusic();
            }
        });
    }

    private void requestLocationIfNeeded() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION
            );
        } else {
            startLocationUpdates();
        }
    }

    private void startLocationUpdates() {
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        2000,
                        0,
                        new LocationListener() {
                            @Override
                            public void onLocationChanged(@NonNull Location location) {
                                double lat = location.getLatitude();
                                double lng = location.getLongitude();
                                textViewInfo.setText("Lat: " + lat + ", Lng: " + lng);
                            }
                        }
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION
            );
        } else {
            dispatchTakePictureIntent();
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Pokušaj direktnog pokretanja kamere; sistem će prijaviti grešku ako ne postoji aplikacija
        cameraLauncher.launch(takePictureIntent);
    }

    private void startMusic() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.fortnite_default_dance);
        }
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    private void stopMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (gyroscope != null) {
            sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    // Task 7 & 8: GET method to fetch all products and display first one
    private void fetchAllProducts() {
        // For now, use Beeceptor since main API URL is placeholder
        // When you set up your real backend, change this to use apiService.getAllProducts()
        if (beeceptorApiService == null) {
            Toast.makeText(this, "API servis nije inicijalizovan", Toast.LENGTH_SHORT).show();
            return;
        }
        
        Call<List<Proizvod>> call = beeceptorApiService.getDummyJson();
        call.enqueue(new Callback<List<Proizvod>>() {
            @Override
            public void onResponse(Call<List<Proizvod>> call, Response<List<Proizvod>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    List<Proizvod> products = response.body();
                    firstProduct = products.get(0);
                    
                    // Task 8: Update TextView with first product attributes
                    String text = "ID: " + firstProduct.getId()
                            + "\nTitle: " + firstProduct.getTitle()
                            + "\nDescription: " + firstProduct.getDescription();
                    textViewInfo.setText(text);
                } else {
                    Toast.makeText(MainActivity.this, "Nema proizvoda ili greška u odgovoru (HTTP " + response.code() + ")", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Proizvod>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Greška pri učitavanju proizvoda: " + t.getMessage(), Toast.LENGTH_LONG).show();
                
                // Fallback to dummy data if API fails
                if (firstProduct != null) {
                    String text = "ID: " + firstProduct.getId()
                            + "\nTitle: " + firstProduct.getTitle()
                            + "\nDescription: " + firstProduct.getDescription();
                    textViewInfo.setText(text);
                }
            }
        });
    }

    // Task 7: POST method to add a product (demonstration)
    private void addProduct(Proizvod proizvod) {
        if (apiService == null) {
            Toast.makeText(this, "API servis nije inicijalizovan", Toast.LENGTH_SHORT).show();
            return;
        }
        
        Call<Proizvod> call = apiService.addProduct(proizvod);
        call.enqueue(new Callback<Proizvod>() {
            @Override
            public void onResponse(Call<Proizvod> call, Response<Proizvod> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(MainActivity.this, "Proizvod dodat uspešno", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Greška pri dodavanju proizvoda (HTTP " + response.code() + ")", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Proizvod> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Greška: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Task 10: GET method to fetch dummy JSON from beeceptor and show second post's title
    private void fetchDummyJsonFromBeeceptor() {
        if (beeceptorApiService == null) {
            Toast.makeText(this, "Beeceptor API servis nije inicijalizovan", Toast.LENGTH_SHORT).show();
            return;
        }
        
        Call<List<Proizvod>> call = beeceptorApiService.getDummyJson();
        call.enqueue(new Callback<List<Proizvod>>() {
            @Override
            public void onResponse(Call<List<Proizvod>> call, Response<List<Proizvod>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().size() >= 2) {
                    List<Proizvod> posts = response.body();
                    Proizvod secondPost = posts.get(1); // Second post (index 1)
                    
                    // Task 10: Replace TextView content with title of second post
                    textViewInfo.setText(secondPost.getTitle());
                } else {
                    Toast.makeText(MainActivity.this, "Nema dovoljno postova ili greška u odgovoru (HTTP " + response.code() + ")", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Proizvod>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Greška pri učitavanju sa beeceptor: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            } else {
                Toast.makeText(this, "Dozvola za lokaciju je odbijena", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "Dozvola za kameru je odbijena", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // SensorEventListener implementation
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelX = event.values[0];
            accelY = event.values[1];
            accelZ = event.values[2];

            // Task 9: Button text shows accelerometer values in real time
            String text = String.format("Ax: %.2f Ay: %.2f Az: %.2f", accelX, accelY, accelZ);
            buttonProduct.setText(text);
        } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            gyroX = event.values[0];
            gyroY = event.values[1];
            gyroZ = event.values[2];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used
    }
}
