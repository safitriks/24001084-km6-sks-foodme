package com.example.foodme.data.mapper

import com.example.foodme.data.model.User
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser?.toUser() =
    this?.let {
        User(
            fullName = this.displayName.orEmpty(),
            email = this.email.orEmpty(),
        )
    }
