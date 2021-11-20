package com.vitaly.presentation.main

import androidx.recyclerview.widget.DiffUtil
import com.vitaly.domain.models.Pizza

class MainFragmentDiffUtil(
    private val oldList: List<Pizza>,
    private val newList: List<Pizza>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}