package com.andres.nyuzuk.presentation.extension

import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import java.text.SimpleDateFormat
import java.util.*

fun View.setVisibility(visibility: Boolean) {
    this.visibility = if (visibility) View.VISIBLE else View.GONE
}

fun View.isVisible() = visibility == View.VISIBLE

fun FragmentManager.doTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun Fragment.getColor(colorResource: Int) = ContextCompat.getColor(context!!, colorResource)

fun String.toDate(dateFormat: String = "yyyy-MM-dd'T'HH:mm:ss'Z'", timeZone: TimeZone = TimeZone.getTimeZone("UTC")): Date {
    val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
    parser.timeZone = timeZone
    return parser.parse(this)
}