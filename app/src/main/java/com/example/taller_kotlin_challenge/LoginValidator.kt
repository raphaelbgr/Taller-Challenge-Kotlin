package com.example.taller_kotlin_challenge

/**
 * Validates login credentials.
 *
 * Rules:
 * - Both username and password must be non-blank after trimming.
 * - Leading/trailing whitespace is not accepted as valid input.
 */
object LoginValidator {

    /**
     * Returns true only when both [username] and [password] are non-empty after trimming.
     * Inputs that consist solely of whitespace are rejected.
     */
    fun isValid(username: String, password: String): Boolean {
        return username.trim().isNotEmpty() && password.trim().isNotEmpty()
    }
}
