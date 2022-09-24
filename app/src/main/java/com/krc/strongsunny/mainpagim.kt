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
import com.krc.strongsunny.databinding.ActivityMainpagimBinding
import com.krc.strongsunny.databinding.ActivityPaketlerBinding

class mainpagim : AppCompatActivity() {
    private  lateinit var binding : ActivityMainpagimBinding
    private  lateinit var auth : FirebaseAuth

    private lateinit var codem : String

    private lateinit var db : FirebaseFirestore
    private lateinit var newdb : FirebaseFirestore

    private lateinit var postarraylist : ArrayList<paketlist>

    private lateinit var gelir : String
    private var totalpak : Int = 0

    private var netge : Double = 0.0
    private var bekleme : Double = 0.0

    private  lateinit var feedAdapter : anapktcel

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityMainpagimBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



        auth = Firebase.auth

        db = Firebase.firestore
        newdb = Firebase.firestore
        getdata()
        getuser()
        getbekleme()
        postarraylist = ArrayList<paketlist>()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)



        feedAdapter = anapktcel(postarraylist)
        feedAdapter.setOnClickListener(object : anapktcel.onItemClickListener{
            override fun onItemClick(position: Int) {






            }


        })

        binding.recyclerView.adapter = feedAdapter
        binding.imageButton3.setOnClickListener {

            val intent = Intent(this@mainpagim, realmain::class.java)

            startActivity(intent)

        }

        binding.imageButton2.setOnClickListener {

            Firebase.auth.signOut()
            val intent = Intent(this@mainpagim, MainActivity::class.java)

            startActivity(intent)

        }

        binding.pakets.setOnClickListener {

            val intent = Intent(this@mainpagim, paketler::class.java)

            startActivity(intent)

        }
        binding.button56.setOnClickListener {

            val intent = Intent(this@mainpagim, reff::class.java)

            startActivity(intent)

        }


        binding.adpackma.setOnClickListener {

            val intent = Intent(this@mainpagim, paketler::class.java)

            startActivity(intent)

        }

        binding.button4.setOnClickListener {

            val intent = Intent(this@mainpagim, taleppage::class.java)

            startActivity(intent)

        }


    }



    private  fun getdata() {
        var getsayi = 0
        totalpak = 0
        db.collection("paketlerim").whereEqualTo("sahip",auth.currentUser?.email).whereEqualTo("durum" , "1").addSnapshotListener { value, error ->

            if(error != null) {
                Toast.makeText(this,error.localizedMessage, Toast.LENGTH_LONG).show()
            }else {

                if (value != null) {

                    if(!value.isEmpty) {

                        val doc = value.documents
                        for (docc in doc) {
                            getsayi = getsayi + 1

                            binding.textView8.text = "Paket Sayım:$getsayi"
                            binding.textView12.text = "Panellerim($getsayi)"

                            totalpak += (docc.get("fiyat") as String).toInt()
                            binding.textView51.text = "Yatırımda(" + totalpak +"$)"

                            getrealpack(idsi = docc.get("packet_id") as String)







                        }

                    }

                }

            }

        }

    }



    private  fun getrealpack(idsi : String) {
        var getsayi = 0
        postarraylist.clear()
        db.collection("paketler").document(idsi).addSnapshotListener { value, error ->

            if(error != null) {
                Toast.makeText(this,error.localizedMessage, Toast.LENGTH_LONG).show()
            }else {

                if (value != null) {



                            val name = value.get("name") as String
                            val fiyat = value.get("fiyat") as String
                            val imgurl = value.get("img") as String
                            netge += (value.get("getiri") as String).toDouble()
                    binding.textView10.text = "Günlük Getiri :  +" + netge + "$"
                            var orann = "%"+value.get("oran") as String +"(" + value.get("getiri") as String + "$)"
                              val idsi = value.id
                            val post = paketlist(imgurl,orann,fiyat,name,idsi)
                            postarraylist.add(post)

                        }


                feedAdapter.notifyDataSetChanged()

                }
                feedAdapter.notifyDataSetChanged()

            }
        feedAdapter.notifyDataSetChanged()

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

                            binding.textView6.text = docc.get("username") as String
                            binding.textView9.text = docc.get("bakiye") as String + "$"
                            codem = docc.get("codem") as String
                            getref(codem)

                        }

                    }

                }

            }


        }

    }


    private  fun getref(code : String) {
    var sayac = 0
        db.collection("referanslar").whereEqualTo("code",code).addSnapshotListener { value, error ->

            if(error != null) {
                Toast.makeText(this,error.localizedMessage, Toast.LENGTH_LONG).show()
            }else {

                if (value != null) {

                    if(!value.isEmpty) {

                        val doc = value.documents
                        for (docc in doc) {
                            sayac = sayac + 1
                            binding.textView7.text = "Alt Üyelerim:$sayac"

                        }

                    }

                }

            }

        }

    }


    private  fun getbekleme() {
        bekleme = 0.0
        db.collection("yatirmatalep").whereEqualTo("sahip",auth.currentUser?.email).addSnapshotListener { value, error ->

            if(error != null) {
                Toast.makeText(this,error.localizedMessage, Toast.LENGTH_LONG).show()
            }else {

                if (value != null) {

                    if(!value.isEmpty) {

                        val doc = value.documents
                        for (docc in doc) {

                            bekleme += (docc.get("tutar") as String).toDouble()

                        }

                    }

                }

            }


        }

    }

}