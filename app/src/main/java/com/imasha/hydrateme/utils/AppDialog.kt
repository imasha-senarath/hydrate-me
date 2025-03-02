package com.imasha.hydrateme.utils

import android.app.Activity
import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.imasha.hydrateme.R
import com.imasha.hydrateme.databinding.DialogGoalUpdateBinding
import com.imasha.hydrateme.databinding.DialogSuccessBinding
import com.imasha.hydrateme.databinding.DialogUpdateBinding
import com.imasha.hydrateme.utils.AppConstants.NAME_DIALOG
import com.imasha.hydrateme.utils.AppConstants.WEIGHT_DIALOG
import com.imasha.hydrateme.utils.Validations.isAnyNotEmpty

object AppDialog {

    private var dialog: AlertDialog? = null

    // Error Dialog
    fun showErrorDialog(message: String, context: Context): AlertDialog {
        val builder = AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.error_title))
            .setMessage(message)
            .setPositiveButton(context.getString(R.string.ok_btn)) { dialog, _ -> dialog.dismiss() }
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
            .setPositiveButton(context.getString(R.string.ok_btn)) { dialog, _ -> dialog.dismiss() }
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
            .setPositiveButton(context.getString(R.string.yes_btn)) { dialog, _ ->
                onConfirm()
                dialog.dismiss()
            }
            .setNegativeButton(context.getString(R.string.no_btn)) { dialog, _ -> dialog.dismiss() }
            .setCancelable(false)

        dialog = builder.create()
        dialog?.show()

        return dialog!!
    }

    // Selection Dialog
    fun <T : Enum<T>> showSelectionDialog(
        context: Context,
        currentValue: T,
        enumClass: Class<T>,
        onValueSelected: (selectedValue: T) -> Unit
    ): AlertDialog {
        val enumValues = enumClass.enumConstants
        val enumNames = enumValues?.map { it.toString() }?.toTypedArray() ?: arrayOf()
        val currentIndex = enumValues?.indexOf(currentValue) ?: 0

        val builder = AlertDialog.Builder(context)
            .setSingleChoiceItems(enumNames, currentIndex) { dialog, which ->
                val selectedEnum = enumValues?.get(which)
                if (currentValue != selectedEnum) {
                    selectedEnum?.let { onValueSelected(it) }
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
        binding.editTextValue.hint = context.getString(R.string.enter_value, title)


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
                Toast.makeText(context, context.getString(R.string.field_cant_empty_msg), Toast.LENGTH_SHORT).show()
            } else {
                onUpdate(newValue)
                dialog.dismiss()
            }
        }

        binding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
        return dialog
    }

    fun showGoalUpdateDialog(
        currentValue: (Any),
        context: Context,
        onUpdate: (Int) -> Unit
    ): AlertDialog {
        val binding = DialogGoalUpdateBinding.inflate(LayoutInflater.from(context))

        val dialog = AlertDialog.Builder(context)
            .setView(binding.root)
            .create()

        binding.editTextValue.setText(currentValue.toString())

        binding.btnUpdate.setOnClickListener {
            val newValue =  binding.editTextValue.text.toString()
            if(newValue.isEmpty()) {
                Toast.makeText(context, context.getString(R.string.field_cant_empty_msg), Toast.LENGTH_SHORT).show()
            } else {
                onUpdate(newValue.toInt())
                dialog.dismiss()
            }
        }

        binding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
        return dialog
    }

    fun showSuccessDialog(
        context: Context,
        message: String,
        onConfirm: () -> Unit
    ): AlertDialog {
        val binding = DialogSuccessBinding.inflate(LayoutInflater.from(context))

        val dialog = AlertDialog.Builder(context)
            .setView(binding.root)
            .create()

        binding.tvMsg.text = message

        binding.btnOk.setOnClickListener {
            dialog.dismiss()
            onConfirm()
        }

        dialog.show()
        return dialog
    }

    fun isValidContext(context: Context): Boolean {
        return context !is Activity || context.isFinishing || context.isDestroyed
    }
}