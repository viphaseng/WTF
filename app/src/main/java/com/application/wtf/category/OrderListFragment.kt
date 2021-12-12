package com.application.wtf.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.application.wtf.R
import com.application.wtf.databinding.FragmentListOrderBinding
import com.application.wtf.listener.OnRemoveItemListener
import com.application.wtf.payload.ItemDetail
import java.text.DecimalFormat

class OrderListFragment : Fragment(), OnRemoveItemListener<ItemDetail> {

    private lateinit var binding: FragmentListOrderBinding
    private val adapter = OrderListAdapter(arrayListOf(), this)
    private val bundleList get() = arguments?.getParcelableArrayList<ItemDetail>("order_list")
    private var itemList = ArrayList<ItemDetail>()
    private var totalAmount: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bundleList?.let {
            itemList = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
        binding.recyclerViewOrder.adapter = adapter
        binding.empty.isVisible = bundleList.isNullOrEmpty()
        itemList.let { list ->
            list.forEach {
                totalAmount += it.price
            }
            updateTotalAmount()
            updateItemCart()
        }

        binding.buttonHistory.setOnClickListener {
            findNavController().navigate(R.id.action_order_list_to_order_history)

        }
        binding.buttonCheckOut.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelableArrayList("make_order_list", itemList)
            findNavController().navigate(R.id.action_order_list_to_make_order, bundle)
        }
    }

    override fun onRemoveItem(t: ItemDetail) {
        itemList.remove(t)
        totalAmount -= t.price
        updateTotalAmount()
        updateItemCart()
    }

    private fun updateItemCart() {
        adapter.updateList(itemList)
    }

    private fun updateTotalAmount() {
        binding.totalAmount.text = "$ ${DecimalFormat("##.##").format(totalAmount)}"
    }
}