package com.andres.nyuzuk.presentation.extension

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

fun View.setVisibility(visibility: Boolean) {
    this.visibility = if (visibility) View.VISIBLE else View.GONE
}

fun FragmentManager.doTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun Activity.hideKeyboard() {
    currentFocus?.let {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

fun Fragment.getColor(colorResource: Int) = ContextCompat.getColor(context!!, colorResource)