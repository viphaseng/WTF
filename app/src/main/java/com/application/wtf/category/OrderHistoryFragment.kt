package com.application.wtf.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.application.wtf.databinding.FragmentListOrderBinding
import com.application.wtf.databinding.FragmentOrderHistoryBinding
import com.application.wtf.viewmodel.OrderViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class OrderHistoryFragment : Fragment() {

    private lateinit var binding: FragmentOrderHistoryBinding
    private val adapter = OrderHistoryAdapter(arrayListOf())
    private val viewModel: OrderViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
        binding.recyclerViewOrder.adapter = adapter

        viewModel.orderHistoryResponse.observe(viewLifecycleOwner, { result ->
            if (result.data != null) {
                adapter.updateList(result.data)
            }
        })
    }
}