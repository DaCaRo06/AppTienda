package com.daniel.apptienda.viewmodels

sealed interface RegisterUiEvent {
    object NavigateToHome : RegisterUiEvent
    data class ShowError(val messageResId: Int) : RegisterUiEvent
}
