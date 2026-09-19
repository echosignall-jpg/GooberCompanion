# Goober Companion

Private Android companion prototype with mood-based chat, local conversation history, optional overlay bubble, notification-access prototype, and YouTube handoff.

## Build the APK

1. Install Android Studio and open this repository.
2. Let Gradle sync.
3. Select **Build > Build Bundle(s) / APK(s) > Build APK(s)**.
4. The debug APK will be at `app/build/outputs/apk/debug/app-debug.apk`.
5. Install it on your Android phone, allowing installation from your file manager/browser if Android asks.

Command line:

```bash
./gradlew assembleDebug
```

On Windows:

```bat
gradlew.bat assembleDebug
```

The uploaded character sheet is not automatically stored as a binary asset by this scaffold. Replace `app/src/main/res/drawable/ic_goober.xml` with artwork you own or have permission to use. The current vector is a temporary placeholder.

Notification access and overlay access are opt-in Android permissions. The notification listener currently logs notification text for the prototype; production work should add an in-app allowlist, encrypted storage, and a visible delete button before using it as memory.

This prototype opens YouTube through the official app/browser. It does not download or scrape video content.
