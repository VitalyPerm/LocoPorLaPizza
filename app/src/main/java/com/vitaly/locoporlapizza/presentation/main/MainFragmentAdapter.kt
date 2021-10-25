package com.vitaly.locoporlapizza.presentation.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vitaly.locoporlapizza.R
import com.vitaly.locoporlapizza.databinding.ItemFragmentMainBinding
import com.vitaly.locoporlapizza.domain.PizzaResponse
import com.vitaly.locoporlapizza.utils.loadPicture

class MainFragmentAdapter(private val onClick: (Int) -> Unit) : RecyclerView.Adapter<MainFragmentAdapter.MainFragmentViewHolder>(), Filterable {
    //Отфильтрованный лист
    var pizzaList = mutableListOf<PizzaResponse>()
    // Лист пицц c сервера при запуске приложения
    var pizzaStartList = emptyList<PizzaResponse>()



    inner class MainFragmentViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = ItemFragmentMainBinding.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainFragmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_fragment_main, parent, false)
        return MainFragmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainFragmentViewHolder, position: Int) {
        with(holder.binding) {
            ivPizza.loadPicture(pizzaList[position].imageUrls[0])
            tvPizzaName.text = pizzaList[position].name
            tvPizzaDesc.text = pizzaList[position].description
            tvPrice.text = holder.itemView.context.getString(R.string.price, pizzaList[position].price.toInt())
        }
        holder.itemView.setOnClickListener {
            onClick.invoke(pizzaStartList[position].id)
        }
    }

    override fun getItemCount(): Int = pizzaList.size

    fun setList(pizzaList: List<PizzaResponse>) {
        val diffCallback = MainFragmentDiffUtil(this.pizzaList, pizzaList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.pizzaList.clear()
        this.pizzaList.addAll(pizzaList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getFilter(): Filter {
        var pizzaFilterList: MutableList<PizzaResponse>
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val pizzaChar = p0.toString().lowercase()
                pizzaFilterList = if (pizzaChar.isEmpty()) {
                    pizzaStartList.toMutableList()
                } else {
                    val resultList = mutableListOf<PizzaResponse>()
                    pizzaStartList.forEach {
                        if (it.name.lowercase().contains(pizzaChar)) resultList.add(it)
                    }
                    resultList
                }
                val filterResult = FilterResults()
                filterResult.values = pizzaFilterList
                return filterResult
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                pizzaFilterList = p1?.values as MutableList<PizzaResponse>
                setList(pizzaFilterList)
            }
        }
    }
}

class MainFragmentDiffUtil(
    private val oldList: List<PizzaResponse>,
    private val newList: List<PizzaResponse>
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