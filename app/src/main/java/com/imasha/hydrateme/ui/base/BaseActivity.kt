package com.imasha.hydrateme.ui.base

import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.imasha.hydrateme.R
import com.imasha.hydrateme.data.enums.Gender
import com.imasha.hydrateme.data.model.Record
import com.imasha.hydrateme.data.model.User
import com.imasha.hydrateme.databinding.DialogLoadingBinding
import com.imasha.hydrateme.databinding.ToolbarLayoutBinding
import com.imasha.hydrateme.utils.Calculations
import com.imasha.hydrateme.utils.DateUtils.DD_MM_YYYY
import com.imasha.hydrateme.utils.DateUtils.HH_MM
import com.imasha.hydrateme.utils.LanguageUtils
import com.imasha.hydrateme.utils.LanguageUtils.applyLanguage
import com.imasha.hydrateme.utils.LanguageUtils.getCurrentLanguage
import java.text.SimpleDateFormat
import java.util.*

open class BaseActivity : AppCompatActivity() {

    private lateinit var progressDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        applyLanguage(getCurrentLanguage(), this)
    }

    protected fun setUpToolbar(
        toolbar: ToolbarLayoutBinding,
        title: Int,
        isBackButtonEnabled: Boolean
    ) {
        setSupportActionBar(toolbar.appbar)

        supportActionBar?.apply {
            this.title = getString(title)

            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)

            setDisplayShowTitleEnabled(false)

            toolbar.title.text = getString(title)

            if (isBackButtonEnabled) {
                setHomeAsUpIndicator(R.drawable.ic_back)
            } else {
                toolbar.btnNotification.visibility = View.VISIBLE
                setHomeAsUpIndicator(R.drawable.ic_menu)
            }
        }

        if (isBackButtonEnabled) {
            toolbar.appbar.setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    fun showLoading(): AlertDialog {
        val binding = DialogLoadingBinding.inflate(LayoutInflater.from(this))

        progressDialog = AlertDialog.Builder(this)
            .setView(binding.root)
            .setCancelable(false)
            .create()

        progressDialog.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        progressDialog.show()
        return progressDialog
    }

    fun hideLoading() {
        if (::progressDialog.isInitialized) {
            progressDialog.dismiss()
        }
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun calculateWaterIntake(user: User): Int {
        val weight = user.weight
        val gender = user.gender
        val goal = user.goal

        return if(goal < 1) {
            Calculations.getWaterIntake(weight, gender == Gender.MALE, 0.0)
        } else {
            user.goal
        }
    }

    fun sortRecords(records: List<Record>): List<Record> {
        val dateFormatter = SimpleDateFormat(DD_MM_YYYY, Locale.getDefault())
        val timeFormatter = SimpleDateFormat(HH_MM, Locale.getDefault())

        return records.sortedWith(
            compareByDescending<Record> { dateFormatter.parse(it.date) }
                .thenByDescending { timeFormatter.parse(it.time) }
        )
    }

    fun getAppVersion(): String {
        return try {
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            packageInfo.versionName ?: "Unknown"
        } catch (e: PackageManager.NameNotFoundException) {
            "Unknown"
        }
    }
}