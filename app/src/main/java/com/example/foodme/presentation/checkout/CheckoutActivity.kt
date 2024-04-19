package com.example.foodme.presentation.checkout

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.foodme.R
import com.example.foodme.data.datasource.auth.FirebaseAuthDataSource
import com.example.foodme.data.datasource.auth.FirebaseAuthDataSourceImpl
import com.example.foodme.data.datasource.cart.CartDataSource
import com.example.foodme.data.datasource.cart.CartDatabaseDataSource
import com.example.foodme.data.datasource.menu.MenuApiDataSource
import com.example.foodme.data.datasource.menu.MenuDataSource
import com.example.foodme.data.repository.CartRepository
import com.example.foodme.data.repository.CartRepositoryImpl
import com.example.foodme.data.repository.MenuRepository
import com.example.foodme.data.repository.MenuRepositoryImpl
import com.example.foodme.data.repository.UserRepository
import com.example.foodme.data.repository.UserRepositoryImpl
import com.example.foodme.data.source.firebase.FirebaseService
import com.example.foodme.data.source.firebase.FirebaseServiceImpl
import com.example.foodme.data.source.local.database.AppDatabase
import com.example.foodme.data.source.network.service.RestaurantApiService
import com.example.foodme.databinding.ActivityCheckoutBinding
import com.example.foodme.presentation.checkout.adapter.PriceListAdapter
import com.example.foodme.presentation.common.adapter.CartListAdapter
import com.example.foodme.presentation.main.MainActivity
import com.example.foodme.utils.GenericViewModelFactory
import com.example.foodme.utils.proceedWhen
import com.example.foodme.utils.toIndonesianFormat

class CheckoutActivity : AppCompatActivity() {

    private val binding: ActivityCheckoutBinding by lazy {
        ActivityCheckoutBinding.inflate(layoutInflater)
    }

    private val viewModel: CheckoutViewModel by viewModels {
        val db = AppDatabase.getInstance(this)
        val s = RestaurantApiService.invoke()
        val fbs: FirebaseService = FirebaseServiceImpl()
        val ds: CartDataSource = CartDatabaseDataSource(db.cartDao())
        val mds: MenuDataSource = MenuApiDataSource(s)
        val fas: FirebaseAuthDataSource = FirebaseAuthDataSourceImpl(fbs)
        val ur: UserRepository = UserRepositoryImpl(fas)
        val mr: MenuRepository = MenuRepositoryImpl(mds)
        val rp: CartRepository = CartRepositoryImpl(ds)
        GenericViewModelFactory.create(CheckoutViewModel(rp, mr, ur))
    }

    private val adapter: CartListAdapter by lazy {
        CartListAdapter()
    }
    private val priceItemAdapter: PriceListAdapter by lazy {
        PriceListAdapter {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupList()
        observeData()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.btnCheckout.setOnClickListener{
            doCheckout()
        }
    }

    private fun doCheckout() {
        viewModel.checkoutCart().observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    dialogCheckoutSuccess(this)
                },
                doOnError = {
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    Toast.makeText(
                        this,
                        getString(R.string.text_check_out_error),
                        Toast.LENGTH_SHORT
                    ).show()
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                }
            )
        }
    }

    private fun dialogCheckoutSuccess(context: Context) {
        val dialogView: View = LayoutInflater.from(context).inflate(R.layout.layout_dialog, null)
        val finishBtn = dialogView.findViewById<Button>(R.id.btn_close)
        val alertDialogBuilder = AlertDialog.Builder(context)
        val dialog = alertDialogBuilder.create()
        alertDialogBuilder.setView(dialogView)
        finishBtn.setOnClickListener {
            deleteCart()
            startActivity(Intent(this, MainActivity::class.java))
            dialog.dismiss()
        }
        alertDialogBuilder.show()
    }

    private fun deleteCart() {
        viewModel.deleteAllCart()
    }

    private fun setupList() {
        binding.layoutContent.rvCart.adapter = adapter
        binding.layoutContent.rvShoppingSummary.adapter = priceItemAdapter
    }

    private fun observeData() {
        viewModel.checkoutData.observe(this) { result ->
            result.proceedWhen(doOnSuccess = {
                binding.layoutState.root.isVisible = false
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = false
                binding.layoutContent.root.isVisible = true
                binding.layoutContent.rvCart.isVisible = true
                binding.cvSectionOrder.isVisible = true
                binding.btnCheckout.isEnabled = true
                result.payload?.let { (carts, priceItems, totalPrice) ->
                    adapter.submitData(carts)
                    binding.tvTotalPrice.text = totalPrice.toIndonesianFormat()
                    priceItemAdapter.submitData(priceItems)
                }
            }, doOnLoading = {
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = true
                binding.layoutState.tvError.isVisible = false
                binding.layoutContent.root.isVisible = false
                binding.layoutContent.rvCart.isVisible = false
                binding.cvSectionOrder.isVisible = false
                binding.btnCheckout.isEnabled = false
            }, doOnError = {
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = true
                binding.layoutState.tvError.text = result.exception?.message.orEmpty()
                binding.layoutContent.root.isVisible = false
                binding.layoutContent.rvCart.isVisible = false
                binding.cvSectionOrder.isVisible = false
            }, doOnEmpty = { data ->
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = true
                binding.layoutState.tvError.text = getString(R.string.text_cart_is_empty)
                data.payload?.let { (_, _, totalPrice) ->
                    binding.tvTotalPrice.text = totalPrice.toIndonesianFormat()
                }
                binding.layoutContent.root.isVisible = false
                binding.layoutContent.rvCart.isVisible = false
                binding.btnCheckout.isEnabled = false
                binding.cvSectionOrder.isVisible = false
            })
        }
    }
}