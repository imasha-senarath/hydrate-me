package com.imasha.hydrateme.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.imasha.hydrateme.R
import com.imasha.hydrateme.data.repository.AppRepository
import com.imasha.hydrateme.data.source.FirebaseSource
import com.imasha.hydrateme.databinding.ActivityHomeBinding
import com.imasha.hydrateme.ui.base.BaseActivity
import com.imasha.hydrateme.ui.login.LoginActivity
import com.imasha.hydrateme.ui.login.LoginViewModel
import com.imasha.hydrateme.utils.AppDialog

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    private lateinit var appDialog: AppDialog;

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

        appDialog = AppDialog(this);

        setUpToolbar(binding.toolbar.appbar, R.string.app_name, false)

        binding.toolbar.appbar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_settings -> {
                    Toast.makeText(this, "Settings Clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_about -> {
                    Toast.makeText(this, "Profile Clicked", Toast.LENGTH_SHORT).show()
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
    }

    private fun initViewModels() {
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

}