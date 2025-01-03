package com.imasha.hydrateme.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.imasha.hydrateme.R
import com.imasha.hydrateme.adapters.CupAdapter
import com.imasha.hydrateme.data.repository.AppRepository
import com.imasha.hydrateme.data.source.FirebaseSource
import com.imasha.hydrateme.databinding.ActivityHomeBinding
import com.imasha.hydrateme.ui.base.BaseActivity
import com.imasha.hydrateme.ui.login.LoginActivity
import com.imasha.hydrateme.ui.login.LoginViewModel
import com.imasha.hydrateme.ui.profile.ProfileActivity
import com.imasha.hydrateme.utils.AppDialog
import com.imasha.hydrateme.utils.DateUtils
import com.imasha.hydrateme.utils.DateUtils.DD_MM_YYYY
import com.imasha.hydrateme.utils.DateUtils.getCurrentDate

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    private lateinit var appDialog: AppDialog

    private lateinit var currentUserId: String

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

        initViewModels();

        homeViewModel.getUserId();

        appDialog = AppDialog(this);

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
                R.id.nav_notifications -> {
                    Toast.makeText(this, "Notifications Clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_settings -> {
                    Toast.makeText(this, "Settings Clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_about -> {
                    Toast.makeText(this, "About Clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_logout -> {
                    appDialog.showConfirmationDialog("Logout") {
                        homeViewModel.logout();
                    }
                    true
                }
                else -> false
            }
        }

        homeViewModel.initCupSizes()

        binding.cupList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

    }

    private fun initViewModels() {
        homeViewModel.userId.observe(this) { userId  ->
            if(userId != null) {
                currentUserId = userId
            }
        }

        homeViewModel.cupSize.observe(this) { itemList ->
            binding.cupList.adapter = CupAdapter(itemList) { clickedCup ->
                val drinkMap = mapOf(
                    "user" to currentUserId,
                    "size" to clickedCup.size,
                    "date" to getCurrentDate(DD_MM_YYYY),
                )

                homeViewModel.saveData("Drinks", "", drinkMap)
            }
        }

        homeViewModel.saveDataStatus.observe(this) { result ->
            result.onSuccess {
                showToast("Drink Added.")
            }.onFailure { exception ->
                appDialog.showErrorDialog(exception.message.toString());
            }
        }

        homeViewModel.logoutStatus.observe(this) { result ->
            result.onSuccess {
                navigateToLoginActivity()
            }.onFailure { exception ->
                appDialog.showErrorDialog(exception.message.toString());
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

}