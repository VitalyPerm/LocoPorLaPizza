package com.vitaly.locoporlapizza.presentation.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vitaly.locoporlapizza.R
import com.vitaly.locoporlapizza.data.db.PizzaEntity
import com.vitaly.locoporlapizza.databinding.ItemCartBinding
import com.vitaly.locoporlapizza.utils.editPizzaQuantity

class CartFragmentAdapter(private val editPizzaQuantityListener: (PizzaEntity) -> Unit) :
    RecyclerView.Adapter<CartFragmentAdapter.CartFragmentViewHolder>() {
    var pizzaList = mutableListOf<PizzaEntity>()

    inner class CartFragmentViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = ItemCartBinding.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartFragmentViewHolder {
        return CartFragmentViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CartFragmentViewHolder, position: Int) {
        with(holder.binding) {
            Glide.with(ivPizza).load(pizzaList[position].imageUrls[0]).into(ivPizza)
            tvPizzaName.text = pizzaList[position].name
            tvPrice.text =
                holder.itemView.context.getString(R.string.price, pizzaList[position].price.toInt())
            tvQuantity.text = pizzaList[position].quantity.toString()
            btnIncrement.setOnClickListener {
                editPizzaQuantityListener.invoke(
                    editPizzaQuantity(
                        pizzaList[holder.adapterPosition],
                        true
                    )
                )
            }
            btnDecrement.setOnClickListener {
                editPizzaQuantityListener.invoke(
                    editPizzaQuantity(
                        pizzaList[holder.adapterPosition],
                        false
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int = pizzaList.size

    fun setList(pizzaList: List<PizzaEntity>) {
        val diffCallback = CartFragmentDiffUtil(this.pizzaList, pizzaList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.pizzaList.clear()
        this.pizzaList.addAll(pizzaList)
        diffResult.dispatchUpdatesTo(this)
    }
}

class CartFragmentDiffUtil(
    private val oldList: List<PizzaEntity>,
    private val newList: List<PizzaEntity>
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