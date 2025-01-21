package com.imasha.hydrateme.ui.history

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.imasha.hydrateme.R
import com.imasha.hydrateme.adapters.HistoryAdapter
import com.imasha.hydrateme.databinding.ActivityHistoryBinding
import com.imasha.hydrateme.ui.base.BaseActivity
import com.imasha.hydrateme.utils.AppDialog
import com.imasha.hydrateme.utils.Calculations.getTotalByDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryActivity : BaseActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private val historyViewModel: HistoryViewModel by viewModels()

    private lateinit var currentUserId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModels()

        showLoading()

        historyViewModel.getUserId();

        setUpToolbar(binding.toolbar, R.string.history, true)

        binding.recordList.layoutManager = LinearLayoutManager(this)
    }

    private fun initViewModels() {
        historyViewModel.userId.observe(this) { userId ->
            if (userId != null) {
                currentUserId = userId
            }

            historyViewModel.getRecords();
        }

        historyViewModel.getRecordStatus.observe(this) { result ->
            hideLoading()

            result.onSuccess { records ->
                val filteredList = getTotalByDate(records)
                val sortedList = sortRecords(filteredList)

                binding.recordList.adapter = HistoryAdapter(sortedList, this)

            }.onFailure { exception ->
                AppDialog.showErrorDialog(exception.message.orEmpty(), this)
            }
        }
    }
}