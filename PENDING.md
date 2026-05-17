# Pending Work — Taller-Challenge-Kotlin

## Project Purpose
Android Kotlin coding challenge (Taller interview/assessment) implementing a login screen with
input validation. Three tasks:
- Task 1: Fix `LoginValidator.isValid` logic to trim inputs and reject blank values.
- Task 2: Comprehensive unit tests for the validator covering edge cases.
- Task 3: Espresso instrumentation test verifying that invalid credentials show an error view.

## Current State (2026-05-17)
- Gradle project compiles (unverified — no device/emulator available in this environment).
- `FixProductionCode.kt` (unit test file) doubles as both production logic and JUnit tests in
  `test/` source set — an unusual structure that blurs production code / test code separation.
- `LoginValidator.isValid` has a logic bug: the outer `if/else` condition is incorrect and
  produces wrong results for leading/trailing-space inputs.
- `TallerCodeChallengeTestActivity` has the layout with `username`, `password`, `bt_login` and
  `error_text` views, but the `onCreate` body never wires up the login button click handler.
  Without the click handler, `error_text` stays `GONE` and the Espresso test would always fail.
- `FixProductionCodeTest` (androidTest) references views but contains no `ActivityScenario` /
  `ActivityTestRule` launch — the activity is never started before interacting with views.
- `TestIdlingResource` and `SimpleCountingIdlingResource` are implemented but never registered
  in any test.
- The commented-out assert `assertFalse(LoginValidator.isValid(" login ", " password "))` was
  left in code — its intent is unclear given that trimmed non-empty values should be accepted.

## Prioritized Pending Items
1. **Fix `LoginValidator.isValid` logic bug** — simplify to: trim both inputs, reject if either
   trimmed result is empty.
2. **Wire login button click handler in Activity** — check `LoginValidator.isValid` on click,
   show/hide `error_text` accordingly.
3. **Add `ActivityScenario` launch to Espresso test** — test is currently non-functional without it.
4. **Register `countingIdlingResource` in the Espresso test** — complete the idling resource
   integration that was started in the last commit.
5. **Add input type / password transformation** to the password `EditText` in the layout.
6. **Move `LoginValidator` to a dedicated production source file** — currently lives inside a
   JUnit test class file in `test/`, which is unusual.
7. **Resolve the `data_extraction_rules.xml` TODO** left by Android Studio scaffold.
