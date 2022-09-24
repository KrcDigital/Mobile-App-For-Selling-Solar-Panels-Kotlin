package com.krc.strongsunny

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.krc.strongsunny.databinding.ActivityRealmainBinding
import com.squareup.picasso.Picasso


class realmain : AppCompatActivity() {
    private  lateinit var binding : ActivityRealmainBinding
    private  lateinit var auth : FirebaseAuth

    private lateinit var db : FirebaseFirestore
    private lateinit var newdb : FirebaseFirestore

    private  var img1 : String = ""
    private  var img2 : String = ""
    private  var img3 : String = ""
    private  var img4 : String = ""
    private  var img5 : String = ""
    private  var imgsayac : Int = 0

    private lateinit var postarrayimg : ArrayList<imglistt>

    private lateinit var fiyatim : String
    private lateinit var bakiye : String
    private lateinit var eposta : String

    private  lateinit var feedAdapter : imgadapter

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityRealmainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = Firebase.auth
        postarrayimg = ArrayList<imglistt>()

        db = Firebase.firestore
        newdb = Firebase.firestore



        getdata()

        binding.recana.layoutManager = LinearLayoutManager(this)



        feedAdapter = imgadapter(postarrayimg)
        feedAdapter.setOnClickListener(object : imgadapter.onItemClickListener{
            override fun onItemClick(position: Int) {

                val intent = Intent(this@realmain, imagebig::class.java)

                intent.putExtra("urls",postarrayimg.get(position).urls)


                startActivity(intent)




            }


        })

        binding.recana.adapter = feedAdapter

        binding.constraintLayout12.setOnClickListener {

            val intent = Intent(this@realmain, paketler::class.java)

            startActivity(intent)

        }

        binding.constraintLayout13.setOnClickListener {

            val intent = Intent(this@realmain, mainpagim::class.java)

            startActivity(intent)

        }








    }







    private  fun getdata() {

        db.collection("anasayfaimg").addSnapshotListener { value, error ->

            if(error != null) {
                Toast.makeText(this,error.localizedMessage, Toast.LENGTH_LONG).show()
            }else {

                if (value != null) {

                    if(!value.isEmpty) {

                        val doc = value.documents
                        postarrayimg.clear()
                        for (docc in doc) {


                            val imgurl = docc.get("url") as String

                            val postimg = imglistt(imgurl)
                            postarrayimg.add(postimg)






                        }
                        feedAdapter.notifyDataSetChanged()

                    }

                }

            }

        }

    }






}