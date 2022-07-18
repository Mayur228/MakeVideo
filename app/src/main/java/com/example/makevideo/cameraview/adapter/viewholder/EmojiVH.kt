package com.example.makevideo.cameraview.adapter.viewholder

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.makevideo.R

class EmojiVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(emoji: Int) {
        itemView.findViewById<ImageView>(R.id.emojiIV).setImageResource(emoji)
    }
}