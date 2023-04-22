package com.example.comffee.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.comffee.controller.CartControllers
import com.example.comffee.databinding.CartBinding
import com.example.comffee.model.Cart
import com.example.comffee.model.Item
//import com.squareup.picasso.Picasso

class ListItemCartAdapter (private val cart: Cart, val refreshCart: () -> Unit) : RecyclerView.Adapter<ListItemCartAdapter.ListViewHolder>() {
    var context: Context? = null // Context for Toast to work

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = CartBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        context = viewGroup.context
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bindItem(cart.items[position])
    }

    override fun getItemCount(): Int = cart.items.size

    inner class ListViewHolder(private val binding: CartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: Item) {
            with(binding) {
                cartBookName.text = book.title
                Picasso.get().load(book.imagePath).into(cartBookPic)
                val control = CartControllers()

                cartBookCancel.setOnClickListener {
                    val isRemoved = control.removeBookFromCart(book.bookId)

                    if (isRemoved) {
                        Toast.makeText(context, "${book.title} Removed", Toast.LENGTH_SHORT).show()
                        refreshCart()
                    } else {
                        Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}