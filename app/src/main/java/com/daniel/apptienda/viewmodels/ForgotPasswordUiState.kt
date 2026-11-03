package com.daniel.apptienda.viewmodels

data class ForgotPasswordUiState(
    val email: String = "",

    val emailError: Int? = null,

    val isLoading: Boolean = false,

    val isRecoverEnabled: Boolean = false
)
