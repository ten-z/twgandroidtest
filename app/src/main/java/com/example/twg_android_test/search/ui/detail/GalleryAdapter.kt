package com.example.twg_android_test.search.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.twg_android_test.R

class GalleryAdapter(private val imageUrls: List<String>) :
    RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_gallery_image, parent, false)
        return GalleryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val url = imageUrls[position]
        Glide.with(holder.itemView.context)
            .load(url)
            .apply(
                RequestOptions().placeholder(R.mipmap.ic_pic_place_holder)
                    .error(R.mipmap.ic_pic_place_holder)
            )
            .into(holder.ivGallery)
    }

    override fun getItemCount(): Int = imageUrls.size

    class GalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivGallery: ImageView = itemView.findViewById(R.id.iv_gallery_image)
    }
}