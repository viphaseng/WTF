package com.application.wtf.category

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.wtf.R
import com.application.wtf.category.CategoryDetailAdapter.ItemDetailViewHolder
import com.application.wtf.databinding.ItemCategoryDetailBinding
import com.application.wtf.listener.OnItemClickListener
import com.application.wtf.network.RetroInstance.Companion.BASE_URL
import com.application.wtf.payload.ItemDetail
import com.squareup.picasso.Picasso

class CategoryDetailAdapter(
    private val list: ArrayList<ItemDetail>,
    private val itemClickListener: OnItemClickListener<ItemDetail>
) : RecyclerView.Adapter<ItemDetailViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<ItemDetail>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemDetailViewHolder {
        val inflater = ItemCategoryDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemDetailViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ItemDetailViewHolder, position: Int) {
        val item: ItemDetail = list[position]
        holder.bind(item, itemClickListener)
    }

    override fun getItemCount(): Int = list.size
    inner class ItemDetailViewHolder(private val viewBinding: ItemCategoryDetailBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(data: ItemDetail, listener: OnItemClickListener<ItemDetail>) {
            val imageUrl = "$BASE_URL${data.image}"
            Picasso.get().load(imageUrl)
                .placeholder(R.drawable.progress_animation)
                .into(viewBinding.imageView)
            viewBinding.itemName.text = data.name
            val symbol = "$"
            viewBinding.itemPrice.text = "$symbol${data.price}"
            itemView.setOnClickListener {
                listener.onClickItem(data)
            }
        }
    }
}


