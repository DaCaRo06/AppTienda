package com.daniel.apptienda.viewmodels

data class RegisterUiState(
    val name: String = "",

    val email: String = "",

    val emailError: Int? = null,

    val password: String = "",

    val passwordError: Int? = null,

    val isLoading: Boolean = false,

    val isRegisterEnabled: Boolean = false
)
