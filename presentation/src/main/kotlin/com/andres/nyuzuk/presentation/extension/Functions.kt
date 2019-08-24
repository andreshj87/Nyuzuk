package com.andres.nyuzuk.presentation.extension

import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

fun View.setVisibility(visibility: Boolean) {
    this.visibility = if (visibility) View.VISIBLE else View.GONE
}

fun FragmentManager.doTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}