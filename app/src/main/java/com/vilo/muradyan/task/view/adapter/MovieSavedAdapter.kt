package com.vilo.muradyan.task.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vilo.muradyan.task.R
import com.vilo.muradyan.task.data.model.Results
import com.bumptech.glide.Glide

class MovieSavedAdapter(results: List<Results>, private val callback: Callback) : RecyclerView.Adapter<MovieSavedAdapter.ViewHolder>() {


    private var mList = ArrayList<Results>()

    init {
        this.mList = results as java.util.ArrayList<Results>
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v  = LayoutInflater.from(viewGroup.context).
            inflate(R.layout.movie_save_item,viewGroup,false)
        return ViewHolder(v)
    }


    fun addMovie(results: List<Results>) {
        val initPosition = mList.size
        mList = results as ArrayList<Results>
        notifyItemRangeInserted(initPosition, mList.size)
    }
    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val imageUrl = "https://image.tmdb.org/t/p/original"

        Glide.with(holder.itemView.context).load(imageUrl + mList[position].poster_path).centerCrop().into(holder.movieImage)
        holder.pillarName.text = mList[position].title

        holder.itemView.setOnClickListener {
            callback.onItemClick(mList[position], holder.movieImage,holder.pillarName)
        }
        holder.pillarName.text = mList[position].title
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pillarName: TextView = itemView.findViewById(R.id.pillarName) as TextView
        var movieImage: ImageView = itemView.findViewById(R.id.Image) as ImageView

    }


    interface Callback{
        fun onItemClick(item: Results, imageView: ImageView, textView: TextView)
    }
}