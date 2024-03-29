package com.example.foodme.data.datasource.category

import com.example.foodme.data.model.Category

class DummyCategoryDataSource : CategoryDataSource {
    override fun getCategories(): List<Category> {
        return listOf(
            Category(name = "Rice", imgUrl = "https://github.com/safitriks/foodme-assets/blob/main/category_img/ic_rice.png?raw=true"),
            Category(name = "Noodle", imgUrl ="https://github.com/safitriks/foodme-assets/blob/main/category_img/ic_noodle.png?raw=true"),
            Category(name = "Snack", imgUrl = "https://github.com/safitriks/foodme-assets/blob/main/category_img/ic_snack.png?raw=true"),
            Category(name = "Drink", imgUrl = "https://github.com/safitriks/foodme-assets/blob/main/category_img/ic_drink.png?raw=true"),
            Category(name = "Dessert", imgUrl = "https://github.com/safitriks/foodme-assets/blob/main/category_img/ic_dessert.png?raw=true")
        )
    }
}