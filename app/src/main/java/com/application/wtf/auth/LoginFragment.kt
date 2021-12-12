package com.application.wtf.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.application.wtf.R
import com.application.wtf.databinding.FragmentLoginBinding
import com.application.wtf.network.Resource
import com.application.wtf.viewmodel.AuthViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private val viewModel: AuthViewModel by viewModel()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEvent()
        viewModel.username.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Resource.Status.SUCCESS -> {
                    val nextArgs = Bundle()
                    nextArgs.putString("username", result.data)
                    findNavController().navigate(R.id.action_login_fragment_to_main_category, nextArgs)
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), "Unauthorized", Toast.LENGTH_SHORT)
                        .show()
                    binding.progressCircular.isVisible = false
                }
                else -> {
                    binding.progressCircular.isVisible = result.status == Resource.Status.LOADING
                }
            }
        })
    }

    private fun initEvent() {
        binding.buttonLogin.setOnClickListener {
            val userName = binding.inputFieldUsername.editText?.text.toString()
            val password = binding.inputFieldPassword.editText?.text.toString()
            when {
                userName.isEmpty() -> binding.inputFieldUsername.error = "Invalid Username"
                password.isEmpty() -> binding.inputFieldPassword.error = "Invalid Password"
                else -> {
                    binding.inputFieldUsername.error = null
                    binding.inputFieldPassword.error = null
                    viewModel.login(userName, password)
                }
            }
        }

        binding.textViewRegister.setOnClickListener {
            findNavController().navigate(R.id.action_login_fragment_to_register_fragment)
        }
    }
}