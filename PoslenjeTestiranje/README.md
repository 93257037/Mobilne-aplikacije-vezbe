# OdNule - Android Aplikacija

Mobilna aplikacija za prodaju proizvoda kreirana u Android Studio-u koristeći Javu.

## 📋 Sadržaj

1. [O Projektu](#o-projektu)
2. [Zahtevi](#zahtevi)
3. [Struktura Projekta](#struktura-projekta)
4. [Korak-po-Korak Uputstvo](#korak-po-korak-uputstvo)
5. [Ekrani i Funkcionalnosti](#ekrani-i-funkcionalnosti)
6. [Napomene](#napomene)

---

## 🎯 O Projektu

Ova Android aplikacija demonstrira kompletnu implementaciju osnovnih funkcionalnosti mobilne aplikacije za prodaju proizvoda, uključujući:

- Splash Screen sa automatskim prelaskom
- Sistem prijave i registracije korisnika
- Navigation Drawer za navigaciju
- Prikaz i pretragu proizvoda
- Detaljni prikaz proizvoda
- Upravljanje korisničkim nalogom
- Sistem obaveštenja

Sve aktivnosti nasleđuju `AppCompatActivity` i implementiraju sve metode životnog ciklusa sa detaljnim logovanje-om.

---

## 📦 Zahtevi

Potrebno je imati instalirano:

- **Android Studio** (Hedgehog 2023.1.1 ili novija verzija)
- **JDK 11** ili noviji
- **Android SDK** sa API Level 34 ili višim
- **Gradle** (dolazi sa Android Studio-m)

---

## 📁 Struktura Projekta

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/kcalorific/odnule/
│   │   │   ├── SplashScreen.java           # Početni ekran
│   │   │   ├── LoginScreen.java            # Ekran za prijavu
│   │   │   ├── RegisterScreen.java         # Ekran za registraciju
│   │   │   ├── HomeScreen.java             # Glavni ekran sa drawer-om
│   │   │   ├── MainScreen.java             # Fragment sa proizvodima
│   │   │   ├── AccountScreen.java          # Fragment sa nalogom
│   │   │   ├── NotificationsScreen.java    # Fragment sa obaveštenjima
│   │   │   ├── ProductDetailScreen.java    # Detalji proizvoda
│   │   │   ├── models/
│   │   │   │   ├── User.java               # Model korisnika
│   │   │   │   └── Product.java            # Model proizvoda
│   │   │   └── MainActivity.java           # Originalna aktivnost
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   ├── activity_splash.xml
│   │   │   │   ├── activity_login.xml
│   │   │   │   ├── activity_register.xml
│   │   │   │   ├── activity_home.xml
│   │   │   │   ├── activity_product_detail.xml
│   │   │   │   ├── fragment_main.xml
│   │   │   │   ├── fragment_account.xml
│   │   │   │   ├── fragment_notifications.xml
│   │   │   │   ├── item_product.xml
│   │   │   │   └── nav_header.xml
│   │   │   ├── menu/
│   │   │   │   └── drawer_menu.xml
│   │   │   ├── values/
│   │   │   │   ├── strings.xml
│   │   │   │   ├── colors.xml
│   │   │   │   └── themes.xml
│   │   │   └── ...
│   │   └── AndroidManifest.xml
│   └── ...
└── build.gradle
```

---

## 🛠️ Korak-po-Korak Uputstvo

### KORAK 1: Kreiranje Novog Projekta

1. Otvorite **Android Studio**
2. Izaberite **File → New → New Project**
3. Izaberite **Empty Views Activity**
4. Popunite podatke:
   - **Name**: OdNule
   - **Package name**: com.kcalorific.odnule
   - **Language**: Java
   - **Minimum SDK**: API 34
5. Kliknite **Finish**

### KORAK 2: Dodavanje Zavisnosti

Otvorite `app/build.gradle` i proverite da imate sledeće zavisnosti:

```gradle
dependencies {
    implementation libs.appcompat
    implementation libs.material
    implementation libs.activity
    implementation libs.constraintlayout
    testImplementation libs.junit
    androidTestImplementation libs.ext.junit
    androidTestImplementation libs.espresso.core
}
```

Sinhronizujte projekat klikom na **Sync Now**.

### KORAK 3: Kreiranje Model Klasa

#### 3.1 Kreiranje User.java

1. U `app/src/main/java/com/kcalorific/odnule/` kreirajte novi paket `models`
2. Desni klik na `models` → **New → Java Class**
3. Naziv: `User`
4. Kopirajte kod iz `User.java` fajla

**Ključni elementi:**
- Atributi: firstName, lastName, birthYear, email
- Getteri i setteri za sve atribute
- Metode: getFullName(), getAge()

#### 3.2 Kreiranje Product.java

1. U istom paketu `models` kreirajte novu klasu
2. Naziv: `Product`
3. Kopirajte kod iz `Product.java` fajla

**Ključni elementi:**
- Atributi: id, name, description, price
- Getteri i setteri za sve atribute
- Metoda: getFormattedPrice()

### KORAK 4: Kreiranje Ekrana (Aktivnosti)

#### 4.1 SplashScreen

**Java klasa (SplashScreen.java):**
1. Desni klik na paket → **New → Java Class**
2. Naziv: `SplashScreen`
3. Nasledite `AppCompatActivity`
4. Implementirajte sve metode životnog ciklusa:
   - onCreate, onStart, onRestart, onResume, onPause, onStop, onDestroy
5. U `onCreate()` dodajte Handler sa odloženim izvršavanjem:
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

**Layout (activity_splash.xml):**
1. U `res/layout/` → **New → Layout Resource File**
2. Naziv: `activity_splash`
3. Root element: `RelativeLayout`
4. Dodajte ImageView za logo i TextView-ove za naziv i slogan aplikacije

#### 4.2 LoginScreen

**Java klasa (LoginScreen.java):**
1. Kreirajte novu klasu koja nasleduje `AppCompatActivity`
2. Implementirajte sve metode životnog ciklusa
3. Dodajte polja za UI komponente:
   ```java
   private EditText etEmail;
   private EditText etPassword;
   private Button btnLogin;
   private Button btnGoToRegister;
   ```
4. U `onCreate()`:
   - Inicijalizujte view-ove
   - Postavite listener-e na dugmad
5. Implementirajte metode:
   - `handleLogin()` - validacija i prelazak na HomeScreen
   - `goToRegisterScreen()` - prelazak na RegisterScreen

**Layout (activity_login.xml):**
1. Koristite `ScrollView` sa `LinearLayout`
2. Dodajte `TextInputLayout` sa `TextInputEditText` za email:
   - `inputType="textEmailAddress"`
3. Dodajte `TextInputLayout` sa `TextInputEditText` za lozinku:
   - `inputType="textPassword"`
4. Dodajte dva `Button`-a:
   - btnLogin - za prijavu
   - btnGoToRegister - za prelazak na registraciju

#### 4.3 RegisterScreen

**Java klasa (RegisterScreen.java):**
1. Kreirajte novu klasu koja nasleduje `AppCompatActivity`
2. Implementirajte sve metode životnog ciklusa
3. Dodajte polja za sve unose:
   - etFirstName, etLastName, etEmail, etPhone, etPassword, etConfirmPassword
4. Implementirajte `handleRegistration()` sa validacijom:
   - Proverite da li su sva polja popunjena
   - Proverite da li se lozinke poklapaju
   - Pređite na HomeScreen

**Layout (activity_register.xml):**
1. Koristite `ScrollView` sa `LinearLayout` (vertikalna orijentacija)
2. Dodajte `TextInputLayout` za svako polje:
   - Ime (`inputType="textPersonName"`)
   - Prezime (`inputType="textPersonName"`)
   - Email (`inputType="textEmailAddress"`)
   - Telefon (`inputType="phone"`)
   - Lozinka (`inputType="textPassword"`)
   - Potvrda lozinke (`inputType="textPassword"`)
3. Dodajte dugmad za registraciju i povratak

#### 4.4 HomeScreen

**Java klasa (HomeScreen.java):**
1. Kreirajte novu klasu koja nasleduje `AppCompatActivity`
2. Implementirajte `NavigationView.OnNavigationItemSelectedListener`
3. Implementirajte sve metode životnog ciklusa
4. Dodajte polja:
   ```java
   private DrawerLayout drawerLayout;
   private NavigationView navigationView;
   private Toolbar toolbar;
   ```
5. U `onCreate()`:
   - Postavite toolbar kao ActionBar
   - Konfigurišite NavigationDrawer
   - Učitajte podrazumevani fragment (MainScreen)
6. Implementirajte `onNavigationItemSelected()` za navigaciju

**Layout (activity_home.xml):**
1. Root element: `DrawerLayout`
2. Glavni sadržaj:
   - `AppBarLayout` sa `Toolbar`
   - `TextView` za poruku dobrodošlice
   - `FrameLayout` (id: fragmentContainer) za fragmente
3. `NavigationView`:
   - `headerLayout="@layout/nav_header"`
   - `menu="@menu/drawer_menu"`

#### 4.5 ProductDetailScreen

**Java klasa (ProductDetailScreen.java):**
1. Kreirajte novu klasu koja nasleduje `AppCompatActivity`
2. Implementirajte sve metode životnog ciklusa
3. U `onCreate()`:
   - Učitajte podatke iz Intent-a
   - Prikažite podatke o proizvodu
4. Implementirajte `onOptionsItemSelected()` za "nazad" dugme

**Layout (activity_product_detail.xml):**
1. `LinearLayout` sa vertikalnom orijentacijom
2. `Toolbar` na vrhu
3. `ScrollView` sa:
   - `ImageView` za sliku proizvoda
   - `TextView` za naziv, cenu i opis
4. `Button` za dodavanje u korpu

### KORAK 5: Kreiranje Fragmenata

#### 5.1 MainScreen (Fragment)

**Java klasa (MainScreen.java):**
1. Kreirajte klasu koja nasleduje `Fragment`
2. U `onCreateView()`:
   - Inflate-ujte layout
   - Inicijalizujte ListView, EditText za pretragu, Spinner za filter
   - Učitajte mokap podatke proizvoda
3. Implementirajte:
   - `loadMockProducts()` - kreiranje liste proizvoda
   - `setupProductsList()` - postavljanje adaptera
   - `setupSearch()` - TextWatcher za pretragu
   - `setupFilter()` - filtriranje po ceni
   - Custom `ProductAdapter` za ListView

**Layout (fragment_main.xml):**
1. `LinearLayout` sa vertikalnom orijentacijom
2. `TextView` za naslov
3. `EditText` za pretragu (`inputType="text"`)
4. `Spinner` za filter opcije
5. `ListView` za prikaz proizvoda

**Layout stavke proizvoda (item_product.xml):**
1. `LinearLayout` sa vertikalnom orijentacijom
2. `TextView` za naziv proizvoda
3. `TextView` za opis
4. `TextView` za cenu

#### 5.2 AccountScreen (Fragment)

**Java klasa (AccountScreen.java):**
1. Kreirajte klasu koja nasleduje `Fragment`
2. U `onCreateView()`:
   - Inicijalizujte view-ove
   - Učitajte mokap podatke korisnika
   - Prikažite podatke

**Layout (fragment_account.xml):**
1. `ScrollView` sa `LinearLayout`
2. `CardView` sa:
   - `ImageView` za avatar
   - `TextView` za ime, email, godište, starost
3. Dugmad za izmenu profila i promenu lozinke

#### 5.3 NotificationsScreen (Fragment)

**Java klasa (NotificationsScreen.java):**
1. Kreirajte klasu koja nasleduje `Fragment`
2. U `onCreateView()`:
   - Učitajte mokap obaveštenja
   - Postavite ListView adapter

**Layout (fragment_notifications.xml):**
1. `LinearLayout` sa vertikalnom orijentacijom
2. `TextView` za naslov
3. `ListView` za obaveštenja

### KORAK 6: Kreiranje Navigation Drawer Resursa

#### 6.1 Navigation Header (nav_header.xml)

U `res/layout/`:
1. Kreirajte novi layout resource file
2. Naziv: `nav_header`
3. Root element: `LinearLayout`
4. Dodajte:
   - `ImageView` za avatar
   - `TextView` za ime korisnika
   - `TextView` za email

#### 6.2 Drawer Menu (drawer_menu.xml)

U `res/menu/`:
1. Kreirajte novi menu resource file
2. Naziv: `drawer_menu`
3. Dodajte stavke menija:
   ```xml
   <item android:id="@+id/nav_main"
         android:icon="@android:drawable/ic_menu_search"
         android:title="@string/nav_main" />
   <item android:id="@+id/nav_account"
         android:icon="@android:drawable/ic_menu_manage"
         android:title="@string/nav_account" />
   <item android:id="@+id/nav_notifications"
         android:icon="@android:drawable/ic_menu_info_details"
         android:title="@string/nav_notifications" />
   <item android:id="@+id/nav_logout"
         android:icon="@android:drawable/ic_lock_power_off"
         android:title="@string/nav_logout" />
   ```

### KORAK 7: Ažuriranje strings.xml

U `res/values/strings.xml` dodajte sve potrebne string resurse:

```xml
<string name="app_name">OdNule</string>
<string name="login_title">Prijava</string>
<string name="register_title">Registracija</string>
<string name="hint_email">Email adresa</string>
<string name="hint_password">Lozinka</string>
<!-- ... i ostali stringovi -->
```

**Napomena**: Svi tekstovi vidljivi korisniku moraju biti definisani u strings.xml, ne hardkodirani u layoutima!

### KORAK 8: Ažuriranje colors.xml

U `res/values/colors.xml` dodajte boje:

```xml
<color name="primary">#2196F3</color>
<color name="primary_dark">#1976D2</color>
<color name="accent">#FF5722</color>
<color name="light_gray">#F5F5F5</color>
```

### KORAK 9: Ažuriranje AndroidManifest.xml

1. Dodajte permisiju za internet iznad `<application>` taga:
   ```xml
   <uses-permission android:name="android.permission.INTERNET" />
   ```

2. Dodajte sve aktivnosti:
   ```xml
   <!-- SplashScreen kao LAUNCHER -->
   <activity
       android:name=".SplashScreen"
       android:exported="true"
       android:theme="@style/Theme.AppCompat.NoActionBar">
       <intent-filter>
           <action android:name="android.intent.action.MAIN" />
           <category android:name="android.intent.category.LAUNCHER" />
       </intent-filter>
   </activity>

   <!-- LoginScreen -->
   <activity
       android:name=".LoginScreen"
       android:exported="false"
       android:theme="@style/Theme.AppCompat.NoActionBar" />

   <!-- RegisterScreen -->
   <activity
       android:name=".RegisterScreen"
       android:exported="false"
       android:theme="@style/Theme.AppCompat.NoActionBar" />

   <!-- HomeScreen -->
   <activity
       android:name=".HomeScreen"
       android:exported="false" />

   <!-- ProductDetailScreen -->
   <activity
       android:name=".ProductDetailScreen"
       android:exported="false"
       android:parentActivityName=".HomeScreen" />
   ```

### KORAK 10: Testiranje Aplikacije

1. Povežite Android uređaj ili pokrenite emulator
2. Kliknite **Run → Run 'app'** (ili Shift+F10)
3. Testiranje toka aplikacije:
   - **SplashScreen** se prikazuje 5 sekundi
   - Automatski prelazi na **LoginScreen**
   - Klik na "Kreiraj novi nalog" → **RegisterScreen**
   - Popunite polja i registrujte se → **HomeScreen**
   - Otvorite **NavigationDrawer** (hamburger ikona)
   - Navigirajte kroz **Proizvodi**, **Moj nalog**, **Obaveštenja**
   - Na stranici **Proizvodi**:
     - Pretražite proizvode
     - Filtrirajte po ceni
     - Kliknite na proizvod → **ProductDetailScreen**
   - Testirajte dugme "Odjavi se"

---

## 📱 Ekrani i Funkcionalnosti

### 1. SplashScreen
- **Trajanje**: 5 sekundi
- **Funkcionalnost**: Automatski prelazi na LoginScreen
- **Layout**: RelativeLayout sa logom i nazivom aplikacije

### 2. LoginScreen
- **Polja**: Email (textEmailAddress), Lozinka (textPassword)
- **Dugmad**: "Prijavi se", "Kreiraj novi nalog"
- **Funkcionalnost**: 
  - Validacija unosa
  - Prelazak na HomeScreen nakon prijave
  - Prelazak na RegisterScreen za registraciju

### 3. RegisterScreen
- **Layout**: LinearLayout sa vertikalnom orijentacijom
- **Polja**:
  - Ime (textPersonName)
  - Prezime (textPersonName)
  - Email (textEmailAddress)
  - Telefon (phone)
  - Lozinka (textPassword)
  - Potvrda lozinke (textPassword)
- **Funkcionalnost**:
  - Validacija svih polja
  - Provera poklapanja lozinki
  - Prelazak na HomeScreen nakon registracije

### 4. HomeScreen
- **Layout**: RelativeLayout (za glavni deo) sa DrawerLayout
- **Komponente**:
  - Toolbar (uvek vidljiv)
  - NavigationDrawer sa stavkama menija
  - FrameLayout za dinamičko učitavanje fragmenata
- **Funkcionalnost**:
  - Navigacija kroz aplikaciju
  - Učitavanje fragmenata (MainScreen, AccountScreen, NotificationsScreen)
  - Odjava korisnika

### 5. MainScreen (Fragment)
- **Funkcionalnosti**:
  - Prikaz svih proizvoda u ListView-u
  - Pretraga proizvoda po nazivu i opisu
  - Filtriranje proizvoda po ceni (Spinner)
  - Klik na proizvod → ProductDetailScreen
- **Mokap podaci**: 10 proizvoda sa nazivom, opisom i cenom

### 6. AccountScreen (Fragment)
- **Funkcionalnosti**:
  - Prikaz podataka korisnika (ime, prezime, godište, email)
  - Prikazuje starost korisnika (izračunava se iz godišta)
  - Dugmad za izmenu profila i promenu lozinke (UIonly)
- **Mokap podaci**: User("Marko", "Marković", 1995, "marko.markovic@example.com")

### 7. NotificationsScreen (Fragment)
- **Funkcionalnosti**:
  - Prikaz liste obaveštenja
- **Mokap podaci**: 8 različitih obaveštenja

### 8. ProductDetailScreen (Activity)
- **Funkcionalnosti**:
  - Prikaz detaljnih informacija o proizvodu
  - Dugme "Dodaj u korpu" sa Toast porukom
  - "Nazad" dugme u Toolbar-u
- **Podaci**: Prima podatke iz Intent-a (ID, naziv, opis, cena)

---

## 🔍 Ključni Koncepti i Implementacije

### Životni Ciklus Aktivnosti

Sve aktivnosti implementiraju sve metode životnog ciklusa:

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_name);
    Log.d(TAG, "onCreate: Aktivnost kreirana");
    // Inicijalizacija
}

@Override
protected void onStart() {
    super.onStart();
    Log.d(TAG, "onStart: Aktivnost postala vidljiva");
}

@Override
protected void onRestart() {
    super.onRestart();
    Log.d(TAG, "onRestart: Aktivnost se restartuje");
}

@Override
protected void onResume() {
    super.onResume();
    Log.d(TAG, "onResume: Aktivnost u prvom planu");
}

@Override
protected void onPause() {
    super.onPause();
    Log.d(TAG, "onPause: Aktivnost pauzirana");
}

@Override
protected void onStop() {
    super.onStop();
    Log.d(TAG, "onStop: Aktivnost zaustavljena");
}

@Override
protected void onDestroy() {
    super.onDestroy();
    Log.d(TAG, "onDestroy: Aktivnost uništena");
}
```

**Zašto je ovo važno?**
- Omogućava praćenje stanja aplikacije kroz Logcat
- Pomaže u razumevanju Android životnog ciklusa
- Korisno za debugging

### Prelazak Između Aktivnosti

**Primer iz LoginScreen → HomeScreen:**

```java
Intent intent = new Intent(LoginScreen.this, HomeScreen.class);
intent.putExtra("USER_EMAIL", email);
startActivity(intent);
finish(); // Zatvara trenutnu aktivnost
```

### Handler za Odloženo Izvršavanje

**SplashScreen primer:**

```java
new Handler().postDelayed(new Runnable() {
    @Override
    public void run() {
        // Kod koji se izvršava nakon 5 sekundi
        Intent intent = new Intent(SplashScreen.this, LoginScreen.class);
        startActivity(intent);
        finish();
    }
}, 5000); // Vreme u milisekundama
```

### NavigationDrawer Implementacija

**Ključni koraci:**

1. DrawerLayout kao root element layouta
2. ActionBarDrawerToggle za otvaranje/zatvaranje drawer-a
3. NavigationView.OnNavigationItemSelectedListener interfejs
4. Fragment transakcije za prelazak između ekrana

```java
@Override
public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    int itemId = item.getItemId();
    
    if (itemId == R.id.nav_main) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new MainScreen())
                .commit();
    }
    // ... ostale stavke
    
    drawerLayout.closeDrawer(GravityCompat.START);
    return true;
}
```

### Custom ListView Adapter

**Implementacija u MainScreen:**

```java
private class ProductAdapter extends ArrayAdapter<Product> {
    
    public ProductAdapter() {
        super(requireContext(), R.layout.item_product, filteredProducts);
    }
    
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_product, parent, false);
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
}
```

### Pretraga i Filtriranje

**TextWatcher za pretragu:**

```java
etSearch.addTextChangedListener(new TextWatcher() {
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        filterProducts(s.toString());
    }
    
    // Ostale metode...
});
```

**Spinner za filtriranje po ceni:**

```java
spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        applyPriceFilter(position);
    }
    
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
});
```

---

## 📝 Napomene

### Važne Napomene za Razvoj

1. **Sve stringove definisati u strings.xml**
   - NE hardkodirati tekstove u XML layoutima
   - Koristi `android:text="@string/resource_name"`

2. **InputType atributi**
   - Email: `android:inputType="textEmailAddress"`
   - Lozinka: `android:inputType="textPassword"`
   - Telefon: `android:inputType="phone"`
   - Ime/Prezime: `android:inputType="textPersonName"`

3. **Toolbar treba da bude vidljiv u svakom trenutku**
   - HomeScreen koristi DrawerLayout sa Toolbar-om
   - Toolbar ostaje vidljiv pri navigaciji kroz fragmente

4. **Validacija unosa**
   - Proveravati da li su polja popunjena
   - Koristiti `EditText.setError()` za prikaz greške
   - Pozivati `requestFocus()` na polju sa greškom

5. **Mokap podaci**
   - Proizvodi: Lista od 10 proizvoda sa realističnim podacima
   - Korisnik: Marko Marković, 1995, marko.markovic@example.com

6. **Permisije**
   - INTERNET permisija dodana u AndroidManifest.xml
   - Potrebna za buduće API pozive

### Česte Greške i Rešenja

**Greška: "Resource not found"**
- **Uzrok**: String nije definisan u strings.xml
- **Rešenje**: Dodati string u `res/values/strings.xml`

**Greška: "Cannot resolve symbol 'R'"**
- **Uzrok**: Gradle nije sinhronizovan ili greška u XML fajlovima
- **Rešenje**: Clean Project → Rebuild Project → Sync Gradle

**Greška: Fragment se ne prikazuje**
- **Uzrok**: Zaboravljen commit() ili pogrešan ID kontejnera
- **Rešenje**: Proveriti da li je `fragmentContainer` ID tačan i da li je `commit()` pozvan

**Greška: NavigationDrawer se ne otvara**
- **Uzrok**: ActionBarDrawerToggle nije pravilno konfigurisan
- **Rešenje**: Proveriti da li je `toggle.syncState()` pozvan i da li je listener postavljen

### Testiranje

**Koraci za testiranje:**

1. **Pokretanje aplikacije**
   - Trebalo bi da se prikaže SplashScreen
   - Nakon 5 sekundi prelazi na LoginScreen

2. **Registracija**
   - Kliknuti na "Kreiraj novi nalog"
   - Popuniti sva polja
   - Kliknuti "Registruj se"
   - Trebalo bi da pređe na HomeScreen

3. **Navigacija**
   - Otvoriti NavigationDrawer (hamburger ikona)
   - Navigirati kroz Proizvodi, Moj nalog, Obaveštenja
   - Proveriti da Toolbar ostaje vidljiv

4. **Proizvodi**
   - Pretražiti proizvode unosom teksta
   - Filtrirati po ceni koristeći Spinner
   - Kliknuti na proizvod za detalje

5. **Logcat praćenje**
   - Otvoriti Logcat u Android Studio-u
   - Proveriti logove za svaku aktivnost (onCreate, onStart, itd.)

### Dodatni Resursi

- [Android Developer Documentation](https://developer.android.com/docs)
- [Material Design Components](https://material.io/develop/android)
- [Android Activity Lifecycle](https://developer.android.com/guide/components/activities/activity-lifecycle)
- [Fragments Guide](https://developer.android.com/guide/fragments)

---

## 🎓 Zaključak

Ova aplikacija demonstrira kompletnu implementaciju osnovnih Android koncepata:

✅ Višestruke aktivnosti sa pravilnim životnim ciklusom  
✅ Navigacija između ekrana  
✅ NavigationDrawer za intuitivnu navigaciju  
✅ Fragmenti za modularnu strukturu  
✅ ListView sa custom adapterom  
✅ Pretraga i filtriranje podataka  
✅ Material Design komponente  
✅ Validacija korisničkog unosa  
✅ Intent-based komunikacija između aktivnosti  
✅ Svi tekstovi u strings.xml (internacionalizacija spremna)  

Pratite korake pažljivo i testirajte aplikaciju nakon svakog većeg koraka!

---

**Kreirao**: Android Studio projekat  
**Verzija**: 1.0  
**Datum**: 2025

