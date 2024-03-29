package com.example.foodme.presentation.home.adapter.category

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.foodme.data.model.Category
import com.example.foodme.databinding.ItemCategoryMenuBinding
import com.example.foodme.utils.ViewHolderBinder


class CategoryItemViewHolder (
    private val binding: ItemCategoryMenuBinding
    ): RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Category>
    {

        override fun bind(item: Category) {
            item.let {
                binding.ivCategoryImage.load(item.imgUrl) {
                    crossfade(true)
                }
                binding.tvCategoryName.text = it.name
            }
        }
    }