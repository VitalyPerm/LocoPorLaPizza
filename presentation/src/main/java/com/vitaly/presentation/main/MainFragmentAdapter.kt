package com.vitaly.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.vitaly.domain.models.Pizza
import com.vitaly.presentation.R
import com.vitaly.presentation.utils.loadPicture

class MainFragmentAdapter(
    private val progressDrawable: CircularProgressDrawable,
    private val onClick: (Int) -> Unit
) :
    RecyclerView.Adapter<MainFragmentViewHolder>(), Filterable {
    //Отфильтрованный лист
    private var pizzaList = mutableListOf<Pizza>()

    // Лист пицц c сервера при запуске приложения
    var pizzaStartList = emptyList<Pizza>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainFragmentViewHolder {
        return MainFragmentViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_fragment_main, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainFragmentViewHolder, position: Int) {
        with(holder.binding) {
            ivPizza.loadPicture(pizzaList[position].imageUrls[0], progressDrawable)
            tvPizzaName.text = pizzaList[position].name
            tvPizzaDesc.text = pizzaList[position].description
            tvPrice.text =
                holder.itemView.context.getString(R.string.price, pizzaList[position].price.toInt())
        }
        holder.itemView.setOnClickListener {
            onClick.invoke(pizzaStartList[position].id)
        }
    }

    override fun getItemCount(): Int = pizzaList.size

    fun setList(pizzaList: List<Pizza>) {
        val diffCallback = MainFragmentDiffUtil(this.pizzaList, pizzaList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.pizzaList.clear()
        this.pizzaList.addAll(pizzaList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getFilter(): Filter {
        var pizzaFilterList: MutableList<Pizza>
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val pizzaChar = p0.toString().lowercase()
                pizzaFilterList = if (pizzaChar.isEmpty()) {
                    pizzaStartList.toMutableList()
                } else {
                    val resultList = mutableListOf<Pizza>()
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
                pizzaFilterList = p1?.values as MutableList<Pizza>
                setList(pizzaFilterList)
            }
        }
    }
}