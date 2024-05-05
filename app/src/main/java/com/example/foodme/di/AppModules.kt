package com.example.foodme.di

import android.content.SharedPreferences
import com.example.foodme.data.datasource.auth.FirebaseAuthDataSource
import com.example.foodme.data.datasource.auth.FirebaseAuthDataSourceImpl
import com.example.foodme.data.datasource.cart.CartDataSource
import com.example.foodme.data.datasource.cart.CartDatabaseDataSource
import com.example.foodme.data.datasource.category.CategoryApiDataSource
import com.example.foodme.data.datasource.category.CategoryDataSource
import com.example.foodme.data.datasource.menu.MenuApiDataSource
import com.example.foodme.data.datasource.menu.MenuDataSource
import com.example.foodme.data.datasource.user.UserDataSource
import com.example.foodme.data.datasource.user.UserDataSourceImpl
import com.example.foodme.data.repository.CartRepository
import com.example.foodme.data.repository.CartRepositoryImpl
import com.example.foodme.data.repository.CategoryRepository
import com.example.foodme.data.repository.CategoryRepositoryImpl
import com.example.foodme.data.repository.MenuRepository
import com.example.foodme.data.repository.MenuRepositoryImpl
import com.example.foodme.data.repository.UserPreferenceRepository
import com.example.foodme.data.repository.UserPreferenceRepositoryImpl
import com.example.foodme.data.repository.UserRepository
import com.example.foodme.data.repository.UserRepositoryImpl
import com.example.foodme.data.source.firebase.FirebaseService
import com.example.foodme.data.source.firebase.FirebaseServiceImpl
import com.example.foodme.data.source.local.database.AppDatabase
import com.example.foodme.data.source.local.database.dao.CartDao
import com.example.foodme.data.source.local.pref.UserPreference
import com.example.foodme.data.source.local.pref.UserPreferenceImpl
import com.example.foodme.data.source.network.service.RestaurantApiService
import com.example.foodme.presentation.cart.CartViewModel
import com.example.foodme.presentation.checkout.CheckoutViewModel
import com.example.foodme.presentation.detailMenu.DetailMenuViewModel
import com.example.foodme.presentation.home.HomeViewModel
import com.example.foodme.presentation.login.LoginViewModel
import com.example.foodme.presentation.main.MainViewModel
import com.example.foodme.presentation.profile.ProfileViewModel
import com.example.foodme.presentation.register.RegisterViewModel
import com.example.foodme.utils.SharedPreferenceUtils
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

object AppModules {
    private val networkModule =
        module {
            single<RestaurantApiService> {
                RestaurantApiService.invoke()
            }
        }

    private val firebaseModule =
        module {
            single<FirebaseAuth> {
                FirebaseAuth.getInstance()
            }
            single<FirebaseService> {
                FirebaseServiceImpl(get())
            }
        }

    private val localModule =
        module {
            single<AppDatabase> {
                AppDatabase.createInstance(androidContext())
            }
            single<CartDao> {
                get<AppDatabase>().cartDao()
            }
            single<SharedPreferences> {
                SharedPreferenceUtils.createPreference(
                    androidContext(),
                    UserPreferenceImpl.PREF_NAME,
                )
            }
            single<UserPreference> {
                UserPreferenceImpl(get())
            }
        }

    private val dataSource =
        module {
            single<CartDataSource> {
                CartDatabaseDataSource(get())
            }
            single<CategoryDataSource> {
                CategoryApiDataSource(get())
            }
            single<MenuDataSource> {
                MenuApiDataSource(get())
            }
            single<UserDataSource> {
                UserDataSourceImpl(get())
            }
            single<FirebaseAuthDataSource> {
                FirebaseAuthDataSourceImpl(get())
            }
        }

    private val repository =
        module {
            single<CartRepository> {
                CartRepositoryImpl(get())
            }
            single<CategoryRepository> {
                CategoryRepositoryImpl(get())
            }
            single<MenuRepository> {
                MenuRepositoryImpl(get())
            }
            single<UserPreferenceRepository> {
                UserPreferenceRepositoryImpl(get())
            }
            single<UserRepository> {
                UserRepositoryImpl(get())
            }
        }

    private val viewModelModule =
        module {
            viewModelOf(::MainViewModel)
            viewModelOf(::HomeViewModel)
            viewModelOf(::CartViewModel)
            viewModelOf(::CheckoutViewModel)
            viewModelOf(::DetailMenuViewModel)
            viewModelOf(::LoginViewModel)
            viewModelOf(::ProfileViewModel)
            viewModelOf(::RegisterViewModel)
            viewModel { params ->
                DetailMenuViewModel(
                    extras = params.get(),
                    cartRepository = get(),
                )
            }
        }

    val modules =
        listOf(
            networkModule,
            localModule,
            dataSource,
            repository,
            firebaseModule,
            viewModelModule,
        )
}
