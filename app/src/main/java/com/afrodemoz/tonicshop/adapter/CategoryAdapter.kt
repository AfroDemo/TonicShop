package com.afrodemoz.tonicshop.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.afrodemoz.tonicshop.R
import com.afrodemoz.tonicshop.databinding.CategoryRowItemsBinding
import com.afrodemoz.tonicshop.model.CategoryData
import com.afrodemoz.tonicshop.view.CategoryActivity

class CategoryAdapter(
    var c: Context,
    var categoryList:ArrayList<CategoryData>
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>()
{
    inner class CategoryViewHolder(var v:CategoryRowItemsBinding): RecyclerView.ViewHolder(v.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = DataBindingUtil.inflate<CategoryRowItemsBinding>(
            inflater, R.layout.category_row_items, parent, false
        )

        return CategoryViewHolder(v)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val newList = categoryList[position]

        holder.v.isCategory = categoryList[position]

        holder.v.root.setOnClickListener{
            val categoryName = newList.categoryName
            val categoryPrice = newList.categoryPrice
            val categoryDetail = newList.categoryDetail
            val categoryLocation = newList.categoryLocation
            val categoryImage = newList.categoryImage

            val detIntent = Intent(c, CategoryActivity::class.java)
            detIntent.putExtra("categoryName", categoryName)
            detIntent.putExtra("categoryPrice", categoryPrice)
            detIntent.putExtra("categoryLocation", categoryLocation)
            detIntent.putExtra("categoryDetail", categoryDetail)
            detIntent.putExtra("categoryImage", categoryImage)
            c.startActivity(detIntent)
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}