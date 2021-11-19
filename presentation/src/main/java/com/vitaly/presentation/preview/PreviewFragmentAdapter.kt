package com.vitaly.presentation.preview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.vitaly.presentation.R
import com.vitaly.presentation.utils.loadPicture

class PreviewFragmentAdapter(
    private val progressDrawable: CircularProgressDrawable,
    private var images: List<String>
) : RecyclerView.Adapter<PagerViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_preview_fragment, parent, false)
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.binding.image.loadPicture(images[position], progressDrawable)
    }

    override fun getItemCount(): Int = images.size
}