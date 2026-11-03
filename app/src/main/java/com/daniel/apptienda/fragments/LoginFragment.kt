package com.daniel.apptienda.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.daniel.apptienda.R
import com.daniel.apptienda.databinding.LoginScreenBinding
import com.daniel.apptienda.viewmodels.LoginUiEvent
import com.daniel.apptienda.viewmodels.LoginViewModel
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private lateinit var binding: LoginScreenBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Navegación a Registro y Recuperación de contraseña
        view.findViewById<TextView>(R.id.tvIrALogin).setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        view.findViewById<TextView>(R.id.forgotPassword).setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        // Recoger Estado y Eventos del ViewModel con Coroutines Flow
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                // 1. Escuchar cambios de estado de la UI
                launch {
                    viewModel.uiState.collect { state ->
                        binding.tilEmail.error = state.emailError?.let { getString(it) }
                        binding.tilPassword.error = state.passwordError?.let { getString(it) }
                        binding.btnLogin.isEnabled = state.isLoginEnabled && !state.isLoading
                    }
                }

                // 2. Escuchar eventos de un solo uso
                launch {
                    viewModel.uiEvent.collect { event ->
                        when (event) {
                            is LoginUiEvent.NavigateToHome -> {
                                Toast.makeText(requireContext(), "¡Login exitoso!", Toast.LENGTH_SHORT).show()
                            }
                            is LoginUiEvent.ShowError -> {
                                Toast.makeText(requireContext(), getString(event.messageResId), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }

        // Enviar eventos de texto al ViewModel
        binding.etEmail.doOnTextChanged { text, _, _, _ ->
            viewModel.onEmailChanged(text.toString())
        }

        binding.etPassword.doOnTextChanged { text, _, _, _ ->
            viewModel.onPasswordChanged(text.toString())
        }

        // Clic en botón de inicio de sesión
        binding.btnLogin.setOnClickListener {
            viewModel.onLoginClicked()
        }
    }
}
