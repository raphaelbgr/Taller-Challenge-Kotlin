# Architecture — Taller-Challenge-Kotlin

## Overview

Single-module Android application with one `Activity`, one domain logic object, and two test
source sets. The project is intentionally minimal — it is a coding challenge, not a production app.

## Module Map

```
:app
├── main (production code)
│   ├── LoginValidator          object — pure validation logic, no Android deps
│   └── TallerCodeChallengeTestActivity
│                               AppCompatActivity — login UI, delegates to LoginValidator
├── test (JUnit 4 — runs on JVM)
│   └── FixProductionCode       test class exercising LoginValidator
└── androidTest (Espresso — runs on device/emulator)
    ├── FixProductionCodeTest   @RunWith(AndroidJUnit4) — launches Activity, drives UI
    ├── TestIdlingResource      global singleton — wraps the counting idling resource
    └── SimpleCountingIdlingResource
                                IdlingResource implementation using AtomicInteger counter
```

## Data Flow — Login Validation

```
User types username/password
         │
         ▼
[bt_login click]
         │
         ▼
TallerCodeChallengeTestActivity.onCreate click listener
         │  reads EditText.text.toString() for both fields
         ▼
LoginValidator.isValid(username, password)
         │  trims both strings, checks non-empty
         ▼
   true  ─────────────────────────────────── error_text.visibility = GONE
   false ─────────────────────────────────── error_text.visibility = VISIBLE
```

## Testing Strategy

| Source set | Runner | What is tested |
|------------|--------|----------------|
| `test` | JUnit 4 on JVM | `LoginValidator` in isolation — no Android framework |
| `androidTest` | AndroidJUnit4 on device | Full UI flow via Espresso view interactions |

The `SimpleCountingIdlingResource` allows future async operations (network calls, coroutines) to
be synchronised with Espresso without flakiness. When an async operation starts, call
`TestIdlingResource.increment()`; call `TestIdlingResource.decrement()` when it completes.
Espresso waits until the counter reaches zero before proceeding with assertions.

## Dependency Graph

```
:app → androidx.core.ktx
     → androidx.appcompat
     → com.google.android.material
     → androidx.activity
     → androidx.constraintlayout
     → [test]        junit:junit
     → [androidTest] androidx.test.ext:junit
     → [androidTest] androidx.test.espresso:espresso-core
     → [androidTest] androidx.test:core-ktx
```

## Build System

Gradle with Kotlin DSL (`build.gradle.kts`). Dependency versions centralised in
`gradle/libs.versions.toml` (version catalog). AGP 8.13.2, Kotlin 2.0.21.
