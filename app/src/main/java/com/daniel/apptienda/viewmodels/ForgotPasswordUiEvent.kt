package com.daniel.apptienda.viewmodels

sealed interface ForgotPasswordUiEvent {
    object NavigateToLogin : ForgotPasswordUiEvent
    data class ShowError(val messageResId: Int) : ForgotPasswordUiEvent
}
