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
            tvPrice.text = pizzaList[position].price.toInt().toString() + "₽"
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