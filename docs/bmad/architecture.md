# Architecture Document
## Taller-Challenge-Kotlin

**Version:** 1.0  
**Date:** 2026-05-17  
**Status:** Complete (coding challenge)

---

## 1. Introduction

This document describes the technical architecture of the Taller coding challenge Android app.
The project is intentionally minimal — one Activity, one domain object, one unit test class, and
one instrumented test class.

## 2. Technical Summary

| Attribute | Value |
|-----------|-------|
| Platform | Android |
| Language | Kotlin |
| Architecture pattern | Thin Activity + isolated domain object |
| Min SDK | 24 (Android 7.0 Nougat) |
| Target / Compile SDK | 36 |
| Build system | Gradle 8.x, Kotlin DSL |
| AGP | 8.13.2 |
| Kotlin | 2.0.21 |

## 3. High-Level Architecture

The application follows a deliberate simplicity-first structure. There is no ViewModel, no
repository, and no network layer — all intentional for a self-contained assessment exercise.

```
┌─────────────────────────────────────────────────────┐
│                       :app                          │
│                                                     │
│  ┌──────────────────────┐   ┌────────────────────┐  │
│  │  main source set     │   │  test source set   │  │
│  │                      │   │  (JVM, JUnit 4)    │  │
│  │  LoginValidator      │◄──│  FixProductionCode │  │
│  │  (domain object)     │   └────────────────────┘  │
│  │                      │                           │
│  │  TallerCodeChallenge │   ┌────────────────────┐  │
│  │  TestActivity        │◄──│ androidTest source │  │
│  │  (UI / glue)         │   │ FixProductionCode  │  │
│  └──────────────────────┘   │ Test (Espresso)    │  │
│                             │ TestIdlingResource │  │
│                             │ SimpleCountingIdle │  │
│                             │ ngResource         │  │
│                             └────────────────────┘  │
└─────────────────────────────────────────────────────┘
```

## 4. Component Descriptions

### 4.1 LoginValidator (`main` source set)

**Type:** Kotlin `object` (singleton)  
**Responsibility:** Pure credential validation, no Android dependencies.

```kotlin
object LoginValidator {
    fun isValid(username: String, password: String): Boolean =
        username.trim().isNotEmpty() && password.trim().isNotEmpty()
}
```

This is the only piece of domain logic in the project. Being in `main` makes it reachable from
both `test` and `androidTest` source sets.

### 4.2 TallerCodeChallengeTestActivity (`main` source set)

**Type:** `AppCompatActivity`  
**Responsibility:** Inflates the login layout, wires the button click, delegates to
`LoginValidator`, and updates the error view visibility.

Lifecycle interactions:
- `onCreate` — inflates layout, binds views, sets `OnClickListener`.
- No `onSaveInstanceState` overrides — state is ephemeral for this challenge.

### 4.3 FixProductionCode (`test` source set)

**Type:** JUnit 4 test class  
**Responsibility:** Exercises `LoginValidator.isValid` with all meaningful input combinations on
the JVM. No Android framework is involved.

Test methods:
- `testLoginValidatorTask1` — basic valid/invalid pair (Task 1 coverage).
- `testLoginValidatorInvalid` — comprehensive false and true assertions (Task 2 coverage).
- `testLoginValidatorWhitespaceEdgeCases` — tab and newline whitespace variants.

### 4.4 FixProductionCodeTest (`androidTest` source set)

**Type:** `@RunWith(AndroidJUnit4::class)` instrumented test  
**Responsibility:** Launches the Activity via `ActivityScenario`, performs Espresso view
interactions, and asserts UI state.

Test methods:
- `useAppContext` — sanity check for the target package name.
- `invalidLoginShowsError` — types bad credentials, clicks login, asserts `error_text` is shown.

Lifecycle management:
- `@Before` — registers `TestIdlingResource.countingIdlingResource`; launches `ActivityScenario`.
- `@After` — unregisters the idling resource; closes the scenario.

### 4.5 TestIdlingResource / SimpleCountingIdlingResource (`androidTest` source set)

**Type:** `object` / `IdlingResource` implementation  
**Responsibility:** Provides an Espresso-compatible counter that blocks Espresso assertions until
all async operations complete.

Protocol:
1. Before an async operation starts: `TestIdlingResource.increment()`.
2. When the operation finishes: `TestIdlingResource.decrement()`.
3. Espresso polls `isIdleNow()` (counter == 0) before executing matchers/actions.

## 5. Layout

`activity_taller_code_challenge_test.xml` — `ConstraintLayout` root → `LinearLayout` (vertical,
centered) containing:

| View | ID | Purpose |
|------|----|---------|
| `EditText` | `username` | Username input |
| `EditText` | `password` | Password input (`inputType="textPassword"`) |
| `Button` | `bt_login` | Triggers validation |
| `TextView` | `error_text` | Error label (initially `GONE`) |

## 6. Dependency Map

```
LoginValidator ────────────────────────────► (no dependencies)

TallerCodeChallengeTestActivity ───────────► LoginValidator
                                ───────────► androidx.appcompat
                                ───────────► androidx.activity
                                ───────────► androidx.core (WindowInsets)

FixProductionCode ─────────────────────────► LoginValidator
                  ─────────────────────────► junit:junit

FixProductionCodeTest ─────────────────────► TallerCodeChallengeTestActivity
                      ─────────────────────► TestIdlingResource
                      ─────────────────────► espresso-core
                      ─────────────────────► androidx.test.ext:junit
                      ─────────────────────► androidx.test:core-ktx (ActivityScenario)
```

## 7. Build Configuration

- Version catalog at `gradle/libs.versions.toml` centralises all dependency versions.
- `app/build.gradle.kts` — AGP application plugin; `compileSdk release(36)`.
- `settings.gradle.kts` — single module `:app`; repository resolution via `google()` +
  `mavenCentral()`.
- `gradle.properties` — standard Android Gradle properties.

## 8. Key Decisions

| Decision | Rationale |
|----------|-----------|
| `LoginValidator` as `object` in `main` | Makes it reachable from both test source sets without duplication. |
| No ViewModel | Overkill for a single-screen challenge with no async state. |
| `ActivityScenario` over `ActivityTestRule` | `ActivityTestRule` is deprecated; `ActivityScenario` is the current AndroidX testing API. |
| `SimpleCountingIdlingResource` | Demonstrates awareness of async testing concerns even though the current implementation is synchronous. |
| `android:exported="true"` | Required by `ActivityScenario.launch`; also makes the activity the app launcher. |
