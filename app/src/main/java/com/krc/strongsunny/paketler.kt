package com.krc.strongsunny

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.krc.strongsunny.databinding.ActivityPaketlerBinding
import com.krc.strongsunny.databinding.ActivityRealmainBinding

class paketler : AppCompatActivity() {
    private  lateinit var binding : ActivityPaketlerBinding
    private  lateinit var auth : FirebaseAuth

    private lateinit var db : FirebaseFirestore
    private lateinit var newdb : FirebaseFirestore

    private lateinit var postarraylist : ArrayList<paketlist>


    private  lateinit var feedAdapter : anapktcel

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityPaketlerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth

        db = Firebase.firestore
        newdb = Firebase.firestore
        getdata()

        postarraylist = ArrayList<paketlist>()

        binding.recpak.layoutManager = LinearLayoutManager(this)



        feedAdapter = anapktcel(postarraylist)
        feedAdapter.setOnClickListener(object : anapktcel.onItemClickListener{
            override fun onItemClick(position: Int) {


                val intent = Intent(this@paketler, detays::class.java)
                intent.putExtra("names",postarraylist.get(position).names)
                intent.putExtra("fiyat",postarraylist.get(position).fiyat)
                intent.putExtra("getiri",postarraylist.get(position).getiri)
                intent.putExtra("urls",postarraylist.get(position).urls)
                intent.putExtra("idsi",postarraylist.get(position).idsi)

                startActivity(intent)




            }


        })

        binding.recpak.adapter = feedAdapter
        binding.constraintLayout18.setOnClickListener {

            val intent = Intent(this@paketler, mainpagim::class.java)

            startActivity(intent)

        }

        binding.imageButton3.setOnClickListener {

            val intent = Intent(this@paketler, realmain::class.java)

            startActivity(intent)

        }


    }



    private  fun getdata() {

        db.collection("paketler").orderBy("fiyat").addSnapshotListener { value, error ->

            if(error != null) {
                Toast.makeText(this,error.localizedMessage, Toast.LENGTH_LONG).show()
            }else {

                if (value != null) {

                    if(!value.isEmpty) {

                        val doc = value.documents
                        postarraylist.clear()
                        for (docc in doc) {

                            val name = docc.get("name") as String
                            val fiyat = docc.get("fiyat") as String
                            val imgurl = docc.get("img") as String
                            var idler = docc.id as String
                            var orann = "%"+docc.get("oran") as String +"(" + docc.get("getiri") as String + "$)"
                            val post = paketlist(imgurl,orann,fiyat,name,idler)
                            postarraylist.add(post)






                        }
                        feedAdapter.notifyDataSetChanged()

                    }
                    feedAdapter.notifyDataSetChanged()

                }
                feedAdapter.notifyDataSetChanged()

            }

            feedAdapter.notifyDataSetChanged()
        }

    }

}