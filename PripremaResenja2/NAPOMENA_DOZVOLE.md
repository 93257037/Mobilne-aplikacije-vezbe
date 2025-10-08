# 🔐 NAPOMENA O DOZVOLAMA - Android 13+ Fix

## Problem

Na Android 13 (API 33) i novijim verzijama, dozvola `READ_EXTERNAL_STORAGE` je deprecated i ne prikazuje se popup.

## Rešenje Implementirano

Kod sada dinamički bira ispravnu dozvolu:

```java
String dozvola;
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
    // Android 13+ (API 33+)
    dozvola = Manifest.permission.READ_MEDIA_IMAGES;
} else {
    // Starije verzije
    dozvola = Manifest.permission.READ_EXTERNAL_STORAGE;
}
```

## AndroidManifest Dozvole

```xml
<!-- Za Android 12 i starije -->
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"
    android:maxSdkVersion="32" />

<!-- Za Android 13+ -->
<uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />
```

## Testiranje

1. Pokreni aplikaciju na Android 13+ uređaju/emulatoru
2. Klikni "Dodaj belešku"
3. Trebao bi da se pojavi popup: "Allow [App Name] to access photos and media on your device?"
4. Klikni "Allow" ili "Deny"

## Alternativa (Ako ne trebaš dozvolu)

Ako ti stvarno ne treba pristup skladištu (pošto sve čuvaš u SQLite), možeš ukloniti proveru dozvole i direktno otvoriti formu:

```java
private void proveriDozvoluIDodajBelesku() {
    // Direktno otvori formu bez provere dozvole
    otvoriFormuZaDodavanje();
}
```

I ukloniti:
- `permissionLauncher` iz koda
- Dozvole iz AndroidManifest.xml

## Dostupne Dozvole na Android 13+

- `READ_MEDIA_IMAGES` - za slike
- `READ_MEDIA_VIDEO` - za video
- `READ_MEDIA_AUDIO` - za audio fajlove

## Više Informacija

https://developer.android.com/about/versions/13/behavior-changes-13#granular-media-permissions

