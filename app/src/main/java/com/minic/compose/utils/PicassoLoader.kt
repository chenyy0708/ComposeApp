package com.minic.compose.utils

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import jp.wasabeef.picasso.transformations.CropCircleTransformation


/**
 * 描述: Picasso,Glide新版AS会报错
 * 作者: ChenYy
 * 日期: 2019/11/19 13:58
 */

object PicassoLoader {

    const val ROUND = 0
    const val NORMAL = 1

    fun loadBitmap(type: Int, url: String): Bitmap? {
        var result: Bitmap? = null
        val picasso = Picasso.get().load(url)
        if (type == ROUND) {
            picasso.transform(CropCircleTransformation())
        }
        picasso.into(object : Target {
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                result = null
            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                result = bitmap
            }

        })
        return result
    }

    /**
     * 加载图片Bitmap
     */
    fun loadBitmap(url: String): Bitmap? {
        return loadBitmap(NORMAL, url)
    }
}