package com.daniel.apptienda.viewmodels

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.apptienda.R
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ForgotPasswordViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ForgotPasswordUiState())
    val uiState: StateFlow<ForgotPasswordUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<ForgotPasswordUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEmailChanged(email: String) {
        _uiState.update { currentState ->
            val emailError = validateEmail(email)
            currentState.copy(
                email = email,
                emailError = emailError,
                isRecoverEnabled = email.isNotBlank() && emailError == null
            )
        }
    }

    fun onRecoverClicked() {
        val currentState = _uiState.value
        if (!currentState.isRecoverEnabled || currentState.isLoading) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            kotlinx.coroutines.delay(1500)
            _uiState.update { it.copy(isLoading = false) }
            _uiEvent.send(ForgotPasswordUiEvent.NavigateToLogin)
        }
    }

    private fun validateEmail(email: String): Int? {
        return when {
            email.isEmpty() -> null
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> R.string.error_invalid_email
            else -> null
        }
    }
}
