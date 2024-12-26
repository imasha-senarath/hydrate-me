package com.imasha.hydrateme.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.imasha.hydrateme.R
import javax.inject.Inject

class AppDialog @Inject constructor(val context: Context) {

    private var dialog: AlertDialog? = null

    // Show Loading Dialog
    fun showLoadingDialog(): AlertDialog {
        if (dialog == null) {
            val progressIndicator = CircularProgressIndicator(context).apply {
                isIndeterminate = true
                setIndicatorColor(
                    ContextCompat.getColor(context, R.color.md_theme_primary)
                )
            }

            val builder = AlertDialog.Builder(context)
            builder.setView(progressIndicator)
            builder.setCancelable(false)

            dialog = builder.create()
        }

        dialog?.show()
        return dialog!!
    }

    // Hide Loading Dialog
    fun hideLoadingDialog() {
        dialog?.dismiss()
        dialog = null
    }

    // Error Dialog
    fun showErrorDialog(message: String): AlertDialog {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error")
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        builder.setCancelable(false)

        dialog = builder.create()
        dialog?.show()

        return dialog!!
    }

    // Information Dialog
    fun showInfoDialog(message: String): AlertDialog {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Information")
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        builder.setCancelable(false)

        dialog = builder.create()
        dialog?.show()

        return dialog!!
    }

    // Confirmation Dialog
    fun showConfirmationDialog(message: String, onConfirm: () -> Unit): AlertDialog {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Confirm")
        builder.setMessage(message)
        builder.setPositiveButton("Yes") { dialog, _ ->
            onConfirm()
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
        builder.setCancelable(false)

        dialog = builder.create()
        dialog?.show()

        return dialog!!
    }
}