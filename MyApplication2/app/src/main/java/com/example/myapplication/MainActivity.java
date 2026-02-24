package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 101;

    private EditText editText1;
    private EditText editText2;
    private ImageView imageViewPhoto;
    private CheckBox checkBox;
    private Switch switchRepeat;

    private ActivityResultLauncher<Intent> cameraLauncher;

    private MediaPlayer mediaPlayer;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initCameraLauncher();
        initMediaPlayer();
        initRetrofit();
        setupListeners();
    }

    private void initViews() {
        editText1      = findViewById(R.id.editText1);
        editText2      = findViewById(R.id.editText2);
        imageViewPhoto = findViewById(R.id.imageViewPhoto);
        checkBox       = findViewById(R.id.checkBox);
        switchRepeat   = findViewById(R.id.switchRepeat);
    }

    private void initCameraLauncher() {
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Bundle extras = result.getData().getExtras();
                        if (extras != null) {
                            Bitmap imageBitmap = (Bitmap) extras.get("data");
                            if (imageBitmap != null) {
                                imageViewPhoto.setImageBitmap(imageBitmap);
                            } else {
                                Toast.makeText(this, "Greška: Slika nije dobijena", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else if (result.getResultCode() == RESULT_CANCELED) {
                        Toast.makeText(this, "Snimanje otkazano", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void initMediaPlayer() {
        mediaPlayer = MediaPlayer.create(this, R.raw.fortnite_default_dance);
    }

    private void initRetrofit() {
        apiService = RetrofitClient.getApiService();
    }

    private void setupListeners() {
        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString().trim();

                if (input.equalsIgnoreCase("kamera")) {
                    openCamera();
                    return;
                }

                try {
                    int commentId = Integer.parseInt(input);
                    if (commentId > 0) {
                        fetchComment(commentId);
                    }
                } catch (NumberFormatException e) {
                }
            }
        });

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                playSound();
            } else {
                stopSound();
            }
        });
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
            launchCamera();
        }
    }

    private void launchCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(takePictureIntent);
    }

    private void playSound() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.fortnite_default_dance);
        }
        if (mediaPlayer != null) {
            boolean repeat = switchRepeat.isChecked();
            mediaPlayer.setLooping(repeat);
            mediaPlayer.seekTo(0);
            mediaPlayer.start();
        }
    }

    private void stopSound() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
        }
    }

    private void fetchComment(int id) {
        Call<Comment> call = apiService.getComment(id);
        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(@NonNull Call<Comment> call, @NonNull Response<Comment> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Comment comment = response.body();
                    editText2.setText(comment.getBody());
                } else {
                    Toast.makeText(MainActivity.this,
                            "Komentar nije pronađen (HTTP " + response.code() + ")",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Comment> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this,
                        "Greška pri učitavanju komentara: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
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
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchCamera();
            } else {
                Toast.makeText(this, "Dozvola za kameru je odbijena", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
