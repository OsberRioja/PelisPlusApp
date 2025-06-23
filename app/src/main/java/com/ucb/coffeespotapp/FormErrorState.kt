package com.ucb.coffeespotapp

data class FormErrorState(
    val usernameError: String? = null,
    val birthDateError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
)
