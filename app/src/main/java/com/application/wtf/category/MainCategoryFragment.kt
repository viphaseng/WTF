package com.application.wtf.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.application.wtf.R
import com.application.wtf.R.dimen
import com.application.wtf.R.layout
import com.application.wtf.R.string
import com.application.wtf.databinding.FragmentCategorieBinding
import com.application.wtf.listener.OnAddCartItemListener
import com.application.wtf.listener.OnItemClickListener
import com.application.wtf.network.Resource
import com.application.wtf.payload.CategoryItemList
import com.application.wtf.payload.ItemDetail
import com.application.wtf.viewmodel.CategoryViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import org.koin.android.viewmodel.ext.android.viewModel

class MainCategoryFragment : Fragment(), OnItemClickListener<ItemDetail> {

    private val viewModel: CategoryViewModel by viewModel()
    private lateinit var binding: FragmentCategorieBinding
    private val username get() = arguments?.getString("username")
    private lateinit var adapter: CategoryDetailAdapter
    var cartList = ArrayList<ItemDetail>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategorieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CategoryDetailAdapter(arrayListOf(), this)
        binding.recyclerViewMenu.adapter = adapter
        binding.textViewGreetingTitle.text = getString(string.greeting_title_format, username)
        countTotalItem()
        binding.buttonCart.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelableArrayList("order_list", cartList)
            findNavController().navigate(R.id.action_main_category_to_order_list, bundle)

        }
        viewModel.mainCategory.observe(viewLifecycleOwner, { result ->
            if (result.status == Resource.Status.SUCCESS)
                result.data?.let { updateCategoryItem(it) }
        })
        viewModel.categoryDetailResponse.observe(viewLifecycleOwner, { result ->
            val isSuccess = result.status == Resource.Status.SUCCESS
            val isLoading = result.status == Resource.Status.LOADING
            binding.loading.isVisible = isLoading
            binding.recyclerViewMenu.isVisible = isSuccess
            if (isSuccess && result.data?.menu != null) {
                adapter.updateList(result.data.menu)
            }

        })
    }

    private fun updateCategoryItem(items: List<CategoryItemList>) {
        items.forEachIndexed { index, itemData ->
            view?.let {
                val chipLayout =
                    layoutInflater.inflate(layout.item_chip_filter, binding.chipGroup, false)
                val chip = chipLayout as Chip
                val chipParam = ViewGroup.LayoutParams.WRAP_CONTENT
                val padding = resources.getDimensionPixelOffset(dimen.dimen_16dp).toFloat()
                chip.layoutParams = ChipGroup.LayoutParams(chipParam, chipParam)
                chip.chipStartPadding = padding
                chip.chipEndPadding = padding
                chip.text = itemData.name
                chip.id = index
                chip.tag = itemData
                chip.isChecked = true
                binding.chipGroup.addView(chip)
            }

        }

        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            if (chip != null) {
                val item = chip.tag as CategoryItemList
                viewModel.loadCategoryDetail(item.id)
                binding.labelCategoryDetail.text = getString(string.label_category_detail, item.name)
            }
        }
        binding.chipGroup.check(viewModel.chipID)
    }

    private fun countTotalItem() {
        binding.buttonCart.text = if (cartList.size == 0) "" else cartList.size.toString()
    }

    override fun onClickItem(t: ItemDetail) {
        val menuDetailDialog = MenuDetailDialog(t, object : OnAddCartItemListener<ItemDetail> {
            override fun onAddCart(t: ItemDetail) {
                cartList.add(t)
                countTotalItem()
            }
        })
        menuDetailDialog.show(childFragmentManager, "")
    }
}