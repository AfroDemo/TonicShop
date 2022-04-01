package com.afrodemoz.tonicshop.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.afrodemoz.tonicshop.MainActivity
import com.afrodemoz.tonicshop.R
import com.afrodemoz.tonicshop.uitel.getProgressDrawable
import com.afrodemoz.tonicshop.uitel.loadImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_detail.*
import java.util.*

class DetailActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    var quant = 0
    var totalPric = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Initialising auth object
        auth = Firebase.auth
        val back = findViewById<ImageView>(R.id.back0)
        back.setOnClickListener(){
            back.setEnabled(false)

            val intent = Intent()
            intent.setClass(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val prodIntent = intent
        val podName = prodIntent.getStringExtra("prodName")
        val podPrice = prodIntent.getStringExtra("prodPrice")
        val podDetail = prodIntent.getStringExtra("prodDetail")
        val podImage = prodIntent.getStringExtra("prodImage")

        detailName.text = podName
        detailPrice.text = podPrice
        detailDetail.text = podDetail

        detailImage.loadImage(podImage, getProgressDrawable(this))

        val buyBtn = findViewById<Button>(R.id.buyBtn)
        buyBtn.setOnClickListener(){
            if (quant > 0) {
                buyData()
            }
        }

        val cartBtn = findViewById<Button>(R.id.addCatBtn)
        cartBtn.setOnClickListener(){
            if (quant > 0) {
                cartData()
            }
        }

        val incrBtn = findViewById<Button>(R.id.incrBtn)
        val decrBtn = findViewById<Button>(R.id.decrBtn)

        incrBtn.setOnClickListener(){
            quant += 1

            if(quant<1){
                quant = 1
                quantityVal.text = quant.toString()
            }else {
                quantityVal.text = quant.toString()

                val totalPrice = findViewById<TextView>(R.id.totalPrice)
                val cost = podPrice?.toBigDecimal()
                val totalCost = quant.toBigDecimal() * cost!!
                totalPric = totalCost.toInt()

                totalPrice.visibility = View.VISIBLE
                totalPrice.text = totalPric.toString()
            }
        }

        decrBtn.setOnClickListener(){
            quant -= 1

            if(quant<1){
                quant = 1
                quantityVal.text = quant.toString()
            }else {
                quantityVal.text = quant.toString()

                val totalPrice = findViewById<TextView>(R.id.totalPrice)
                val cost = podPrice?.toBigDecimal()
                val totalCost = quant.toBigDecimal() * cost!!
                totalPric = totalCost.toInt()

                totalPrice.visibility = View.VISIBLE
                totalPrice.text = totalPric.toString()
            }
        }


    }

    private fun buyData() {
        val totalPrice = totalPric.toString()
        val totalCount = quant.toString()

        val prodIntent = intent
        val podName = prodIntent.getStringExtra("prodName")
        val podPrice = prodIntent.getStringExtra("prodPrice")
        val podDetail = prodIntent.getStringExtra("prodDetail")
        val podTime = prodIntent.getStringExtra("prodTime")

        val buyIntent = Intent(this, BuyActivity::class.java)
        buyIntent.putExtra("prodName", podName)
        buyIntent.putExtra("prodPrice", podPrice)
        buyIntent.putExtra("prodCount", totalCount)
        buyIntent.putExtra("prodCost", totalPrice)
        buyIntent.putExtra("prodTime", podTime)
        buyIntent.putExtra("prodDetail", podDetail)
        startActivity(buyIntent)
    }

    private fun cartData() {

        val firebaseUser = auth.currentUser
        val userEmail = firebaseUser!!.email
        val userId = firebaseUser!!.uid
        val totalPrice = totalPric.toString()
        val prodCount = quant.toString()
        var cartId = UUID.randomUUID().toString().replace("-", "").toUpperCase()
        val currentTimestamp = System.currentTimeMillis().toString()

        val prodIntent = intent
        val podName = prodIntent.getStringExtra("prodName")
        val podPrice = prodIntent.getStringExtra("prodPrice")
        val podImage = prodIntent.getStringExtra("prodImage")

        // 👇 create a map of value
        val product = mapOf(
            "cartId" to cartId,
            "cartTime" to currentTimestamp,
            "prodName" to podName,
            "prodPrice" to podPrice,
            "prodCount" to prodCount,
            "totalPrice" to totalPrice,
            "prodImage" to podImage,
            "userEmail" to userEmail,
            "userId" to userId
        )
        // 👇 write to database
        val database = FirebaseDatabase.getInstance().getReference()
        database.child("tempCart").child(userId).child(cartId).setValue(product)

        Toast.makeText(this, "Successfully Added", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}