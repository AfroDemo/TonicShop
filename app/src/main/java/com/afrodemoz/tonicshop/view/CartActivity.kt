package com.afrodemoz.tonicshop.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.afrodemoz.tonicshop.MainActivity
import com.afrodemoz.tonicshop.R
import com.afrodemoz.tonicshop.adapter.CartAdapter
import com.afrodemoz.tonicshop.model.CartData
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_cart.*
import kotlin.random.Random

class CartActivity : AppCompatActivity() {
    lateinit var mAdView : AdView

    private var toto = 0
    private val rom = Random.nextInt().toString().replace("-","")

    // Creating firebaseAuth object
    lateinit var auth: FirebaseAuth
    lateinit var dref : DatabaseReference

    private lateinit var cartList: ArrayList<CartData>
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        // Initialize Firebase Auth
        auth = Firebase.auth
        // Initialize Admob
        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        val back = findViewById<ImageView>(R.id.back3)
        back.setOnClickListener(){
            back.setEnabled(false)

            val intent = Intent()
            intent.setClass(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        //INITIATING CATEGORY
        cartList = ArrayList()
        cartAdapter = CartAdapter(this,cartList)

        cartRecycler.layoutManager = LinearLayoutManager(this)
        cartRecycler.setHasFixedSize(true)
        cartRecycler.adapter = cartAdapter

        getCartData()

        clearBtn.setOnClickListener {
            deleteData()
        }
        cartOrder.setOnClickListener {
            submitData()
        }
    }

    private fun submitData() {
        val id = auth.currentUser?.uid.toString()
        dref = FirebaseDatabase.getInstance().getReference("tempCart").child(id)
        dref.removeValue().addOnSuccessListener {

            val intent = Intent()
            intent.setClass(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun deleteData() {
        val id = auth.currentUser?.uid.toString()
        dref = FirebaseDatabase.getInstance().getReference("tempCart").child(id)
        dref.removeValue().addOnSuccessListener {

            val intent = Intent()
            intent.setClass(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getCartData() {
        val id = auth.currentUser?.uid.toString()
        dref = FirebaseDatabase.getInstance().getReference("tempCart").child(id)

        dref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){

                    for (cartSnapshot in snapshot.children){

                        val cart = cartSnapshot.getValue(CartData::class.java)
                        cartList.add(cart!!)

                        val str = cartSnapshot.child("totalPrice").getValue(String::class.java)!!

                        val value = str.toInt()

                        toto += value
                    }
                    orderCost.text = toto.toString()
                    orderSerial.text = rom

                    cartRecycler.adapter = cartAdapter
                }else{
                    conin.isVisible = false
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}