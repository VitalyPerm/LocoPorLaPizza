package com.vitaly.presentation.cart

import androidx.recyclerview.widget.DiffUtil
import com.vitaly.data.db.PizzaEntity
import com.vitaly.domain.models.Pizza


class CartFragmentDiffUtil(
    private val oldList: List<Pizza>,
    private val newList: List<Pizza>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].name == newList[newItemPosition].name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}