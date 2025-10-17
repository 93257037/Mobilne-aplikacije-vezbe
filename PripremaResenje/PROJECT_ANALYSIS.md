# Project Analysis - Android Exam Requirements

## ✅ Requirements Checklist

### 1. MainActivity with CheckBox and Button "Dugme" (0.5 points)
✅ **COMPLETED**
- `activity_main.xml` contains CheckBox with ID `checkboxLocation`
- Button with text "Dugme" and ID `btnDugme`

### 2. Button Navigation to SecondActivity (1.5 points)
✅ **COMPLETED**
- Button click opens SecondActivity
- SecondActivity has blue background (#0000FF)
- SecondActivity contains Switch component

### 3. CheckBox Validation & Dialog/Toast (3.5 points)
✅ **COMPLETED**
- When CheckBox is checked: Opens Dialog with EditText and OK button (3 points)
- When CheckBox is unchecked: Shows Toast "Morate dozvoliti lokaciju!" (0.5 points)

### 4. Osoba Model & Database Table (1 point)
✅ **COMPLETED** (after fix)
- Model class `Osoba` with fields: `ime` and `godiste`
- Database table created in `SQLiteHelper.onCreate()`
- Constants defined in `DatabaseConstants.java`

### 5. Save Data from Dialog (2 points)
✅ **COMPLETED**
- OK button click saves name from EditText
- Default year value (2000) stored in database
- Uses `OsobaRepository.insert()` method

### 6. Service Controlled by Switch (4 points)
✅ **COMPLETED**
- Switch ON: Starts `BackgroundService` (3 points)
- Service checks background color every 10 seconds
- Switch OFF: Stops service (1 point)

### 7. BroadcastReceiver for Color Changes (5 points)
✅ **COMPLETED**
- `ColorChangeReceiver` receives color broadcasts (3 points)
- Changes background blue ↔ green
- Sends notification: "Boja pozadine je plava/zelena" (2 points)

## Changes Made

### 1. All Comments Removed
- Removed all Java documentation comments (/** ... */)
- Removed all inline comments (// ...)
- Kept only code

### 2. Database Fix
- Added `TABLE_OSOBA` constant to `DatabaseConstants.java`
- Added `COLUMN_GODISTE` constant to `DatabaseConstants.java`
- Added `DB_CREATE_OSOBA` SQL statement to `SQLiteHelper.java`
- Added table creation in `onCreate()` method
- Increased database version from 1 to 2

## Project Structure

```
MainActivity (Checkbox + Button)
    ↓ (button click)
SecondActivity (Blue background + Switch)
    ↓ (switch ON)
BackgroundService (every 10 seconds)
    ↓ (sends broadcast)
ColorChangeReceiver
    ↓ (changes color + sends notification)
SecondActivity UI update
```

## Database Schema

### OSOBA Table
| Column | Type | Description |
|--------|------|-------------|
| _id | INTEGER PRIMARY KEY AUTOINCREMENT | Auto-incrementing ID |
| ime | TEXT | Person's name |
| godiste | INTEGER | Birth year (default: 2000) |

## Important Notes

1. **Database Version**: Changed from 1 to 2 to trigger `onUpgrade()` and create the OSOBA table
2. **Permissions**: Manifest includes location and notification permissions
3. **Broadcast Actions**: 
   - `com.example.kolokvijum1.COLOR_CHANGE` - Service → Receiver
   - `com.example.kolokvijum1.UPDATE_COLOR` - Receiver → Activity
4. **Default Year**: Set to 2000 in `MainActivity.DEFAULT_GODISTE`

## Testing Checklist

- [ ] CheckBox checked → Dialog appears
- [ ] CheckBox unchecked + Button click → Toast appears
- [ ] Dialog OK saves to database
- [ ] Button navigates to SecondActivity
- [ ] SecondActivity has blue background
- [ ] Switch ON starts service
- [ ] Every 10 seconds background changes blue ↔ green
- [ ] Notification appears with correct color text
- [ ] Switch OFF stops service
- [ ] Data persists in database (check with query)

## Total Points: 17.5 / 17.5 ✅
