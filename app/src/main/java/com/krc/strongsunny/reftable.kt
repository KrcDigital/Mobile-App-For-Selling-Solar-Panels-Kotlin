package com.krc.strongsunny

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.krc.strongsunny.databinding.PaketrecBinding
import com.squareup.picasso.Picasso
import com.krc.strongsunny.databinding.RefcellBinding

class reftable (private  val postList: ArrayList<reflist>) : RecyclerView.Adapter<reftable.PostHolder>() {
    private  lateinit var binding : RefcellBinding

    private lateinit var mListener : onItemClickListener

    interface  onItemClickListener{
        fun onItemClick(position: Int)

    }

    fun setOnClickListener(listener: onItemClickListener){
        mListener = listener

    }




    class PostHolder(val binding : RefcellBinding, listener: reftable.onItemClickListener) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = RefcellBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  PostHolder(binding,mListener)


    }

    override fun onBindViewHolder(holder: reftable.PostHolder, position: Int) {

        holder.binding.textView37.text = postList.get(position).uname
        holder.binding.textView38.text = postList.get(position).mail
        holder.binding.textView30.text = postList.get(position).pakettutar






    }

    override fun getItemCount(): Int {
        return  postList.size
    }

}