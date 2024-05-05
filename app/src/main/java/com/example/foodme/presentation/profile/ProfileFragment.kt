package com.example.foodme.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.foodme.R
import com.example.foodme.databinding.FragmentProfileBinding
import com.example.foodme.presentation.main.MainActivity
import com.example.foodme.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setClickListener()
        observeProfileData()
    }

    private fun observeProfileData() {
        viewModel.getCurrentUser().let {
            binding.ivProfile.setImageResource(R.drawable.ic_tab_profile)
            binding.nameEditText.setText(it?.fullName.orEmpty())
            binding.emailEditText.setText(it?.email.orEmpty())
        }
    }

    private fun setClickListener() {
        binding.ivEdit.setOnClickListener {
            binding.nameInputLayout.isEnabled = true
            binding.emailInputLayout.isVisible = false
            binding.ivSave.isVisible = true
            binding.ivEdit.isVisible = false
        }
        binding.ivSave.setOnClickListener {
            val username = binding.nameEditText.text.toString()
            updateProfile(username)
        }
        binding.btnLogout.setOnClickListener {
            doLogout()
        }
    }

    private fun updateProfile(username: String) {
        viewModel.updateUsername(username).observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbLoadingSave.isVisible = false
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.text_success_notif),
                        Toast.LENGTH_SHORT,
                    ).show()
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                },
                doOnError = {
                    binding.pbLoadingSave.isVisible = false
                    Toast.makeText(
                        requireContext(),
                        "Update Profile Failed : ${it.exception?.message.orEmpty()}",
                        Toast.LENGTH_SHORT,
                    ).show()
                    binding.ivSave.isVisible = true
                },
                doOnLoading = {
                    binding.pbLoadingSave.isVisible = true
                    binding.ivSave.isVisible = false
                },
            )
        }
    }

    private fun doLogout() {
        viewModel.doLogout().observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.btnLogout.isVisible = true
                    navigateToMain()
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    binding.btnLogout.isVisible = true
                    Toast.makeText(
                        requireContext(),
                        "Register Failed : ${it.exception?.message.orEmpty()}",
                        Toast.LENGTH_SHORT,
                    ).show()
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnLogout.isVisible = false
                },
            )
        }
    }

    private fun navigateToMain() {
        startActivity(
            Intent(requireContext(), MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            },
        )
    }
}
