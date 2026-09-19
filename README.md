# Goober Companion

Private Android companion prototype with mood-based chat, optional overlay bubble, local conversation history, notification-access prototype, and YouTube handoff.

## Real AI providers

The app now supports any provider that implements the common OpenAI-compatible `POST /chat/completions` format. This includes OpenAI-compatible gateways and many hosted/local servers. It does not support arbitrary proprietary APIs without an adapter.

Open **AI provider settings** in the app and enter:

- **Base URL:** normally the provider API root, such as `https://api.openai.com/v1`; if your provider gives the full `/chat/completions` URL, that also works.
- **API key:** your provider key.
- **Model:** the exact model name supplied by the provider.

The app sends the key in an HTTPS `Authorization: Bearer` header and does not put it in the repository. The key is saved in the app's local preferences; use a dedicated key with spending limits and never commit it to GitHub. For stronger production security, move calls to your own backend or use Android Keystore-backed encrypted storage.

## Build the APK

1. Install Android Studio and open this repository.
2. Let Gradle sync.
3. Select **Build > Build Bundle(s) / APK(s) > Build APK(s)**.
4. The debug APK will be at `app/build/outputs/apk/debug/app-debug.apk`.

Command line:

```bash
./gradlew assembleDebug
```

On Windows:

```bat
gradlew.bat assembleDebug
```

This prototype opens YouTube through the official app/browser and does not download or scrape video content. Notification and overlay features are opt-in Android permissions.
