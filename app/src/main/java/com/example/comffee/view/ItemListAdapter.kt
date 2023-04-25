package com.example.comffee.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.comffee.controller.ItemListController
import com.example.comffee.databinding.ItemListBinding
import com.example.comffee.databinding.ItemListLayoutBinding
import com.example.comffee.model.Item
import com.squareup.picasso.Picasso
import java.text.DecimalFormat

class ItemListAdapter(private val itemList: ArrayList<Item>) : RecyclerView.Adapter<ItemListAdapter.ListViewHolder>() {
    private val formatter = DecimalFormat("#,###")
    var context: Context? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemListLayoutBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        context = viewGroup.context
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    inner class ListViewHolder(private val binding: ItemListLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            with(binding) {
                itemName.text = item.item_name
                itemPrice.text = "Rp"+formatter.format(item.price)
                itemDescription.text = item.description
                Picasso.get().load(item.photo).into(imgItem)
                imgAddToShoppingCart.setOnClickListener() {
                    ItemListController().tambahItemKeCart(item.item_id)
                }
            }
        }
    }
}