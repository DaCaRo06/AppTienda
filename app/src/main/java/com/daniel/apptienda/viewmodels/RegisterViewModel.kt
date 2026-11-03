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

class RegisterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<RegisterUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onNameChanged(name: String) {
        _uiState.update { currentState ->
            currentState.copy(
                name = name,
                isRegisterEnabled = isFormValid(name, currentState.email, currentState.password, currentState.emailError, currentState.passwordError)
            )
        }
    }

    fun onEmailChanged(email: String) {
        _uiState.update { currentState ->
            val emailError = validateEmail(email)
            currentState.copy(
                email = email,
                emailError = emailError,
                isRegisterEnabled = isFormValid(currentState.name, email, currentState.password, emailError, currentState.passwordError)
            )
        }
    }

    fun onPasswordChanged(password: String) {
        _uiState.update { currentState ->
            val passwordError = validatePassword(password)
            currentState.copy(
                password = password,
                passwordError = passwordError,
                isRegisterEnabled = isFormValid(currentState.name, currentState.email, password, currentState.emailError, passwordError)
            )
        }
    }

    fun onRegisterClicked() {
        val currentState = _uiState.value

        if (!currentState.isRegisterEnabled || currentState.isLoading) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // Simulación de llamada de registro
            kotlinx.coroutines.delay(1500)
            val isSuccess = currentState.email.isNotBlank() && currentState.password.length >= 6 && currentState.name.isNotBlank()

            _uiState.update { it.copy(isLoading = false) }

            if (isSuccess) {
                _uiEvent.send(RegisterUiEvent.NavigateToHome)
            } else {
                _uiEvent.send(RegisterUiEvent.ShowError(R.string.error_invalid_credentials))
            }
        }
    }

    private fun validateEmail(email: String): Int? {
        return when {
            email.isEmpty() -> null
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> R.string.error_invalid_email
            else -> null
        }
    }

    private fun validatePassword(password: String): Int? {
        return when {
            password.isEmpty() -> null
            password.length < 6 -> R.string.error_password_too_short
            else -> null
        }
    }

    private fun isFormValid(
        name: String,
        email: String,
        password: String,
        emailError: Int?,
        passwordError: Int?
    ): Boolean {
        return name.isNotBlank() &&
                email.isNotBlank() &&
                password.isNotBlank() &&
                emailError == null &&
                passwordError == null
    }
}
