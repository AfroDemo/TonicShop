package com.afrodemoz.tonicshop.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.afrodemoz.tonicshop.MainActivity
import com.afrodemoz.tonicshop.R
import com.afrodemoz.tonicshop.uitel.getProgressDrawable
import com.afrodemoz.tonicshop.uitel.loadImage
import kotlinx.android.synthetic.main.activity_category.*

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        val back = findViewById<ImageView>(R.id.back2)
        back.setOnClickListener(){
            back.setEnabled(false)

            val intent = Intent()
            intent.setClass(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val categoryIntent = intent
        val podName = categoryIntent.getStringExtra("categoryName")
        val podPrice = categoryIntent.getStringExtra("categoryPrice")
        val podDetail = categoryIntent.getStringExtra("categoryDetail")
        val podLocation = categoryIntent.getStringExtra("categoryLocation")
        val podImage = categoryIntent.getStringExtra("categoryImage")

        categoryName.text = podName
        categoryPrice.text = podPrice
        categoryDetail.text = podDetail

        categoryImage.loadImage(podImage, getProgressDrawable(this))
    }
}