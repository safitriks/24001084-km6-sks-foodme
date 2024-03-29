package com.example.foodme.presentation.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.foodme.R
import com.example.foodme.data.datasource.cart.CartDataSource
import com.example.foodme.data.datasource.cart.CartDatabaseDataSource
import com.example.foodme.data.model.Cart
import com.example.foodme.data.repository.CartRepository
import com.example.foodme.data.repository.CartRepositoryImpl
import com.example.foodme.data.source.local.database.AppDatabase
import com.example.foodme.databinding.FragmentCartBinding
import com.example.foodme.presentation.checkout.CheckoutActivity
import com.example.foodme.presentation.common.adapter.CartListAdapter
import com.example.foodme.presentation.common.adapter.CartListener
import com.example.foodme.utils.GenericViewModelFactory
import com.example.foodme.utils.hideKeyboard
import com.example.foodme.utils.proceedWhen
import com.example.foodme.utils.toIndonesianFormat

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding

    private val viewModel: CartViewModel by viewModels {
        val db = AppDatabase.getInstance(requireContext())
        val ds: CartDataSource = CartDatabaseDataSource(db.cartDao())
        val rp: CartRepository = CartRepositoryImpl(ds)
        GenericViewModelFactory.create(CartViewModel(rp))
    }

    private val adapter: CartListAdapter by lazy {
        CartListAdapter(object : CartListener {
            override fun onPlusTotalItemCartClicked(cart: Cart) {
                viewModel.increaseCart(cart)
            }

            override fun onMinusTotalItemCartClicked(cart: Cart) {
                viewModel.decreaseCart(cart)
            }

            override fun onRemoveCartClicked(cart: Cart) {
                viewModel.removeCart(cart)
            }

            override fun onUserDoneEditingNotes(cart: Cart) {
                viewModel.setCartNotes(cart)
                hideKeyboard()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        observeData()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.btnCheckout.setOnClickListener {
            startActivity(Intent(requireContext(), CheckoutActivity::class.java))
        }
    }

    private fun observeData() {
        viewModel.getAllCarts().observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.rvCart.isVisible = false
                    binding.btnCheckout.isEnabled = false
                },
                doOnSuccess = {
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    binding.rvCart.isVisible = true
                    binding.btnCheckout.isEnabled = true
                    result.payload?.let { (carts, totalPrice) ->
                        adapter.submitData(carts)
                        binding.tvTotalPrice.text = totalPrice.toIndonesianFormat()
                    }
                },
                doOnError = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = result.exception?.message.orEmpty()
                    binding.rvCart.isVisible = false
                },
                doOnEmpty = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = getString(R.string.text_cart_is_empty)
                    binding.rvCart.isVisible = false
                    binding.btnCheckout.isEnabled = false
                    result.payload?.let { (carts, totalPrice) ->
                        binding.tvTotalPrice.text = totalPrice.toIndonesianFormat()
                    }
                }
            )
        }
    }

    private fun setupList() {
        binding.rvCart.adapter = this@CartFragment.adapter
    }
}