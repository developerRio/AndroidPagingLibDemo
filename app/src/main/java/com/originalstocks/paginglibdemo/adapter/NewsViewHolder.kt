package com.originalstocks.paginglibdemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.originalstocks.paginglibdemo.data.PhotosData
import com.originalstocks.paginglibdemo.databinding.ItemNewsBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_news.view.*

class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(photosData: PhotosData?) {
        if (photosData != null) {
            itemView.txt_news_name.text = photosData.title
            if (!photosData.image.isNullOrEmpty())
                Picasso.get().load(photosData.image).into(itemView.img_news_banner)
        }
    }

    companion object {
        fun create(parent: ViewGroup): NewsViewHolder {
            val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context))
            //val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
            return NewsViewHolder(binding.root)
        }
    }

}