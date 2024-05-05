package com.example.foodme.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodme.R
import com.example.foodme.data.model.Category
import com.example.foodme.data.model.Menu
import com.example.foodme.databinding.FragmentHomeBinding
import com.example.foodme.presentation.detailMenu.DetailMenuActivity
import com.example.foodme.presentation.home.adapter.category.CategoryListAdapter
import com.example.foodme.presentation.home.adapter.menu.MenuListAdapter
import com.example.foodme.presentation.main.MainViewModel
import com.example.foodme.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModel()
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            FragmentHomeBinding.inflate(
                layoutInflater,
                container,
                false,
            )
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupCategory()
        setupMenu()
        observeGridMode()
        getCategoryData()
        getMenuData(null)
        setClickAction()
        getUsername()
    }

    private fun getUsername() {
        if (mainViewModel.isLogin()) {
            val username = mainViewModel.getCurrentUsername()?.fullName
            if (username != null) {
                binding.layoutHeader.tvName.text = getString(R.string.text_name, username)
            } else {
                binding.layoutHeader.tvName.text = getString(R.string.text_default_username)
            }
        } else {
            binding.layoutHeader.tvName.text = getString(R.string.text_default_username)
        }
    }

    private val categoryAdapter: CategoryListAdapter by lazy {
        CategoryListAdapter {
            getMenuData(it.name)
        }
    }

    private val menuAdapter: MenuListAdapter by lazy {
        MenuListAdapter(viewModel.getListMode()) {
            navigateToDetail(it)
        }
    }

    private fun getMenuData(categoryName: String? = null) {
        viewModel.getMenus(categoryName).observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data ->
                        bindMenuList(data)
                        binding.pbLoadingCatalog.isVisible = false
                    }
                },
                doOnLoading = {
                    binding.pbLoadingCatalog.isVisible = true
                },
            )
        }
    }

    private fun getCategoryData() {
        viewModel.getCategory().observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data -> bindCategory(data) }
                    binding.pbLoadingCategory.isVisible = false
                },
                doOnLoading = {
                    binding.pbLoadingCategory.isVisible = true
                },
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
            item,
        )
    }
}
