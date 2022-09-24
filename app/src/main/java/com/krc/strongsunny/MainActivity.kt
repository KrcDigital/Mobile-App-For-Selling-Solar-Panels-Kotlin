package com.krc.strongsunny

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.krc.strongsunny.databinding.ActivityLoginpageBinding
import com.krc.strongsunny.databinding.ActivityMainBinding

private  lateinit var binding : ActivityMainBinding
private  lateinit var auth : FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = FirebaseAuth.getInstance()


        if (auth.currentUser?.email != null) {
            val intent = Intent(this@MainActivity, mainpagim::class.java)

            startActivity(intent)
        }



        binding.button.setOnClickListener{
            val intent = Intent(this@MainActivity, loginpage::class.java)

            startActivity(intent)

        }

        binding.button2.setOnClickListener{
            val intent = Intent(this@MainActivity, signtoc::class.java)

            startActivity(intent)

        }


    }


}