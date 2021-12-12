package com.application.wtf.category

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.wtf.R
import com.application.wtf.category.CategoryDetailAdapter.ItemDetailViewHolder
import com.application.wtf.category.MakeOrderAdapter.OrderViewHolder
import com.application.wtf.databinding.ItemCategoryDetailBinding
import com.application.wtf.databinding.ItemMakeOrderBinding
import com.application.wtf.listener.OnItemClickListener
import com.application.wtf.network.RetroInstance.Companion.BASE_URL
import com.application.wtf.payload.ItemDetail
import com.squareup.picasso.Picasso

class MakeOrderAdapter(
    private val list: ArrayList<ItemDetail>
) : RecyclerView.Adapter<OrderViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: ArrayList<ItemDetail>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val inflater = ItemMakeOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val item: ItemDetail = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size
    inner class OrderViewHolder(private val viewBinding: ItemMakeOrderBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(data: ItemDetail) {
            viewBinding.textViewPrice.text = "$ ${data.price}"
            viewBinding.tectViewName.text = data.name
        }
    }
}


