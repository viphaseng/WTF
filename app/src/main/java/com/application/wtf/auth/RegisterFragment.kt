package com.application.wtf.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.application.wtf.R
import com.application.wtf.databinding.FragmentRegisterBinding
import com.application.wtf.network.Resource
import com.application.wtf.viewmodel.AuthViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale

class RegisterFragment : Fragment() {

    private val viewModel: AuthViewModel by viewModel()
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            materialAlertDialogBuilder = MaterialAlertDialogBuilder(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEvent()
        initObservable()
    }

    private fun initObservable() {
        viewModel.username.observe(viewLifecycleOwner, { result ->
            binding.groupLoading.isVisible = result.status == Resource.Status.LOADING
            when (result.status) {
                Resource.Status.SUCCESS -> {
                    launchCustomAlertDialog(
                        getString(R.string.dialog_success_title, result.data),
                        getString(R.string.dialog_success_desc),
                        username = result.data ?: "",
                        statusSuccess = true
                    )
                }
                Resource.Status.ERROR -> {
                    launchCustomAlertDialog(
                        getString(R.string.dialog_error),
                        result.data ?: "",
                        username = "",
                        statusSuccess = false
                    )
                }
                else -> {
                    //ignore
                }
            }
        })
    }

    private fun initEvent() {
        binding.imageViewBack.setOnClickListener { activity?.onBackPressed() }
        binding.buttonLogin.setOnClickListener {
            val userName = binding.inputFieldUsername.editText?.text.toString()
            val password = binding.inputFieldPassword.editText?.text.toString()
            val fullName = binding.inputFieldFullName.editText?.text.toString()
            val dob = binding.inputFieldDob.editText?.text.toString()
            when {
                userName.isEmpty() -> binding.inputFieldUsername.error = "Invalid Username"
                password.isEmpty() -> binding.inputFieldPassword.error = "Invalid Password"
                dob.isEmpty() -> binding.inputFieldDob.error = "Invalid Date"
                else -> {
                    binding.inputFieldUsername.error = null
                    binding.inputFieldPassword.error = null
                    binding.groupLoading.isVisible = true
                    viewModel.register(userName, password, fullName, dob)
                }
            }
        }
        binding.inputFieldDate.setOnClickListener {
            val builderDate = MaterialDatePicker.Builder.datePicker()
            val pickerDate = builderDate.build()
            pickerDate.show(childFragmentManager, pickerDate.toString())
            pickerDate.addOnPositiveButtonClickListener {
                val formatter: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                val date: String = formatter.format(pickerDate.selection)
                binding.inputFieldDate.setText(date)
            }
        }
    }

    private fun launchCustomAlertDialog(title: String, message: String, username: String, statusSuccess: Boolean) {
        materialAlertDialogBuilder
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                if (statusSuccess) {
                    val args = Bundle()
                    args.putString("username", username)
                    findNavController().navigate(R.id.action_register_fragment_to_main_category, args)
                } else {
                    activity?.onBackPressed()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}