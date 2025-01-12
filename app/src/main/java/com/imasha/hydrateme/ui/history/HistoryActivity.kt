package com.imasha.hydrateme.ui.history

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.imasha.hydrateme.R
import com.imasha.hydrateme.adapters.HistoryAdapter
import com.imasha.hydrateme.data.repository.AppRepository
import com.imasha.hydrateme.data.source.FirebaseSource
import com.imasha.hydrateme.databinding.ActivityHistoryBinding
import com.imasha.hydrateme.ui.base.BaseActivity
import com.imasha.hydrateme.utils.AppDialog
import com.imasha.hydrateme.utils.Calculations.getTotalByDate

class HistoryActivity : BaseActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var historyViewModel: HistoryViewModel

    private lateinit var currentUserId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_history)

        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseSource = FirebaseSource(firebaseAuth)
        val appRepository = AppRepository(firebaseSource)
        val factory = HistoryViewModelFactory(appRepository)

        historyViewModel = ViewModelProvider(this, factory)[HistoryViewModel::class.java]
        initViewModels()

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