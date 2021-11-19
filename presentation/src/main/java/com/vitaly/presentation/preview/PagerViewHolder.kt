package com.vitaly.presentation.preview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.vitaly.presentation.databinding.ItemPreviewFragmentBinding


class PagerViewHolder(item: View) : RecyclerView.ViewHolder(item) {
    val binding = ItemPreviewFragmentBinding.bind(item)
}