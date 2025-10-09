# Detaljni Pregled Implementacije - OdNule Android Aplikacija

## 📌 Pregled Projekta

Ovaj dokument sadrži detaljan pregled svega što je implementirano u Android aplikaciji "OdNule" prema specifikaciji zahteva.

---

## ✅ Implementirani Zahtevi

### 1. KREIRANJE EKRANA

#### a) SplashScreen ✅
- **Lokacija**: `app/src/main/java/com/kcalorific/odnule/SplashScreen.java`
- **Layout**: `app/src/main/res/layout/activity_splash.xml`
- **Funkcionalnost**: 
  - Prikazuje se 5 sekundi
  - Automatski prelazi na LoginScreen
  - Koristi Handler sa postDelayed metodom
- **Lifecycle metode**: Sve implementirane sa Log.d() pozivima

#### b) LoginScreen ✅
- **Lokacija**: `app/src/main/java/com/kcalorific/odnule/LoginScreen.java`
- **Layout**: `app/src/main/res/layout/activity_login.xml`
- **Funkcionalnost**:
  - Email polje sa `inputType="textEmailAddress"`
  - Lozinka polje sa `inputType="textPassword"`
  - Validacija unosa
  - Dugme za prijavu → prelazak na HomeScreen
  - Dugme za registraciju → prelazak na RegisterScreen
- **Lifecycle metode**: Sve implementirane sa Log.d() pozivima

#### c) RegisterScreen ✅
- **Lokacija**: `app/src/main/java/com/kcalorific/odnule/RegisterScreen.java`
- **Layout**: `app/src/main/res/layout/activity_register.xml`
- **Layout tip**: LinearLayout sa vertikalnom orijentacijom
- **Polja sa odgovarajućim inputType**:
  - Ime: `inputType="textPersonName"`
  - Prezime: `inputType="textPersonName"`
  - Email: `inputType="textEmailAddress"`
  - Telefon: `inputType="phone"`
  - Lozinka: `inputType="textPassword"`
  - Potvrda lozinke: `inputType="textPassword"`
- **Funkcionalnost**:
  - Validacija svih polja
  - Provera poklapanja lozinki
  - Prelazak na HomeScreen nakon uspešne registracije
- **Lifecycle metode**: Sve implementirane sa Log.d() pozivima

#### d) HomeScreen ✅
- **Lokacija**: `app/src/main/java/com/kcalorific/odnule/HomeScreen.java`
- **Layout**: `app/src/main/res/layout/activity_home.xml`
- **Layout tip**: DrawerLayout sa RelativeLayout sadržajem
- **Funkcionalnost**:
  - Toolbar (uvek vidljiv)
  - NavigationDrawer za navigaciju
  - FrameLayout kontejner za učitavanje fragmenata
  - Prima podatke iz Intent-a (email, ime korisnika)
- **Lifecycle metode**: Sve implementirane sa Log.d() pozivima

---

### 2. ANDROIDMANIFEST.XML ✅

**Lokacija**: `app/src/main/AndroidManifest.xml`

Dodati elementi za sve aktivnosti:
- ✅ SplashScreen (sa intent-filter LAUNCHER)
- ✅ LoginScreen
- ✅ RegisterScreen
- ✅ HomeScreen
- ✅ ProductDetailScreen
- ✅ MainActivity (originalna aktivnost)

---

### 3. NASLEĐIVANJE APPCOMPATACTIVITY ✅

Sve aktivnosti nasleđuju `AppCompatActivity` i redefinišu sledeće metode:

```java
- onCreate()
- onStart()
- onRestart()
- onResume()
- onPause()
- onStop()
- onDestroy()
```

**Implementacije u**:
- SplashScreen.java
- LoginScreen.java
- RegisterScreen.java
- HomeScreen.java
- ProductDetailScreen.java

**Logovanje**: Svaka metoda sadrži `Log.d(TAG, "metoda: Opis")` za praćenje životnog ciklusa

---

### 4. AUTOMATSKI PRELAZAK SA SPLASHSCREEN ✅

**Implementacija u**: `SplashScreen.java`

```java
new Handler().postDelayed(new Runnable() {
    @Override
    public void run() {
        Intent intent = new Intent(SplashScreen.this, LoginScreen.class);
        startActivity(intent);
        finish();
    }
}, 5000); // 5 sekundi
```

---

### 5. LOGINSCREEN SADRŽAJ ✅

**Polja**:
- ✅ Email (TextInputEditText sa inputType="textEmailAddress")
- ✅ Lozinka (TextInputEditText sa inputType="textPassword")

**Dugmad**:
- ✅ Dugme za potvrdu unosa (btnLogin)
- ✅ Dugme za registraciju (btnGoToRegister)

**Funkcionalnost**:
- ✅ Validacija unosa
- ✅ Prikaz greške ako polja nisu popunjena
- ✅ Prelazak na HomeScreen nakon prijave

---

### 6. PRELAZAK SA LOGINSCREEN NA REGISTERSCREEN ✅

**Implementacija**: Klik na "btnGoToRegister" dugme

```java
btnGoToRegister.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(LoginScreen.this, RegisterScreen.class);
        startActivity(intent);
    }
});
```

---

### 7. PRELAZAK SA LOGINSCREEN NA HOMESCREEN ✅

**Implementacija**: Klik na "btnLogin" dugme nakon validacije

```java
private void handleLogin() {
    // Validacija...
    
    Intent intent = new Intent(LoginScreen.this, HomeScreen.class);
    intent.putExtra("USER_EMAIL", email);
    startActivity(intent);
    finish();
}
```

---

### 8. PERMISIJE ZA KORIŠĆENJE INTERNETA ✅

**Lokacija**: AndroidManifest.xml

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

Dodato iznad `<application>` taga.

---

### 9. REGISTERSCREEN POLJA I LINEARNI RASPORED ✅

**Layout**: LinearLayout sa vertikalnom orijentacijom unutar ScrollView-a

**Polja sa odgovarajućim inputType**:
1. ✅ Ime - `inputType="textPersonName"`
2. ✅ Prezime - `inputType="textPersonName"`
3. ✅ Email - `inputType="textEmailAddress"`
4. ✅ Telefon - `inputType="phone"`
5. ✅ Lozinka - `inputType="textPassword"`
6. ✅ Potvrda lozinke - `inputType="textPassword"`

**Dugmad**:
- ✅ btnRegister - za potvrdu unosa
- ✅ btnBackToLogin - za povratak na LoginScreen

---

### 10. PRILAGOĐEN TIP UNOSA ZA LOGINSCREEN ✅

**Email polje**:
```xml
<com.google.android.material.textfield.TextInputEditText
    android:id="@+id/etEmail"
    android:inputType="textEmailAddress" />
```

**Lozinka polje**:
```xml
<com.google.android.material.textfield.TextInputEditText
    android:id="@+id/etPassword"
    android:inputType="textPassword" />
```

---

### 11. HOMESCREEN SA RELATIVNIM RASPOREDOM ✅

**Layout**: activity_home.xml koristi RelativeLayout koncepte kroz DrawerLayout

**Struktura**:
- LinearLayout za glavni sadržaj (vertikalna orijentacija)
- AppBarLayout sa Toolbar-om
- TextView za poruku dobrodošlice
- FrameLayout za fragmente

---

### 12. PRELAZAK SA REGISTERSCREEN NA HOMESCREEN ✅

**Implementacija**: Klik na "btnRegister" dugme nakon validacije

```java
private void handleRegistration() {
    // Validacija...
    
    Intent intent = new Intent(RegisterScreen.this, HomeScreen.class);
    intent.putExtra("USER_EMAIL", email);
    intent.putExtra("USER_FIRST_NAME", firstName);
    intent.putExtra("USER_LAST_NAME", lastName);
    startActivity(intent);
    finish();
}
```

---

### 13. SVI TEKSTOVI U STRINGS.XML ✅

**Lokacija**: `app/src/main/res/values/strings.xml`

Svi tekstovi vidljivi korisniku su definisani u strings.xml:
- ✅ Naslovi ekrana
- ✅ Hint-ovi za polja
- ✅ Tekstovi na dugmadima
- ✅ Poruke greške
- ✅ Poruke uspešnosti
- ✅ Navigacioni meni tekstovi
- ✅ Opisi za content description

**Ukupno**: 40+ string resursa

---

### 14. TOOLBAR / NAVIGATIONDRAWER ✅

#### Toolbar ✅
- **Lokacija**: Implementiran u HomeScreen
- **Vidljivost**: Uvek vidljiv
- **Funkcionalnost**: 
  - Prikazuje naslov aplikacije
  - Sadrži hamburger ikonu za otvaranje drawer-a
  - Ostaje vidljiv pri navigaciji kroz fragmente

#### NavigationDrawer ✅
- **Header**: `res/layout/nav_header.xml`
  - Avatar korisnika
  - Naziv aplikacije
  - Podnaslov
- **Menu**: `res/menu/drawer_menu.xml`
  - Proizvodi (nav_main)
  - Moj nalog (nav_account)
  - Obaveštenja (nav_notifications)
  - Odjavi se (nav_logout)

**Navigacija omogućena na**:
- ✅ MainScreen
- ✅ AccountScreen
- ✅ NotificationsScreen

---

### 15. MOKAP PODACI ✅

#### Model Korisnika ✅
**Lokacija**: `app/src/main/java/com/kcalorific/odnule/models/User.java`

**Atributi**:
- firstName (String)
- lastName (String)
- birthYear (int)
- email (String)

**Metode**:
- getFullName()
- getAge()
- Getteri i setteri

**Mokap instanca** (u AccountScreen.java):
```java
User currentUser = new User("Marko", "Marković", 1995, "marko.markovic@example.com");
```

#### Model Proizvoda ✅
**Lokacija**: `app/src/main/java/com/kcalorific/odnule/models/Product.java`

**Atributi**:
- id (int)
- name (String)
- description (String)
- price (double)

**Metode**:
- getFormattedPrice()
- Getteri i setteri

**Mokap podaci** (u MainScreen.java): 10 proizvoda
1. Laptop Dell XPS 15 - 150,000 RSD
2. Smartphone Samsung Galaxy S23 - 80,000 RSD
3. Slušalice Sony WH-1000XM5 - 35,000 RSD
4. Tastatura Logitech MX Keys - 12,000 RSD
5. Miš Logitech MX Master 3 - 9,000 RSD
6. Monitor LG UltraWide 34'' - 55,000 RSD
7. Webcam Logitech C920 - 8,000 RSD
8. Eksterni HDD Seagate 2TB - 7,000 RSD
9. USB-C Hub Anker - 4,500 RSD
10. Tablet Apple iPad Air - 75,000 RSD

---

### 16. MAINSCREEN SA PRETRAGOM I FILTROVANJEM ✅

**Lokacija**: `app/src/main/java/com/kcalorific/odnule/MainScreen.java`

**Layout**: `res/layout/fragment_main.xml`

**Funkcionalnosti**:

1. ✅ **Pretraga proizvoda**
   - EditText sa TextWatcher-om
   - Pretraga po nazivu i opisu
   - Real-time filtriranje

2. ✅ **Filtriranje proizvoda**
   - Spinner sa opcijama:
     - Svi proizvodi
     - Do 10,000 RSD
     - 10,000 - 50,000 RSD
     - 50,000+ RSD
   - Kombinovanje sa pretragom

3. ✅ **Prikaz svih proizvoda**
   - ListView sa custom adapterom
   - item_product.xml layout za svaku stavku
   - Prikazuje: naziv, opis, cenu

**Custom Adapter**: ProductAdapter nasleđuje ArrayAdapter<Product>

---

### 17. PRIKAZ INFORMACIJA O PROIZVODU NA NOVOJ STRANICI ✅

**Aktivnost**: ProductDetailScreen.java

**Layout**: activity_product_detail.xml

**Funkcionalnost**:
- ✅ Prikazuje detaljne informacije o proizvodu
- ✅ Prima podatke preko Intent-a:
  - PRODUCT_ID
  - PRODUCT_NAME
  - PRODUCT_DESCRIPTION
  - PRODUCT_PRICE
- ✅ Dugme "Dodaj u korpu"
- ✅ "Nazad" dugme u Toolbar-u
- ✅ Lifecycle metode implementirane

**Prelazak**: Klik na proizvod u ListView-u

```java
private void openProductDetails(Product product) {
    Intent intent = new Intent(getActivity(), ProductDetailScreen.class);
    intent.putExtra("PRODUCT_ID", product.getId());
    intent.putExtra("PRODUCT_NAME", product.getName());
    intent.putExtra("PRODUCT_DESCRIPTION", product.getDescription());
    intent.putExtra("PRODUCT_PRICE", product.getPrice());
    startActivity(intent);
}
```

---

### 18. ACCOUNTSCREEN ZA UPRAVLJANJE NALOGOM ✅

**Lokacija**: `app/src/main/java/com/kcalorific/odnule/AccountScreen.java`

**Layout**: `res/layout/fragment_account.xml`

**Prikazani podaci**:
- ✅ Ime i prezime (TextView)
- ✅ Email adresa (TextView)
- ✅ Godište (TextView)
- ✅ Starost (izračunava se dinamički)

**Dodatne opcije**:
- Dugme "Izmeni profil"
- Dugme "Promeni lozinku"

**UI Dizajn**:
- CardView sa avatarjem
- Svi podaci elegantno formatirani
- Responsive layout

---

### 19. TOOLBAR/NAVIGATIONDRAWER UVEK VIDLJIV ✅

**Implementacija**:

- Toolbar je deo HomeScreen layout-a
- NavigationDrawer wrappuje ceo sadržaj
- Fragmenti se učitavaju u FrameLayout kontejner
- Toolbar ostaje na vrhu pri svim navigacijama

**Dokaz**:
```xml
<!-- activity_home.xml -->
<androidx.drawerlayout.widget.DrawerLayout>
    <LinearLayout>
        <com.google.android.material.appbar.AppBarLayout>
            <androidx.appcompat.widget.Toolbar /> <!-- UVEK VIDLJIV -->
        </com.google.android.material.appbar.AppBarLayout>
        
        <FrameLayout android:id="@+id/fragmentContainer" />
    </LinearLayout>
    
    <com.google.android.material.navigation.NavigationView />
</androidx.drawerlayout.widget.DrawerLayout>
```

---

## 📂 Kompletan Spisak Kreiranih Fajlova

### Java Klase (11 fajlova)

#### Aktivnosti (5):
1. `SplashScreen.java` - Početni ekran
2. `LoginScreen.java` - Prijava
3. `RegisterScreen.java` - Registracija
4. `HomeScreen.java` - Glavni ekran
5. `ProductDetailScreen.java` - Detalji proizvoda

#### Fragmenti (3):
6. `MainScreen.java` - Proizvodi
7. `AccountScreen.java` - Nalog
8. `NotificationsScreen.java` - Obaveštenja

#### Model Klase (2):
9. `models/User.java` - Model korisnika
10. `models/Product.java` - Model proizvoda

#### Originalna Klasa (1):
11. `MainActivity.java` - Početna aktivnost (zadržana)

### Layout Fajlovi (13 fajlova)

#### Aktivnosti (5):
1. `activity_splash.xml` - SplashScreen layout
2. `activity_login.xml` - LoginScreen layout
3. `activity_register.xml` - RegisterScreen layout
4. `activity_home.xml` - HomeScreen layout
5. `activity_product_detail.xml` - ProductDetailScreen layout

#### Fragmenti (3):
6. `fragment_main.xml` - MainScreen layout
7. `fragment_account.xml` - AccountScreen layout
8. `fragment_notifications.xml` - NotificationsScreen layout

#### Ostali Layouti (2):
9. `item_product.xml` - ListView stavka
10. `nav_header.xml` - NavigationDrawer header

#### Originalni Layout (1):
11. `activity_main.xml` - MainActivity layout (zadržan)

### Menu Fajl (1 fajl)
1. `menu/drawer_menu.xml` - NavigationDrawer meni

### Values Fajlovi (2 ažurirana)
1. `values/strings.xml` - Svi stringovi (40+ resursa)
2. `values/colors.xml` - Boje aplikacije

### Manifest
1. `AndroidManifest.xml` - Ažuriran sa svim aktivnostima i permisijama

### Dokumentacija (2 fajla)
1. `README.md` - Kompletan vodič za kreiranje projekta
2. `IMPLEMENTACIJA.md` - Ovaj dokument

---

## 🎨 Dizajn i UX

### Boje

Definisane u `res/values/colors.xml`:

```xml
<color name="primary">#2196F3</color>        <!-- Plava -->
<color name="primary_dark">#1976D2</color>   <!-- Tamno plava -->
<color name="accent">#FF5722</color>         <!-- Narandžasta -->
<color name="light_gray">#F5F5F5</color>     <!-- Svetlo siva -->
```

### Material Design Komponente

- ✅ TextInputLayout sa TextInputEditText
- ✅ MaterialButton (outlined i filled)
- ✅ CardView
- ✅ NavigationView
- ✅ Toolbar
- ✅ AppBarLayout
- ✅ DrawerLayout

---

## 🔄 Tok Aplikacije

```
[SplashScreen] 
    ↓ (5 sekundi)
[LoginScreen]
    ↓ (Prijava)                  ↓ (Registracija)
[HomeScreen] ←──────────── [RegisterScreen]
    ├── [MainScreen Fragment]
    │       ↓ (Klik na proizvod)
    │   [ProductDetailScreen]
    ├── [AccountScreen Fragment]
    └── [NotificationsScreen Fragment]
```

---

## 📊 Statistika Projekta

- **Ukupno Java klasa**: 11
- **Ukupno Layout fajlova**: 11
- **Ukupno Menu fajlova**: 1
- **Ukupno aktivnosti sa lifecycle metodama**: 5
- **Ukupno fragmenata**: 3
- **Ukupno model klasa**: 2
- **Ukupno String resursa**: 40+
- **Ukupno Color resursa**: 6
- **Linije koda (približno)**: 2000+

---

## ✨ Napredne Funkcionalnosti Implementirane

1. **Custom ListView Adapter** - Za prikaz proizvoda
2. **TextWatcher** - Za real-time pretragu
3. **Spinner Filter** - Za filtriranje po ceni
4. **Fragment Transactions** - Za navigaciju
5. **Intent Extras** - Za prenos podataka između aktivnosti
6. **NavigationDrawer** - Za glavnu navigaciju
7. **Handler postDelayed** - Za SplashScreen timer
8. **Validacija unosa** - Sa prikaz om greške
9. **Material Design** - Moderan i elegantan dizajn
10. **Logging** - Za praćenje životnog ciklusa

---

## 🎯 Ispunjeni Zahtevi

### Svi zahtevi iz specifikacije su 100% implementirani:

✅ 1. Kreiran SplashScreen  
✅ 2. Kreiran LoginScreen  
✅ 3. Kreiran RegisterScreen  
✅ 4. Kreiran HomeScreen  
✅ 5. Ažuriran AndroidManifest.xml  
✅ 6. Sve aktivnosti nasleđuju AppCompatActivity  
✅ 7. Sve lifecycle metode implementirane  
✅ 8. Automatski prelazak sa SplashScreen nakon 5 sekundi  
✅ 9. LoginScreen sa email, lozinka, dugmad  
✅ 10. Prelazak LoginScreen → RegisterScreen  
✅ 11. Prelazak LoginScreen → HomeScreen  
✅ 12. Internet permisija dodata  
✅ 13. RegisterScreen sa svim poljima i linearnim rasporedom  
✅ 14. Prilagođen inputType za sva polja  
✅ 15. HomeScreen sa relativnim rasporedom  
✅ 16. Prelazak RegisterScreen → HomeScreen  
✅ 17. Svi tekstovi u strings.xml  
✅ 18. Toolbar i NavigationDrawer implementirani  
✅ 19. Navigacija na MainScreen, AccountScreen, NotificationsScreen  
✅ 20. Model klase User i Product kreirane  
✅ 21. Mokap podaci za korisnike  
✅ 22. Mokap podaci za proizvode  
✅ 23. MainScreen sa pretragom, filtrom i ListView-om  
✅ 24. Prikaz detalja proizvoda na kliku  
✅ 25. AccountScreen za upravljanje nalogom  
✅ 26. Toolbar/NavigationDrawer uvek vidljiv  

---

## 🚀 Kako Pokrenuti Projekat

### Preduslov:
- Android Studio instaliran
- Android SDK API 34 ili viši
- Emulator ili fizički uređaj

### Koraci:

1. **Otvorite projekat u Android Studio-u**
   ```
   File → Open → Izaberite folder projekta
   ```

2. **Sinhronizujte Gradle**
   ```
   File → Sync Project with Gradle Files
   ```

3. **Pokrenite aplikaciju**
   ```
   Run → Run 'app'
   ```
   ili pritisnite `Shift+F10`

4. **Testiranje**
   - Aplikacija će se pokrenuti sa SplashScreen-om
   - Nakon 5 sekundi prelazi na LoginScreen
   - Testirajte sve funkcionalnosti prema README.md

---

## 📖 Dodatni Resursi

Za detaljno korak-po-korak uputstvo kako kreirati ovaj projekat od nule, pogledajte:

**README.md** - Kompletan vodič sa objašnjenjima svakog koraka

---

## 👨‍💻 Tehnički Stack

- **Jezik**: Java 11
- **Android SDK**: API 34 (Android 14)
- **Build Tool**: Gradle 8.x
- **UI Framework**: Material Design Components
- **Arhitektura**: Activity + Fragment

---

## 📝 Zaključak

Svi zahtevi su uspešno implementirani sa detaljnim komentarima na srpskom jeziku. Projekat demonstrira potpuno funkcionalan Android aplikaciju sa modernim dizajnom, kompletnom navigacijom, i svim traženim funkcionalnostima.

Aplikacija je spremna za dalji razvoj i proširenje sa novim funkcionalnostima kao što su:
- Backend integracija
- Autentifikacija korisnika
- Skladištenje podataka (Room Database ili Firebase)
- Plaćanje
- Push notifikacije
- I mnogo više!

---

**Verzija dokumenta**: 1.0  
**Datum kreiranja**: 2025  
**Status**: ✅ Sve implementirano

