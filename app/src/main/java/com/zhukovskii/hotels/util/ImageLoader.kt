package com.zhukovskii.hotels.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.zhukovskii.hotels.R
import com.zhukovskii.hotels.config.Config
import com.zhukovskii.hotels.transformation.BorderCropTransformation

class ImageLoader {

    companion object {
        fun load(
            context: Context,
            imageURLSuffix: String?,
            view: ImageView,
            cropPixels: Int = 0
        ) {
            Glide.with(context)
                .load(Config.IMAGE_URL_PREFIX + (imageURLSuffix ?: ""))
                .transform(BorderCropTransformation(cropPixels))
                .transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(R.drawable.resort)
                .into(view)
        }
    }
}