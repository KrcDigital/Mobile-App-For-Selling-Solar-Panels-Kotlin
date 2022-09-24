package com.krc.strongsunny

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.collection.LLRBNode
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.krc.strongsunny.databinding.ActivityMainBinding
import com.krc.strongsunny.databinding.ActivitySigntocBinding

private  lateinit var binding : ActivitySigntocBinding
private  lateinit var auth : FirebaseAuth
private lateinit var db : FirebaseFirestore

class signtoc : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigntocBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = Firebase.auth

        db = Firebase.firestore
        binding.imageButton8.setOnClickListener {

            val intent = Intent(this@signtoc, MainActivity::class.java)

            startActivity(intent)
        }

        binding.button7.setOnClickListener {

            if (binding.editTextTextPersonNamea.text.isEmpty()) {
                binding.textView49a.setTextColor(Color.RED)
            }

            else {

            auth.createUserWithEmailAndPassword(binding.editTextTextPersonNamec.text.toString(), binding.editTextTextPersonNamed.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "Kayıt Başarılı")
                        gekayit()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "${task.exception.toString()}", task.exception)
                        Toast.makeText(baseContext, "${task.exception?.message}",
                            Toast.LENGTH_SHORT).show()
                 }
                }


            }








        }
    }

    private  fun gekayit() {
        val data = hashMapOf(
            "admin" to 0,
            "bakiye" to "0",
            "codem" to (100000000..999999999).random().toString(),
            "cuzdan" to binding.editTextTextPersonNamea.text.toString(),
            "eposta" to binding.editTextTextPersonNamec.text.toString(),
            "referans" to binding.editTextTextPersonNameb.text.toString(),
            "sifre" to binding.editTextTextPersonNamed.text.toString(),

            "username" to binding.editTextTextPersonName.text.toString()
        )

        db.collection("users")
            .add(data)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot written with ID: ${documentReference.id}")

                refkayit()

                val intent = Intent(this@signtoc, loginpage::class.java)

                startActivity(intent)
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }


    private  fun refkayit() {
        val data = hashMapOf(

            "user_email" to binding.editTextTextPersonNamec.text.toString(),
            "code" to binding.editTextTextPersonNameb.text.toString(),
            "kullanici" to binding.editTextTextPersonName.text.toString()
        )

        db.collection("referanslar")
            .add(data)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                val intent = Intent(this@signtoc, loginpage::class.java)

                startActivity(intent)
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }

}