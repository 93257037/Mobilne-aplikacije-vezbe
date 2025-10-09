# Android Aplikacija - Kompletan Vodič

## Pregled Aplikacije

Ova Android aplikacija demonstrira sledeće funkcionalnosti:

1. **MusicService** - Servis koji pušta muziku u pozadini sa notifikacijom
2. **Notifikacije** - Kreiran kanal za notifikacije sa interaktivnim dugmetom "Ispiši"
3. **BroadcastReceiver** - Prima broadcast signale i izvršava akcije (ispis u logcat, bojenje teksta)
4. **TestActivity** - Aktivnost sa tekstom "Oboji me!" koji se boji u crveno
5. **SQLite Baza podataka** - Čuvanje korisnika sa CRUD operacijama
6. **SharedPreferences** - Čuvanje podataka o ulogovanom korisniku i podešavanjima sinhronizacije
7. **ContentProvider** - Učitavanje kontakata sa uređaja

---

## Struktura Projekta

### Java Klase

```
com.kcalorific.vezbepetisest/
├── MainActivity.java              // Glavna aktivnost
├── LoginActivity.java             // Aktivnost za prijavu korisnika
├── TestActivity.java              // Aktivnost sa tekstom "Oboji me!"
├── SettingsActivity.java          // Podešavanja sinhronizacije
├── ContactsActivity.java          // Prikaz kontakata (ContentProvider)
├── MusicService.java              // Servis za puštanje muzike
├── PrintBroadcastReceiver.java   // BroadcastReceiver za notifikacije
├── Korisnik.java                  // Model klasa za korisnika
├── DatabaseHelper.java            // SQLite baza - CRUD operacije
└── PreferencesManager.java        // Manager za SharedPreferences
```

### Layout Fajlovi

```
res/layout/
├── activity_main.xml              // Layout za MainActivity
├── activity_login.xml             // Layout za LoginActivity
├── activity_test.xml              // Layout za TestActivity
├── activity_settings.xml          // Layout za SettingsActivity
└── activity_contacts.xml          // Layout za ContactsActivity
```

---

## Detaljno Objašnjenje Implementacije

### 1. MusicService (Servis za Muziku)

**Lokacija:** `app/src/main/java/com/kcalorific/vezbepetisest/MusicService.java`

**Funkcionalnost:**
- Pokreće melodiju koristeći `MediaPlayer`
- Radi kao foreground service (Android 8.0+)
- Prikazuje notifikaciju sa naslovom "Melodija puštena!"
- Notifikacija sadrži dugme "Ispiši" koje šalje broadcast

**Ključni kod:**
```java
// Kreiranje MediaPlayer-a
mediaPlayer = MediaPlayer.create(this, android.R.raw.default_ringtone);
mediaPlayer.setLooping(true);
mediaPlayer.start();

// Pokretanje kao foreground service
startForeground(NOTIFICATION_ID, notification);
```

**Napomena:** Koristi sistemski ringtone. Za custom muziku, dodati MP3 fajl u `res/raw/` folder.

---

### 2. Kanal za Notifikacije

**Lokacija:** `MusicService.java` - metoda `createNotificationChannel()`

**Funkcionalnost:**
- Kreira kanal sa nazivom "Muzički kanal"
- Obavezan za Android 8.0 (API 26) i novije verzije
- Koristi se za sve notifikacije servisa

**Ključni kod:**
```java
NotificationChannel channel = new NotificationChannel(
    CHANNEL_ID,
    CHANNEL_NAME,
    NotificationManager.IMPORTANCE_DEFAULT
);
notificationManager.createNotificationChannel(channel);
```

---

### 3. Notifikacija sa Dugmetom "Ispiši"

**Funkcionalnost:**
- Prikazuje notifikaciju sa tekstom "Melodija puštena!"
- Sadrži akciono dugme "Ispiši"
- Klik na dugme šalje broadcast `ACTION_PRINT`

**Ključni kod:**
```java
.addAction(android.R.drawable.ic_menu_info_details, "Ispiši", printPendingIntent)
```

---

### 4. PrintBroadcastReceiver

**Lokacija:** `app/src/main/java/com/kcalorific/vezbepetisest/PrintBroadcastReceiver.java`

**Funkcionalnost:**
- Prima broadcast kada se klikne na dugme "Ispiši" u notifikaciji
- Ispisuje poruku "Pozdrav iz servisa!" u logcat
- Šalje novi broadcast `ACTION_COLOR_TEXT` za bojenje teksta u TestActivity

**Ključni kod:**
```java
Log.d(TAG, "Pozdrav iz servisa!");
Intent colorIntent = new Intent("ACTION_COLOR_TEXT");
context.sendBroadcast(colorIntent);
```

---

### 5. TestActivity

**Lokacija:** `app/src/main/java/com/kcalorific/vezbepetisest/TestActivity.java`

**Funkcionalnost:**
- Prikazuje tekst "Oboji me!"
- Registruje `ColorBroadcastReceiver` koji osluškuje `ACTION_COLOR_TEXT`
- Kada primi broadcast, boji tekst u crveno

**Ključni kod:**
```java
// Registracija BroadcastReceiver-a
colorReceiver = new ColorBroadcastReceiver();
IntentFilter filter = new IntentFilter("ACTION_COLOR_TEXT");
registerReceiver(colorReceiver, filter);

// Bojenje teksta
colorTextView.setTextColor(Color.RED);
```

---

### 6. SQLite Baza Podataka

**Lokacija:** `app/src/main/java/com/kcalorific/vezbepetisest/DatabaseHelper.java`

**Funkcionalnost:**
- Kreira tabelu `korisnici` sa kolonama:
  - `id` (PRIMARY KEY)
  - `korisnicko_ime` (UNIQUE)
  - `lozinka`
  - `email`
  - `ime_prezime`
- Implementira sve CRUD operacije

**CRUD Metode:**

#### CREATE - Dodavanje korisnika
```java
long id = databaseHelper.dodajKorisnika(korisnik);
```

#### READ - Čitanje korisnika
```java
Korisnik korisnik = databaseHelper.dohvatiKorisnika(id);
List<Korisnik> sviKorisnici = databaseHelper.dohvatiSveKorisnike();
```

#### UPDATE - Ažuriranje korisnika
```java
int rowsAffected = databaseHelper.azurirajKorisnika(korisnik);
```

#### DELETE - Brisanje korisnika
```java
int rowsAffected = databaseHelper.obrisiKorisnika(id);
```

**Test korisnici:**
- **admin** / admin123
- **korisnik1** / pass123

---

### 7. SharedPreferences - Ulogovani Korisnik

**Lokacija:** `app/src/main/java/com/kcalorific/vezbepetisest/PreferencesManager.java`

**Funkcionalnost:**
- Čuva podatke o trenutno ulogovanom korisniku
- Omogućava proveru da li je korisnik ulogovan
- Na osnovu toga prikazuje odgovarajuće ekrane

**Ključne metode:**
```java
// Čuvanje korisnika
preferencesManager.sacuvajUlogovanogKorisnika(korisnik);

// Provera da li je ulogovan
boolean isLoggedIn = preferencesManager.isUserLoggedIn();

// Dohvatanje podataka
String username = preferencesManager.getUsername();
String fullName = preferencesManager.getFullName();

// Odjava
preferencesManager.odjaviKorisnika();
```

---

### 8. SharedPreferences - Podešavanja Sinhronizacije

**Funkcionalnost:**
- Čuva interval sinhronizacije
- Dostupni intervali:
  - **Nikad** (0)
  - **Svakog 1 minuta** (60,000 ms)
  - **Svakih 15 minuta** (900,000 ms)
  - **Svakih 30 minuta** (1,800,000 ms)

**Ključne metode:**
```java
// Postavljanje intervala
preferencesManager.postaviIntervalSinhronizacije(PreferencesManager.SYNC_15_MIN);

// Dohvatanje intervala
long interval = preferencesManager.getIntervalSinhronizacije();

// Provera da li je omogućeno
boolean enabled = preferencesManager.isSyncEnabled();
```

**Ekran:** `SettingsActivity` omogućava korisniku da odabere interval putem radio button-a.

---

### 9. ContentProvider - Učitavanje Kontakata

**Lokacija:** `app/src/main/java/com/kcalorific/vezbepetisest/ContactsActivity.java`

**Funkcionalnost:**
- Učitava kontakte sa uređaja koristeći `ContactsContract` ContentProvider
- Traži dozvolu `READ_CONTACTS`
- Prikazuje prvih 20 kontakata sa imenima i brojevima telefona

**Ključni kod:**
```java
// Query za kontakte
Cursor cursor = getContentResolver().query(
    ContactsContract.Contacts.CONTENT_URI,
    projection,
    null,
    null,
    ContactsContract.Contacts.DISPLAY_NAME + " ASC"
);

// Query za brojeve telefona
Cursor phoneCursor = getContentResolver().query(
    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
    null,
    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
    new String[]{contactId},
    null
);
```

**Dozvole:** Aplikacija traži dozvolu u runtime-u (Android 6.0+).

---

### 10. MainActivity - Glavni Ekran

**Funkcionalnost:**
- Proverava da li je korisnik ulogovan
- Prikazuje dva glavna dugmeta:
  1. **"Pokreni muziku i notifikaciju"** - Pokreće `MusicService`
  2. **"Idi na TestActivity"** - Otvara `TestActivity`
- Dodatne opcije:
  - **Podešavanja sinhronizacije** - Otvara `SettingsActivity`
  - **Prikaži kontakte** - Otvara `ContactsActivity`
  - **Odjavi se** - Odjavljivanje korisnika

**Prikaz informacija:**
- Poruka dobrodošlice sa imenom korisnika
- Trenutni interval sinhronizacije

---

## Korišćenje Aplikacije

### Tok Rada

1. **Pokretanje aplikacije**
   - Otvara se `LoginActivity`
   - Unesite kredencijale (admin/admin123 ili korisnik1/pass123)
   - Kliknite "Prijavi se"

2. **Glavna aktivnost (MainActivity)**
   - Vidite poruku dobrodošlice sa vašim imenom
   - Vidite trenutno podešavanje sinhronizacije

3. **Pokretanje muzike**
   - Kliknite na "Pokreni muziku i notifikaciju"
   - Melodija će se pokrenuti u pozadini
   - Pojaviće se notifikacija "Melodija puštena!"

4. **Testiranje BroadcastReceiver-a**
   - Prvo otvorite `TestActivity` klikom na "Idi na TestActivity"
   - Vidite tekst "Oboji me!" (crne boje)
   - Idite u notification drawer
   - Kliknite na dugme "Ispiši" u notifikaciji
   - U logcat-u će se ispisati: "Pozdrav iz servisa!"
   - Tekst "Oboji me!" će postati crven

5. **Podešavanje sinhronizacije**
   - Kliknite na "Podešavanja sinhronizacije"
   - Odaberite interval (Nikad, 1 min, 15 min, 30 min)
   - Kliknite "Sačuvaj podešavanja"
   - Vratite se na glavni ekran - videćete ažurirani interval

6. **Pregled kontakata**
   - Kliknite na "Prikaži kontakte (ContentProvider)"
   - Odobrite dozvolu za čitanje kontakata
   - Videćete prvih 20 kontakata sa uređaja

7. **Odjava**
   - Kliknite na "Odjavi se"
   - Vraćate se na `LoginActivity`

---

## Instalacija i Pokretanje

### Preduslovi

- Android Studio (preporučena verzija: Hedgehog ili novija)
- Android SDK (minSdk: 34, targetSdk: 35)
- Android uređaj ili emulator sa Android 14+

### Koraci za Pokretanje

#### 1. Otvaranje Projekta

```bash
1. Otvorite Android Studio
2. File → Open
3. Izaberite folder projekta (odnule)
4. Sačekajte da se Gradle sinhronizuje
```

#### 2. Kreiranje Emulatora (Opciono)

```bash
1. Tools → Device Manager
2. Create Device
3. Odaberite Pixel 4 ili noviji
4. System Image: API 34 (UpsideDownCake) ili 35
5. Finish
```

#### 3. Pokretanje Aplikacije

```bash
1. Odaberite uređaj/emulator iz dropdown menija
2. Kliknite Run ▶ (zeleni play dugme)
3. Ili: Shift + F10
```

#### 4. Instalacija APK-a (Alternativa)

```bash
1. Build → Build Bundle(s) / APK(s) → Build APK(s)
2. Sačekajte da se build završi
3. Locate APK u: app/build/outputs/apk/debug/app-debug.apk
4. Prebacite APK na Android uređaj
5. Instalirajte i pokrenite
```

---

## Testiranje Funkcionalnosti

### 1. Test MusicService-a

**Koraci:**
1. Prijavite se u aplikaciju
2. Kliknite "Pokreni muziku i notifikaciju"
3. **Očekivani rezultat:**
   - Muzika počinje da svira
   - Notifikacija se pojavljuje
   - Toast poruka: "MusicService pokrenut!"

**Provera logcat-a:**
```
MusicService: Servis kreiran
MusicService: Muzika pokrenuta
MusicService: Notifikacija prikazana, servis pokrenut kao foreground
```

---

### 2. Test Notifikacije i BroadcastReceiver-a

**Koraci:**
1. Pokrenite MusicService
2. Otvorite TestActivity
3. Povucite notification drawer nadole
4. Kliknite na dugme "Ispiši"

**Očekivani rezultat:**
- U logcat-u: "Pozdrav iz servisa!"
- Tekst "Oboji me!" postaje crven

**Provera logcat-a:**
```
PrintBroadcastReceiver: Pozdrav iz servisa!
PrintBroadcastReceiver: Broadcast poslat za bojenje teksta
TestActivity: Tekst obojen u crveno
```

---

### 3. Test Baze Podataka (CRUD)

**Test prijavljivanja:**
```
1. Korisničko ime: admin
2. Lozinka: admin123
3. Kliknite "Prijavi se"
```

**Očekivani rezultat:**
- Toast: "Uspešno prijavljivanje!"
- Prelazak na MainActivity

**Provera logcat-a:**
```
DatabaseHelper: Provera logovanja za admin: true
LoginActivity: Korisnik admin uspešno prijavljen
```

**Test dodavanja korisnika (programski):**
```java
Korisnik noviKorisnik = new Korisnik("test", "test123", "test@example.com", "Test Korisnik");
long id = databaseHelper.dodajKorisnika(noviKorisnik);
```

---

### 4. Test SharedPreferences

**Test sinhronizacije:**
1. Kliknite "Podešavanja sinhronizacije"
2. Odaberite "Svakih 15 minuta"
3. Kliknite "Sačuvaj podešavanja"
4. Vratite se na MainActivity

**Očekivani rezultat:**
- Toast: "Podešavanja sačuvana!"
- TextView prikazuje: "Sinhronizacija: Svakih 15 minuta"

**Provera logcat-a:**
```
PreferencesManager: Interval sinhronizacije postavljen na: Svakih 15 minuta
```

---

### 5. Test ContentProvider-a

**Koraci:**
1. Kliknite "Prikaži kontakte (ContentProvider)"
2. Dozvolite pristup kontaktima (ako je potrebno)

**Očekivani rezultat:**
- Lista kontakata sa uređaja
- Prikazani su naziv i broj telefona
- Toast: "Učitano X kontakata"

**Provera logcat-a:**
```
ContactsActivity: Učitano 20 kontakata
```

**Napomena:** Ako emulator nema kontakte, dodajte testne kontakte:
```bash
Contacts app → + → Dodajte ime i broj
```

---

## AndroidManifest.xml - Ključne Stavke

### Dozvole

```xml
<!-- Čitanje kontakata -->
<uses-permission android:name="android.permission.READ_CONTACTS" />

<!-- Foreground service -->
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />

<!-- Post notifikacije (Android 13+) -->
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
```

### Aktivnosti

```xml
<!-- LoginActivity - LAUNCHER (početni ekran) -->
<activity android:name=".LoginActivity" android:exported="true">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity>

<!-- Ostale aktivnosti -->
<activity android:name=".MainActivity" android:exported="false" />
<activity android:name=".TestActivity" android:exported="false" />
<activity android:name=".SettingsActivity" android:exported="false" />
<activity android:name=".ContactsActivity" android:exported="false" />
```

### Servis

```xml
<service
    android:name=".MusicService"
    android:enabled="true"
    android:exported="false"
    android:foregroundServiceType="mediaPlayback" />
```

### BroadcastReceiver

```xml
<receiver
    android:name=".PrintBroadcastReceiver"
    android:enabled="true"
    android:exported="false" />
```

---

## Kako Rekreirati Projekat od Nule

### Korak 1: Kreiranje Projekta

1. Otvorite Android Studio
2. **New Project**
3. Odaberite **Empty Views Activity**
4. Postavite:
   - Name: `VezbePetISest`
   - Package name: `com.kcalorific.vezbepetisest`
   - Language: **Java**
   - Minimum SDK: **API 34 (UpsideDownCake)**
5. Kliknite **Finish**

---

### Korak 2: Kreiranje Model Klase

**Fajl:** `app/src/main/java/com/kcalorific/vezbepetisest/Korisnik.java`

```java
public class Korisnik {
    private int id;
    private String korisnickoIme;
    private String lozinka;
    private String email;
    private String imePrezime;
    
    // Konstruktori, getteri, setteri
}
```

---

### Korak 3: Kreiranje DatabaseHelper

**Fajl:** `app/src/main/java/com/kcalorific/vezbepetisest/DatabaseHelper.java`

- Ekstenduje `SQLiteOpenHelper`
- Implementira `onCreate()` i `onUpgrade()`
- CRUD metode:
  - `dodajKorisnika()`
  - `dohvatiKorisnika()`
  - `azurirajKorisnika()`
  - `obrisiKorisnika()`

---

### Korak 4: Kreiranje PreferencesManager

**Fajl:** `app/src/main/java/com/kcalorific/vezbepetisest/PreferencesManager.java`

- Upravlja `SharedPreferences`
- Metode za korisnika:
  - `sacuvajUlogovanogKorisnika()`
  - `isUserLoggedIn()`
  - `odjaviKorisnika()`
- Metode za sinhronizaciju:
  - `postaviIntervalSinhronizacije()`
  - `getIntervalSinhronizacije()`

---

### Korak 5: Kreiranje MusicService

**Fajl:** `app/src/main/java/com/kcalorific/vezbepetisest/MusicService.java`

- Ekstenduje `Service`
- Metode:
  - `onCreate()` - kreira kanal za notifikacije
  - `onStartCommand()` - pokreće muziku i notifikaciju
  - `startMusic()` - koristi `MediaPlayer`
  - `createNotificationChannel()`
  - `showNotification()` - `startForeground()`
  - `onDestroy()` - oslobađa resurse

---

### Korak 6: Kreiranje BroadcastReceiver-a

**Fajl:** `app/src/main/java/com/kcalorific/vezbepetisest/PrintBroadcastReceiver.java`

- Ekstenduje `BroadcastReceiver`
- `onReceive()` metoda:
  - Ispisuje "Pozdrav iz servisa!" u logcat
  - Šalje broadcast `ACTION_COLOR_TEXT`

---

### Korak 7: Kreiranje Aktivnosti

#### LoginActivity

**Java:** `app/src/main/java/com/kcalorific/vezbepetisest/LoginActivity.java`
**Layout:** `app/src/main/res/layout/activity_login.xml`

- EditText za korisničko ime
- EditText za lozinku
- Button za prijavljivanje
- Validacija kredencijala kroz bazu
- Čuvanje u SharedPreferences

#### MainActivity

**Java:** `app/src/main/java/com/kcalorific/vezbepetisest/MainActivity.java`
**Layout:** `app/src/main/res/layout/activity_main.xml`

- TextView za dobrodošlicu
- Button za pokretanje MusicService-a
- Button za TestActivity
- Button za podešavanja
- Button za kontakte
- Button za odjavu

#### TestActivity

**Java:** `app/src/main/java/com/kcalorific/vezbepetisest/TestActivity.java`
**Layout:** `app/src/main/res/layout/activity_test.xml`

- TextView sa tekstom "Oboji me!"
- Registracija `ColorBroadcastReceiver-a`
- Bojenje teksta na `ACTION_COLOR_TEXT`

#### SettingsActivity

**Java:** `app/src/main/java/com/kcalorific/vezbepetisest/SettingsActivity.java`
**Layout:** `app/src/main/res/layout/activity_settings.xml`

- RadioGroup sa opcijama intervala
- Button za čuvanje
- TextView za prikaz trenutnog intervala

#### ContactsActivity

**Java:** `app/src/main/java/com/kcalorific/vezbepetisest/ContactsActivity.java`
**Layout:** `app/src/main/res/layout/activity_contacts.xml`

- ListView za prikaz kontakata
- Traženje dozvole `READ_CONTACTS`
- Query ContactsContract ContentProvider-a

---

### Korak 8: Ažuriranje AndroidManifest.xml

1. Dodajte dozvole na vrhu:
```xml
<uses-permission android:name="android.permission.READ_CONTACTS" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
```

2. Registrujte sve aktivnosti:
```xml
<activity android:name=".LoginActivity" android:exported="true">
    <!-- LAUNCHER intent filter -->
</activity>
<activity android:name=".MainActivity" android:exported="false" />
<!-- ... ostale aktivnosti -->
```

3. Registrujte servis:
```xml
<service
    android:name=".MusicService"
    android:foregroundServiceType="mediaPlayback" />
```

4. Registrujte BroadcastReceiver:
```xml
<receiver android:name=".PrintBroadcastReceiver" />
```

---

### Korak 9: Build i Testiranje

1. **Sync Project with Gradle Files**
2. **Build → Rebuild Project**
3. Pokrenite aplikaciju
4. Testirajte sve funkcionalnosti

---

## Česti Problemi i Rešenja

### Problem 1: Muzika ne svira

**Rešenje:**
- Proverite da li je uređaj/emulator zvuk uključen
- Za custom muziku, dodajte MP3 fajl u `res/raw/` folder:
```java
mediaPlayer = MediaPlayer.create(this, R.raw.vas_audio_fajl);
```

---

### Problem 2: Notifikacija se ne prikazuje

**Rešenje:**
- Proverite da li je kanal kreiran (`createNotificationChannel()`)
- Za Android 13+, tražite dozvolu:
```java
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this,
            new String[]{Manifest.permission.POST_NOTIFICATIONS}, 100);
    }
}
```

---

### Problem 3: Tekst se ne boji u crveno

**Rešenje:**
- Proverite da li je TestActivity otvorena kada kliknete "Ispiši"
- BroadcastReceiver mora biti registrovan dok je TestActivity aktivna
- Proverite logcat za poruke

---

### Problem 4: Kontakti se ne učitavaju

**Rešenje:**
- Odobrite dozvolu za čitanje kontakata
- Emulator možda nema kontakte - dodajte testne kontakte
- Proverite da li je dozvola u Manifest-u

---

### Problem 5: Crash pri prijavljivanju

**Rešenje:**
- Proverite da li je baza kreirana
- Proverite da li test korisnici postoje
- Proverite logcat za stack trace

---

## Napredne Funkcionalnosti (Opciono)

### 1. Implementacija Pravog Sync Servisa

```java
public class SyncService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        PreferencesManager pm = new PreferencesManager(this);
        long interval = pm.getIntervalSinhronizacije();
        
        if (interval != PreferencesManager.SYNC_NEVER) {
            // Izvršiti sinhronizaciju podataka
            performSync();
        }
        
        return START_NOT_STICKY;
    }
    
    private void performSync() {
        // Implementacija sinhronizacije sa serverom
    }
}
```

---

### 2. WorkManager za Periodične Zadatke

Dodajte zavisnost u `build.gradle`:
```gradle
implementation "androidx.work:work-runtime:2.8.1"
```

Kreirajte Worker:
```java
public class SyncWorker extends Worker {
    public SyncWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Sinhronizacija podataka
        return Result.success();
    }
}
```

Zakažite periodični posao:
```java
PeriodicWorkRequest syncWorkRequest =
    new PeriodicWorkRequest.Builder(SyncWorker.class, 15, TimeUnit.MINUTES)
        .build();

WorkManager.getInstance(context).enqueue(syncWorkRequest);
```

---

### 3. Enkripcija Lozinki

Koristite `BCrypt` ili `MessageDigest`:

```java
public static String hashPassword(String password) {
    try {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException(e);
    }
}
```

---

### 4. Room Database (Umesto SQLiteOpenHelper)

Dodajte zavisnost:
```gradle
implementation "androidx.room:room-runtime:2.5.2"
annotationProcessor "androidx.room:room-compiler:2.5.2"
```

Entity:
```java
@Entity(tableName = "korisnici")
public class Korisnik {
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    @ColumnInfo(name = "korisnicko_ime")
    private String korisnickoIme;
    
    // ... ostali atributi
}
```

DAO:
```java
@Dao
public interface KorisnikDao {
    @Insert
    long insert(Korisnik korisnik);
    
    @Query("SELECT * FROM korisnici WHERE id = :id")
    Korisnik getById(int id);
    
    @Update
    int update(Korisnik korisnik);
    
    @Delete
    int delete(Korisnik korisnik);
}
```

---

## Zaključak

Ova aplikacija demonstrira sve ključne Android koncepte:

✅ **Service** - MusicService za pozadinski rad  
✅ **Notifikacije** - Sa kanalima i akcijama  
✅ **BroadcastReceiver** - Komunikacija između komponenti  
✅ **SQLite Baza** - CRUD operacije  
✅ **SharedPreferences** - Čuvanje korisničkih podataka  
✅ **ContentProvider** - Pristup sistemskim podacima  
✅ **Runtime Permissions** - Traženje dozvola  
✅ **Multiple Activities** - Navigacija između ekrana  

---

## Kontakt i Podrška

Za pitanja, probleme ili sugestije:
- Otvorite issue na GitHub repozitorijumu
- Proverite logcat za debug poruke
- Koristite Android Studio Debugger

---

## Licenca

Ovaj projekat je kreiran za edukativne svrhe.

---

**Verzija:** 1.0  
**Datum:** 2025  
**Autor:** [Vaše ime]  
**Platforma:** Android 14+ (API 34+)

