package com.krc.strongsunny

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.krc.strongsunny.databinding.ActivityTaleppageBinding
import com.krc.strongsunny.databinding.TalepcellBinding

class taleptable (private  val postList: ArrayList<taleplist>) : RecyclerView.Adapter<taleptable.PostHolder>() {
    private  lateinit var binding : TalepcellBinding

    private lateinit var mListener : onItemClickListener

    interface  onItemClickListener{
        fun onItemClick(position: Int)

    }

    fun setOnClickListener(listener: onItemClickListener){
        mListener = listener

    }




    class PostHolder(val binding : TalepcellBinding, listener: taleptable.onItemClickListener) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = TalepcellBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  PostHolder(binding,mListener)


    }

    override fun onBindViewHolder(holder: taleptable.PostHolder, position: Int) {

        holder.binding.textView46.text = postList.get(position).mail.toString()

        holder.binding.textView45.text = postList.get(position).uname.toString()





    }

    override fun getItemCount(): Int {
        return  postList.size
    }

}