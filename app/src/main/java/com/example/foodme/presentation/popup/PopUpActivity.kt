package com.example.foodme.presentation.popup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.foodme.databinding.ActivityPopUpBinding
import com.example.foodme.presentation.main.MainActivity

class PopUpActivity : AppCompatActivity() {
    private val binding: ActivityPopUpBinding by lazy {
        ActivityPopUpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.btnClose.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}