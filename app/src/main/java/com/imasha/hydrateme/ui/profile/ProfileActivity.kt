package com.imasha.hydrateme.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.imasha.hydrateme.R
import com.imasha.hydrateme.data.model.User
import com.imasha.hydrateme.data.repository.AppRepository
import com.imasha.hydrateme.data.source.FirebaseSource
import com.imasha.hydrateme.databinding.ActivityProfileBinding
import com.imasha.hydrateme.ui.base.BaseActivity
import com.imasha.hydrateme.ui.home.HomeViewModelFactory
import com.imasha.hydrateme.utils.AppDialog
import com.imasha.hydrateme.utils.AppLogger

class ProfileActivity : BaseActivity() {

    private val className = this::class.java.simpleName

    private lateinit var binding: ActivityProfileBinding
    private lateinit var profileViewModel: ProfileViewModel

    private lateinit var currentUserId: String
    private lateinit var currentUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_profile)

        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseSource = FirebaseSource(firebaseAuth)
        val appRepository = AppRepository(firebaseSource)
        val factory = ProfileViewModelFactory(appRepository)

        profileViewModel = ViewModelProvider(this, factory)[ProfileViewModel::class.java]
        initViewModels()

        profileViewModel.getUserId();
        profileViewModel.getProfile();

        setUpToolbar(binding.toolbar, R.string.profile, true)
    }

    private fun initViewModels() {
        profileViewModel.userId.observe(this) { userId ->
            if (userId != null) {
                currentUserId = userId
            }
        }

        profileViewModel.getProfileStatus.observe(this) { result ->
            result.onSuccess { user ->
                currentUser = user
                setupProfile()
            }.onFailure { exception ->
                AppLogger.d(className, exception.toString())
            }
        }

        profileViewModel.addDrinkStatus.observe(this) { result ->
            result.onSuccess {
                showToast("Drink Added.")
            }.onFailure { exception ->
                AppDialog.showErrorDialog(exception.message.toString(), this);
            }
        }
    }

    private fun setupProfile() {
        val name = currentUser.name
        val gender = currentUser.gender
        val weight = currentUser.weight
        val wakeUpTime = currentUser.wakeUpTime
        val bedTime = currentUser.bedTime

        if(name.isNotEmpty()) {
            binding.name.text = name
        }

        if(gender.isNotEmpty()) {
            binding.gender.text = name
        }

        if(!weight.equals(0.0)) {
            binding.weight.text = getString(R.string.weight, weight)
        }

        if(wakeUpTime.isNotEmpty()) {
            binding.wakeUpTime.text = wakeUpTime
        }

        if(bedTime.isNotEmpty()) {
            binding.bedTime.text = bedTime
        }
    }
}