package com.example.comffee.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.comffee.R
import com.example.comffee.databinding.FragmentCartBinding
import com.example.comffee.model.Cart
import com.example.comffee.controllers.*
import java.text.DecimalFormat

class CartFragment : Fragment() , View.OnClickListener {
    private lateinit var binding: FragmentCartBinding
    private lateinit var cart: Cart

    // Button
    lateinit var cartTableCancel: ImageView
    lateinit var cartOrder: Button
    lateinit var cartPay: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onClick(p0: View?) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val control = CartControllers()
        cart = control.getCartData()

        showCartMenus()
        setTotal(control.totalHarga(), view)

        cartTableCancel = view.findViewById(R.id.cartTableCancel)
        cartTableCancel.setOnClickListener {
            control.removeTableFromCart()
            Toast.makeText(
                requireContext(),
                "Table ${cart.table.tableName} removed",
                Toast.LENGTH_SHORT
            ).show()
            refreshCart()
        }

        cartOrder = view.findViewById(R.id.cartOrder)
        cartOrder.setOnClickListener {
            showOrderConfirmationDialog()
            refreshCart()
        }

        cartPay = view.findViewById(R.id.cartPay)
        cartPay.setOnClickListener {
            if (ActiveUser.getActiveTransaction() == null) {
                cartPay.isEnabled = false
                cartPay.isClickable = false
            } else {
                val intent = Intent(context, BillActivity::class.java)
                intent.putExtra("transaction_id", ActiveUser.getActiveTransaction()?.transactionId)
                startActivity(intent)
            }
        }
    }

    fun showCartMenus() {
        binding.rvCartMenu.layoutManager = LinearLayoutManager(requireContext())
        val listMenuCartAdapter = ListMenuCartAdapter(cart) { refreshCart() }
        binding.rvCartMenu.adapter = listMenuCartAdapter
    }

    fun setTotal(total: Int, view: View) {
        var cartTotal: TextView = view.findViewById(R.id.cartTotal)
        var totalFormat = DecimalFormat("#,###").format(total)
        cartTotal.text = "Rp$totalFormat"
    }

    fun refreshCart() {
        // Refresh For Fragment
        parentFragmentManager.beginTransaction().detach(this).commit()
        parentFragmentManager.beginTransaction().attach(this).commit()

    }

    private fun showOrderConfirmationDialog() {
        val positiveButtonClick = { _: DialogInterface, _: Int ->
            val control = OrderControllers()
            val isOrdered = control.order()

            if (isOrdered) {
                Toast.makeText(requireContext(), "Order success!", Toast.LENGTH_SHORT).show()
                refreshCart()
            } else {
                Toast.makeText(requireContext(), "Order error!", Toast.LENGTH_SHORT).show()
            }
        }

        val negativeButtonClick = { _: DialogInterface, _: Int ->

        }

        val addToCartDialog = AlertDialog.Builder(requireContext())
        addToCartDialog.setTitle("Order")
            .setIcon(android.R.drawable.ic_dialog_info)
            .setMessage("Are you sure you want to order?")
            .setPositiveButton(
                "Yes",
                DialogInterface.OnClickListener(function = positiveButtonClick)
            )
            .setNegativeButton(
                "No",
                DialogInterface.OnClickListener(function = negativeButtonClick)
            )
        addToCartDialog.show()
    }
}