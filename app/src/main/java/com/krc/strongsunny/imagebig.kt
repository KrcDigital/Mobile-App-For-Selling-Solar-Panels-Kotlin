package com.krc.strongsunny

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import com.krc.strongsunny.databinding.ActivityImagebigBinding
import com.krc.strongsunny.databinding.ActivityPaketlerBinding
import com.squareup.picasso.Picasso
import kotlin.math.max
import kotlin.math.min

private  lateinit var binding : ActivityImagebigBinding
private lateinit var scaleGestureDetector: ScaleGestureDetector
private var scaleFactor = 1.0f
class imagebig : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImagebigBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        Picasso.get().load(getIntent().getStringExtra("urls")).into(binding.imageView7)


     binding.imageButton12.setOnClickListener {

         val intent = Intent(this@imagebig, realmain::class.java)

         startActivity(intent)
     }

        binding.imageView7.setOnClickListener {
            ScaleListener()
        }
    }
    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        binding.imageView7.onTouchEvent(motionEvent)
        return true
    }
    private inner class ScaleListener () {
        fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
            scaleFactor *= scaleGestureDetector.scaleFactor
            scaleFactor = max(0.1f, min(scaleFactor, 10.0f))
            binding.imageView7.scaleX = scaleFactor
            binding.imageView7.scaleY = scaleFactor
            return true
        }
    }



}