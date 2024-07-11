package com.zhukovskii.hotels.transformation

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.util.Util
import java.nio.charset.Charset
import java.security.MessageDigest

/**
 * Glide transformation for cropping image borders
 */
class BorderCropTransformation(private val cropPixels: Int) : BitmapTransformation() {
    companion object {
        private const val ID = "com.example.app.RemoveBorderTransformation"
        private val ID_BYTES = ID.toByteArray(Charset.forName("UTF-8"))
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }

    override fun equals(other: Any?): Boolean {
        return other is BorderCropTransformation
    }

    override fun hashCode(): Int {
        return Util.hashCode(ID.hashCode())
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val width = toTransform.width
        val height = toTransform.height

        val bitmap = pool.get(
            width - 2 * cropPixels,
            height - 2 * cropPixels,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        val paint = Paint()
        canvas.drawBitmap(toTransform, -cropPixels.toFloat(), -cropPixels.toFloat(), paint)

        return bitmap
    }
}