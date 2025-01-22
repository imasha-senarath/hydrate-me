package com.imasha.hydrateme.ui.notification

import android.os.Bundle
import com.imasha.hydrateme.R
import com.imasha.hydrateme.databinding.ActivityNotificationBinding
import com.imasha.hydrateme.ui.base.BaseActivity

class NotificationActivity : BaseActivity() {

    private lateinit var binding: ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpToolbar(binding.toolbar, R.string.notifications, true)
    }
}