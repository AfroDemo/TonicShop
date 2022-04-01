package com.afrodemoz.tonicshop.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.afrodemoz.tonicshop.R
import com.afrodemoz.tonicshop.databinding.OfferRowItemsBinding
import com.afrodemoz.tonicshop.model.OfferData
import com.afrodemoz.tonicshop.view.OfferActivity

class OfferAdapter(
    var c: Context,
    var offerList:ArrayList<OfferData>
) : RecyclerView.Adapter<OfferAdapter.OfferViewHolder>() {
    inner class OfferViewHolder(var v: OfferRowItemsBinding): RecyclerView.ViewHolder(v.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferAdapter.OfferViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = DataBindingUtil.inflate<OfferRowItemsBinding>(
            inflater, R.layout.offer_row_items, parent, false
        )

        return OfferViewHolder(v)
    }

    override fun onBindViewHolder(holder: OfferAdapter.OfferViewHolder, position: Int) {
        val newList = offerList[position]

        holder.v.isOffer = offerList[position]

        holder.v.root.setOnClickListener{
//            val offerName = newList.offerName
//            val offerPrice = newList.offerPrice
//            val offerDetail = newList.offerDetail
//            val offerLocation = newList.offerLocation
            val offerImage = newList.offerImage

            val detIntent = Intent(c, OfferActivity::class.java)
//            detIntent.putExtra("offerName", offerName)
//            detIntent.putExtra("offerPrice", offerPrice)
//            detIntent.putExtra("offerLocation", offerLocation)
//            detIntent.putExtra("offerDetail", offerDetail)
            detIntent.putExtra("offerImage", offerImage)
            c.startActivity(detIntent)
        }
    }

    override fun getItemCount(): Int {
        return offerList.size
    }
}