package com.example.foodme.presentation.home.adapter.menu

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.foodme.data.model.Menu
import com.example.foodme.databinding.ItemMenuGridBinding
import com.example.foodme.utils.ViewHolderBinder
import com.example.foodme.utils.toIndonesianFormat

class MenuGridItemViewHolder(
    private val itemClick: (Menu) -> Unit,
    private val binding: ItemMenuGridBinding,
) : ViewHolder(binding.root), ViewHolderBinder<Menu> {
    override fun bind(item: Menu) {
        item.let {
            binding.ivGridImage.load(item.imgUrl) {
                crossfade(true)
            }
            binding.tvGridName.text = it.name
            binding.tvGridPrice.text = it.price.toIndonesianFormat()
            itemView.setOnClickListener {
                itemClick(item)
            }
        }
    }
}
