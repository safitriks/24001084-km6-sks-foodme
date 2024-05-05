package com.example.foodme.presentation.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.foodme.R
import com.example.foodme.data.model.Cart
import com.example.foodme.databinding.ItemCartMenuBinding
import com.example.foodme.databinding.ItemCartMenuOrderBinding
import com.example.foodme.utils.ViewHolderBinder
import com.example.foodme.utils.doneEditing
import com.example.foodme.utils.toIndonesianFormat

class CartListAdapter(private val cartListener: CartListener? = null) :
    RecyclerView.Adapter<ViewHolder>() {
    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<Cart>() {
                override fun areItemsTheSame(
                    oldItem: Cart,
                    newItem: Cart,
                ): Boolean {
                    return oldItem.id == newItem.id && oldItem.menuId == newItem.menuId
                }

                override fun areContentsTheSame(
                    oldItem: Cart,
                    newItem: Cart,
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )

    fun submitData(data: List<Cart>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        return if (cartListener != null) {
            CartViewHolder(
                ItemCartMenuBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                ),
                cartListener,
            )
        } else {
            CartOrderViewHolder(
                ItemCartMenuOrderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                ),
            )
        }
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        (holder as ViewHolderBinder<Cart>).bind(dataDiffer.currentList[position])
    }
}

class CartViewHolder(
    private val binding: ItemCartMenuBinding,
    private val cartListener: CartListener?,
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Cart> {
    override fun bind(item: Cart) {
        setCartData(item)
        setCartNotes(item)
        setClickListeners(item)
    }

    private fun setCartData(item: Cart) {
        with(binding) {
            binding.ivItemImage.load(item.menuImgUrl) {
                crossfade(true)
            }
            tvNumber.text = item.itemQuantity.toString()
            tvNameItem.text = item.menuName
            tvPriceItem.text = (item.itemQuantity * item.menuPrice).toIndonesianFormat()
        }
    }

    private fun setCartNotes(item: Cart) {
        binding.etNotesItem.setText(item.itemNotes)
        binding.etNotesItem.doneEditing {
            binding.etNotesItem.clearFocus()
            val newItem =
                item.copy().apply {
                    itemNotes = binding.etNotesItem.text.toString().trim()
                }
            cartListener?.onUserDoneEditingNotes(newItem)
        }
    }

    private fun setClickListeners(item: Cart) {
        with(binding) {
            cvLess.setOnClickListener { cartListener?.onMinusTotalItemCartClicked(item) }
            cvMore.setOnClickListener { cartListener?.onPlusTotalItemCartClicked(item) }
            ivTrash.setOnClickListener { cartListener?.onRemoveCartClicked(item) }
        }
    }
}

class CartOrderViewHolder(
    private val binding: ItemCartMenuOrderBinding,
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Cart> {
    override fun bind(item: Cart) {
        setCartData(item)
        setCartNotes(item)
    }

    private fun setCartData(item: Cart) {
        with(binding) {
            binding.ivMenuImage.load(item.menuImgUrl) {
                crossfade(true)
            }
            tvTotalQuantity.text =
                itemView.rootView.context.getString(
                    R.string.total_quantity,
                    item.itemQuantity.toString(),
                )
            tvMenuName.text = item.menuName
            tvMenuPrice.text = item.menuPrice.toIndonesianFormat()
        }
    }

    private fun setCartNotes(item: Cart) {
        binding.tvNotes.text = item.itemNotes
    }
}

interface CartListener {
    fun onPlusTotalItemCartClicked(cart: Cart)

    fun onMinusTotalItemCartClicked(cart: Cart)

    fun onRemoveCartClicked(cart: Cart)

    fun onUserDoneEditingNotes(cart: Cart)
}
