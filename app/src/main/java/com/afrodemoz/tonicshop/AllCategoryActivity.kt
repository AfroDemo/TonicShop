package com.afrodemoz.tonicshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import com.afrodemoz.tonicshop.adapter.CategoryAdapter
import com.afrodemoz.tonicshop.model.CategoryData
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_all_category.*

class AllCategoryActivity : AppCompatActivity() {

    lateinit var dref : DatabaseReference

    private lateinit var categoryList: ArrayList<CategoryData>
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_category)

        val back = findViewById<ImageView>(R.id.back3)
        back.setOnClickListener(){
            back.setEnabled(false)

            val intent = Intent()
            intent.setClass(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        //INITIATING CATEGORY
        categoryList = ArrayList()
        categoryAdapter = CategoryAdapter(this,categoryList)

        allCategory.layoutManager = GridLayoutManager(this, 3)
        allCategory.setHasFixedSize(true)
        allCategory.adapter = categoryAdapter

        getCategoryData()
    }

    private fun getCategoryData() {
        dref = FirebaseDatabase.getInstance().getReference("Categories")

        dref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){

                    for (prodSnapshot in snapshot.children){

                        val category = prodSnapshot.getValue(CategoryData::class.java)
                        categoryList.add(category!!)
                    }

                    allCategory.adapter = categoryAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}