# TestiranjeZaKolokvijum1 - Android Projekat

## Opis Projekta

Ovo je Android aplikacija kreirana za pripremu za Kolokvijum 1 - popravni rok (januar). Aplikacija implementira sistem za registraciju korisnika, prijavu i upravljanje ličnim beleškama.

## Funkcionalnosti

1. **Registracija korisnika** - Unos imena i prezimena, email adrese i lozinke
2. **Prijava korisnika** - Login pomoću email adrese i lozinke
3. **Početni ekran** - Prikaz korisnikovog imena sa porukom dobrodošlice
4. **Upravljanje beleškama** - Dodavanje, pregled i filtriranje beleški po datumu
5. **Provera dozvola** - Provera dozvole za čitanje skladišta pre dodavanja beleške
6. **SQLite baza podataka** - Lokalno skladištenje korisnika i beleški

---

## DETALJNO UPUTSTVO ZA KREIRANJE PROJEKTA OD POČETKA

### KORAK 1: Kreiranje Novog Android Projekta

1. Otvorite **Android Studio**
2. Kliknite na **"New Project"**
3. Izaberite **"Empty Views Activity"**
4. Unesite sledeće podatke:
   - **Name:** `TestiranjeZaKolokvijum1`
   - **Package name:** `com.example.pripremaresenja2`
   - **Save location:** Odaberite željenu lokaciju
   - **Language:** Java
   - **Minimum SDK:** API 30 (Android 11.0)
5. Kliknite **"Finish"**

---

### KORAK 2: Ažuriranje build.gradle (Module: app)

Otvorite `app/build.gradle` i proverite da sadrži sledeće zavisnosti:

```gradle
dependencies {
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.11.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
}
```

---

### KORAK 3: Kreiranje Model Klasa

#### 3.1 Kreiranje User.java

1. Desni klik na paket `com.example.pripremaresenja2`
2. Izaberite **New > Java Class**
3. Unesite ime: `User`
4. Kopirajte kod:

```java
package com.example.pripremaresenja2;

public class User {
    private int id;
    private String imePrezime;
    private String email;
    private String lozinka;

    public User(int id, String imePrezime, String email, String lozinka) {
        this.id = id;
        this.imePrezime = imePrezime;
        this.email = email;
        this.lozinka = lozinka;
    }

    public User(String imePrezime, String email, String lozinka) {
        this.imePrezime = imePrezime;
        this.email = email;
        this.lozinka = lozinka;
    }

    public User() {}

    // Getteri i setteri
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getImePrezime() { return imePrezime; }
    public void setImePrezime(String imePrezime) { this.imePrezime = imePrezime; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getLozinka() { return lozinka; }
    public void setLozinka(String lozinka) { this.lozinka = lozinka; }
}
```

#### 3.2 Kreiranje Beleska.java

1. Desni klik na paket
2. **New > Java Class**
3. Ime: `Beleska`
4. Kopirajte kod:

```java
package com.example.pripremaresenja2;

public class Beleska {
    private int id;
    private String naslov;
    private String tekst;
    private String datum;
    private int userId;

    public Beleska(int id, String naslov, String tekst, String datum, int userId) {
        this.id = id;
        this.naslov = naslov;
        this.tekst = tekst;
        this.datum = datum;
        this.userId = userId;
    }

    public Beleska(String naslov, String tekst, String datum, int userId) {
        this.naslov = naslov;
        this.tekst = tekst;
        this.datum = datum;
        this.userId = userId;
    }

    public Beleska() {}

    // Getteri i setteri
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNaslov() { return naslov; }
    public void setNaslov(String naslov) { this.naslov = naslov; }
    public String getTekst() { return tekst; }
    public void setTekst(String tekst) { this.tekst = tekst; }
    public String getDatum() { return datum; }
    public void setDatum(String datum) { this.datum = datum; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
}
```

---

### KORAK 4: Kreiranje DatabaseHelper Klase

1. Desni klik na paket
2. **New > Java Class**
3. Ime: `DatabaseHelper`
4. Kopirajte kompletan kod iz `DatabaseHelper.java` fajla projekta

**Važne funkcionalnosti DatabaseHelper klase:**
- Nasledjuje `SQLiteOpenHelper`
- Kreira tabele `korisnici` i `beleske`
- Pri kreiranju baze, dodaje 3 inicijalne beleške
- Sadrži metode za CRUD operacije (Create, Read, Update, Delete)

---

### KORAK 5: Kreiranje String Resursa

Otvorite `res/values/strings.xml` i dodajte sve potrebne stringove:

```xml
<resources>
    <string name="app_name">TestiranjeZaKolokvijum1</string>
    <string name="ime_prezime_hint">Ime i prezime</string>
    <string name="email_hint">Email</string>
    <string name="lozinka_hint">Lozinka</string>
    <string name="registruj_se">Registruj se</string>
    <string name="naslov_registracija">Registracija</string>
    <string name="prijavi_se">Prijavi se</string>
    <string name="naslov_login">Prijava</string>
    <string name="dobrodosli_nazad">Dobrodošli nazad!</string>
    <string name="moje_beleske">Moje beleške</string>
    <string name="dodaj_belesku">Dodaj belešku</string>
    <string name="filtriraj_po_datumu">Filtriraj po datumu</string>
    <string name="sve_beleske">Sve beleške</string>
    <string name="naslov_hint">Naslov</string>
    <string name="tekst_hint">Tekst beleške</string>
    <string name="sacuvaj">Sačuvaj</string>
    <string name="izaberi_datum">Izaberi datum</string>
    <string name="email_vec_postoji">Email je već registrovan!</string>
    <string name="pogresni_podaci">Pogrešni podaci.</string>
    <string name="uspesna_registracija">Uspešna registracija!</string>
    <string name="popunite_sva_polja">Molimo popunite sva polja</string>
    <string name="pristup_skladistu_nije_dozvoljen">Pristup skladištu nije dozvoljen!</string>
    <string name="beleska_sacuvana">Beleška je sačuvana!</string>
    <string name="ok">OK</string>
    <string name="odustani">Odustani</string>
</resources>
```

---

### KORAK 6: Kreiranje Layout Fajlova

#### 6.1 activity_main.xml (Registracija)

1. Otvorite `res/layout/activity_main.xml`
2. Zamenite sadržaj sa:

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout 
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="24dp">

    <TextView
        android:id="@+id/naslov_registracija"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/naslov_registracija"
        android:textSize="28sp"
        android:textStyle="bold"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent" />

    <EditText
        android:id="@+id/editTextImePrezime"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginTop="32dp"
        android:hint="@string/ime_prezime_hint"
        android:inputType="textPersonName"
        android:minHeight="48dp"
        app:layout_constraintTop_toBottomOf="@+id/naslov_registracija"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent" />

    <EditText
        android:id="@+id/editTextEmail"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"
        android:hint="@string/email_hint"
        android:inputType="textEmailAddress"
        android:minHeight="48dp"
        app:layout_constraintTop_toBottomOf="@+id/editTextImePrezime"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent" />

    <EditText
        android:id="@+id/editTextLozinka"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"
        android:hint="@string/lozinka_hint"
        android:inputType="textPassword"
        android:minHeight="48dp"
        app:layout_constraintTop_toBottomOf="@+id/editTextEmail"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent" />

    <Button
        android:id="@+id/buttonRegistracija"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginTop="24dp"
        android:text="@string/registruj_se"
        app:layout_constraintTop_toBottomOf="@+id/editTextLozinka"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

#### 6.2 activity_login.xml

1. Desni klik na `res/layout`
2. **New > Layout Resource File**
3. Ime: `activity_login`
4. Kopirajte sadržaj iz projekta

#### 6.3 activity_home.xml

1. Desni klik na `res/layout`
2. **New > Layout Resource File**
3. Ime: `activity_home`
4. Kopirajte sadržaj iz projekta

#### 6.4 fragment_beleske.xml

1. Desni klik na `res/layout`
2. **New > Layout Resource File**
3. Ime: `fragment_beleske`
4. Kopirajte sadržaj iz projekta

#### 6.5 item_beleska.xml

1. Desni klik na `res/layout`
2. **New > Layout Resource File**
3. Ime: `item_beleska`
4. Kopirajte sadržaj iz projekta

#### 6.6 dialog_dodaj_belesku.xml

1. Desni klik na `res/layout`
2. **New > Layout Resource File**
3. Ime: `dialog_dodaj_belesku`
4. Kopirajte sadržaj iz projekta

---

### KORAK 7: Kreiranje Menu Fajla

1. Desni klik na `res`
2. **New > Android Resource Directory**
3. Resource type: **menu**
4. Kliknite OK
5. Desni klik na novi `menu` folder
6. **New > Menu Resource File**
7. Ime: `home_menu`
8. Dodajte sadržaj:

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">
    
    <item
        android:id="@+id/menu_moje_beleske"
        android:title="@string/moje_beleske"
        app:showAsAction="ifRoom" />
    
</menu>
```

---

### KORAK 8: Kreiranje Activity i Fragment Klasa

#### 8.1 MainActivity.java (Registracija)

Ažurirajte postojeću `MainActivity.java` sa kodom iz projekta.

**Ključne funkcionalnosti:**
- Validacija unosa
- Provera da li email već postoji
- Čuvanje korisnika u bazu
- Prebacivanje na LoginActivity

#### 8.2 LoginActivity.java

1. Desni klik na paket
2. **New > Activity > Empty Views Activity**
3. Ime: `LoginActivity`
4. Kopirajte kod iz projekta

**Ključne funkcionalnosti:**
- Validacija unosa
- Provera kredencijala u bazi
- Čuvanje userId u SharedPreferences
- Prebacivanje na HomeActivity

#### 8.3 HomeActivity.java

1. Desni klik na paket
2. **New > Activity > Empty Views Activity**
3. Ime: `HomeActivity`
4. Kopirajte kod iz projekta

**Ključne funkcionalnosti:**
- Postavljanje Toolbar-a
- Učitavanje imena korisnika iz SharedPreferences
- Otvaranje BeleškeFragment-a

#### 8.4 BeleškeFragment.java

1. Desni klik na paket
2. **New > Fragment > Fragment (Blank)**
3. Ime: `BeleškeFragment`
4. Kopirajte kod iz projekta

**Ključne funkcionalnosti:**
- Prikaz liste beleški u RecyclerView-u
- Provera dozvole za čitanje skladišta
- Otvaranje dialoga za dodavanje beleške
- Filtriranje beleški po današnjem datumu

#### 8.5 BeleskeAdapter.java

1. Desni klik na paket
2. **New > Java Class**
3. Ime: `BeleskeAdapter`
4. Kopirajte kod iz projekta

**Ključne funkcionalnosti:**
- RecyclerView.Adapter za prikaz beleški
- ViewHolder pattern
- Metoda za ažuriranje liste

---

### KORAK 9: Ažuriranje AndroidManifest.xml

Otvorite `app/src/main/AndroidManifest.xml` i dodajte:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">
    
    <!-- Dozvola za čitanje skladišta -->
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"
        android:maxSdkVersion="32" />
    <uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />
    
    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.TestiranjeZaKolokvijum1"
        tools:targetApi="31">
        
        <activity
            android:name=".HomeActivity"
            android:exported="false" />
        
        <activity
            android:name=".LoginActivity"
            android:exported="false" />
        
        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```

---

### KORAK 10: Ažuriranje Tema (Themes.xml)

#### res/values/themes.xml

```xml
<resources>
    <style name="Base.Theme.TestiranjeZaKolokvijum1" parent="Theme.Material3.DayNight.NoActionBar">
    </style>

    <style name="Theme.TestiranjeZaKolokvijum1" parent="Base.Theme.TestiranjeZaKolokvijum1" />
</resources>
```

#### res/values-night/themes.xml

```xml
<resources>
    <style name="Base.Theme.TestiranjeZaKolokvijum1" parent="Theme.Material3.DayNight.NoActionBar">
    </style>
</resources>
```

---

## TESTIRANJE APLIKACIJE

### 1. Testiranje Registracije
- Pokrenite aplikaciju
- Unesite ime i prezime, email i lozinku
- Kliknite "Registruj se"
- Trebali biste biti prebačeni na ekran za prijavu

### 2. Testiranje Prijave
- Unesite email i lozinku sa kojima ste se registrovali
- Kliknite "Prijavi se"
- Ako su podaci tačni, trebali biste biti prebačeni na HomeActivity

### 3. Testiranje Beleški
- U HomeActivity-u kliknite na menu stavku "Moje beleške"
- Videćete inicijalne 3 beleške (za test korisnika)
- Kliknite "Dodaj belešku" i testirajte proveru dozvole
- Dodajte novu belešku sa DatePicker-om
- Testirajte filtriranje po datumu

---

## STRUKTURA PROJEKTA

```
TestiranjeZaKolokvijum1/
├── app/
│   └── src/
│       └── main/
│           ├── java/com/example/pripremaresenja2/
│           │   ├── MainActivity.java
│           │   ├── LoginActivity.java
│           │   ├── HomeActivity.java
│           │   ├── BeleškeFragment.java
│           │   ├── BeleskeAdapter.java
│           │   ├── DatabaseHelper.java
│           │   ├── User.java
│           │   └── Beleska.java
│           ├── res/
│           │   ├── layout/
│           │   │   ├── activity_main.xml
│           │   │   ├── activity_login.xml
│           │   │   ├── activity_home.xml
│           │   │   ├── fragment_beleske.xml
│           │   │   ├── item_beleska.xml
│           │   │   └── dialog_dodaj_belesku.xml
│           │   ├── menu/
│           │   │   └── home_menu.xml
│           │   └── values/
│           │       └── strings.xml
│           └── AndroidManifest.xml
└── README.md
```

---

## MAPIRANJE ZAHTEVA SA KOLOKVIJUMA

| Zahtev | Implementacija | Fajl |
|--------|---------------|------|
| 1. Model i tabela | User i Beleska klase + DatabaseHelper | User.java, Beleska.java, DatabaseHelper.java |
| 2. GUI registracije | EditText polja + dugme | activity_main.xml, MainActivity.java |
| 3. Skrivena lozinka | inputType="textPassword" | activity_main.xml, activity_login.xml |
| 4. Provera emaila | emailPostoji() metoda | MainActivity.java |
| 5. 3 inicijalne beleške | onCreate() metoda DatabaseHelper | DatabaseHelper.java |
| 6. LoginActivity | Email, lozinka, prijava | LoginActivity.java, activity_login.xml |
| 7. HomeActivity Toolbar | Toolbar sa menijem | HomeActivity.java, activity_home.xml |
| 8. BeleškeFragment | RecyclerView + dugmad | BeleškeFragment.java, fragment_beleske.xml |
| 9. Provera dozvole | ActivityResultLauncher | BeleškeFragment.java |
| 10. Čuvanje beleške | dodajBelesku() metoda | BeleškeFragment.java, DatabaseHelper.java |
| 11. Filtriranje po datumu | getBeleskePoDatumu() | BeleškeFragment.java, DatabaseHelper.java |

---

## NAPOMENE

- Projekat koristi **SQLite** bazu podataka
- **SharedPreferences** se koriste za čuvanje sesije korisnika
- **RecyclerView** se koristi za efikasan prikaz liste
- **Material Design** komponente za moderan UI
- Dozvole se proveravaju u runtime-u (Android 6.0+)

---

## AUTOR

Projekat kreiran za pripremu Kolokvijum 1 - popravni rok (januar)

---

## LICENCA

Ovaj projekat je kreiran u edukativne svrhe.

