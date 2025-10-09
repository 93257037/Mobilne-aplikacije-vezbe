# 🚀 Brzi Vodič - OdNule Android Aplikacija

## Šta je Implementirano?

Kompletna Android aplikacija za prodaju proizvoda sa:
- ✅ 5 Aktivnosti (SplashScreen, LoginScreen, RegisterScreen, HomeScreen, ProductDetailScreen)
- ✅ 3 Fragmenta (MainScreen, AccountScreen, NotificationsScreen)
- ✅ 2 Model klase (User, Product)
- ✅ NavigationDrawer za navigaciju
- ✅ Pretraga i filtriranje proizvoda
- ✅ Mokap podaci
- ✅ Svi tekstovi u strings.xml

---

## 📱 Tok Aplikacije

```
1. SplashScreen (5 sekundi) → automatski prelazi na
2. LoginScreen → 
   - Prijava → HomeScreen
   - Registracija → RegisterScreen → HomeScreen
3. HomeScreen (NavigationDrawer):
   - Proizvodi (pretraga, filter, prikaz)
   - Moj nalog (podaci korisnika)
   - Obaveštenja (lista obaveštenja)
4. Klik na proizvod → ProductDetailScreen
```

---

## 🗂️ Struktura Projekta

```
app/src/main/
├── java/com/kcalorific/odnule/
│   ├── SplashScreen.java          ← Početni ekran (5 sekundi)
│   ├── LoginScreen.java           ← Prijava (email + lozinka)
│   ├── RegisterScreen.java        ← Registracija (6 polja)
│   ├── HomeScreen.java            ← Glavni ekran (Drawer)
│   ├── ProductDetailScreen.java   ← Detalji proizvoda
│   ├── MainScreen.java            ← Fragment: Proizvodi
│   ├── AccountScreen.java         ← Fragment: Nalog
│   ├── NotificationsScreen.java   ← Fragment: Obaveštenja
│   └── models/
│       ├── User.java              ← Model korisnika
│       └── Product.java           ← Model proizvoda
│
├── res/
│   ├── layout/
│   │   ├── activity_splash.xml
│   │   ├── activity_login.xml
│   │   ├── activity_register.xml
│   │   ├── activity_home.xml
│   │   ├── activity_product_detail.xml
│   │   ├── fragment_main.xml
│   │   ├── fragment_account.xml
│   │   ├── fragment_notifications.xml
│   │   ├── item_product.xml
│   │   └── nav_header.xml
│   ├── menu/
│   │   └── drawer_menu.xml
│   └── values/
│       ├── strings.xml            ← 40+ stringova
│       └── colors.xml             ← Boje aplikacije
│
└── AndroidManifest.xml            ← Sve aktivnosti + permisije

```

---

## ⚙️ Ključne Funkcionalnosti

### 1. Lifecycle Metode
Sve aktivnosti implementiraju:
```java
onCreate(), onStart(), onRestart(), onResume(), 
onPause(), onStop(), onDestroy()
```
Sa `Log.d()` pozivima za praćenje.

### 2. Validacija Unosa
- Proverava da li su polja popunjena
- Prikazuje greške pomoću `setError()`
- Potvrđuje poklapanje lozinki

### 3. NavigationDrawer
- Hamburger ikona za otvaranje
- Navigacija: Proizvodi, Moj nalog, Obaveštenja, Odjava
- Toolbar uvek vidljiv

### 4. Pretraga i Filtriranje
- **Pretraga**: Real-time po nazivu/opisu
- **Filter**: Po ceni (4 opcije)
- **ListView**: Custom adapter sa proizvodima

### 5. Intent Komunikacija
```java
// Slanje podataka
Intent intent = new Intent(From.this, To.class);
intent.putExtra("KEY", value);
startActivity(intent);

// Primanje podataka
String value = getIntent().getStringExtra("KEY");
```

---

## 🎨 InputType Atributi

| Polje | InputType |
|-------|-----------|
| Email | `textEmailAddress` |
| Lozinka | `textPassword` |
| Telefon | `phone` |
| Ime/Prezime | `textPersonName` |
| Opšti tekst | `text` |

---

## 📊 Mokap Podaci

### Korisnik
```java
User("Marko", "Marković", 1995, "marko.markovic@example.com")
```

### Proizvodi (10 komada)
1. Laptop Dell XPS 15 - 150,000 RSD
2. Samsung Galaxy S23 - 80,000 RSD
3. Sony WH-1000XM5 - 35,000 RSD
4. Logitech MX Keys - 12,000 RSD
5. Logitech MX Master 3 - 9,000 RSD
6. LG UltraWide 34" - 55,000 RSD
7. Logitech C920 - 8,000 RSD
8. Seagate 2TB - 7,000 RSD
9. Anker USB-C Hub - 4,500 RSD
10. Apple iPad Air - 75,000 RSD

---

## 🔧 Kako Pokrenuti?

1. **Otvorite projekat**
   ```
   Android Studio → File → Open → Izaberite folder
   ```

2. **Sync Gradle**
   ```
   File → Sync Project with Gradle Files
   ```

3. **Run**
   ```
   Run → Run 'app' (ili Shift+F10)
   ```

4. **Testiraj**
   - SplashScreen → čeka 5 sekundi
   - LoginScreen → klikni "Kreiraj novi nalog"
   - RegisterScreen → popuni polja → registruj se
   - HomeScreen → otvori drawer → navigiraj
   - MainScreen → pretraži/filtriraj/klikni proizvod

---

## 🐛 Česte Greške

### "Resource not found"
**Rešenje**: Sync Gradle → Clean Project → Rebuild

### "Cannot resolve symbol 'R'"
**Rešenje**: Proverite XML fajlove za greške → Sync Gradle

### NavigationDrawer se ne otvara
**Rešenje**: Proverite `toggle.syncState()` u `onCreate()`

### Fragment se ne prikazuje
**Rešenje**: Proverite ID kontejnera (`fragmentContainer`) i `commit()` poziv

---

## 📚 Dokumentacija

Za detaljnija objašnjenja pogledajte:

1. **README.md** - Kompletan korak-po-korak vodič
2. **IMPLEMENTACIJA.md** - Detaljan pregled svega implementiranog
3. **BRZI_VODIC.md** - Ovaj dokument (brzi pregled)

---

## ✅ Checklist Implementacije

- [x] SplashScreen sa 5-sekundnim čekanjem
- [x] LoginScreen sa email/lozinka poljima
- [x] RegisterScreen sa 6 polja (linearni layout)
- [x] HomeScreen sa NavigationDrawer
- [x] ProductDetailScreen za detalje
- [x] MainScreen fragment (pretraga, filter, ListView)
- [x] AccountScreen fragment (podaci korisnika)
- [x] NotificationsScreen fragment (lista obaveštenja)
- [x] User i Product model klase
- [x] Svi lifecycle metode implementirane
- [x] Svi stringovi u strings.xml
- [x] Internet permisija u Manifest-u
- [x] Sve aktivnosti u Manifest-u
- [x] InputType prilagođen za svako polje
- [x] Validacija unosa svuda
- [x] Mokap podaci učitani
- [x] Navigation između ekrana radi
- [x] Toolbar uvek vidljiv
- [x] Komentari na srpskom jeziku
- [x] README na srpskom jeziku

---

## 🎯 Principais Koncepti

1. **Activity Lifecycle** - onCreate → onStart → onResume → onPause → onStop → onDestroy
2. **Fragment Lifecycle** - onCreateView → onStart → onResume → onPause → onStop → onDestroyView
3. **Intent** - Prenos podataka između aktivnosti
4. **ListView + Adapter** - Prikazivanje liste podataka
5. **TextWatcher** - Real-time praćenje unosa
6. **NavigationDrawer** - Navigacija sa bočnim menijem
7. **Material Design** - Moderan UI/UX

---

## 🔗 Brze Reference

### Navigacija
```java
// Activity → Activity
Intent intent = new Intent(CurrentActivity.this, NextActivity.class);
startActivity(intent);

// Fragment → Fragment
getSupportFragmentManager().beginTransaction()
    .replace(R.id.fragmentContainer, new SomeFragment())
    .commit();
```

### Validacija
```java
if (text.isEmpty()) {
    editText.setError("Greška");
    editText.requestFocus();
    return;
}
```

### ListView Click
```java
listView.setOnItemClickListener((parent, view, position, id) -> {
    Object item = list.get(position);
    // Obrada klika
});
```

### String Resursi
```xml
<!-- strings.xml -->
<string name="app_name">OdNule</string>

<!-- Java -->
String text = getString(R.string.app_name);

<!-- XML -->
android:text="@string/app_name"
```

---

## 💡 Saveti

1. **Uvek koristite strings.xml** za tekstove
2. **Validujte unos** pre prelaska na sledeći ekran
3. **Pozovite finish()** nakon Intent-a ako ne želite povratak
4. **Koristite Log.d()** za praćenje toka aplikacije
5. **Testirajte na emulatoru I fizičkom uređaju**

---

## 📞 Dodatna Pomoć

Pogledajte README.md za:
- Detaljno objašnjenje svakog koraka
- Primere koda
- Rešavanje problema
- Best practices

---

**Uspešno kodiranje! 🚀**

