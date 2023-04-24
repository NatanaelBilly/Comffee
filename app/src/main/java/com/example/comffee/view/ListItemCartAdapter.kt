package com.example.comffee.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.comffee.controller.CartControllers
import com.example.comffee.databinding.ItemCartBinding
import com.example.comffee.model.Cart
import com.example.comffee.model.CartItem
import com.example.comffee.model.Item
//import com.squareup.picasso.Picasso

class ListItemCartAdapter (private val listItem: ArrayList<Item>, val refreshCart: () -> Unit) : RecyclerView.Adapter<ListItemCartAdapter.ListViewHolder>() {
    var context: Context? = null // Context for Toast to work

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        context = viewGroup.context
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bindItem(listItem[position])
    }

    override fun getItemCount(): Int = listItem.size

    inner class ListViewHolder(private val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: Item) {
            with(binding) {
                itemNama.text=item.item_name
                itemDescription.text=item.description
                itemPrice.text=item.price.toString()

            }
        }
    }

}