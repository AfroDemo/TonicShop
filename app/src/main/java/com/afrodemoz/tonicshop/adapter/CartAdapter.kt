package com.afrodemoz.tonicshop.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.afrodemoz.tonicshop.R
import com.afrodemoz.tonicshop.databinding.CartItemBinding
import com.afrodemoz.tonicshop.model.CartData
import com.afrodemoz.tonicshop.view.DetailActivity

class CartAdapter(
    var c: Context,
    var cartList:ArrayList<CartData>
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    inner class CartViewHolder(var v: CartItemBinding): RecyclerView.ViewHolder(v.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = DataBindingUtil.inflate<CartItemBinding>(
            inflater, R.layout.cart_item, parent, false
        )

        return CartViewHolder(v)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val newList = cartList[position]

        holder.v.isCart = cartList[position]
    }

    override fun getItemCount(): Int {
        return cartList.size
    }
}