package com.example.taller_kotlin_challenge

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class FixProductionCode {

    /**
     * Task 1 — basic valid credential pair is accepted.
     */
    @Test
    fun testLoginValidatorTask1() {
        assertTrue(LoginValidator.isValid("admin", "password"))
        assertFalse(LoginValidator.isValid("", "password"))
    }

    /**
     * Task 2 — comprehensive edge-case coverage for the validator.
     *
     * Inputs with leading/trailing whitespace are rejected because the validator
     * trims both fields and checks non-emptiness; a field of only spaces trims to
     * empty and is therefore invalid.
     */
    @Test
    fun testLoginValidatorInvalid() {
        // Blank / empty inputs — all invalid
        assertFalse(LoginValidator.isValid("", "password"))
        assertFalse(LoginValidator.isValid("", ""))
        assertFalse(LoginValidator.isValid("login", ""))
        assertFalse(LoginValidator.isValid("login  ", ""))
        assertFalse(LoginValidator.isValid("login  ", "  "))
        assertFalse(LoginValidator.isValid("login", "  "))
        assertFalse(LoginValidator.isValid(" ", "  password   "))
        assertFalse(LoginValidator.isValid("", "  password   "))
        assertFalse(LoginValidator.isValid("", "password"))

        // Valid — non-blank after trimming
        assertTrue(LoginValidator.isValid("login", "password"))
        assertTrue(LoginValidator.isValid(" login ", " password "))
    }

    /**
     * Task 2 — additional whitespace-only and mixed cases.
     */
    @Test
    fun testLoginValidatorWhitespaceEdgeCases() {
        assertFalse(LoginValidator.isValid("   ", "   "))
        assertFalse(LoginValidator.isValid("\t", "password"))
        assertFalse(LoginValidator.isValid("login", "\n"))
        assertTrue(LoginValidator.isValid("  admin  ", "  secret  "))
    }
}
