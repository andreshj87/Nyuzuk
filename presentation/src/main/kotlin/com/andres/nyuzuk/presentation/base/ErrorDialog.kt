package com.andres.nyuzuk.presentation.base

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.andres.nyuzuk.R

class ErrorDialog {
    fun show(context: Context, errorUi: ErrorUi, block: () -> Any) = AlertDialog.Builder(context)
        .setTitle(errorUi.title)
        .setMessage(errorUi.message)
        .setPositiveButton(context.getString(R.string.button_positive)) { _, _ -> Unit }
        .setOnDismissListener { block() }
        .create()
        .show()
}