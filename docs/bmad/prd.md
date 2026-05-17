# Product Requirements Document
## Taller-Challenge-Kotlin

**Version:** 1.0  
**Date:** 2026-05-17  
**Status:** Complete (coding challenge)

---

## 1. Introduction / Overview

This document describes the requirements for the Taller coding challenge Android application.
The project is a self-contained assessment exercise, not a production application. Its purpose is
to demonstrate correct Kotlin/Android development practices: writing clean validation logic,
wiring Android UI components, and verifying behaviour with both JUnit unit tests and Espresso
instrumented tests.

## 2. Goals

- Implement a login screen with username and password inputs and a login button.
- Validate credentials: both fields must be non-blank (after trimming whitespace).
- Display an error message when credentials are invalid; hide it when valid.
- Cover the validation logic with a comprehensive JUnit unit test suite.
- Cover the UI interaction with an Espresso instrumented test that confirms the error view
  becomes visible after submitting invalid credentials.
- Integrate an Espresso `IdlingResource` to support future asynchronous operations without
  introducing test flakiness.

## 3. Non-Goals

- Network authentication — credentials are validated locally only.
- Navigation beyond the login screen.
- Persistence of credentials or session state.
- Production-grade security (hashing, Keystore, etc.).

## 4. User Stories

### US-1: Valid credentials suppress the error
**As a** user on the login screen,  
**When I** enter a non-blank username and non-blank password and tap Login,  
**Then** the error message is not shown.

*Acceptance criteria:*
- `LoginValidator.isValid("admin", "password")` returns `true`.
- `error_text` view has `visibility = GONE` after the button click.

### US-2: Blank credentials show the error
**As a** user on the login screen,  
**When I** leave either field empty (or enter only whitespace) and tap Login,  
**Then** the error message becomes visible.

*Acceptance criteria:*
- `LoginValidator.isValid("", "password")` returns `false`.
- `LoginValidator.isValid("login", "")` returns `false`.
- `LoginValidator.isValid("   ", "   ")` returns `false`.
- `error_text` view has `visibility = VISIBLE` after the button click with bad credentials.

### US-3: Whitespace-padded inputs are treated as valid
**As a** user on the login screen,  
**When I** enter a username and password that are non-empty after trimming (e.g. " admin "),  
**Then** the credentials are accepted.

*Acceptance criteria:*
- `LoginValidator.isValid(" admin ", " secret ")` returns `true`.

## 5. Functional Requirements

| ID | Requirement |
|----|-------------|
| FR-1 | The login screen must contain: `EditText` for username (id `username`), `EditText` for password (id `password`, `inputType="textPassword"`), `Button` (id `bt_login`), `TextView` error label (id `error_text`, initially `GONE`). |
| FR-2 | Tapping `bt_login` must invoke `LoginValidator.isValid` with the current field contents. |
| FR-3 | `LoginValidator.isValid` must return `true` if and only if both `username.trim()` and `password.trim()` are non-empty strings. |
| FR-4 | When `isValid` returns `false`, `error_text.visibility` must be set to `VISIBLE`. |
| FR-5 | When `isValid` returns `true`, `error_text.visibility` must be set to `GONE`. |
| FR-6 | Unit tests must cover: valid pair, empty username, empty password, blank (whitespace-only) username, blank password, both blank, leading/trailing spaces on both. |
| FR-7 | The Espresso test `invalidLoginShowsError` must launch the activity, type bad credentials, click login, and assert `error_text` is displayed. |
| FR-8 | `SimpleCountingIdlingResource` must be registered before the test and unregistered after. |

## 6. Technical Requirements

| ID | Requirement |
|----|-------------|
| TR-1 | Language: Kotlin. |
| TR-2 | Minimum SDK: 24. Target/Compile SDK: 36. |
| TR-3 | Build system: Gradle with Kotlin DSL and version catalog (`libs.versions.toml`). |
| TR-4 | Unit tests: JUnit 4, running on JVM (no Android framework). |
| TR-5 | Instrumented tests: Espresso + AndroidJUnit4, running on device or emulator. |
| TR-6 | `LoginValidator` must reside in the `main` source set so it is accessible from both `test` and `androidTest`. |
| TR-7 | The launcher `Activity` must have `android:exported="true"` so `ActivityScenario.launch` works. |

## 7. Success Metrics

- All JUnit unit tests pass on the JVM (`./gradlew :app:testDebugUnitTest`).
- All Espresso tests pass on a connected device/emulator (`./gradlew :app:connectedDebugAndroidTest`).
- No compiler warnings related to the validation logic or test setup.
