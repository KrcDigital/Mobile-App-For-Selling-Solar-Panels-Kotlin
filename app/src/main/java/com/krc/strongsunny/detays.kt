package com.krc.strongsunny

import android.R.attr.label
import android.content.*
import android.content.ContentValues.TAG
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.type.Date
import com.google.type.DateTime
import com.krc.strongsunny.databinding.ActivityDetaysBinding
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class detays : AppCompatActivity() {

    private  lateinit var auth : FirebaseAuth

    private lateinit var codem : String
    private lateinit var bakiye : String
    private lateinit var eposta : String
    private lateinit var telegramnumber : String
    private  var ptutar : Double = 0.0

    private lateinit var postarraylist : ArrayList<adresstring>

    private lateinit var db : FirebaseFirestore
    private lateinit var newdb : FirebaseFirestore
    lateinit var binding : ActivityDetaysBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetaysBinding.inflate(layoutInflater)
        val view = binding.root

        binding.textView17.text = getIntent().getStringExtra("names")
        binding.textView19.text = getIntent().getStringExtra("fiyat") + "$"
        Picasso.get().load(getIntent().getStringExtra("urls")).into(binding.imageView5)
        binding.textView23.text = getIntent().getStringExtra("getiri")
        binding.textView25.text = getIntent().getStringExtra("fiyat") + "$"

        postarraylist = ArrayList<adresstring>()


        setContentView(view)
        auth = Firebase.auth

        db = Firebase.firestore
        newdb = Firebase.firestore
        // binding.geri.setOnClickListener(this)
        binding.imageButton4.setOnClickListener {

            val intent = Intent(this@detays, paketler::class.java)

            startActivity(intent)

        }
        getdatauser()
        getdataadres()
        gettelegram()
        getpakettutar()
        binding.textView21.setOnClickListener {

            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("text", binding.textView20.text.toString())
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(this, "Adres Kopyalandı", Toast.LENGTH_LONG).show()


        }

        binding.constraintLayout6.setOnClickListener {

            val telegram = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/$telegramnumber"))
            telegram.setPackage("org.telegram.messenger")
            startActivity(telegram)

        }

        binding.button8.setOnClickListener {

            if (ptutar + getIntent().getStringExtra("fiyat").toString()!!.toDouble() <= bakiye.toDouble()) {


           // println("bakiye ${bakiye.toInt()}")
           // println("bakiye ${getIntent().getStringExtra("fiyat").toString()!!.toInt()}")

            if (bakiye.toDouble() >= getIntent().getStringExtra("fiyat").toString()!!.toDouble() ) {

            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")


            val data = hashMapOf(
                "date" to sdf.format(Calendar.getInstance().time),
                "durum" to "1",
                "sahip" to eposta,
                "fiyat" to getIntent().getStringExtra("fiyat").toString(),
                "packet_id" to getIntent().getStringExtra("idsi")
            )

            db.collection("paketlerim")
                .add(data)
                .addOnSuccessListener { documentReference ->
                    Log.d(ContentValues.TAG, "DocumentSnapshot written with ID: ${documentReference.id}")





                    val intent = Intent(this@detays, mainpagim::class.java)

                    startActivity(intent)
                }
                .addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error adding document", e)
                }
            }


            else {
                binding.button8.setText("Bakiye Yetersiz")
                binding.button8.setTextColor(Color.RED)
            }

            }
            else {
                binding.button8.setText("Bakiye Yetersiz")
                binding.button8.setTextColor(Color.RED)
            }


        }

    }
    var sayacc = 0
    private  fun getdataadres() {
        sayacc = 0
        db.collection("adresler").addSnapshotListener { value, error ->

            if(error != null) {
                Toast.makeText(this,error.localizedMessage, Toast.LENGTH_LONG).show()
            }else {

                if (value != null) {

                    if(!value.isEmpty) {

                        val doc = value.documents
                        for (docc in doc) {


                            sayacc = sayacc + 1
                            val adresim = docc.get("adres") as String

                            val post = adresstring(adresim)
                            postarraylist.add(post)



                        }

                    }

                }

            }
            val rnds = (0..sayacc-1).random()
            binding.textView20.text = postarraylist.get(rnds).adress.toString()


        }

    }
    private  fun getdatauser() {
        println("hopaldim1")

        db.collection("users").whereEqualTo("eposta" ,auth.currentUser?.email.toString()).addSnapshotListener { value, error ->

            if(error != null) {
                Toast.makeText(this,error.localizedMessage, Toast.LENGTH_LONG).show()
            }else {
                println("hopaldim2")

                if (value != null) {

                    if(!value.isEmpty) {

                        val doc = value.documents
                        for (docc in doc) {


                            bakiye = docc.get("bakiye") as String
                            println("hopaldim${bakiye}")
                            eposta = docc.get("eposta") as String
                            codem = docc.id as String


                        }

                    }

                }

            }

        }

    }


    private  fun getpakettutar() {
    ptutar = 0.0
        db.collection("paketlerim").whereEqualTo("sahip" ,auth.currentUser?.email.toString()).addSnapshotListener { value, error ->

            if(error != null) {
                Toast.makeText(this,error.localizedMessage, Toast.LENGTH_LONG).show()
            }else {
                println("hopaldim2")

                if (value != null) {

                    if(!value.isEmpty) {

                        val doc = value.documents
                        for (docc in doc) {


                            ptutar += (docc.get("fiyat") as String).toDouble()
                            println("pakettutarım" + ptutar)

                        }

                    }

                }

            }

        }

    }

    private  fun gettelegram() {

        db.collection("telegram").addSnapshotListener { value, error ->

            if(error != null) {
                Toast.makeText(this,error.localizedMessage, Toast.LENGTH_LONG).show()
            }else {

                if (value != null) {

                    if(!value.isEmpty) {

                        val doc = value.documents
                        for (docc in doc) {


                            telegramnumber = docc.get("numara") as String


                        }

                    }

                }

            }

        }

    }

}