package com.krc.strongsunny

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import com.krc.strongsunny.databinding.ActivityMainpagimBinding
import com.krc.strongsunny.databinding.ActivityReffBinding

class reff : AppCompatActivity() {
    private  lateinit var binding : ActivityReffBinding
    private  lateinit var auth : FirebaseAuth

    private lateinit var codem : String

    private var deger : Int = 0
    private var refsayaci : Int = 0

    private lateinit var db : FirebaseFirestore
    private lateinit var newdb : FirebaseFirestore

    private lateinit var postarraylist : ArrayList<reflist>


    private lateinit var name : String
    private lateinit var fiyat : String


    private  lateinit var feedAdapter : reftable

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityReffBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth

        db = Firebase.firestore
        newdb = Firebase.firestore


        getuser()

        postarraylist = ArrayList<reflist>()

        binding.refrec.layoutManager = LinearLayoutManager(this)



        feedAdapter = reftable(postarraylist)
        feedAdapter.setOnClickListener(object : reftable.onItemClickListener{
            override fun onItemClick(position: Int) {






            }


        })

        binding.refrec.adapter = feedAdapter

        binding.imageButton6.setOnClickListener {

            val intent = Intent(this@reff, realmain::class.java)

            startActivity(intent)


        }


        binding.textView33.setOnClickListener {

                val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("text", binding.textView33.text.toString())
                clipboardManager.setPrimaryClip(clipData)
                Toast.makeText(this, "Referans Kopyalandı", Toast.LENGTH_LONG).show()



        }


    }




    private  fun getuser() {
        db.collection("users").whereEqualTo("eposta",auth.currentUser?.email).addSnapshotListener { value, error ->

            if(error != null) {
                Toast.makeText(this,error.localizedMessage, Toast.LENGTH_LONG).show()
            }else {

                if (value != null) {

                    if(!value.isEmpty) {

                        val doc = value.documents
                        for (docc in doc) {
                            refsayaci = refsayaci + 1
                            val code = docc.get("codem") as String
                            binding.textView33.text = code
                            getdata(code)






                        }

                    }

                }

            }

        }

    }













    private  fun getdata(codem : String) {
        var refsayaci = -1
        db.collection("referanslar").whereEqualTo("code",codem).addSnapshotListener { value, error ->

            if(error != null) {
                Toast.makeText(this,error.localizedMessage, Toast.LENGTH_LONG).show()
            }else {

                if (value != null) {

                    if(!value.isEmpty) {

                        val doc = value.documents
                        postarraylist.clear()
                        for (docc in doc) {
                            name = docc.get("kullanici") as String
                            fiyat = docc.get("user_email") as String

                            getuserpak(emaili = docc.get("user_email") as String , name , refsayaci )






                        }

                    }
                    feedAdapter.notifyDataSetChanged()

                }
                feedAdapter.notifyDataSetChanged()

            }

            feedAdapter.notifyDataSetChanged()
        }

    }

    private  fun getuserpak(emaili : String , namesi : String , refsayac : Int) {
    refsayaci +=1
        db.collection("paketlerim").whereEqualTo("sahip",emaili).addSnapshotListener { value, error ->

            if(error != null) {
                Toast.makeText(this,error.localizedMessage, Toast.LENGTH_LONG).show()
            }else {

                if (value != null) {

                    if(!value.isEmpty) {
                        deger = 0
                        val doc = value.documents
                        for (docc in doc) {

                            println("fiyatbu " + (docc.get("fiyat") as String).toInt() )
                            deger += (docc.get("fiyat") as String).toInt()





                        }

                    }

                }

                val post = reflist(namesi,emaili as String," Paket Tutarları : ("+ deger.toString()+"$)")
                postarraylist.add(post)
                feedAdapter.notifyDataSetChanged()
            }


        }



    }





}