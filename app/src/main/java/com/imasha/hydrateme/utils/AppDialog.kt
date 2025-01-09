package com.imasha.hydrateme.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.imasha.hydrateme.R
import com.imasha.hydrateme.databinding.DialogSelectThemeBinding
import com.imasha.hydrateme.utils.AppConstants.DARK_THEME_MODE
import com.imasha.hydrateme.utils.AppConstants.DEFAULT_THEME_MODE
import com.imasha.hydrateme.utils.AppConstants.LIGHT_THEME_MODE
import com.imasha.hydrateme.utils.AppConstants.THEME_MODE
import com.imasha.hydrateme.utils.SharedPrefManager.saveInt
import com.imasha.hydrateme.utils.SharedPrefManager.saveString
import com.imasha.hydrateme.utils.ThemeUtils.applyTheme

object AppDialog {

    private var dialog: AlertDialog? = null

    // Error Dialog
    fun showErrorDialog(message: String, context: Context): AlertDialog {
        val builder = AlertDialog.Builder(context)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .setCancelable(false)

        dialog = builder.create()
        dialog?.show()

        return dialog!!
    }

    // Information Dialog
    fun showInfoDialog(
        title: String,
        message: String,
        context: Context
    ): AlertDialog {
        val builder = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .setCancelable(false)

        dialog = builder.create()
        dialog?.show()

        return dialog!!
    }

    // Confirmation Dialog
    fun showConfirmationDialog(
        title: String,
        message: String,
        context: Context,
        onConfirm: () -> Unit
    ): AlertDialog {
        val builder = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Yes") { dialog, _ ->
                onConfirm()
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            .setCancelable(false)

        dialog = builder.create()
        dialog?.show()

        return dialog!!
    }

    // Theme Dialog
    fun showThemeSelectionDialog(
        context: Context
    ): AlertDialog {
        val binding = DialogSelectThemeBinding.inflate(LayoutInflater.from(context))

        val dialog = AlertDialog.Builder(context)
            .setView(binding.root)
            .create()

        binding.lightTheme.setOnClickListener {
            applyTheme(LIGHT_THEME_MODE, context)
            saveInt(THEME_MODE, LIGHT_THEME_MODE)
            dialog.dismiss()
        }

        binding.darkTheme.setOnClickListener {
            applyTheme(DARK_THEME_MODE, context)
            saveInt(THEME_MODE, DARK_THEME_MODE)
            dialog.dismiss()
        }

        binding.systemTheme.setOnClickListener {
            applyTheme(DEFAULT_THEME_MODE, context)
            saveInt(THEME_MODE, DEFAULT_THEME_MODE)
            dialog.dismiss()
        }

        dialog.show()
        return dialog
    }

    fun isValidContext(context: Context): Boolean {
        return context !is Activity || context.isFinishing || context.isDestroyed
    }
}