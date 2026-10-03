# Milestone-1-Signaling

Food ordering Android app (Kotlin), Milestone 1: **Secure Foundation**.
Course: Signaling and Network Control (NETW704), German University in Cairo.


## What this milestone covers
- **Firebase Email Authentication:** sign up and log in with email and password.
- **Role at sign-up:** a user registers as either a **Buyer** or a **Seller**.
- **Firebase Realtime Database:** the profile is saved as JSON under `users/<UID>` right after registration.
- **Home screen:** shows the profile (email, role) and lets the user edit **name** and **phone**.
- **Session handling:** a logged-in user skips the login screen; a Log Out button ends the session.

## Database structure
```json
{
  "users": {
    "<UID>": {
      "uid": "<UID>",
      "name": "Buyer One",
      "email": "buyer1@test.com",
      "phone": "01000000000",
      "role": "buyer"
    }
  }
}
```

## Project structure
| File | Purpose |
|---|---|
| `LoginActivity.kt` | Email/password login, auto-skip when already signed in |
| `RegisterActivity.kt` | Sign up, input validation, saves the profile to the Realtime Database |
| `HomeActivity.kt` | Loads and updates the profile, logout |
| `User.kt` | Data model (converted to and from JSON by Firebase) |

## How to run
1. Open the project in Android Studio.
2. Add your own `google-services.json` from the Firebase console into the `app/` folder (package name: `com.example.milestone_1_signaling`).
3. In Firebase: enable **Email/Password** under Authentication and create a **Realtime Database**.
4. Sync Gradle, select a device or emulator, and click **Run**.

## Tools
Android Studio, Kotlin, Firebase Authentication, Firebase Realtime Database.
