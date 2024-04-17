package com.samseptiano.jetpackscrollimage.util

import android.content.Context
import android.widget.Toast

/**
 * Created by samuel.septiano on 17/04/2024.
 */
object CommonUtil {
     fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}