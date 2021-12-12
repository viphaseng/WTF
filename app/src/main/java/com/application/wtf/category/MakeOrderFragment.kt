package com.application.wtf.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.application.wtf.R
import com.application.wtf.databinding.FragmentCheckoutBinding
import com.application.wtf.extension.convertDate
import com.application.wtf.extension.formatDate
import com.application.wtf.network.Resource
import com.application.wtf.payload.ItemDetail
import com.application.wtf.viewmodel.OrderViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MakeOrderFragment : Fragment() {

    private val adapter = MakeOrderAdapter(arrayListOf())
    private val bundleList get() = arguments?.getParcelableArrayList<ItemDetail>("make_order_list")
    private lateinit var binding: FragmentCheckoutBinding
    private val viewModel: OrderViewModel by viewModel()
    private val arrayId = ArrayList<String>()
    private var totalPrice: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonDone.setOnClickListener {
            findNavController().popBackStack(R.id.main_category, false)
        }
        binding.recyclerViewOrder.adapter = adapter
        bundleList?.let { list ->
            list.forEach {
                totalPrice += it.price
                arrayId.add(it.id)
            }
            binding.totalAmount.text = "$ $totalPrice"
            adapter.updateList(list)

        }
        viewModel.requestOrder(arrayId)
        viewModel.orderResponse.observe(viewLifecycleOwner, { result ->
            when (result.status) {
                Resource.Status.SUCCESS -> {
                    binding.textViewOrderId.text = result.data?.id
                    binding.textViewName.text = getString(R.string.text_thanks_format, result.data?.user?.fullName)
                    val convertedDate = result.data?.date?.convertDate()
                    convertedDate?.let { date ->
                        binding.textViewOrderDate.text = date.formatDate()
                    }
                }
                else -> {
                    //ignore
                }
            }
        })
    }
}