package com.afrodemoz.tonicshop.view

import android.content.Intent
import android.widget.ImageView
import com.afrodemoz.tonicshop.R
import kotlinx.android.synthetic.main.activity_detail.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.afrodemoz.tonicshop.MainActivity
import com.afrodemoz.tonicshop.uitel.getProgressDrawable
import com.afrodemoz.tonicshop.uitel.loadImage

class OfferActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer)

        val back = findViewById<ImageView>(R.id.back1)
        back.setOnClickListener(){
            back.setEnabled(false)

            val intent = Intent()
            intent.setClass(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val offerIntent = intent
//        val podName = offerIntent.getStringExtra("offerName")
//        val podPrice = offerIntent.getStringExtra("offerPrice")
//        val podDetail = offerIntent.getStringExtra("offerDetail")
        val podImage = offerIntent.getStringExtra("offerImage")

//        detailName.text = podName
//        detailPrice.text = podPrice
//        detailDetail.text = podDetail

        detailImage.loadImage(podImage, getProgressDrawable(this))
    }
}