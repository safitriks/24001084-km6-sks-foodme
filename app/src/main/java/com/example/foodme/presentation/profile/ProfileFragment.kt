package com.example.foodme.presentation.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import coil.load
import coil.transform.CircleCropTransformation
import com.example.foodme.R
import com.example.foodme.data.datasource.auth.FirebaseAuthDataSourceImpl
import com.example.foodme.data.repository.UserRepositoryImpl
import com.example.foodme.data.source.firebase.FirebaseService
import com.example.foodme.data.source.firebase.FirebaseServiceImpl
import com.example.foodme.databinding.FragmentProfileBinding
import com.example.foodme.presentation.main.MainActivity
import com.example.foodme.utils.GenericViewModelFactory
import com.example.foodme.utils.proceedWhen

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModels() {
        val service : FirebaseService = FirebaseServiceImpl()
        val dataSource = FirebaseAuthDataSourceImpl(service)
        val repo = UserRepositoryImpl(dataSource)
        GenericViewModelFactory.create(ProfileViewModel(repo))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListener()
        observeEditMode()
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
        binding.layoutHeaderProfile.ivEdit.setOnClickListener {
            viewModel.changeEditMode()
        }
        binding.btnLogout.setOnClickListener {
            doLogout()
        }
    }

    private fun doLogout() {
        viewModel.doLogout().observe(viewLifecycleOwner){
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
                        Toast.LENGTH_SHORT
                    ).show()
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnLogout.isVisible = false
                }
            )
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(requireContext(), MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    private fun observeEditMode() {
        viewModel.isEditMode.observe(viewLifecycleOwner) {
            binding.emailEditText.isEnabled = it
            binding.nameEditText.isEnabled = it
            binding.layoutHeaderProfile.ivEdit.setImageResource(viewModel.changeIcon())
        }
    }
}