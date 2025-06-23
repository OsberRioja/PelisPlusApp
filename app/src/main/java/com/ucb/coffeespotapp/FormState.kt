package com.ucb.coffeespotapp

data class FormState(
    val username: String = "",
    val birthDate: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
)

fun validateForm(state: FormState): FormErrorState {
    return FormErrorState(
        usernameError = when{
            state.username.isBlank() -> "El nombre de usuario no puede estar vacio"
            state.username.length < 3 ->  "El nombre de usuario debe tener al menos 3 caracteres"
            state.username.contains(Regex("[0-9]")) -> "El nombre de usuario no puede contener numeros"
            else -> null
        },
        birthDateError = when{
            state.birthDate.isBlank()-> "La fecha de nacimiento no puede estar vacia"
            !state.birthDate.matches(Regex("[0-3][0-9]-[0-1]\\d-\\d{4}")) -> "La fecha de nacimiento debe estar en el formato dd-mm-yyyy"
            else -> null
        },
        emailError = when{
            state.email.isBlank() -> "El email no puede estar vacio"
            !state.email.contains(Regex("\\w+([.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+")) -> "El email debe tener formato correcto de email"
            else -> null
        },
        passwordError = when {
            state.password.isBlank() -> "La contraseña no puede estar vacia"
            state.password.length < 8 -> "La contraseña debe tener al menos 8 caracteres"
            else -> null
        },
        confirmPasswordError = when {
            state.confirmPassword.isBlank() -> "La confirmacion de contraseña no puede estar vacia"
            state.confirmPassword != state.password -> "La confirmacion de contraseña no coincide con la contraseña"
            else -> null
        }
    )
}

fun isFormValid(errorState: FormErrorState): Boolean {
    return errorState.usernameError == null &&
            errorState.birthDateError == null &&
            errorState.emailError == null &&
            errorState.passwordError == null &&
            errorState.confirmPasswordError == null
}