package com.example.foodme.presentation.detailMenu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.foodme.R
import com.example.foodme.data.datasource.cart.CartDataSource
import com.example.foodme.data.datasource.cart.CartDatabaseDataSource
import com.example.foodme.data.model.Menu
import com.example.foodme.data.repository.CartRepository
import com.example.foodme.data.repository.CartRepositoryImpl
import com.example.foodme.data.source.local.database.AppDatabase
import com.example.foodme.databinding.ActivityDetailMenuBinding
import com.example.foodme.utils.GenericViewModelFactory
import com.example.foodme.utils.proceedWhen
import com.example.foodme.utils.toIndonesianFormat

class DetailMenuActivity : AppCompatActivity() {
    private val binding: ActivityDetailMenuBinding by lazy {
        ActivityDetailMenuBinding.inflate(layoutInflater)
    }
    private val viewModel: DetailMenuViewModel by viewModels {
        val db = AppDatabase.getInstance(this)
        val ds: CartDataSource = CartDatabaseDataSource(db.cartDao())
        val rp: CartRepository = CartRepositoryImpl(ds)
        GenericViewModelFactory.create(
            DetailMenuViewModel(intent?.extras, rp)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        bindMenu(viewModel.menu)
        setClickListener()
        observeData()
    }

    private fun setClickListener() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.cvLess.setOnClickListener {
            viewModel.minus()
        }
        binding.cvMore.setOnClickListener {
            viewModel.add()
        }
        binding.btnAddToCart.setOnClickListener {
            addMenuToCart()
        }
        binding.tvLocName.setOnClickListener{
            navigateToMaps()
        }
    }

    private fun navigateToMaps() {
        startActivity(Intent(Intent.ACTION_VIEW, viewModel.getLocationUrl()))
    }

    private fun addMenuToCart() {
        viewModel.addToCart().observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(
                        this,
                        getString(R.string.text_add_to_cart_success), Toast.LENGTH_SHORT
                    ).show()
                    finish()
                },
                doOnError = {
                    Toast.makeText(this, getString(R.string.add_to_cart_failed), Toast.LENGTH_SHORT)
                        .show()
                },
                doOnLoading = {
                    Toast.makeText(this, getString(R.string.loading), Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    private fun bindMenu(menu: Menu?) {
        menu?.let { item ->
            binding.ivMenuImg.load(item.imgUrl) {
                crossfade(true)
            }
            binding.tvMenuName.text = item.name
            binding.tvMenuDetails.text = item.details
            binding.tvLocName.text = item.location
            binding.tvMenuPrice.text = item.price.toIndonesianFormat()
        }
    }

    private fun observeData() {
        viewModel.priceLiveData.observe(this) {
            binding.btnAddToCart.isEnabled = it != 0.0
            binding.btnAddToCart.text = getString(R.string.text_total_price, it.toIndonesianFormat())
        }
        viewModel.menuCountLiveData.observe(this) {
            binding.tvNumber.text = it.toString()
        }
    }

    companion object {
        const val EXTRA_MENU = "EXTRA_Menu"
        fun startActivity(context: Context, menu: Menu) {
            val intent = Intent(context, DetailMenuActivity::class.java)
            intent.putExtra(EXTRA_MENU, menu)
            context.startActivity(intent)
        }
    }
}