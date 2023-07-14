package com.example.furnitureapp.fragments.loginRegister

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.furnitureapp.R
import com.example.furnitureapp.activities.ShoppingActivity
import com.example.furnitureapp.databinding.FragmentLoginBinding
import com.example.furnitureapp.dialog.setupBottomSheetDialog
import com.example.furnitureapp.util.Resource
import com.example.furnitureapp.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvDontHaveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.apply {
            buttonLoginLogin.setOnClickListener {
                val email = edEmailLogin.text.toString().trim()
                val password = edPasswordLogin.text.toString()
                viewModel.login(email, password)
            }
        }

        binding.tvForgotPasswordLogin.setOnClickListener {
            setupBottomSheetDialog {email ->
                viewModel.resetPassword(email)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.resetPassword.collect {
                    when(it) {
                        is Resource.Loading -> {
                        }
                        is Resource.Success -> {
                            Snackbar.make(requireView(), getString(R.string.reset_link_was_send_to_your_email), Snackbar.LENGTH_LONG).show()
                        }
                        is Resource.Error -> {
                            Snackbar.make(requireView(), "Error: ${it.message}", Snackbar.LENGTH_LONG).show()
                        }
                        else -> Unit
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.login.collect {
                    when(it) {
                       is Resource.Loading -> {
                           binding.buttonLoginLogin.startAnimation()
                       }
                       is Resource.Success -> {
                           binding.buttonLoginLogin.revertAnimation()
                           Intent(requireActivity(), ShoppingActivity::class.java).also {intent ->
                               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                               startActivity(intent)
                           }
                       }
                       is Resource.Error -> {
                           Snackbar.make(binding.root, it.message.toString(), Snackbar.LENGTH_LONG).show()
                           binding.buttonLoginLogin.revertAnimation()
                       }
                        else -> Unit
                    }
                }
            }
        }
    }
}