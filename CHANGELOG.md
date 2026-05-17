# Changelog

## 2026-05-17

### Added
- `LoginValidator.kt` — extracted `LoginValidator` object from the test source set into its own
  file in `main`, making the validation logic available to both production code and tests.
- `README.md` — project overview, setup instructions, test commands, and design notes.
- `ARCHITECTURE.md` — module map, data flow diagram, testing strategy, dependency graph.
- `PENDING.md` — prioritised list of remaining work items derived from code inspection.
- `testCore` version entry and `androidx-test-core` library alias to `gradle/libs.versions.toml`.
- `androidTestImplementation(libs.androidx.test.core)` dependency to `app/build.gradle.kts`
  to support `ActivityScenario` in Espresso tests.

### Fixed
- `LoginValidator.isValid` — replaced complex branching logic with the correct single expression:
  `username.trim().isNotEmpty() && password.trim().isNotEmpty()`.
- `TallerCodeChallengeTestActivity.onCreate` — wired the login button click listener; it now
  evaluates credentials via `LoginValidator` and shows/hides `error_text` accordingly. Without
  this, the Espresso Task 3 test could never pass.
- `FixProductionCodeTest` — added `ActivityScenario.launch` in `@Before` and `scenario.close()`
  in `@After`; registered/unregistered `TestIdlingResource.countingIdlingResource` with Espresso.
  The test was previously non-functional because the activity was never launched.
- `AndroidManifest.xml` — changed activity to `exported="true"` with a LAUNCHER intent-filter;
  `ActivityScenario` requires the activity to be exported.
- `activity_taller_code_challenge_test.xml` — added `android:inputType="textPassword"` to the
  password `EditText` so the field masks characters as expected.

### Changed
- `FixProductionCode.kt` (unit test) — removed the inlined `LoginValidator` copy; tests now
  reference the production `LoginValidator` object. Added `testLoginValidatorWhitespaceEdgeCases`
  test covering tab and newline whitespace. Uncommented
  `assertTrue(LoginValidator.isValid(" login ", " password "))` — the trimming validator accepts
  inputs with surrounding spaces because the trimmed values are non-empty.
