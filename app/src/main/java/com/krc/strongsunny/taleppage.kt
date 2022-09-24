package com.krc.strongsunny

import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.krc.strongsunny.databinding.ActivityReffBinding
import com.krc.strongsunny.databinding.ActivityTaleppageBinding
import kotlin.properties.Delegates

class taleppage : AppCompatActivity() {
    private  lateinit var binding : ActivityTaleppageBinding
    private  lateinit var auth : FirebaseAuth

    private lateinit var codem : String
    private lateinit var cuzdan : String
    private lateinit var emailim : String
    private lateinit var ne : String

    private var krc : Double = 0.0

    private lateinit var db : FirebaseFirestore
    private lateinit var newdb : FirebaseFirestore
    private  var adresadet : Int = 0

    private  var bakim : String = "0"
    private  lateinit var useridsi : String

    private lateinit var postarraylist : ArrayList<taleplist>
    private lateinit var postarrayadres : ArrayList<adresstring>


    private  lateinit var feedAdapter : taleptable

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityTaleppageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth

        ne = "0"
        db = Firebase.firestore
        newdb = Firebase.firestore
        postarrayadres = ArrayList<adresstring>()
        adresleim()
        getuser("cekmetalep","Çekme")

        binding.button5.setOnClickListener {
            ne = "0"

            getuser("cekmetalep","Çekme")
            binding.textView43.setText("Çek")
            binding.button5.setTextColor(Color.BLACK)
            binding.button6.setTextColor(Color.WHITE)
            binding.textView52.text = "Bakiye"
            binding.textView40.setTextSize(TypedValue.COMPLEX_UNIT_SP, 34F);


        }
        binding.button6.setOnClickListener {
            binding.textView43.setText("Yatır")
            binding.textView52.text = "Cüzdan Adresi(USDT TRC20)"
            ne = "1"

            val rnds = (0..adresadet-1).random()

            binding.textView40.text = postarrayadres.get(rnds).adress.toString()

            binding.button5.setTextColor(Color.WHITE)
            binding.button6.setTextColor(Color.BLACK)
            getuser("yatirmatalep","Yatırma")
            binding.textView40.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14F);

        }
        binding.imageButton7.setOnClickListener {

            val intent = Intent(this@taleppage, mainpagim::class.java)

            startActivity(intent)

        }


        postarraylist = ArrayList<taleplist>()

        binding.textView40.text = bakim.toString() + "$"



        binding.recview.layoutManager = LinearLayoutManager(this)

        binding.textView40.setOnClickListener {

            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("text", binding.textView40.text.toString())
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(this, "Adres Kopyalandı", Toast.LENGTH_LONG).show()


        }

        feedAdapter = taleptable(postarraylist)
        feedAdapter.setOnClickListener(object : taleptable.onItemClickListener{
            override fun onItemClick(position: Int) {






            }


        })
        binding.recview.adapter = feedAdapter

        binding.constraintLayout21.setOnClickListener {

            if (ne == "0") {

            if (binding.editTextNumber.text.toString().toInt() >= 10 && binding.editTextNumber.text.toString().toInt()<= bakim.toInt()  ) {

              krc = bakim.toDouble() - binding.editTextNumber.text.toString().toDouble()
                val data = hashMapOf(
                    "cüzdan" to cuzdan as String,
                    "durum" to "Beklemede",
                    "sahip" to emailim,
                    "tutar" to binding.editTextNumber.text.toString()
                )

                db.collection("cekmetalep")
                    .add(data)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                        println("yenibakiye " + krc)

                        val washingtonRef = db.collection("users").document(useridsi)

                        washingtonRef
                            .update("bakiye", krc.toString())
                            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
                            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }






                        val intent = Intent(this@taleppage, mainpagim::class.java)

                        startActivity(intent)
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }



            }

            else {
                binding.textView44.setTextColor(Color.RED)
            }

            }

            if (ne == "1") {

                val data = hashMapOf(
                    "cüzdan" to cuzdan as String,
                    "durum" to "Beklemede",
                    "sahip" to emailim,
                    "tutar" to binding.editTextNumber.text.toString()
                )

                db.collection("yatirmatalep")
                    .add(data)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                        val intent = Intent(this@taleppage, mainpagim::class.java)

                        startActivity(intent)
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }

            }



        }



    }




    private  fun getuser(tur : String, cins: String) {
        var refsayaci = 0
        db.collection("users").whereEqualTo("eposta",auth.currentUser?.email).addSnapshotListener { value, error ->

            if(error != null) {
                Toast.makeText(this,error.localizedMessage, Toast.LENGTH_LONG).show()
            }else {

                if (value != null) {

                    if(!value.isEmpty) {

                        val doc = value.documents
                        postarraylist.clear()
                        for (docc in doc) {
                            val code = docc.id as String
                            useridsi = docc.id as String
                            cuzdan = docc.get("cuzdan") as String
                            bakim = docc.get("bakiye") as String
                            emailim = docc.get("eposta") as String
                            getdata(auth.currentUser?.email.toString(), tur , cins)
                            if (ne == "0") {
                                binding.textView40.text = bakim.toString() + "$"
                            }






                        }

                    }

                }

            }

        }

    }


    private  fun adresleim() {
        adresadet = 0
        postarrayadres.clear()
        db.collection("adresler").addSnapshotListener { value, error ->

            if(error != null) {
                Toast.makeText(this,error.localizedMessage, Toast.LENGTH_LONG).show()
            }else {

                if (value != null) {

                    if(!value.isEmpty) {

                        val doc = value.documents
                        for (docc in doc) {

                            adresadet += 1
                            val adres = docc.get("adres") as String
                            val post = adresstring(adres)
                            postarrayadres.add(post)





                        }

                    }

                }



            }


        }

    }










    private  fun getdata(codem : String, ne : String , tur : String) {
        var refsayaci = 0
        db.collection(ne).whereEqualTo("sahip",codem).addSnapshotListener { value, error ->

            if(error != null) {
                Toast.makeText(this,error.localizedMessage, Toast.LENGTH_LONG).show()
            }else {

                if (value != null) {

                    if(!value.isEmpty) {

                        val doc = value.documents
                        postarraylist.clear()
                        for (docc in doc) {
                            refsayaci = refsayaci + 1
                            val name = docc.get("tutar") as String
                            val fiyat = docc.get("durum") as String

                            val post = taleplist("$name $ $tur Talebim",fiyat)
                            postarraylist.add(post)


                            println("deneme")



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