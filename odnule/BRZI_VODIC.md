# Brzi Vodič - Za Brzo Testiranje

## 🎯 Quick Start

### 1️⃣ Otvorite Projekat u Android Studio
```
File → Open → Izaberite folder "odnule"
```

### 2️⃣ Sinhronizujte Gradle
```
Kliknite "Sync Now" kada se pojavi notifikacija
```

### 3️⃣ Pokrenite Aplikaciju
```
Shift + F10 ili kliknite Run ▶
```

---

## 🔐 Test Korisnici

| Korisničko Ime | Lozinka  | Ime i Prezime        |
|----------------|----------|----------------------|
| admin          | admin123 | Admin Administrator  |
| korisnik1      | pass123  | Petar Petrović      |

---

## 🧪 Brzi Test Scenario (5 minuta)

### Korak 1: Login
```
1. Aplikacija se otvara na LoginActivity
2. Unesite: admin / admin123
3. Kliknite "Prijavi se"
```
**✅ Rezultat:** Prelaz na MainActivity, poruka "Dobrodošli, Admin Administrator (admin)!"

---

### Korak 2: Pokrenite Muziku
```
1. Kliknite "Pokreni muziku i notifikaciju"
```
**✅ Rezultat:** 
- Muzika svira
- Notifikacija "Melodija puštena!" se pojavljuje
- Toast: "MusicService pokrenut!"

---

### Korak 3: Test BroadcastReceiver-a
```
1. Kliknite "Idi na TestActivity"
2. Vidite tekst "Oboji me!" (crne boje)
3. Povucite notification drawer sa vrha
4. Kliknite dugme "Ispiši" u notifikaciji
```
**✅ Rezultat:**
- Tekst "Oboji me!" postaje **CRVEN**
- U logcat-u: "Pozdrav iz servisa!"

---

### Korak 4: Podešavanje Sinhronizacije
```
1. Vratite se na MainActivity (Back dugme)
2. Kliknite "Podešavanja sinhronizacije"
3. Odaberite "Svakih 15 minuta"
4. Kliknite "Sačuvaj podešavanja"
5. Vratite se na MainActivity
```
**✅ Rezultat:** Vidite "Sinhronizacija: Svakih 15 minuta"

---

### Korak 5: Test ContentProvider-a
```
1. Kliknite "Prikaži kontakte (ContentProvider)"
2. Dozvolite pristup kontaktima
```
**✅ Rezultat:** Lista kontakata sa uređaja

**Napomena:** Ako emulator nema kontakte, dodajte ih:
```
Contacts app → + → Dodajte ime i broj telefona
```

---

## 📱 Navigacija po Ekranima

```
LoginActivity (Start)
    ↓ (uspešan login)
MainActivity
    ├─ Dugme 1 → MusicService (muzika + notifikacija)
    ├─ Dugme 2 → TestActivity (tekst "Oboji me!")
    ├─ Settings → SettingsActivity (sinhronizacija)
    ├─ Contacts → ContactsActivity (ContentProvider)
    └─ Logout → LoginActivity (odjavljivanje)
```

---

## 🔍 Logcat Filteri

Za praćenje svih događaja:
```
com.kcalorific.vezbepetisest
```

Za praćenje specifične komponente:
```
MusicService
PrintBroadcastReceiver
TestActivity
DatabaseHelper
PreferencesManager
```

---

## 🎨 Očekivano Ponašanje

### LoginActivity
- **Input:** Korisničko ime i lozinka
- **Validacija:** Proverava u bazi
- **Uspeh:** Prelazi na MainActivity + čuva u SharedPreferences
- **Neuspeh:** Toast "Pogrešno korisničko ime ili lozinka"

### MainActivity
- **Provera:** Da li je korisnik ulogovan
- **Nije ulogovan:** Automatski prelazi na LoginActivity
- **Ulogovan:** Prikazuje personalizovani ekran
- **Dugme 1:** Pokreće MusicService
- **Dugme 2:** Otvara TestActivity

### TestActivity
- **Prikaz:** Tekst "Oboji me!" (inicijalno crne boje)
- **BroadcastReceiver:** Osluškuje ACTION_COLOR_TEXT
- **Akcija:** Boji tekst u crveno kada primi broadcast

### SettingsActivity
- **Radio buttons:** 4 opcije intervala
- **Čuvanje:** U SharedPreferences
- **Validacija:** Mora biti odabran interval

### ContactsActivity
- **Dozvola:** Traži READ_CONTACTS
- **Učitavanje:** Query ContactsContract
- **Prikaz:** Prvih 20 kontakata, sortirano po imenu

### MusicService
- **Pokretanje:** Kao foreground service
- **Muzika:** MediaPlayer sa loop-om
- **Notifikacija:** "Melodija puštena!" + dugme "Ispiši"
- **Zaustavljanje:** Oslobađa resurse

### PrintBroadcastReceiver
- **Trigger:** Klik na "Ispiši" u notifikaciji
- **Akcija 1:** Log.d("Pozdrav iz servisa!")
- **Akcija 2:** Šalje broadcast ACTION_COLOR_TEXT

---

## ⚠️ Česti Problemi

### Problem: Muzika ne svira
**Rešenje:** Proverite volume na uređaju/emulatoru

### Problem: Notifikacija se ne pojavljuje
**Rešenje:** 
- Proverite da li je kanal kreiran
- Za Android 13+, dozvolite POST_NOTIFICATIONS

### Problem: Tekst se ne boji
**Rešenje:** 
- TestActivity mora biti otvorena kada kliknete "Ispiši"
- BroadcastReceiver se registruje u onCreate, deregistruje u onDestroy

### Problem: Kontakti se ne učitavaju
**Rešenje:**
- Dozvolite READ_CONTACTS dozvolu
- Dodajte testne kontakte u emulatoru

### Problem: Ne mogu da se prijavim
**Rešenje:**
- Koristite test kredencijale: admin/admin123 ili korisnik1/pass123
- Proverite logcat za poruke iz DatabaseHelper

---

## 📂 Ključni Fajlovi

### Java (10 fajlova)
```
MainActivity.java              - Glavni ekran
LoginActivity.java             - Login
TestActivity.java              - "Oboji me!"
SettingsActivity.java          - Sinhronizacija
ContactsActivity.java          - Kontakti
MusicService.java              - Muzički servis
PrintBroadcastReceiver.java    - Broadcast
Korisnik.java                  - Model
DatabaseHelper.java            - Baza
PreferencesManager.java        - SharedPreferences
```

### Layouts (5 fajlova)
```
activity_main.xml              - MainActivity layout
activity_login.xml             - Login layout
activity_test.xml              - TestActivity layout
activity_settings.xml          - Settings layout
activity_contacts.xml          - Contacts layout
```

---

## 🎓 Demonstracija Znanja

Ova aplikacija pokriva:

**Servisi:**
- ✅ Service lifecycle
- ✅ Foreground service
- ✅ MediaPlayer

**Notifikacije:**
- ✅ NotificationChannel (Android 8+)
- ✅ NotificationCompat
- ✅ PendingIntent
- ✅ Action buttons

**Broadcasts:**
- ✅ BroadcastReceiver
- ✅ IntentFilter
- ✅ sendBroadcast()
- ✅ registerReceiver() / unregisterReceiver()

**Baza Podataka:**
- ✅ SQLiteOpenHelper
- ✅ CREATE TABLE
- ✅ CRUD operacije
- ✅ Cursor manipulation

**SharedPreferences:**
- ✅ Context.MODE_PRIVATE
- ✅ SharedPreferences.Editor
- ✅ commit() vs apply()
- ✅ Data types (boolean, int, String, long)

**ContentProvider:**
- ✅ ContentResolver
- ✅ query() metoda
- ✅ ContactsContract
- ✅ Cursor handling

**Permissions:**
- ✅ Manifest permissions
- ✅ Runtime permissions (Android 6+)
- ✅ onRequestPermissionsResult()

**Activities:**
- ✅ Activity lifecycle
- ✅ Intent navigation
- ✅ finish()
- ✅ onResume()

---

## 🏆 Checklist za Završetak Projekta

Pre nego što predate projekat, proverite:

- [ ] Projekat se otvara u Android Studio bez greške
- [ ] Gradle sync prolazi uspešno
- [ ] Build prolazi bez greške
- [ ] Aplikacija se pokreće
- [ ] Login radi sa test korisnicima
- [ ] Muzika svira kada se pokrene
- [ ] Notifikacija se pojavljuje
- [ ] Dugme "Ispiši" loguje poruku
- [ ] Tekst se boji u crveno
- [ ] Podešavanja sinhronizacije se čuvaju
- [ ] Kontakti se učitavaju
- [ ] Logout vraća na login ekran
- [ ] README.md postoji i čitljiv je
- [ ] Kod je komentarisan (srpski)
- [ ] AndroidManifest.xml ima sve komponente

---

## 📞 Demonstracija Profesoru

### Scenario za Pokazivanje (3 minuta)

1. **Pokretanje** (10s)
   - Pokažite LoginActivity
   - Prijavite se: admin / admin123

2. **MusicService + Notifikacija** (30s)
   - Kliknite "Pokreni muziku i notifikaciju"
   - Pokažite notifikaciju sa dugmetom "Ispiši"

3. **BroadcastReceiver** (30s)
   - Otvorite TestActivity
   - Kliknite "Ispiši" u notifikaciji
   - Pokažite promenu boje teksta
   - Pokažite logcat: "Pozdrav iz servisa!"

4. **Baza podataka** (20s)
   - Odjavite se
   - Prijavite se sa drugim korisnikom: korisnik1 / pass123
   - Pokažite promenu imena na glavnom ekranu

5. **SharedPreferences - Sinhronizacija** (30s)
   - Kliknite "Podešavanja sinhronizacije"
   - Promenite interval
   - Vratite se - pokažite ažuriran prikaz

6. **ContentProvider** (30s)
   - Kliknite "Prikaži kontakte"
   - Pokažite učitane kontakte
   - Pokažite logcat sa brojem učitanih kontakata

7. **Zaključak** (30s)
   - Pokažite AndroidManifest.xml (dozvole, komponente)
   - Pokažite strukturu projekta u Project Explorer
   - Pokažite README.md

**Ukupno vreme: 3 minuta**

---

## 💡 Dodatne Funkcije (Bonus)

Ako želite da impresionrate:

1. **Enkriptujte lozinke** u bazi (SHA-256)
2. **WorkManager** za sinhronizaciju umesto samo podešavanja
3. **Room** umesto SQLiteOpenHelper
4. **ViewModel + LiveData** za MVVM arhitekturu
5. **Retrofit** za pravu sinhronizaciju sa serverom
6. **Custom muzika** umesto sistemskog ringtone-a

---

**Verzija:** 1.0  
**Status:** ✅ READY FOR SUBMISSION  
**Poslednja provera:** 2025

---

## 📖 Resursi

**Detaljni vodič:** README.md (1,200+ linija)  
**Pregled implementacije:** IMPLEMENTACIJA.md  
**Ovaj dokument:** BRZI_VODIC.md

**Srećno! 🚀**

