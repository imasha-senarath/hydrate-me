package com.imasha.hydrateme.utils

import android.app.Activity
import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.imasha.hydrateme.R
import com.imasha.hydrateme.data.enums.Gender
import com.imasha.hydrateme.data.enums.getName
import com.imasha.hydrateme.databinding.DialogSelectThemeBinding
import com.imasha.hydrateme.databinding.DialogUpdateBinding
import com.imasha.hydrateme.utils.AppConstants.DARK_THEME_MODE
import com.imasha.hydrateme.utils.AppConstants.DEFAULT_THEME_MODE
import com.imasha.hydrateme.utils.AppConstants.LIGHT_THEME_MODE
import com.imasha.hydrateme.utils.AppConstants.NAME_DIALOG
import com.imasha.hydrateme.utils.AppConstants.THEME_MODE
import com.imasha.hydrateme.utils.AppConstants.WEIGHT_DIALOG
import com.imasha.hydrateme.utils.SharedPrefManager.saveInt
import com.imasha.hydrateme.utils.ThemeUtils.applyTheme
import com.imasha.hydrateme.utils.Validations.isAnyNotEmpty

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

    // Theme Dialog
 /*   fun showThemeDialog(
        context: Context,
    ): AlertDialog {

        val currentTheme =
        val themeOptions = Gender.values().map { it.getName() }.toTypedArray()
        val currentIndex = Gender.values().indexOf(currentTheme)

        val builder = AlertDialog.Builder(context)
            .setSingleChoiceItems(themeOptions, currentIndex) { dialog, which ->
                //onGenderSelected(Gender.values()[which])
                dialog.dismiss()
            }
            .setCancelable(true)

        val dialog = builder.create()
        dialog.show()

        return dialog
    }*/

    // Gender Dialog
    fun showGenderSelectionDialog(
        context: Context,
        currentGender: Gender,
        onGenderSelected: (selectedGender: Gender) -> Unit
    ): AlertDialog {
        val genderOptions = Gender.values().map { it.getName() }.toTypedArray()
        val currentIndex = Gender.values().indexOf(currentGender)

        val builder = AlertDialog.Builder(context)
            .setSingleChoiceItems(genderOptions, currentIndex) { dialog, which ->
                if(currentGender != Gender.values()[which]) {
                    onGenderSelected(Gender.values()[which])
                }
                dialog.dismiss()
            }
            .setCancelable(true)

        val dialog = builder.create()
        dialog.show()

        return dialog
    }

    fun showUpdateDialog(
        title: String,
        currentValue: (Any),
        context: Context,
        onUpdate: (Any) -> Unit
    ): AlertDialog {
        val binding = DialogUpdateBinding.inflate(LayoutInflater.from(context))

        val dialog = AlertDialog.Builder(context)
            .setView(binding.root)
            .create()

        binding.tvTitle.text = context.getString(R.string.update_title, title)
        binding.editTextValue.hint = "Enter your $title"


        if(currentValue.isAnyNotEmpty()) {
            binding.editTextValue.setText(currentValue.toString())
        }

        when (title) {
            NAME_DIALOG -> {
                binding.editTextValue.inputType = InputType.TYPE_CLASS_TEXT
            }
            WEIGHT_DIALOG -> {
                binding.editTextValue.inputType = InputType.TYPE_CLASS_NUMBER
            }
        }

        binding.btnUpdate.setOnClickListener {
            val newValue =  binding.editTextValue.text.toString()
            if(newValue.isEmpty()) {
                Toast.makeText(context, "Field can't be empty", Toast.LENGTH_SHORT).show()
            } else {
                if(currentValue != newValue) {
                    onUpdate(newValue)
                }
                dialog.dismiss()
            }
        }

        binding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
        return dialog
    }

    fun isValidContext(context: Context): Boolean {
        return context !is Activity || context.isFinishing || context.isDestroyed
    }
}