```markdown
# Taller-Challenge-Kotlin Development Patterns

> Auto-generated skill from repository analysis

## Overview
This skill covers the core development practices found in the `Taller-Challenge-Kotlin` repository. It is designed to help contributors understand and apply the project's coding conventions, commit standards, and testing patterns. The repository uses Kotlin without a specific framework, and emphasizes clear file organization, conventional commits, and modular code structure.

## Coding Conventions

### File Naming
- Use **PascalCase** for all file names.
  - **Example:**  
    `UserProfile.kt`  
    `MainActivity.kt`

### Import Style
- Use **relative imports** to reference other files or modules within the project.
  - **Example:**  
    ```kotlin
    import com.example.project.UserProfile
    ```

### Export Style
- Use **named exports** for classes, functions, or objects.
  - **Example:**  
    ```kotlin
    class UserProfile { ... }
    fun calculateScore() { ... }
    ```

### Commit Messages
- Follow **Conventional Commits** with these prefixes:
  - `feat`: New features
  - `fix`: Bug fixes
  - `chore`: Maintenance tasks
  - `docs`: Documentation changes
- Keep commit messages concise (average: 68 characters).
  - **Example:**  
    ```
    feat: add user authentication flow
    fix: resolve crash on login screen
    docs: update README with setup instructions
    chore: update dependencies
    ```

## Workflows

### Commit Workflow
**Trigger:** When making any code, documentation, or configuration change  
**Command:** `/commit`

1. Make your changes following the coding conventions.
2. Stage your changes using `git add`.
3. Write a commit message using the Conventional Commits format.
4. Commit your changes.

### File Creation Workflow
**Trigger:** When adding new Kotlin files or modules  
**Command:** `/create-file`

1. Name your file using PascalCase (e.g., `NewFeature.kt`).
2. Place the file in the appropriate directory.
3. Use relative imports for any dependencies.
4. Export classes or functions using named exports.

### Testing Workflow
**Trigger:** When writing or updating tests  
**Command:** `/test`

1. Create test files matching the pattern `*.test.*` (e.g., `UserProfile.test.kt`).
2. Write tests using the project's preferred (unspecified) testing framework.
3. Run tests to verify correctness.

## Testing Patterns

- **Test File Naming:**  
  Name test files with the pattern `*.test.*` (e.g., `Feature.test.kt`).
- **Framework:**  
  The specific testing framework is not defined; use standard Kotlin testing practices.
- **Location:**  
  Place test files alongside or in a dedicated test directory.

**Example:**
```kotlin
// UserProfile.test.kt
import org.junit.Test
import kotlin.test.assertEquals

class UserProfileTest {
    @Test
    fun testUserName() {
        val user = UserProfile("Alice")
        assertEquals("Alice", user.name)
    }
}
```

## Commands

| Command       | Purpose                                    |
|---------------|--------------------------------------------|
| /commit       | Guide for making conventional commits       |
| /create-file  | Steps for adding new Kotlin files/modules   |
| /test         | Instructions for writing and running tests  |
```
