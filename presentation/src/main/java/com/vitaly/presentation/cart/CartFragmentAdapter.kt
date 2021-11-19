package com.vitaly.presentation.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vitaly.domain.models.PizzaEntity
import com.vitaly.presentation.R
import com.vitaly.presentation.utils.editPizzaQuantity

class CartFragmentAdapter(private val editPizzaQuantityListener: (PizzaEntity) -> Unit) :
    RecyclerView.Adapter<CartFragmentViewHolder>() {
    private var pizzaList = mutableListOf<PizzaEntity>()

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