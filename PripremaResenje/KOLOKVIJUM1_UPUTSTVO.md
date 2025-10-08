# Kolokvijum 1 - Detaljno Uputstvo za Izradu

## 📋 Uvod

Ovo uputstvo vodi vas korak-po-korak kroz izradu Android aplikacije za Kolokvijum 1. Pratite svaki korak pažljivo i kreirajte tražene fajlove redom kako su navedeni.

**VAŽNO:** Redosled je bitan! Neke klase zavise od drugih, pa ih radite redosledom kako je navedeno.

---

## 🎯 Zadatak - Kompletan Pregled

1. Kreirati novi Android projekat pod nazivom **Kolokvijum1**
2. Prva aktivnost (MainActivity) sadrži **dva fragmenta** (FirstFragment gore, SecondFragment dole)
3. U SharedPreferences pod ključem `"inicijalno"` upisati: `"Zdravo!"`
4. FirstFragment ima **dva dugmeta**: "Proveri" (zeleno) i "Ispiši"
5. Na drugo dugme se **inicijalno ne može kliknuti**
6. Klikom na prvo dugme drugo dugme **toggle enabled/disabled**
7. Klikom na drugo dugme **Toast sa imenom** poslednjeg entiteta ili SharedPreferences vrednost
8. Kreirati **model i tabelu** u bazi za entitet Korisnik sa poljem `ime`
9. SecondFragment ima **EditText i dugme** "Sačuvaj"
10. Klikom na "Sačuvaj" proveriti **dozvolu za lokaciju**
11. Ako korisnik **odbije** - čuvaj u SharedPreferences i idi na SecondActivity  
    Ako korisnik **odobri** - čuvaj u bazu

---

## 📝 KORAK 1: Konfiguriši Projekat

### 1.1 Otvori i izmeni `settings.gradle`

```gradle
rootProject.name = "Kolokvijum1"
include ':app'
```

**Šta radimo:** Menjamo naziv projekta sa podrazumevanog u "Kolokvijum1".

### 1.2 Otvori i izmeni `app/build.gradle`

Pronađi sledeće linije i izmeni ih:

```gradle
android {
    namespace 'com.example.kolokvijum1'  // <- Izmeni ovo
    compileSdk 34

    defaultConfig {
        applicationId "com.example.kolokvijum1"  // <- Izmeni ovo
        ...
    }
}
```

**Šta radimo:** Menjamo package name aplikacije da odgovara nazivu Kolokvijum1.

---

## 📝 KORAK 2: Kreiranje Baze Podataka (Zadatak 8)

**Redosled je bitan!** Prvo pravimo konstante, pa model, pa helper, pa repository.

### 2.1 Kreiraj `DatabaseConstants.java`

**Lokacija:** `app/src/main/java/com/example/kolokvijum1/utils/DatabaseConstants.java`

```java
package com.example.kolokvijum1.utils;

public class DatabaseConstants {
    public static final String TABLE_KORISNIK = "KORISNIK";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_IME = "ime";
}
```

**Šta radimo:** Definišemo konstante za nazive tabele i kolona. Korišćenje konstanti sprečava greške u kucanju.

---

### 2.2 Kreiraj `Korisnik.java` model

**Lokacija:** `app/src/main/java/com/example/kolokvijum1/models/Korisnik.java`

```java
package com.example.kolokvijum1.models;

public class Korisnik {
    private int id;
    private String ime;

    public Korisnik() {
    }

    public Korisnik(int id, String ime) {
        this.id = id;
        this.ime = ime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    @Override
    public String toString() {
        return "Korisnik{" +
                "id=" + id +
                ", ime='" + ime + '\'' +
                '}';
    }
}
```

**Šta radimo:** Kreiramo POJO (Plain Old Java Object) model klasu koja predstavlja Korisnik entitet sa poljem `ime`.

**Ključni delovi:**
- Dva konstruktora (prazan i sa parametrima)
- Getter i setter metode za sva polja
- toString() metoda za lakši debugging

---

### 2.3 Kreiraj `SQLiteHelper.java`

**Lokacija:** `app/src/main/java/com/example/kolokvijum1/database/SQLiteHelper.java`

**NAPOMENA:** Pogledaj kompletan kod u fajlu sa detaljnim komentarima.

**Ključni delovi:**
- Nasleđuje `SQLiteOpenHelper`
- Koristi Singleton pattern
- `onCreate()` metoda kreira tabelu KORISNIK
- `onUpgrade()` metoda rukuje verzionisanjem

```java
// Samo ključni deo - potpuni kod vidi u fajlu
private static final String DB_CREATE_KORISNIK = "create table "
        + TABLE_KORISNIK + "("
        + COLUMN_ID  + " integer primary key autoincrement, "
        + COLUMN_IME + " text"
        + ")";
```

**Šta radimo:** Kreiramo helper klasu koja upravlja SQLite bazom podataka.

---

### 2.4 Kreiraj `KorisnikRepository.java`

**Lokacija:** `app/src/main/java/com/example/kolokvijum1/database/KorisnikRepository.java`

**NAPOMENA:** Pogledaj kompletan kod u fajlu sa detaljnim komentarima.

**Ključne metode:**
- `insertData(String ime)` - dodaje korisnika u bazu
- `getLastKorisnik()` - vraća poslednjeg korisnika (za Zadatak 7)
- `hasAnyData()` - proverava da li ima podataka (za Zadatak 7)
- `getData()`, `updateData()`, `deleteData()` - standardne CRUD operacije

**Šta radimo:** Implementiramo Repository pattern koji odvaja logiku pristupa podacima od ostatka aplikacije.

---

## 📝 KORAK 3: Kreiranje Layout-a za Aktivnosti i Fragmente

### 3.1 Izmeni `activity_main.xml` (Zadatak 2)

**Lokacija:** `app/src/main/res/layout/activity_main.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".MainActivity">

    <!-- First Fragment Container - Top Half -->
    <FrameLayout
        android:id="@+id/firstFragmentContainer"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:background="#F0F0F0"/>

    <!-- Second Fragment Container - Bottom Half -->
    <FrameLayout
        android:id="@+id/secondFragmentContainer"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:background="#FFFFFF"/>

</LinearLayout>
```

**Šta radimo:**
- LinearLayout sa `vertical` orijentacijom
- Dva FrameLayout kontejnera
- `layout_weight="1"` deli ekran na pola (50% - 50%)
- Različite boje pozadine za vizuelnu distinkciju

---

### 3.2 Kreiraj `fragment_first.xml` (Zadatak 4, 5)

**Lokacija:** `app/src/main/res/layout/fragment_first.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:gravity="center"
    android:padding="16dp">

    <Button
        android:id="@+id/btnProveri"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Proveri"
        android:backgroundTint="#4CAF50"
        android:textColor="#FFFFFF"
        android:layout_marginBottom="16dp"/>

    <Button
        android:id="@+id/btnIspisi"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Ispiši"
        android:enabled="false"/>

</LinearLayout>
```

**Šta radimo:**
- **Zadatak 4:** Dva dugmeta - "Proveri" i "Ispiši"
- **Zadatak 4:** "Proveri" je zeleno (`backgroundTint="#4CAF50"`)
- **Zadatak 5:** "Ispiši" je disabled (`android:enabled="false"`)
- `gravity="center"` centrira dugmad

---

### 3.3 Kreiraj `fragment_second.xml` (Zadatak 9)

**Lokacija:** `app/src/main/res/layout/fragment_second.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:gravity="center"
    android:padding="16dp">

    <EditText
        android:id="@+id/etIme"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Unesite ime"
        android:layout_marginBottom="16dp"/>

    <Button
        android:id="@+id/btnSacuvaj"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Sačuvaj"/>

</LinearLayout>
```

**Šta radimo:**
- **Zadatak 9:** EditText polje za unos imena
- **Zadatak 9:** Dugme "Sačuvaj"
- `hint="Unesite ime"` prikazuje placeholder tekst

---

### 3.4 Kreiraj `activity_second.xml` (Zadatak 11)

**Lokacija:** `app/src/main/res/layout/activity_second.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center"
    android:orientation="vertical">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Nema dozvole!"
        android:textSize="24sp"
        android:textStyle="bold"/>

</LinearLayout>
```

**Šta radimo:**
- **Zadatak 11:** Centriran tekst "Nema dozvole!"
- `gravity="center"` centrira TextView
- `textSize="24sp"` i `textStyle="bold"` za veći i boldovan tekst

---

## 📝 KORAK 4: Kreiranje Java Klasa za Fragmente i Aktivnosti

### 4.1 Kreiraj `MainActivity.java` (Zadatak 2, 3)

**Lokacija:** `app/src/main/java/com/example/kolokvijum1/MainActivity.java`

**NAPOMENA:** Pogledaj kompletan kod u fajlu sa detaljnim komentarima.

**Ključni delovi:**

```java
// onCreate metoda
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    
    // Zadatak 3: Inicijalizuj SharedPreferences
    initializeSharedPreferences();
    
    // Zadatak 2: Učitaj fragmente
    if (savedInstanceState == null) {
        loadFragments();
    }
}

// Zadatak 3: SharedPreferences inicijalizacija
private void initializeSharedPreferences() {
    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
    if (!sharedPreferences.contains("inicijalno")) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("inicijalno", "Zdravo!");
        editor.apply();
    }
}

// Zadatak 2: Učitavanje oba fragmenta
private void loadFragments() {
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.firstFragmentContainer, FirstFragment.newInstance());
    transaction.replace(R.id.secondFragmentContainer, SecondFragment.newInstance());
    transaction.commit();
}
```

**Šta radimo:**
- **Zadatak 2:** MainActivity učitava dva fragmenta u svoje kontejnere
- **Zadatak 3:** Inicijalizujemo SharedPreferences sa ključem "inicijalno" = "Zdravo!"

---

### 4.2 Kreiraj `FirstFragment.java` (Zadatak 4, 5, 6, 7)

**Lokacija:** `app/src/main/java/com/example/kolokvijum1/fragments/FirstFragment.java`

**NAPOMENA:** Pogledaj kompletan kod u fajlu sa VEOMA detaljnim komentarima.

**Ključni delovi:**

```java
// Zadatak 6: Toggle funkcionalnost
btnProveri.setOnClickListener(v -> {
    btnIspisi.setEnabled(!btnIspisi.isEnabled());
});

// Zadatak 7: Toast logika
btnIspisi.setOnClickListener(v -> {
    handleIspisiClick();
});

private void handleIspisiClick() {
    if (korisnikRepository.hasAnyData()) {
        // Prikaži ime poslednjeg korisnika
        Cursor cursor = korisnikRepository.getLastKorisnik();
        if (cursor.moveToFirst()) {
            String ime = cursor.getString(cursor.getColumnIndex(DatabaseConstants.COLUMN_IME));
            cursor.close();
            Toast.makeText(requireContext(), ime, Toast.LENGTH_SHORT).show();
            
            // Zameni SharedPreferences ako se poklapa
            String inicijalnoValue = sharedPreferences.getString("inicijalno", "");
            if (inicijalnoValue.equals(ime)) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("inicijalno", "Zdravo!");
                editor.apply();
            }
        }
    } else {
        // Prikaži vrednost iz SharedPreferences
        String inicijalnoValue = sharedPreferences.getString("inicijalno", "");
        Toast.makeText(requireContext(), inicijalnoValue, Toast.LENGTH_SHORT).show();
    }
}
```

**Šta radimo:**
- **Zadatak 6:** "Proveri" dugme toggle-uje enabled stanje "Ispiši" dugmeta
- **Zadatak 7:** "Ispiši" prikazuje Toast sa imenom ili SharedPreferences vrednost
- **Zadatak 7:** Zamenjuje SharedPreferences ako se vrednost poklapa sa imenom

---

### 4.3 Kreiraj `SecondFragment.java` (Zadatak 9, 10, 11)

**Lokacija:** `app/src/main/java/com/example/kolokvijum1/fragments/SecondFragment.java`

**NAPOMENA:** Pogledaj kompletan kod u fajlu sa VEOMA detaljnim komentarima o dozvolama.

**Ključni delovi:**

```java
// Zadatak 10: Registracija launcher-a za dozvole
@Override
public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    requestPermissionLauncher = registerForActivityResult(
        new ActivityResultContracts.RequestPermission(),
        isGranted -> {
            if (isGranted) {
                saveToDatabase();  // Zadatak 11: Odobreno - čuvaj u bazu
            } else {
                saveToSharedPreferencesAndNavigate();  // Zadatak 11: Odbijeno - SharedPreferences + navigacija
            }
        }
    );
}

// Zadatak 10: Provera dozvole
private void checkLocationPermission() {
    if (ContextCompat.checkSelfPermission(requireContext(), 
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        saveToDatabase();
    } else {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
    }
}

// Zadatak 11: Čuvanje u bazu
private void saveToDatabase() {
    String ime = etIme.getText().toString().trim();
    if (!ime.isEmpty()) {
        korisnikRepository.insertData(ime);
        etIme.setText("");
    }
}

// Zadatak 11: Čuvanje u SharedPreferences + navigacija
private void saveToSharedPreferencesAndNavigate() {
    String ime = etIme.getText().toString().trim();
    if (!ime.isEmpty()) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("inicijalno", ime);
        editor.apply();
    }
    Intent intent = new Intent(requireContext(), SecondActivity.class);
    startActivity(intent);
}
```

**Šta radimo:**
- **Zadatak 10:** Proveravamo dozvolu za lokaciju
- **Zadatak 11:** Ako odobreno - čuvamo u bazu, ako odbijeno - čuvamo u SharedPreferences i idemo na SecondActivity

---

### 4.4 Kreiraj `SecondActivity.java` (Zadatak 11)

**Lokacija:** `app/src/main/java/com/example/kolokvijum1/SecondActivity.java`

**NAPOMENA:** Pogledaj kompletan kod u fajlu sa komentarima.

```java
package com.example.kolokvijum1;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }
}
```

**Šta radimo:**
- **Zadatak 11:** Jednostavna aktivnost koja prikazuje "Nema dozvole!" poruku

---

## 📝 KORAK 5: Izmena AndroidManifest.xml

**Lokacija:** `app/src/main/AndroidManifest.xml`

Dodaj sledeće:

### 5.1 Dodaj dozvolu IZNAD `<application>` taga

```xml
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
```

**Šta radimo:** Deklarišemo da aplikacija zahteva dozvolu za pristup preciznoj lokaciji.

### 5.2 Dodaj SecondActivity UNUTAR `<application>` taga

```xml
<activity
    android:name=".SecondActivity"
    android:exported="false" />
```

**Šta radimo:** Registrujemo SecondActivity u manifest fajlu.

**Kompletan manifest:**

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />

    <application
        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.AppCompat.DayNight.NoActionBar"
        tools:targetApi="31">
        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity
            android:name=".SecondActivity"
            android:exported="false" />
    </application>

</manifest>
```

---

## ✅ KORAK 6: Provera i Testiranje

### 6.1 Build Aplikaciju

1. U Android Studio, klikni **Build** → **Rebuild Project**
2. Sačekaj da se build završi bez grešaka

### 6.2 Testiraj Funkcionalnosti

#### Test 1: SharedPreferences Inicijalizacija (Zadatak 3)
1. Pokreni aplikaciju
2. U logcat-u proveri da li je "inicijalno" postavljeno na "Zdravo!"

#### Test 2: Fragmenti (Zadatak 2, 4, 5)
1. Proveri da li se prikazuju oba fragmenta (gore i dole)
2. Proveri da li je "Proveri" dugme zeleno
3. Proveri da li je "Ispiši" dugme disabled

#### Test 3: Toggle Funkcionalnost (Zadatak 6)
1. Klikni na "Proveri"
2. "Ispiši" bi trebalo da postane enabled
3. Klikni ponovo na "Proveri"
4. "Ispiši" bi trebalo da postane disabled ponovo

#### Test 4: Toast Logika (Zadatak 7)
1. Klikni "Proveri" (da omogućiš "Ispiši")
2. Klikni "Ispiši"
3. Toast bi trebalo da prikaže "Zdravo!" (jer nema podataka u bazi)

#### Test 5: Dozvola za Lokaciju (Zadatak 10, 11) - Odobreno
1. U donjem fragmentu, unesi ime "Marko"
2. Klikni "Sačuvaj"
3. Dialog za dozvolu bi trebalo da se pojavi
4. Klikni "Dozvoli"
5. Ime bi trebalo da se sačuva u bazi
6. EditText bi trebalo da se isprazni

#### Test 6: Toast sa Imenom iz Baze (Zadatak 7)
1. Sada klikni "Proveri" (u gornjem fragmentu)
2. Klikni "Ispiši"
3. Toast bi trebalo da prikaže "Marko"

#### Test 7: Dozvola za Lokaciju (Zadatak 11) - Odbijeno
1. Deinstaliraj aplikaciju i instaliraj ponovo (da resetuješ dozvole)
2. Unesi ime "Ana" u donjem fragmentu
3. Klikni "Sačuvaj"
4. Klikni "Odbij" na dijalogu za dozvolu
5. Trebalo bi da se otvori SecondActivity sa tekstom "Nema dozvole!"
6. SharedPreferences bi trebalo da sadrži "Ana" (proveri kroz logcat ili ponovo pokreni)

---

## 📊 Bodovanje - Kontrolna Lista

| Zadatak | Bodovi | Opis |
|---------|--------|------|
| 1 | - | Projekat nazvan Kolokvijum1 ✅ |
| 2 | 1 | MainActivity sa dva fragmenta (gore/dole) ✅ |
| 3 | 1 | SharedPreferences "inicijalno" = "Zdravo!" ✅ |
| 4 | 1.5 | FirstFragment sa dva dugmeta (Proveri zeleno + Ispiši) ✅ |
| 5 | 1 | Drugo dugme inicijalno disabled ✅ |
| 6 | 1.5 | Toggle funkcionalnost ✅ |
| 7 | 5 | Toast logika (ime/SharedPreferences/zamena) ✅ |
| 8 | 1 | Model Korisnik i tabela u bazi ✅ |
| 9 | 1 | SecondFragment sa EditText i Sačuvaj ✅ |
| 10 | 2 | Provera dozvole za lokaciju ✅ |
| 11 | 5 | Logika za odobrenu/odbijenu dozvolu ✅ |
| **UKUPNO** | **20** | |

---

## 🎓 Ključni Koncepti koji se Testiraju

### 1. **Fragmenti i Lifecycle**
- Kreiranje fragmenata
- FragmentTransaction
- Komunikacija između fragmenata i aktivnosti

### 2. **SharedPreferences**
- Čuvanje i čitanje podataka
- Editor pattern
- Provera postojanja ključeva

### 3. **SQLite Baza Podataka**
- SQLiteOpenHelper
- Singleton pattern
- CRUD operacije
- Cursor API
- Repository pattern

### 4. **Android Permissions (Runtime)**
- ActivityResultLauncher
- checkSelfPermission
- RequestPermission contract
- Rukovanje odobrenih/odbijenih dozvola

### 5. **UI Komponente**
- Button styling (colors, enabled/disabled)
- EditText
- Toast poruke
- Layout (LinearLayout, FrameLayout)
- View hierarchy

### 6. **Navigacija**
- Intent
- startActivity()
- Prelazak između aktivnosti

---

## 🐛 Česte Greške i Rešenja

### Greška 1: "Cannot resolve symbol 'R'"
**Rešenje:** Clean i Rebuild projekat. Proveri da li su svi XML fajlovi validni.

### Greška 2: Fragment se ne prikazuje
**Rešenje:** Proveri ID-eve u `activity_main.xml` i `MainActivity.java` da se poklapaju.

### Greška 3: Toast ne prikazuje ime iz baze
**Rešenje:** Proveri `getLastKorisnik()` metodu i da li vraća podatke. Koristi debugger.

### Greška 4: Dozvola se ne traži
**Rešenje:** Proveri `AndroidManifest.xml` da li je dodata `ACCESS_FINE_LOCATION`. Proveri da li je `registerForActivityResult` pozvan u `onCreate()`.

### Greška 5: SecondActivity ne postoji u manifestu
**Rešenje:** Dodaj `<activity android:name=".SecondActivity" android:exported="false" />` u manifest.

---

## 🎉 Završetak

Čestitamo! Ako ste pratili sve korake, vaša aplikacija bi trebalo da radi kompletno i ispunjava sve zahteve Kolokvijuma 1.

**Finalna struktura projekta:**

```
kolokvijum1/
├── MainActivity.java
├── SecondActivity.java
├── database/
│   ├── SQLiteHelper.java
│   └── KorisnikRepository.java
├── fragments/
│   ├── FirstFragment.java
│   └── SecondFragment.java
├── models/
│   └── Korisnik.java
└── utils/
    └── DatabaseConstants.java
```

**Layout fajlovi:**
- `activity_main.xml`
- `activity_second.xml`
- `fragment_first.xml`
- `fragment_second.xml`

**Manifest:**
- Dozvola: `ACCESS_FINE_LOCATION`
- Dve aktivnosti registrovane

---

*Sretno na kolokvijumu! 🍀*

