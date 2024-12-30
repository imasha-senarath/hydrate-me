package com.imasha.hydrateme.ui.base

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.appbar.MaterialToolbar
import com.imasha.hydrateme.R

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    protected fun setUpToolbar(toolbar: MaterialToolbar, title: Int, isBackButtonEnabled: Boolean) {
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            this.title = getString(title)

            setDisplayShowTitleEnabled(false)

            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)

            if(isBackButtonEnabled) {
                setHomeAsUpIndicator(R.drawable.ic_back)
            } else {
                setHomeAsUpIndicator(R.drawable.ic_menu)
            }
        }

        if (isBackButtonEnabled) {
            toolbar.setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }
}