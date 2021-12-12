package com.application.wtf.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.wtf.R
import com.application.wtf.databinding.FragmentMenuDetailBinding
import com.application.wtf.listener.OnAddCartItemListener
import com.application.wtf.listener.OnItemClickListener
import com.application.wtf.network.RetroInstance
import com.application.wtf.payload.ItemDetail
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso

class MenuDetailDialog(private val item: ItemDetail,private val addCartListener: OnAddCartItemListener<ItemDetail>) : BottomSheetDialogFragment() {

    private var _binding: FragmentMenuDetailBinding? = null

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog;

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this._binding = FragmentMenuDetailBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this._binding?.apply {
            val symbol = "$"
            textViewAmount.text = "$symbol${item.price}"
            textViewName.text = item.name
            val imageUrl = "${RetroInstance.BASE_URL}${item.image}"
            Picasso.get().load(imageUrl)
                .placeholder(R.drawable.progress_animation)
                .into(imageView)
            buttonClose.setOnClickListener {
                dismiss()
            }
            buttonCheckOut.setOnClickListener {
                addCartListener.onAddCart(item)
                dismiss()
            }
        }
    }
}