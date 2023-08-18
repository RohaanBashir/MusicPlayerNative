package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.TimeUnit

fun convertDuration(duration: Long): String? {
    var out: String? = null
    val hours: Long
    try {
        hours = duration / 3600000
    } catch (e: Exception) {
        e.printStackTrace()
        return out
    }
    val remainingMinutes = (duration - hours * 3600000) / 60000
    var minutes = remainingMinutes.toString()
    if (minutes == "0") {
        minutes = "00"
    }
    val remainingSeconds = duration - hours * 3600000 - remainingMinutes * 60000
    var seconds = remainingSeconds.toString()
    if (seconds.length < 2) {
        seconds = "00"
    } else {
        seconds = seconds.substring(0, 2)
    }

    out = if (hours > 0) {
        "$hours:$minutes:$seconds"
    } else {
        "$minutes:$seconds"
    }

    return out
}

class MusicRecyclerViewAdapterAdapter(var Song: MutableList<SongInformation>) : RecyclerView.Adapter<MusicRecyclerViewAdapterAdapter.MyViewHolder>() {

    var OnItemClick: ((SongInformation) -> Unit) ?= null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        var inflater = LayoutInflater.from(parent.context)
        var view = inflater.inflate(R.layout.customlayout,parent,false)
        return MyViewHolder(view)

    }

    override fun getItemCount(): Int {
        return Song.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.songName.text = Song[position].displayName
        holder.duration.text = convertDuration(Song[position].duration)
        holder.albumName.text = Song[position].album


        holder.itemView.setOnClickListener {
            OnItemClick?.invoke(Song[position])

        }

    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var songName = itemView.findViewById<TextView>(R.id.nameSong)
        var duration = itemView.findViewById<TextView>(R.id.duration)
        var imageView = itemView.findViewById<ImageView>(R.id.LayoutIV)
        var albumName = itemView.findViewById<TextView>(R.id.album)


    }

}

