# PrepZen - English & Aptitude (Android)

Minimalist Android app for placement and government exam preparation.

## Stack
- Kotlin + MVVM
- Material Design (Day/Night)
- Offline-first JSON content (`app/src/main/assets`)
- Bottom navigation
- AdMob banner + interstitial (Google test IDs included)
- WorkManager daily reminder
- No login/auth

## Main Screens
- Home
- English
- Aptitude
- Quiz
- Progress
- About (from Home)

## Features
- Topic cards with explanation, example, and practice
- Topic search
- Bookmark topics
- Topic-wise quizzes (Easy/Medium/Hard)
- Instant feedback with explanation
- Score tracking in local storage

## Project Structure
- `app/src/main/java/com/prepzen/app/data` -> repositories
- `app/src/main/java/com/prepzen/app/domain` -> models
- `app/src/main/java/com/prepzen/app/ui` -> activities/fragments/viewmodels/adapters
- `app/src/main/assets/topics.json` -> learning content
- `app/src/main/assets/quizzes.json` -> MCQ bank

## AdMob Notes
Current IDs are official Google test IDs:
- App ID: `ca-app-pub-3940256099942544~3347511713`
- Banner ID: `ca-app-pub-3940256099942544/6300978111`
- Interstitial ID: `ca-app-pub-3940256099942544/1033173712`

Replace with production IDs before release.

## Run
1. Open project in Android Studio (Hedgehog+ recommended).
2. Sync Gradle.
3. Run on emulator/device.


## Android SDK Setup
If build fails with `SDK location not found`, create `local.properties` in project root:

```properties
sdk.dir=/Users/<your-user>/Library/Android/sdk
```

Or open Android Studio and install SDK + Build Tools from SDK Manager, then sync.
