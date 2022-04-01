package com.afrodemoz.tonicshop.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.afrodemoz.tonicshop.AllCategoryActivity
import com.afrodemoz.tonicshop.view.CartActivity
import com.afrodemoz.tonicshop.LoginActivity
import com.afrodemoz.tonicshop.adapter.CategoryAdapter
import com.afrodemoz.tonicshop.adapter.OfferAdapter
import com.afrodemoz.tonicshop.adapter.ProductAdapter
import com.afrodemoz.tonicshop.databinding.FragmentHomeBinding
import com.afrodemoz.tonicshop.model.CategoryData
import com.afrodemoz.tonicshop.model.OfferData
import com.afrodemoz.tonicshop.model.ProductData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private var cartCount = 0

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentHomeBinding

    // Creating firebaseAuth object
    lateinit var auth: FirebaseAuth

    lateinit var dref : DatabaseReference

    private lateinit var offerList: ArrayList<OfferData>
    private lateinit var offerAdapter: OfferAdapter

    private lateinit var categoryList: ArrayList<CategoryData>
    private lateinit var categoryAdapter: CategoryAdapter

    private lateinit var productList: ArrayList<ProductData>
    private lateinit var productAdapter: ProductAdapter

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if (currentUser == null) {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        view?.moreCategory?.setEnabled(true)

        val id = auth.currentUser?.uid.toString()
        dref = FirebaseDatabase.getInstance().getReference("tempCart").child(id)

        dref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){

                    cartCount = snapshot.childrenCount.toInt()

                    badge.setNumber(cartCount)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity, error.message, Toast.LENGTH_SHORT).show()
            }

        })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        // Initialize Firebase Auth
        auth = Firebase.auth

        //INITIATING OFFER
        offerList = ArrayList()
        offerAdapter = OfferAdapter(requireActivity(),offerList)

        //INITIATING CATEGORY
        categoryList = ArrayList()
        categoryAdapter = CategoryAdapter(requireActivity(),categoryList)

        //INITIATING PRODUCTS
        productList = ArrayList()
        productAdapter = ProductAdapter(requireActivity(),productList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater)

        binding.onSearch.setOnClickListener(){
            editTextTextPersonName.isVisible = !editTextTextPersonName.isVisible
        }

        binding.moreCategory.setOnClickListener(){
            moreCategory.setEnabled(false)

            val intent = Intent(activity, AllCategoryActivity::class.java)
            startActivity(intent)
        }

        binding.cartBtn.setOnClickListener(){
            val intent = Intent(activity, CartActivity::class.java)
            startActivity(intent)
        }

        binding.offerRecycler.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.offerRecycler.setHasFixedSize(true)
        binding.offerRecycler.adapter = offerAdapter
        getOfferData()

        binding.categoryRecycler.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.categoryRecycler.setHasFixedSize(true)
        binding.categoryRecycler.adapter = categoryAdapter

        getCategoryData()

        binding.prodList.layoutManager = LinearLayoutManager(activity)
        binding.prodList.setHasFixedSize(true)
        binding.prodList.adapter = productAdapter

        getProductData()

        return binding.root
    }

    private fun getOfferData() {
        dref = FirebaseDatabase.getInstance().getReference("offers")

        dref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){

                    for (prodSnapshot in snapshot.children){

                        val offer = prodSnapshot.getValue(OfferData::class.java)
                        offerList.add(offer!!)
                    }

                    offerRecycler.adapter = offerAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity, error.message, Toast.LENGTH_SHORT).show()
            }

        })
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

                    categoryRecycler.adapter = categoryAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun getProductData(){
        dref = FirebaseDatabase.getInstance().getReference("Products")
        dref.orderByChild("prodTime")
        dref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (productSnapshot in snapshot.children){
                        val product = productSnapshot.getValue(ProductData::class.java)
                        productList.add(product!!)
                    }
                    prodList.adapter = productAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}