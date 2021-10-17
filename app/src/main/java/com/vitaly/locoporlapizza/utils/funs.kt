package com.vitaly.locoporlapizza.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadPicture(url: String?) {
    if (url != null) {
        Glide.with(this).load(url).into(this)
    }
}