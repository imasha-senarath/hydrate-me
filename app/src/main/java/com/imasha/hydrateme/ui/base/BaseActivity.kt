package com.imasha.hydrateme.ui.base

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.imasha.hydrateme.R
import com.imasha.hydrateme.data.enums.Gender
import com.imasha.hydrateme.data.model.Record
import com.imasha.hydrateme.data.model.User
import com.imasha.hydrateme.databinding.ToolbarLayoutBinding
import com.imasha.hydrateme.utils.Calculations
import com.imasha.hydrateme.utils.DateUtils.DD_MM_YYYY
import com.imasha.hydrateme.utils.DateUtils.HH_MM_AA
import java.text.SimpleDateFormat
import java.util.*

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    protected fun setUpToolbar(toolbar: ToolbarLayoutBinding, title: Int, isBackButtonEnabled: Boolean) {
        setSupportActionBar(toolbar.appbar)

        supportActionBar?.apply {
            this.title = getString(title)

            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)

            setDisplayShowTitleEnabled(false)

            toolbar.title.text = getString(title)

            if(isBackButtonEnabled) {
                setHomeAsUpIndicator(R.drawable.ic_back)
            } else {
                toolbar.icNotification.visibility = View.VISIBLE
                setHomeAsUpIndicator(R.drawable.ic_menu)
            }
        }

        if (isBackButtonEnabled) {
            toolbar.appbar.setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun calculateWaterIntake(user: User) : Int{
        val weight = user.weight
        val gender = user.gender

        return Calculations.getWaterIntake(weight, gender == Gender.MALE, 0.0)
    }

    fun sortRecords(records: List<Record>): List<Record> {
        val dateFormatter = SimpleDateFormat(DD_MM_YYYY, Locale.getDefault())
        val timeFormatter = SimpleDateFormat(HH_MM_AA, Locale.getDefault())

        return records.sortedWith(
            compareByDescending<Record> { dateFormatter.parse(it.date) }
                .thenByDescending { timeFormatter.parse(it.time) }
        )
    }
}