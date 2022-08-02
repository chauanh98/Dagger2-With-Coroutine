package com.example.dagger2.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.dagger2.R
import com.squareup.picasso.Picasso

@BindingAdapter("image")
fun loadImage(img: ImageView, imagePath: String?) {
    Picasso.get()
        .load(imagePath)
        .error(R.drawable.ic_launcher_background)
        .placeholder(R.drawable.ic_launcher_foreground)
        .into(img)
}
