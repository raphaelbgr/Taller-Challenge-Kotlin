package com.example.taller_kotlin_challenge

import org.junit.Test

import org.junit.Assert.*

class FixProductionCode {

    object LoginValidator {
        /**
         * Task 1 - Added fix:
         * Only correct credentials are accepted.
         * Inputs are trimmed.
         * Empty/blank values are rejected.
         */
        fun isValid(username: String, password: String): Boolean {
            return if (!(username.trim() == username && password.trim() == password)
                || (username.isNotEmpty() && password.isNotEmpty())) {

                username.trim().isNotEmpty() && password.trim().isNotEmpty()
            } else {
                false
            }
        }
    }

    /**
     * Task 1 - test
     */
    @Test
    fun testLoginValidatorTask1() {
        assertTrue(LoginValidator.isValid("admin", "password"))
        assertFalse(LoginValidator.isValid("", "password"))
    }

    /**
     * Task 2
     */
    @Test
    fun testLoginValidatorInvalid() {
        // False asserts
        assertFalse(LoginValidator.isValid("", "password"))
        assertFalse(LoginValidator.isValid("", ""))
        assertFalse(LoginValidator.isValid("login", ""))
        assertFalse(LoginValidator.isValid("login  ", ""))
        assertFalse(LoginValidator.isValid("login  ", "  "))
        assertFalse(LoginValidator.isValid("login", "  "))
//        assertFalse(LoginValidator.isValid(" login ", " password "))
        assertFalse(LoginValidator.isValid(" ", "  password   "))
        assertFalse(LoginValidator.isValid("", "  password   "))
        assertFalse(LoginValidator.isValid("", "password"))

        // True asserts
        assertTrue(LoginValidator.isValid("login", "password"))
    }
}