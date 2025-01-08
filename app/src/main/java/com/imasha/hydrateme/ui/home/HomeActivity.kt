package com.imasha.hydrateme.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.imasha.hydrateme.R
import com.imasha.hydrateme.adapters.CupAdapter
import com.imasha.hydrateme.adapters.RecordAdapter
import com.imasha.hydrateme.data.model.User
import com.imasha.hydrateme.data.repository.AppRepository
import com.imasha.hydrateme.data.source.FirebaseSource
import com.imasha.hydrateme.databinding.ActivityHomeBinding
import com.imasha.hydrateme.ui.base.BaseActivity
import com.imasha.hydrateme.ui.login.LoginActivity
import com.imasha.hydrateme.ui.profile.ProfileActivity
import com.imasha.hydrateme.ui.settings.SettingsActivity
import com.imasha.hydrateme.utils.AppConstants.DRINKS
import com.imasha.hydrateme.utils.AppDialog.showConfirmationDialog
import com.imasha.hydrateme.utils.AppDialog.showErrorDialog
import com.imasha.hydrateme.utils.DateUtils.DD_MM_YYYY
import com.imasha.hydrateme.utils.DateUtils.HH_MM_AA
import com.imasha.hydrateme.utils.DateUtils.getCurrentDate
import com.imasha.hydrateme.utils.DateUtils.getCurrentTime

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    private lateinit var currentUserId: String
    private lateinit var currentUser: User

    private var intake: Int = 3200 // default value
    private var waterUsage: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_main)

        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseSource = FirebaseSource(firebaseAuth)
        val appRepository = AppRepository(firebaseSource)
        val factory = HomeViewModelFactory(appRepository)

        homeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        initViewModels()

        homeViewModel.getUserId();
        homeViewModel.getProfile();

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
                    Toast.makeText(this, "History", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_notifications -> {
                    Toast.makeText(this, "Notifications", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_settings -> {
                    navigateToSettingsActivity()
                    true
                }
                R.id.nav_about -> {
                    Toast.makeText(this, "About", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_logout -> {
                    showConfirmationDialog("Logout", this) {
                        homeViewModel.logout();
                    }
                    true
                }
                else -> false
            }.also {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }
        }

        homeViewModel.initCupSizes()
        binding.cupList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        homeViewModel.getTodayRecords()
        binding.recordList.layoutManager = LinearLayoutManager(this)
    }

    private fun initViewModels() {
        homeViewModel.userId.observe(this) { userId  ->
            if(userId != null) {
                currentUserId = userId
            }
        }

        homeViewModel.getProfileStatus.observe(this) { result ->
            result.onSuccess { user ->
                currentUser = user
                intake = homeViewModel.calculateWaterIntake(currentUser)
            }.onFailure { exception ->
                showErrorDialog(exception.message.orEmpty(), this)
            }

            binding.drinkTarget.text = getString(R.string.drink_target, waterUsage, intake)
        }

        homeViewModel.cupSize.observe(this) { itemList ->
            binding.cupList.adapter = CupAdapter(itemList, this) { clickedCup ->
                val drinkMap = mapOf(
                    "user" to currentUserId,
                    "size" to clickedCup.size,
                    "time" to getCurrentTime(HH_MM_AA),
                    "date" to getCurrentDate(DD_MM_YYYY),
                )

                homeViewModel.addRecord(drinkMap)
                homeViewModel.getTodayRecords()
            }
        }

        homeViewModel.getRecordStatus.observe(this) { result ->
            result.onSuccess { records ->
                binding.recordList.adapter = RecordAdapter(records, this) { record ->
                    showToast("Deleted ${record.size} ml")
                }

            }.onFailure { exception ->
                showErrorDialog(exception.message.orEmpty(), this)
            }
        }

        homeViewModel.addDrinkStatus.observe(this) { result ->
            result.onSuccess {
                showToast("Drink Added.")
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
        startActivity(intent)
    }
}