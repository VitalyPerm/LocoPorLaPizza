package com.vitaly.presentation.cart

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.vitaly.presentation.databinding.ItemCartBinding

class CartFragmentViewHolder(item: View) : RecyclerView.ViewHolder(item) {
    val binding = ItemCartBinding.bind(item)
}