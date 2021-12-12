package com.application.wtf.category

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.wtf.R
import com.application.wtf.category.OrderListAdapter.ItemViewHolder
import com.application.wtf.databinding.ItemListOrderBinding
import com.application.wtf.listener.OnRemoveItemListener
import com.application.wtf.network.RetroInstance.Companion.BASE_URL
import com.application.wtf.payload.ItemDetail
import com.squareup.picasso.Picasso

class OrderListAdapter(
    private val list: ArrayList<ItemDetail>,
    private val removeListener: OnRemoveItemListener<ItemDetail>
) : RecyclerView.Adapter<ItemViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: ArrayList<ItemDetail>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = ItemListOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item: ItemDetail = list[position]
        holder.bind(item, removeListener)
    }

    override fun getItemCount(): Int = list.size
    inner class ItemViewHolder(private val viewBinding: ItemListOrderBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(data: ItemDetail, listener: OnRemoveItemListener<ItemDetail>) {
            val imageUrl = "$BASE_URL${data.image}"
            Picasso.get().load(imageUrl)
                .placeholder(R.drawable.progress_animation)
                .into(viewBinding.imageView)
            viewBinding.itemName.text = data.name
            val symbol = "$"
            viewBinding.itemPrice.text = "$symbol${data.price}"
            itemView.setOnClickListener {
                listener.onRemoveItem(data)
            }
        }
    }
}


