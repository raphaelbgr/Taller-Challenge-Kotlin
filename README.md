# Taller-Challenge-Kotlin

Android Kotlin coding challenge implementing a login screen with credential validation, unit tests,
and Espresso instrumentation tests.

## What It Does

The app presents a single login screen with a username field, password field, login button, and a
hidden error label. Tapping the button validates the inputs: if both trimmed fields are non-empty
the error label stays hidden; otherwise it becomes visible.

Three tasks are covered by this project:

| Task | Scope |
|------|-------|
| 1 | `LoginValidator.isValid` — fix logic to trim inputs and reject blank values |
| 2 | Unit tests (`FixProductionCode`) covering valid, blank, and whitespace-only combinations |
| 3 | Espresso instrumented test verifying the error view appears on bad credentials |

## Requirements

| Tool | Version |
|------|---------|
| Android Studio | Ladybug or newer |
| Android Gradle Plugin | 8.13.2 |
| Kotlin | 2.0.21 |
| Min SDK | 24 (Android 7.0) |
| Target / Compile SDK | 36 |

## Project Structure

```
app/src/
  main/
    java/com/example/taller_kotlin_challenge/
      LoginValidator.kt                  # Credential validation logic
      TallerCodeChallengeTestActivity.kt # Login screen activity
    res/layout/
      activity_taller_code_challenge_test.xml
  test/
    java/com/example/taller_kotlin_challenge/
      FixProductionCode.kt               # JUnit 4 unit tests
  androidTest/
    java/com/example/taller_kotlin_challenge/
      FixProductionCodeTest.kt           # Espresso instrumented tests
                                         # + SimpleCountingIdlingResource
```

## Setup

1. Clone the repository.
2. Open in Android Studio (File → Open → select repo root).
3. Let Gradle sync complete.
4. Create a `local.properties` file at the repo root if it does not exist and set `sdk.dir` to
   your Android SDK path (Android Studio does this automatically).

## Running Tests

### Unit tests (no device needed)
```
./gradlew :app:testDebugUnitTest
```

### Instrumented tests (device or emulator required)
```
./gradlew :app:connectedDebugAndroidTest
```

## Key Design Decisions

- `LoginValidator` is a Kotlin `object` (singleton) in the `main` source set so both production
  code and tests can reference the same implementation.
- `SimpleCountingIdlingResource` lives in the `androidTest` source set alongside the Espresso
  test that uses it; it is registered in `@Before` and unregistered in `@After`.
- The activity is marked `exported="true"` because `ActivityScenario.launch` requires it.
