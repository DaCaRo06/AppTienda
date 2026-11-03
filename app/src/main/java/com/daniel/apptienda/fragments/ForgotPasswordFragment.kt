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
import com.daniel.apptienda.databinding.ForgotPasswordScreenBinding
import com.daniel.apptienda.viewmodels.ForgotPasswordUiEvent
import com.daniel.apptienda.viewmodels.ForgotPasswordViewModel
import kotlinx.coroutines.launch

class ForgotPasswordFragment : Fragment() {

    private lateinit var binding: ForgotPasswordScreenBinding
    private val viewModel: ForgotPasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ForgotPasswordScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Volver al login
        view.findViewById<TextView>(R.id.tvVolverALogin)?.setOnClickListener {
            findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
        }

        //Recoger estado y eventos del Viewmodel
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.uiState.collect { state ->
                        binding.tilRecEmail?.error = state.emailError?.let { getString(it) }
                        binding.btnSendCode?.isEnabled = state.isRecoverEnabled && !state.isLoading
                    }
                }

                launch {
                    viewModel.uiEvent.collect { event ->
                        when (event) {
                            is ForgotPasswordUiEvent.NavigateToLogin -> {
                                Toast.makeText(requireContext(), "Correo de recuperación enviado", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
                            }
                            is ForgotPasswordUiEvent.ShowError -> {
                                Toast.makeText(requireContext(), getString(event.messageResId), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }

        binding.etRecEmail?.doOnTextChanged { text, _, _, _ ->
            viewModel.onEmailChanged(text.toString())
        }

        binding.btnSendCode?.setOnClickListener {
            viewModel.onRecoverClicked()
        }
    }
}
