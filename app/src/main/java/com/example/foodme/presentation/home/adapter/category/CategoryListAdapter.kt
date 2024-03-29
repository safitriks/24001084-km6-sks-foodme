package com.example.foodme.presentation.home.adapter.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodme.data.model.Category
import com.example.foodme.databinding.ItemCategoryMenuBinding
import com.example.foodme.utils.ViewHolderBinder

class CategoryListAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var asyncDataDiffer = AsyncListDiffer(
        this, object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    )

    fun submitDataCategory(data: List<Category>) {
        asyncDataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CategoryItemViewHolder(
            ItemCategoryMenuBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = asyncDataDiffer.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder !is ViewHolderBinder<*>) return
        (holder as ViewHolderBinder<Category>).bind(asyncDataDiffer.currentList[position])
    }
}