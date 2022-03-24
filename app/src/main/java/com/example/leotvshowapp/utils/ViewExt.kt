package com.example.leotvshowapp.utils

import android.view.View

fun View.setVisible(isVisible: Boolean, useInvisible: Boolean = false) {
    visibility = if (isVisible) View.VISIBLE else if (useInvisible) View.INVISIBLE else View.GONE
}