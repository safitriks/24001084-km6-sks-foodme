package com.example.foodme.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import coil.load
import coil.transform.CircleCropTransformation
import com.example.foodme.R
import com.example.foodme.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModels()

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
        viewModel.profileData.observe(viewLifecycleOwner) {
            binding.ivProfile.load(it.profileImg) {
                crossfade(true)
                error(R.drawable.ic_tab_profile)
                transformations(CircleCropTransformation())
            }
            binding.nameEditText.setText(it.name)
            binding.usernameEditText.setText(it.username)
            binding.emailEditText.setText(it.email)
        }
    }

    private fun setClickListener() {
        binding.layoutHeaderProfile.ivEdit.setOnClickListener {
            viewModel.changeEditMode()
        }
    }

    private fun observeEditMode() {
        viewModel.isEditMode.observe(viewLifecycleOwner) {
            binding.emailEditText.isEnabled = it
            binding.nameEditText.isEnabled = it
            binding.usernameEditText.isEnabled = it
            binding.layoutHeaderProfile.ivEdit.setImageResource(viewModel.changeIcon())
        }
    }
}