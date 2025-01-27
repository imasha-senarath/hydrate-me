package com.imasha.hydrateme.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager

import com.imasha.hydrateme.R
import com.imasha.hydrateme.adapters.CupAdapter
import com.imasha.hydrateme.adapters.RecordAdapter
import com.imasha.hydrateme.data.model.User
import com.imasha.hydrateme.databinding.ActivityHomeBinding
import com.imasha.hydrateme.ui.base.BaseActivity
import com.imasha.hydrateme.ui.history.HistoryActivity
import com.imasha.hydrateme.ui.login.LoginActivity
import com.imasha.hydrateme.ui.notification.NotificationActivity
import com.imasha.hydrateme.ui.profile.ProfileActivity
import com.imasha.hydrateme.ui.settings.SettingsActivity
import com.imasha.hydrateme.utils.AppConstants
import com.imasha.hydrateme.utils.AppConstants.BED_TIME
import com.imasha.hydrateme.utils.AppConstants.SETTING_CHANGE_REQUEST
import com.imasha.hydrateme.utils.AppConstants.WAKE_UP_TIME
import com.imasha.hydrateme.utils.AppDialog.showConfirmationDialog
import com.imasha.hydrateme.utils.AppDialog.showErrorDialog
import com.imasha.hydrateme.utils.AppDialog.showSuccessDialog
import com.imasha.hydrateme.utils.AppLogger
import com.imasha.hydrateme.utils.Calculations.getTotalWaterUsage
import com.imasha.hydrateme.utils.DateUtils.DD_MM_YYYY
import com.imasha.hydrateme.utils.DateUtils.HH_MM
import com.imasha.hydrateme.utils.DateUtils.getCurrentDate
import com.imasha.hydrateme.utils.DateUtils.getCurrentTime
import com.imasha.hydrateme.utils.SharedPrefManager.savePrefString
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private val className = this::class.java.simpleName

    private lateinit var binding: ActivityHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var currentUserId: String
    private var currentUser: User = User()

    private var intake: Int = 0
    private var waterUsage: Int = 0

    private lateinit var settingsLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_main)

        //val firebaseAuth = FirebaseAuth.getInstance()
        //val firebaseSource = FirebaseSource(firebaseAuth)
        //val appRepository = AppRepository(firebaseSource)
        //val factory = HomeViewModelFactory(appRepository)

        //homeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        settingsLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == SETTING_CHANGE_REQUEST) {
                recreate()
            }
        }

        initViewModels()

        showLoading()

        homeViewModel.getUserId();
        homeViewModel.getFcmToken()
        homeViewModel.getAdvices()

        setUpToolbar(binding.toolbar, R.string.app_name, false)

        binding.toolbar.appbar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_profile -> {
                    navigateToProfileActivity()
                    true
                }
                R.id.nav_history -> {
                    navigateToHistoryActivity()
                    true
                }
                R.id.nav_notifications -> {
                    navigateToNotificationActivity()
                    true
                }
                R.id.nav_settings -> {
                    navigateToSettingsActivity()
                    true
                }
                R.id.nav_logout -> {
                    showConfirmationDialog(getString(R.string.logout_title), getString(R.string.logout_confirm_msg), this) {
                        homeViewModel.logout();
                    }
                    true
                }
                else -> false
            }.also {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }
        }

        binding.toolbar.btnNotification.setOnClickListener {
            navigateToNotificationActivity()
        }

        homeViewModel.initCupSizes()
        binding.cupList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.recordList.layoutManager = LinearLayoutManager(this)
    }

    private fun initViewModels() {
        homeViewModel.userId.observe(this) { userId ->
            if (userId != null) {
                currentUserId = userId
            }

            homeViewModel.getProfile();
        }

        homeViewModel.getFcmTokenStatus.observe(this) { result ->
            result.onSuccess { token ->
                savePrefString(AppConstants.FCM_TOKEN, token)
                AppLogger.d(className, "FCM Token: $token")
            }.onFailure { exception ->
                AppLogger.d(className, exception.toString())
            }
        }

        homeViewModel.getProfileStatus.observe(this) { result ->
            result.onSuccess { user ->
                currentUser = user
                savePrefString(WAKE_UP_TIME, currentUser.wakeUpTime)
                savePrefString(BED_TIME, currentUser.bedTime)
            }.onFailure { exception ->
                AppLogger.d(className, exception.toString())
            }

            intake = calculateWaterIntake(currentUser)
            homeViewModel.getTodayRecords()
        }

        homeViewModel.cupSize.observe(this) { itemList ->
            binding.cupList.adapter = CupAdapter(itemList, this) { clickedCup ->
                val drinkMap = mapOf(
                    "user" to currentUserId,
                    "size" to clickedCup.size,
                    "time" to getCurrentTime(HH_MM),
                    "date" to getCurrentDate(DD_MM_YYYY),
                )

                homeViewModel.addRecord(drinkMap)
                homeViewModel.getTodayRecords()
            }
        }

        homeViewModel.getAdvicesStatus.observe(this) { result ->
            result.onSuccess { advices ->
                binding.advice.text = getString(R.string.advice, advices.random())
            }.onFailure { exception ->
                showErrorDialog(exception.message.orEmpty(), this)
            }
        }

        homeViewModel.getRecordStatus.observe(this) { result ->
            hideLoading()

            result.onSuccess { records ->
                binding.listEmpty.visibility = if (records.isEmpty()) View.VISIBLE else View.GONE

                val latestUsage: Int = getTotalWaterUsage(records)

                if (isCompletedTarget(latestUsage)) {
                    homeViewModel.cancelNotification(this)
                    showSuccessDialog(this, getString(R.string.goal_achieved_msg)) {}
                }

                waterUsage = latestUsage
                setupDrinkProgress()

                val sortedList = sortRecords(records)

                binding.recordList.adapter = RecordAdapter(sortedList, this) { record ->
                    showConfirmationDialog(
                        getString(R.string.delete_title),
                        getString(R.string.delete_confirm_msg),
                        this
                    ) {
                        homeViewModel.deleteRecord(record.id)
                    }
                }

                if (waterUsage < intake) {
                    homeViewModel.scheduleNotification(this)
                }

            }.onFailure { exception ->
                showErrorDialog(exception.message.orEmpty(), this)
            }
        }

        homeViewModel.addDrinkStatus.observe(this) { result ->
            result.onSuccess {
                showToast(getString(R.string.drink_added_msg))
            }.onFailure { exception ->
                showErrorDialog(exception.message.toString(), this);
            }
        }

        homeViewModel.deleteRecordStatus.observe(this) { result ->
            result.onSuccess {
                showToast(getString(R.string.deleted_msg))
                homeViewModel.getTodayRecords()
            }.onFailure { exception ->
                showErrorDialog(exception.message.toString(), this);
            }
        }

        homeViewModel.logoutStatus.observe(this) { result ->
            result.onSuccess {
                navigateToLoginActivity()
            }.onFailure { exception ->
                showErrorDialog(exception.message.toString(), this);
            }
        }
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.getProfile();
    }

    private fun setupDrinkProgress() {
        binding.drinkTarget.text = getString(R.string.drink_target, waterUsage, intake)

        binding.drinkingProgress.apply {
            progress = waterUsage.toFloat()
            progressMax = intake.toFloat()
        }
    }

    private fun isCompletedTarget(latestUsage: Int): Boolean {
        return intake in (waterUsage + 1)..latestUsage
    }

    private fun navigateToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToProfileActivity() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToSettingsActivity() {
        val intent = Intent(this, SettingsActivity::class.java)
        settingsLauncher.launch(intent)
    }

    private fun navigateToHistoryActivity() {
        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToNotificationActivity() {
        val intent = Intent(this, NotificationActivity::class.java)
        startActivity(intent)
    }
}