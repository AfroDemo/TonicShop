package com.afrodemoz.tonicshop.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.afrodemoz.tonicshop.MainActivity
import com.afrodemoz.tonicshop.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_buy.*
import kotlin.random.Random

class BuyActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private val rom = Random.nextInt().toString().replace("-","")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy)

        // Initialising auth object
        auth = Firebase.auth

        val back = findViewById<ImageView>(R.id.backOrder)
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
        val podCount = prodIntent.getStringExtra("prodCount")
        val podCost = prodIntent.getStringExtra("prodCost")
        val podLocation = prodIntent.getStringExtra("prodLocation")
        val podSellName = prodIntent.getStringExtra("sellName")
        val podSellPhone = prodIntent.getStringExtra("sellPhone")

        orderName.text = podName
        orderPrice.text = podPrice
        orderCost.text = podCost
        orderQuantity.text = podCount
        sellName.text = podSellName
        sellPhone.text = podSellPhone
        sellArea.text = podLocation
        orderCode.text = rom


        orderBtn.setOnClickListener(){
            orderData()
        }
    }

    private fun orderData() {

        val firebaseUser = auth.currentUser
        val userEmail = firebaseUser!!.email
        val userId = firebaseUser!!.uid
        val currentTimestamp = System.currentTimeMillis()


        val prodIntent = intent
        val podName = prodIntent.getStringExtra("prodName")
        val podPrice = prodIntent.getStringExtra("prodPrice")
        val podCount = prodIntent.getStringExtra("prodCount")
        val podCost = prodIntent.getStringExtra("prodCost")
        val podSellName = prodIntent.getStringExtra("sellName")
        val podSellPhone = prodIntent.getStringExtra("sellPhone")

        // 👇 create a map of value
        val order = mapOf(
            "orderId" to rom,
            "oderTime" to currentTimestamp,
            "prodName" to podName,
            "prodPrice" to podPrice,
            "prodCount" to podCount,
            "totalPrice" to podCost,
            "sellName" to podSellName,
            "sellPhone" to podSellPhone,
            "userEmail" to userEmail,
            "userId" to userId
        )

        // 👇 write to database
        val database = FirebaseDatabase.getInstance().reference
        database.child("Order").child(userId).child(rom).setValue(order)

        Toast.makeText(this, "Successfully Ordered", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}