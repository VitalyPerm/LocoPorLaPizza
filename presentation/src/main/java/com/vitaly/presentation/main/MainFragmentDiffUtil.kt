package com.vitaly.presentation.main

import androidx.recyclerview.widget.DiffUtil
import com.vitaly.domain.models.PizzaEntity

class MainFragmentDiffUtil(
    private val oldList: List<PizzaEntity>,
    private val newList: List<PizzaEntity>
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