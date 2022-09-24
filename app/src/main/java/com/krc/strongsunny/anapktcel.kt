package com.krc.strongsunny

import android.view.LayoutInflater
import android.view.View.inflate
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso



import com.krc.strongsunny.realmain
import com.krc.strongsunny.anapktcel
import com.krc.strongsunny.databinding.ActivityDetaysBinding.inflate
import com.krc.strongsunny.databinding.PaketrecBinding


class anapktcel (private  val postList: ArrayList<paketlist>) : RecyclerView.Adapter<anapktcel.PostHolder>() {
    private  lateinit var binding : PaketrecBinding

    private lateinit var mListener : onItemClickListener

    interface  onItemClickListener{
        fun onItemClick(position: Int)

    }

    fun setOnClickListener(listener: onItemClickListener){
        mListener = listener

    }




    class PostHolder(val binding : PaketrecBinding, listener: anapktcel.onItemClickListener) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = PaketrecBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  PostHolder(binding,mListener)


    }

    override fun onBindViewHolder(holder: anapktcel.PostHolder, position: Int) {
        holder.binding.textView17.text = postList.get(position).names
        holder.binding.textView19.text = postList.get(position).fiyat + "$"
        holder.binding.textView23.text = postList.get(position).getiri

        Picasso.get().load(postList.get(position).urls).into(holder.binding.imageView5)




    }

    override fun getItemCount(): Int {
        return  postList.size
    }

}