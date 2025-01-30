package com.imasha.hydrateme.ui.notification

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.imasha.hydrateme.R
import com.imasha.hydrateme.adapters.NotificationAdapter
import com.imasha.hydrateme.databinding.ActivityNotificationBinding
import com.imasha.hydrateme.ui.base.BaseActivity
import com.imasha.hydrateme.utils.AppDialog
import com.imasha.hydrateme.utils.ItemDivider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationActivity : BaseActivity() {

    private lateinit var binding: ActivityNotificationBinding
    private val notificationViewModel: NotificationViewModel by viewModels()

    private lateinit var currentUserId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpToolbar(binding.toolbar, R.string.notifications_title, true)

        initViewModels()

        showLoading()

        notificationViewModel.getUserId();

        binding.notificationList.layoutManager = LinearLayoutManager(this)
        binding.notificationList.addItemDecoration(ItemDivider(this, R.drawable.divider))
    }

    private fun initViewModels() {
        notificationViewModel.userId.observe(this) { userId ->
            if (userId != null) {
                currentUserId = userId
            }

            notificationViewModel.getNotifications();
        }

        notificationViewModel.getNotificationStatus.observe(this) { result ->
            hideLoading()
            result.onSuccess { notifications ->

                binding.listEmpty.visibility = if (notifications.isEmpty()) View.VISIBLE else View.GONE

                binding.notificationList.adapter = NotificationAdapter(notifications, this)

            }.onFailure { exception ->
                AppDialog.showErrorDialog(exception.message.orEmpty(), this)
            }
        }
    }
}