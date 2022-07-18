package com.example.makevideo.cameraview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.makevideo.R
import com.example.makevideo.cameraview.adapter.viewholder.EmojiVH

class EmojiAdapter : RecyclerView.Adapter<EmojiVH>() {
    var emojiList: List<Int> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmojiVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_emoji, parent, false)
        return EmojiVH(view)
    }

    override fun onBindViewHolder(holder: EmojiVH, position: Int) {
        holder.bind(emojiList[position])
    }

    override fun getItemCount(): Int {
        return emojiList.size
    }
}