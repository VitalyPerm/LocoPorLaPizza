package com.vitaly.presentation.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.vitaly.domain.models.PizzaEntity
import com.vitaly.presentation.R
import com.vitaly.presentation.databinding.ItemFragmentMainBinding
import com.vitaly.presentation.utils.loadPicture

class MainFragmentAdapter(
    private val progressDrawable: CircularProgressDrawable,
    private val onClick: (Int) -> Unit
) :
    RecyclerView.Adapter<MainFragmentAdapter.MainFragmentViewHolder>(), Filterable {
    //Отфильтрованный лист
    private var pizzaList = mutableListOf<PizzaEntity>()

    // Лист пицц c сервера при запуске приложения
    var pizzaStartList = emptyList<PizzaEntity>()


    inner class MainFragmentViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = ItemFragmentMainBinding.bind(item)
    }

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

    fun setList(pizzaList: List<PizzaEntity>) {
        val diffCallback = MainFragmentDiffUtil(this.pizzaList, pizzaList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.pizzaList.clear()
        this.pizzaList.addAll(pizzaList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getFilter(): Filter {
        var pizzaFilterList: MutableList<PizzaEntity>
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val pizzaChar = p0.toString().lowercase()
                pizzaFilterList = if (pizzaChar.isEmpty()) {
                    pizzaStartList.toMutableList()
                } else {
                    val resultList = mutableListOf<PizzaEntity>()
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
                pizzaFilterList = p1?.values as MutableList<PizzaEntity>
                setList(pizzaFilterList)
            }
        }
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
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}