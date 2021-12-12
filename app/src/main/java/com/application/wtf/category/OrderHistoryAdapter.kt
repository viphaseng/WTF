package com.application.wtf.category

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.wtf.category.OrderHistoryAdapter.HistoryHolder
import com.application.wtf.databinding.ItemOrderHistoryBinding
import com.application.wtf.extension.convertDate
import com.application.wtf.extension.formatDate
import com.application.wtf.payload.OrderHistoryResponse

class OrderHistoryAdapter(
    private val list: ArrayList<OrderHistoryResponse>,
) : RecyclerView.Adapter<HistoryHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<OrderHistoryResponse>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        val inflater = ItemOrderHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryHolder(inflater)
    }

    override fun getItemCount(): Int = list.size
    inner class HistoryHolder(private val viewBinding: ItemOrderHistoryBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(data: OrderHistoryResponse) {
            viewBinding.textViewId.text = data.id
            val convertedDate = data.date.convertDate()
            viewBinding.textViewDate.text = convertedDate?.formatDate()
        }
    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        val item: OrderHistoryResponse = list[position]
        holder.bind(item)
    }
}


