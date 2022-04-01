package com.afrodemoz.tonicshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.afrodemoz.tonicshop.adapter.CategoryAdapter
import com.afrodemoz.tonicshop.adapter.OfferAdapter
import com.afrodemoz.tonicshop.adapter.ProductAdapter
import com.afrodemoz.tonicshop.model.CategoryData
import com.afrodemoz.tonicshop.model.OfferData
import com.afrodemoz.tonicshop.model.ProductData
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import androidx.fragment.app.Fragment
import com.afrodemoz.tonicshop.fragment.HomeFragment
import com.afrodemoz.tonicshop.fragment.SettingFragment

class MainActivity : AppCompatActivity() {

    // Creating firebaseAuth object
    lateinit var auth: FirebaseAuth

    lateinit var dref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firebase Auth
        auth = Firebase.auth

        val currentUser = auth.currentUser
        val useremail = currentUser?.email

        if (auth.currentUser?.uid != "AELlHseRsvT0omwwsPiEjCUcd612"){
            hideItem()
        }

        val homeFragment = HomeFragment()
        val settingFragment = SettingFragment()

        setCurrentFragment(homeFragment)

        nav_view.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navHome -> setCurrentFragment(homeFragment)
                R.id.navAddProduct -> addIntent()
                R.id.navHistory -> historyIntent()
                R.id.navMessage -> messagesIntent()
                R.id.out -> logoutIntent()
            }
            true
        }

    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply{
        replace(R.id.flFragment,fragment)
        commit()
    }


    private fun hideItem() {
        nav_view.menu.removeItem(R.id.navAddProduct)
    }

    fun homeIntent(){
        val intent = Intent()
        intent.setClass(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun addIntent(){
        val intent = Intent()
        intent.setClass(this, NewProductActivity::class.java)
        startActivity(intent)
    }

    fun historyIntent(){

    }

    fun messagesIntent(){

    }

    fun settingIntent(){
        val intent = Intent()
        intent.setClass(this, SettingActivity::class.java)
        startActivity(intent)
    }

    fun logoutIntent(){
        FirebaseAuth.getInstance().signOut()
        finish()
        startActivity(intent)
    }
}