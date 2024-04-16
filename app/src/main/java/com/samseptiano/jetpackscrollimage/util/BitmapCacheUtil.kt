package com.samseptiano.jetpackscrollimage.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.LruCache
import androidx.core.content.res.ResourcesCompat
import com.samseptiano.jetpackscrollimage.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class BitmapCacheUtil {
    private val memoryCache: LruCache<String?, Bitmap>
    init {
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()

        val cacheSize = maxMemory / 8
        memoryCache = object : LruCache<String?, Bitmap>(cacheSize) {
            override fun sizeOf(key: String?, bitmap: Bitmap): Int {
                return bitmap.byteCount / 1024
            }
        }
    }

    private fun addBitmapToMemoryCache(key: String?, bitmap: Bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            memoryCache.put(key, bitmap)
        }
    }

    private fun getBitmapFromMemCache(key: String?): Bitmap? {
        return memoryCache[key]
    }

    suspend fun getLastBitmapCached(ctx:Context, position: Int, imageUrl: String?, listBitmap: ArrayList<Bitmap>): Pair<Int, Bitmap?> {
        val bitmapCache = BitmapCacheUtil()
        val bitmapKey = "BITMAP_KEY"

        return if(listBitmap.isNotEmpty() && listBitmap.size > position) {
            bitmapCache.addBitmapToMemoryCache(bitmapKey, listBitmap[position])

            if(bitmapCache.getBitmapFromMemCache(bitmapKey) == null) {
                convertUrlToBitmap(ctx,position, imageUrl)
            } else {
                Pair(position, bitmapCache.getBitmapFromMemCache(bitmapKey))
            }

        } else {
            convertUrlToBitmap(ctx,position, imageUrl)
        }
    }
    private suspend fun convertUrlToBitmap(ctx: Context, position: Int, imageUrl: String?) : Pair<Int, Bitmap?> {
        var bitmap: Bitmap?
        return withContext(Dispatchers.IO) {
            try {
                val url = URL(imageUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val input: InputStream = connection.inputStream
                bitmap = BitmapFactory.decodeStream(input)

                input.close()
                connection.disconnect()

                return@withContext Pair(position, bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
                return@withContext Pair(position, drawableToBitmap(ResourcesCompat.getDrawable(ctx.resources, R.drawable.ic_broken_image, null)!!))
            }
        }
    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }
}
