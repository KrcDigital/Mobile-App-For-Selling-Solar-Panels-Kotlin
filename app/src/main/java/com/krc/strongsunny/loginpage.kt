package com.krc.strongsunny

import android.R.attr.password
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.krc.strongsunny.databinding.ActivityLoginpageBinding


private  lateinit var binding : ActivityLoginpageBinding
private  lateinit var auth : FirebaseAuth
class loginpage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginpageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = FirebaseAuth.getInstance()

        binding.imageButton.setOnClickListener {

            val intent = Intent(this@loginpage, MainActivity::class.java)

            startActivity(intent)

        }

        binding.button3.setOnClickListener{

                val emailAddr: String = binding.editTextTextEmailAddress.text.toString()
                val pass: String = binding.editTextTextEmailAddresspass.text.toString()




            if (binding.editTextTextEmailAddress.text.isNotEmpty() && binding.editTextTextEmailAddresspass.text.isNotEmpty()){
                // Firebase kütüphanesinden email ve password parametrelerine tanımladığımız edit textleri Stringe çeviriyoruz.
                FirebaseAuth.getInstance().signInWithEmailAndPassword(binding.editTextTextEmailAddress.text.toString(),binding.editTextTextEmailAddresspass.text.toString())
                    .addOnCompleteListener(object:OnCompleteListener<AuthResult>{
                        // Kullanıcı başarılı giriş yaptığında bildirim oluşturuyoruz.
                        override fun onComplete(p0: Task<AuthResult>) {
                            if(p0.isSuccessful){
                                 Toast.makeText(this@loginpage,"Başarılı Giriş: "+FirebaseAuth.getInstance().currentUser?.email, Toast.LENGTH_SHORT).show()
                               // FirebaseAuth.getInstance().signOut()
                                val intent = Intent(this@loginpage, MainActivity::class.java)

                                startActivity(intent)
                            }else{
                                // Kullanıcı hatalı giriş yaptığında bildirim oluşturuyoruz.
                                Toast.makeText(this@loginpage,"Hatalı Giriş: "+p0.exception?.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
            }else{
                // Kullanıcı alanı boş bıraktığında bildirim oluşturuyoruz.
                Toast.makeText(this@loginpage,"Boş alanları doldurunuz", Toast.LENGTH_SHORT).show()
            }





        }




    }



}