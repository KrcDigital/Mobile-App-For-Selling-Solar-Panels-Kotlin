package com.krc.strongsunny

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.krc.strongsunny.databinding.ImagecellBinding
import com.krc.strongsunny.databinding.RefcellBinding
import com.squareup.picasso.Picasso

class imgadapter (private  val postList: ArrayList<imglistt>) : RecyclerView.Adapter<imgadapter.PostHolder>() {
    private  lateinit var binding : ImagecellBinding

    private lateinit var mListener : onItemClickListener

    interface  onItemClickListener{
        fun onItemClick(position: Int)

    }

    fun setOnClickListener(listener: onItemClickListener){
        mListener = listener

    }




    class PostHolder(val binding : ImagecellBinding, listener: imgadapter.onItemClickListener) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = ImagecellBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  PostHolder(binding,mListener)


    }

    override fun onBindViewHolder(holder: imgadapter.PostHolder, position: Int) {

        Picasso.get().load(postList.get(position).urls).into(holder.binding.imageView4)

        println("denemee" + postList.get(position).urls )






    }

    override fun getItemCount(): Int {
        return  postList.size
    }

}