package com.example.leotvshowapp.utils

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.ObjectKey
import com.example.leotvshowapp.R

@SuppressLint("CheckResult")
fun ImageView.loadThumb(url: String?, fallbackRes: Int? = null) {
    val builder: RequestBuilder<Drawable> = Glide.with(this).load(url)
    if (fallbackRes != null) builder.fallback(fallbackRes)
    builder.placeholder(R.drawable.glide_placeholder)
        .error(R.drawable.ic_error_24)
        //.diskCacheStrategy(DiskCacheStrategy.ALL)
        .thumbnail(Glide.with(this).load(url).override(85, 200))
        .into(this)
}

@SuppressLint("CheckResult")
fun ImageView.load(url: String?, fallbackRes: Int? = null) {
    val builder = Glide.with(this).load(url)
    if (fallbackRes != null) builder.fallback(fallbackRes)
    builder.placeholder(R.drawable.glide_placeholder)
        .error(R.drawable.ic_error_24)
        .into(this)
}