package com.afrodemoz.tonicshop

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_new_product.*
import java.text.SimpleDateFormat
import java.util.*

class NewProductActivity : AppCompatActivity() {

    private lateinit var spinner: Spinner
    val selectedType = ""

    lateinit var pickImage: ImageView

    private val pickedImage = 100
    private var imageUri : Uri? = null

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_product)

        // Initialising auth object
        auth = Firebase.auth

        pickImage = findViewById<ImageView>(R.id.addChoose)
        pickImage.setOnClickListener(){
            val picked = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(picked, pickedImage)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == pickedImage){
            imageUri = data?.data
            pickImage.setImageURI(imageUri)

            processData(imageUri)

        }
    }

    private fun processData(imageUri: Uri?) {

        val uploadButton = findViewById<Button>(R.id.uploadProduct)
        uploadButton.setOnClickListener(){
            if (imageUri != null){
                val fileName = UUID.randomUUID().toString().replace("-", "") + "${imageUri!!.lastPathSegment}"

                val reStorage = FirebaseStorage.getInstance().reference.child("Product/$fileName")

                val uploadTask = reStorage.putFile(imageUri!!)

                val urlTask = uploadTask.continueWithTask { task ->
                    if (!task.isSuccessful){
                        task.exception?.let {
                            throw it
                        }
                    }
                    reStorage.downloadUrl
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        val downloadUri = task.result

                        val prodName = productName.text.toString()
                        val prodDetail = productDetail.text.toString()
                        val prodPrice = productPrice.text.toString()

                        // check pass
                        if (prodName.isBlank() || prodPrice.isBlank()) {
                            Toast.makeText(this, "Product name and Product price can't be blank", Toast.LENGTH_SHORT).show()
                        }

                        var clock = 0
                        var prodId = UUID.randomUUID().toString().replace("-", "").toUpperCase()
                        var downloadUrl = downloadUri.toString()
//                        val prodTime = System.currentTimeMillis()
//                        clock = prodTime.toInt()

                        val date = Date()
                        val newDate = Date(date.time + 604800000L * 2 + 24 * 60 * 60)
                        val dt = SimpleDateFormat("yyyy-MM-dd")
                        val stringdate: String = dt.format(newDate).replace("-","")
                        clock = stringdate.toInt()



                        // 👇 create a map of value
                        val product = mapOf(
                            "prodName" to prodName,
                            "prodPrice" to prodPrice,
                            "prodDetail" to prodDetail,
                            "prodTime" to clock,
                            "prodImage" to downloadUrl,
                        )
                        // 👇 write to database
                        val database = FirebaseDatabase.getInstance().getReference()
                        database.child("Products").child(prodId).setValue(product)

                        Toast.makeText(this, "Successfully Uploaded", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        //failed
                    }
                }

            }else{

                val prodName = productName.text.toString()
                val prodDetail = productDetail.text.toString()
                val prodPrice = productPrice.text.toString()
                val prodTime = System.currentTimeMillis().toString()

                // check pass
                if (prodName.isBlank() || prodPrice.isBlank()) {
                    Toast.makeText(this, "Product name, Product price can't be blank", Toast.LENGTH_SHORT).show()
                }

                var prodId = UUID.randomUUID().toString().replace("-", "").toUpperCase()



                // 👇 create a map of value
                val product = mapOf(
                    "prodName" to prodName,
                    "prodPrice" to prodPrice,
                    "prodDetail" to prodDetail,
                    "prodTime" to prodTime,
                    "prodImage" to "https://firebasestorage.googleapis.com/v0/b/x-shop-b5385.appspot.com/o/local%2Fdrive-1150982_640.png?alt=media&token=fe57d836-2e5a-4ea8-ab0a-4414873b3c6d",
                )
                // 👇 write to database
                val database = FirebaseDatabase.getInstance().getReference()
                database.child("Products").child(prodId).setValue(product)

                Toast.makeText(this, "Successfully Uploaded", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
}