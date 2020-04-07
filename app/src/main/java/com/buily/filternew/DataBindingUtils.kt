package com.buily.filternew

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


object DataBindingUtils {


    @JvmStatic
    @BindingAdapter("app:loadImage")
    fun loadImage(image: ImageView, url: String) {
        Glide
            .with(image)
            .load(url)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(image)
    }


}