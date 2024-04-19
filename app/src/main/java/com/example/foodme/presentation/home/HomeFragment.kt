package com.example.foodme.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodme.R
import com.example.foodme.data.datasource.category.CategoryApiDataSource
import com.example.foodme.data.datasource.category.CategoryDataSource
import com.example.foodme.data.datasource.menu.MenuApiDataSource
import com.example.foodme.data.datasource.menu.MenuDataSource
import com.example.foodme.data.datasource.user.UserDataSource
import com.example.foodme.data.datasource.user.UserDataSourceImpl
import com.example.foodme.data.model.Category
import com.example.foodme.data.model.Menu
import com.example.foodme.data.repository.CategoryRepository
import com.example.foodme.data.repository.CategoryRepositoryImpl
import com.example.foodme.data.repository.MenuRepository
import com.example.foodme.data.repository.MenuRepositoryImpl
import com.example.foodme.data.repository.UserPreferenceRepository
import com.example.foodme.data.repository.UserPreferenceRepositoryImpl
import com.example.foodme.data.source.local.pref.UserPreference
import com.example.foodme.data.source.local.pref.UserPreferenceImpl
import com.example.foodme.data.source.network.service.RestaurantApiService
import com.example.foodme.databinding.FragmentHomeBinding
import com.example.foodme.presentation.detailMenu.DetailMenuActivity
import com.example.foodme.presentation.home.adapter.category.CategoryListAdapter
import com.example.foodme.presentation.home.adapter.menu.MenuListAdapter
import com.example.foodme.utils.GenericViewModelFactory
import com.example.foodme.utils.proceedWhen

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels {
        val service = RestaurantApiService.invoke()
        val categoryDataSource: CategoryDataSource = CategoryApiDataSource(service)
        val categoryRepository: CategoryRepository = CategoryRepositoryImpl(categoryDataSource)
        val menuDataSource: MenuDataSource = MenuApiDataSource(service)
        val menuRepository: MenuRepository = MenuRepositoryImpl(menuDataSource)
        val userPref: UserPreference = UserPreferenceImpl(requireContext())
        val userDataSource: UserDataSource = UserDataSourceImpl(userPref)
        val userRepository: UserPreferenceRepository = UserPreferenceRepositoryImpl(userDataSource)
        GenericViewModelFactory.create(HomeViewModel(categoryRepository, menuRepository, userRepository))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCategory()
        setupMenu()
        observeGridMode()
        getCategoryData()
        getMenuData(null)
        setClickAction()
    }

    private val categoryAdapter: CategoryListAdapter by lazy {
        CategoryListAdapter {
            getMenuData(it.name)
        }
    }

    private val menuAdapter: MenuListAdapter by lazy {
        MenuListAdapter (viewModel.getListMode()) {
            navigateToDetail(it)
        }
    }

    private fun getMenuData(categoryName: String? = null) {
        viewModel.getMenus(categoryName).observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data ->
                        bindMenuList(data)
                    }
                }
            )
        }
    }

    private fun getCategoryData() {
        viewModel.getCategory().observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data -> bindCategory(data) }
                }
            )
        }
    }

    private fun setupMenu() {
        binding.rvCatalog.apply {
            adapter = menuAdapter
        }
    }

    private fun setupCategory() {
        binding.rvCategory.apply {
            adapter = categoryAdapter
        }
    }

    private fun observeGridMode() {
        viewModel.isUsingGridMode.observe(viewLifecycleOwner) { isUsingGridMode ->
            setIcon(isUsingGridMode)
            changeLayout(isUsingGridMode)
        }
    }

    private fun bindMenuList(data: List<Menu>) {
        menuAdapter.submitData(data)
    }

    private fun bindCategory(data: List<Category>) {
        categoryAdapter.submitDataCategory(data)
    }

    private fun setClickAction() {
        binding.ivList.setOnClickListener {
            viewModel.changeListMode()
        }
    }

    private fun setIcon(usingGridMode: Boolean) {
        binding.ivList.setImageResource(if (usingGridMode) R.drawable.ic_list else R.drawable.ic_grid)
    }

    private fun changeLayout(usingGridMode: Boolean) {
        val listMode = if (usingGridMode) MenuListAdapter.MODE_GRID else MenuListAdapter.MODE_LIST
        menuAdapter.listMode = listMode
        setupMenu()
        binding.rvCatalog.apply {
            layoutManager = GridLayoutManager(requireContext(), if (usingGridMode) 2 else 1)
        }
    }

    private fun navigateToDetail(item: Menu) {
        DetailMenuActivity.startActivity(
            requireContext(),
            item
        )
    }
}