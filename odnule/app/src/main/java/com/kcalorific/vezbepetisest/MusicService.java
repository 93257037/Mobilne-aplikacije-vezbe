package com.kcalorific.vezbepetisest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

/**
 * Servis za puštanje muzike i prikazivanje notifikacija
 * Ovaj servis pokreće melodiju i prikazuje notifikaciju sa dugmetom "Ispiši"
 */
public class MusicService extends Service {

    // Konstante za notifikacije
    private static final String CHANNEL_ID = "music_channel";
    private static final String CHANNEL_NAME = "Muzički kanal";
    private static final int NOTIFICATION_ID = 1;
    private static final String TAG = "MusicService";

    // MediaPlayer za puštanje muzike
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Servis kreiran");
        
        // Kreiranje kanala za notifikacije (potrebno za Android 8.0+)
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Servis pokrenut");

        try {
            // PRVO pokrenuti kao foreground service (mora biti odmah!)
            showNotification();
            
            // ZATIM pokrenuti muziku
            startMusic();
        } catch (Exception e) {
            Log.e(TAG, "Greška pri pokretanju servisa: " + e.getMessage());
            e.printStackTrace();
            stopSelf(); // Zaustaviti servis ako dođe do greške
        }

        // Servis će se restartovati ako sistem zaustavi proces
        return START_STICKY;
    }

    /**
     * Metoda za pokretanje muzike
     * Koristi sistemski notification zvuk
     * Za custom muziku, staviti MP3 fajl u res/raw folder i zameniti URI
     */
    private void startMusic() {
        try {
            // Oslobađanje prethodnog MediaPlayer-a ako postoji
            if (mediaPlayer != null) {
                mediaPlayer.release();
            }

            // Kreiranje novog MediaPlayer-a sa sistemskim notification zvukom
            // Za custom muziku, kreirati fajl npr. music.mp3 u res/raw/ folderu
            // i koristiti: MediaPlayer.create(this, R.raw.music)
            
            android.net.Uri notificationSound = android.provider.Settings.System.DEFAULT_NOTIFICATION_URI;
            mediaPlayer = MediaPlayer.create(this, notificationSound);
            
            if (mediaPlayer != null) {
                mediaPlayer.setLooping(true); // Beskonačno ponavljanje
                mediaPlayer.start();
                Log.d(TAG, "Muzika pokrenuta");
            } else {
                Log.e(TAG, "MediaPlayer nije mogao biti kreiran");
            }
        } catch (Exception e) {
            Log.e(TAG, "Greška pri pokretanju muzike: " + e.getMessage());
        }
    }

    /**
     * Kreiranje kanala za notifikacije
     * Obavezno za Android 8.0 (API 26) i novije verzije
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("Kanal za obaveštenja o muzici");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
                Log.d(TAG, "Notifikacijski kanal kreiran");
            }
        }
    }

    /**
     * Prikazivanje notifikacije sa dugmetom "Ispiši"
     */
    private void showNotification() {
        try {
            // Intent za BroadcastReceiver koji će biti pozvan klikom na dugme
            Intent printIntent = new Intent(this, PrintBroadcastReceiver.class);
            printIntent.setAction("ACTION_PRINT");
            
            PendingIntent printPendingIntent = PendingIntent.getBroadcast(
                    this,
                    0,
                    printIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );

            // Intent za otvaranje aplikacije klikom na notifikaciju
            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(
                    this,
                    0,
                    notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );

            // Kreiranje notifikacije
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Melodija puštena!")
                    .setContentText("Muzika se trenutno pušta u pozadini")
                    .setSmallIcon(android.R.drawable.ic_media_play)
                    .setContentIntent(contentIntent)
                    .addAction(android.R.drawable.ic_menu_info_details, "Ispiši", printPendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(false) // Notifikacija se ne uklanja klikom
                    .build();

            // Pokretanje kao foreground service (obavezno za Android 8.0+)
            // Za Android 14+ mora biti pozvan odmah u onStartCommand()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // Android 10+ zahteva foregroundServiceType
                startForeground(NOTIFICATION_ID, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK);
            } else {
                startForeground(NOTIFICATION_ID, notification);
            }
            
            Log.d(TAG, "Notifikacija prikazana, servis pokrenut kao foreground");
        } catch (Exception e) {
            Log.e(TAG, "Greška pri kreiranju notifikacije: " + e.getMessage());
            e.printStackTrace();
            throw e; // Propagiramo grešku da bi onStartCommand mogao da je uhvati
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        
        try {
            // Zaustavljanje i oslobađanje MediaPlayer-a
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer.release();
                mediaPlayer = null;
                Log.d(TAG, "Muzika zaustavljena");
            }
        } catch (Exception e) {
            Log.e(TAG, "Greška pri zaustavljanju muzike: " + e.getMessage());
        }

        Log.d(TAG, "Servis uništen");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Ne koristimo vezani servis
        return null;
    }
}

