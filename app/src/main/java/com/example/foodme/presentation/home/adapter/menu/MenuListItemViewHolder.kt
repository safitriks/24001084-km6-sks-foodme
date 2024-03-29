package com.example.foodme.presentation.home.adapter.menu

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodme.data.model.Menu
import com.example.foodme.utils.ViewHolderBinder
import com.example.foodme.utils.toIndonesianFormat
import coil.load
import com.example.foodme.databinding.ItemMenuListBinding

class MenuListItemViewHolder(
    private val binding: ItemMenuListBinding,
    private val listener: OnItemClickedListener<Menu>
): ViewHolder(binding.root), ViewHolderBinder<Menu> {

    override fun bind(item: Menu) {
        item.let{
            binding.ivListImage.load(item.imgUrl) {
                crossfade(true)
            }
            binding.tvListName.text = it.name
            binding.tvListPrice.text = it.price.toIndonesianFormat()
            binding.tvListLocation.text= it.location
            itemView.setOnClickListener {
                listener.onItemClicked(item)
            }
        }
    }
}