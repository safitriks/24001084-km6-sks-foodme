package com.example.foodme.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodme.R
import com.example.foodme.data.datasource.category.DummyCategoryDataSource
import com.example.foodme.data.datasource.menu.DummyMenuDataSource
import com.example.foodme.data.model.Category
import com.example.foodme.data.model.Menu
import com.example.foodme.data.repository.CategoryRepository
import com.example.foodme.data.repository.CategoryRepositoryImpl
import com.example.foodme.data.repository.MenuRepository
import com.example.foodme.data.repository.MenuRepositoryImpl
import com.example.foodme.data.source.local.pref.UserPreferenceImpl
import com.example.foodme.databinding.FragmentHomeBinding
import com.example.foodme.presentation.detailMenu.DetailMenuActivity
import com.example.foodme.presentation.home.adapter.category.CategoryListAdapter
import com.example.foodme.presentation.home.adapter.menu.MenuListAdapter
import com.example.foodme.presentation.home.adapter.menu.OnItemClickedListener
import com.example.foodme.utils.GenericViewModelFactory

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var menuAdapter: MenuListAdapter? = null
    private var categoryAdapter: CategoryListAdapter? = null
    private val viewModel: HomeViewModel by viewModels {
        val userPref = UserPreferenceImpl(requireContext())
        val categoryDataSource = DummyCategoryDataSource()
        val categoryRepository: CategoryRepository = CategoryRepositoryImpl(categoryDataSource)
        val menuDataSource = DummyMenuDataSource()
        val menuRepository: MenuRepository = MenuRepositoryImpl(menuDataSource)
        GenericViewModelFactory.create(HomeViewModel(categoryRepository, menuRepository, userPref))
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
        bindCategory(viewModel.getCategories())
        observeGridMode()
        setClickAction()
    }

    private fun observeGridMode() {
        viewModel.isUsingGridMode.observe(viewLifecycleOwner) { isUsingGridMode ->
            setIcon(isUsingGridMode)
            bindMenuList(isUsingGridMode, viewModel.getMenus())
        }
    }

    private fun setClickAction() {
        binding.ivList.setOnClickListener {
            viewModel.changeListMode()
        }
    }

    private fun setIcon(usingGridMode: Boolean) {
        binding.ivList.setImageResource(if (usingGridMode) R.drawable.ic_list else R.drawable.ic_grid)
    }

    private fun bindCategory(data: List<Category>) {
        categoryAdapter = CategoryListAdapter()
        binding.rvCategory.apply {
            adapter = this@HomeFragment.categoryAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        categoryAdapter?.submitDataCategory(data)
    }

    private fun bindMenuList(isUsingGrid: Boolean, data: List<Menu>) {
        val listMode = if (isUsingGrid) MenuListAdapter.MODE_GRID else MenuListAdapter.MODE_LIST
        menuAdapter = MenuListAdapter(
            listMode = listMode,
            listener = object : OnItemClickedListener<Menu> {
                override fun onItemClicked(item: Menu) {
                    navigateToDetail(item)
                }
            }
        )
        binding.rvCatalog.apply {
            adapter = this@HomeFragment.menuAdapter
            layoutManager = GridLayoutManager(requireContext(), if (isUsingGrid) 2 else 1)
        }
        menuAdapter?.submitData(data)
    }

    private fun navigateToDetail(item: Menu) {
        DetailMenuActivity.startActivity(
            requireContext(),
            item
        )
    }
}