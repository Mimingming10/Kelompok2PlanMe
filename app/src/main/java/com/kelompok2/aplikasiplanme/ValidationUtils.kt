package com.kelompok2.aplikasiplanme

import android.text.TextUtils
import android.util.Patterns
import org.w3c.dom.Text

object ValidationUtils {

    fun isTextNotEmpty(text: String?): Boolean{
        return !TextUtils.isEmpty(text)
    }
    fun isValidEmail(text:String): Boolean{
        return if (TextUtils.isEmpty(text)) false
        else Patterns.EMAIL_ADDRESS.matcher(text).matches()
    }
}