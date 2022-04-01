package com.afrodemoz.tonicshop.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.afrodemoz.tonicshop.R
import com.afrodemoz.tonicshop.databinding.ProductItemBinding
import com.afrodemoz.tonicshop.model.ProductData
import com.afrodemoz.tonicshop.view.DetailActivity

class ProductAdapter(
    var c: Context,
    var productList:ArrayList<ProductData>
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    inner class ProductViewHolder(var v:ProductItemBinding): RecyclerView.ViewHolder(v.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = DataBindingUtil.inflate<ProductItemBinding>(
            inflater, R.layout.product_item, parent, false
        )

        return ProductViewHolder(v)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val newList = productList[position]

        holder.v.isProduct = productList[position]

        holder.v.root.setOnClickListener{
            val prodName = newList.prodName
            val prodPrice = newList.prodPrice
            val prodDetail = newList.prodDetail
            val prodImage = newList.prodImage

            val detIntent = Intent(c, DetailActivity::class.java)
            detIntent.putExtra("prodName", prodName)
            detIntent.putExtra("prodPrice", prodPrice)
            detIntent.putExtra("prodDetail", prodDetail)
            detIntent.putExtra("prodImage", prodImage)
            c.startActivity(detIntent)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}