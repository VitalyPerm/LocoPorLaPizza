package com.vitaly.locoporlapizza.presentation.preview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vitaly.locoporlapizza.R
import com.vitaly.locoporlapizza.databinding.ItemPreviewFragmentBinding
import com.vitaly.locoporlapizza.utils.loadPicture

class PreviewFragmentAdapter(var images: List<String>) : RecyclerView.Adapter<PreviewFragmentAdapter.PagerViewHolder>() {

    inner class PagerViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = ItemPreviewFragmentBinding.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_preview_fragment, parent, false)
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.binding.image.loadPicture(images[position])
    }

    override fun getItemCount(): Int = images.size
}