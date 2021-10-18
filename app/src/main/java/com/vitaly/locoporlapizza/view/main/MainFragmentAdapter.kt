package com.vitaly.locoporlapizza.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vitaly.locoporlapizza.R
import com.vitaly.locoporlapizza.databinding.ItemFragmentMainBinding
import com.vitaly.locoporlapizza.model.PizzaEntity
import com.vitaly.locoporlapizza.utils.loadPicture

class MainFragmentAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<MainFragmentAdapter.MainFragmentViewHolder>() {
    var pizzaList = mutableListOf<PizzaEntity>()


    inner class MainFragmentViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = ItemFragmentMainBinding.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainFragmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_fragment_main, parent, false)
        return MainFragmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainFragmentViewHolder, position: Int) {
        with(holder.binding) {
            ivPizza.loadPicture(pizzaList[position].imageUrl)
            tvPizzaName.text = pizzaList[position].name
            tvPizzaDesc.text = pizzaList[position].description
            tvPrice.text = holder.itemView.context.getString(R.string.price, pizzaList[position].price.toInt())
        }
        holder.itemView.setOnClickListener {
            listener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int = pizzaList.size

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setList(pizzaList: List<PizzaEntity>) {
        val diffCallback = MainFragmentDiffUtil(this.pizzaList, pizzaList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.pizzaList.clear()
        this.pizzaList.addAll(pizzaList)
        diffResult.dispatchUpdatesTo(this)
    }
}

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
        return oldList[oldItemPosition].name == newList[newItemPosition].name &&
                oldList[oldItemPosition].imageUrl == newList[newItemPosition].imageUrl &&
                oldList[oldItemPosition].description == newList[newItemPosition].description &&
                oldList[oldItemPosition].price == newList[newItemPosition].price
    }
}