package com.imasha.hydrateme.ui.profile

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.activity.viewModels
import com.imasha.hydrateme.R
import com.imasha.hydrateme.data.enums.Gender
import com.imasha.hydrateme.data.model.User
import com.imasha.hydrateme.databinding.ActivityProfileBinding
import com.imasha.hydrateme.ui.base.BaseActivity
import com.imasha.hydrateme.utils.AppConstants.NAME_DIALOG
import com.imasha.hydrateme.utils.AppConstants.WEIGHT_DIALOG
import com.imasha.hydrateme.utils.AppDialog
import com.imasha.hydrateme.utils.AppDialog.showSelectionDialog
import com.imasha.hydrateme.utils.AppDialog.showUpdateDialog
import com.imasha.hydrateme.utils.AppLogger
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : BaseActivity() {

    private val className = this::class.java.simpleName

    private lateinit var binding: ActivityProfileBinding
    private val profileViewModel: ProfileViewModel by viewModels()

    private lateinit var currentUserId: String
    private var currentUser: User = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModels()

        profileViewModel.getUserId();

        setUpToolbar(binding.toolbar, R.string.profile, true)

        binding.btnName.setOnClickListener {
            showUpdateDialog(NAME_DIALOG, currentUser.name, this) { newName ->
                currentUser.name = newName.toString()
                binding.name.text = currentUser.name
                profileViewModel.saveProfile(currentUser)
            }
        }

        binding.btnWeight.setOnClickListener {
            showUpdateDialog(WEIGHT_DIALOG, currentUser.weight, this) { newWeight ->
                currentUser.weight = newWeight.toString().toDouble()
                binding.weight.text = getString(R.string.weight, currentUser.weight)
                profileViewModel.saveProfile(currentUser)
            }
        }

        binding.btnGender.setOnClickListener {
            showSelectionDialog(this, currentUser.gender, Gender::class.java) { selectedGender ->
                currentUser.gender = selectedGender
                binding.gender.text = currentUser.gender.toString()
                profileViewModel.saveProfile(currentUser)
            }
        }

        binding.btnWakeUpTime.setOnClickListener {
            showTimePicker() { selectedTime ->
                currentUser.wakeUpTime = selectedTime
                binding.wakeUpTime.text = currentUser.wakeUpTime
                profileViewModel.saveProfile(currentUser)
            }
        }

        binding.btnBedTime.setOnClickListener {
            showTimePicker() { selectedTime ->
                currentUser.bedTime = selectedTime
                binding.bedTime.text = currentUser.bedTime
                profileViewModel.saveProfile(currentUser)
            }
        }
    }

    private fun initViewModels() {
        profileViewModel.userId.observe(this) { userId ->
            if (userId != null) {
                currentUserId = userId
            }

            profileViewModel.getProfile();
        }

        profileViewModel.getProfileStatus.observe(this) { result ->
            result.onSuccess { user ->
                currentUser = user
            }.onFailure { exception ->
                AppLogger.d(className, exception.toString())
            }

            setupProfile()
        }

        profileViewModel.saveProfileStatus.observe(this) { result ->
            result.onSuccess {
                showToast("Profile updated.")
            }.onFailure { exception ->
                AppDialog.showErrorDialog(exception.message.toString(), this);
            }

            profileViewModel.getProfile();
        }
    }

    private fun setupProfile() {
        val name = currentUser.name
        val gender = currentUser.gender.toString()
        val weight = currentUser.weight
        val wakeUpTime = currentUser.wakeUpTime
        val bedTime = currentUser.bedTime

        binding.goal.text = getString(R.string.size_ml, calculateWaterIntake(currentUser))

        if (name.isNotEmpty()) {
            binding.name.text = name
        }

        if (gender.isNotEmpty()) {
            binding.gender.text = gender
        }

        if (!weight.equals(0.0)) {
            binding.weight.text = getString(R.string.weight, weight)
        }

        if (wakeUpTime.isNotEmpty()) {
            binding.wakeUpTime.text = wakeUpTime
        }

        if (bedTime.isNotEmpty()) {
            binding.bedTime.text = bedTime
        }
    }

    private fun showTimePicker(onTimeSet: (String) -> Unit) {
        val is24HourView = false
        TimePickerDialog(this, { _, hourOfDay, minute ->
            val amPm = if (hourOfDay >= 12) "PM" else "AM"
            val hour = if (hourOfDay > 12) hourOfDay - 12 else if (hourOfDay == 0) 12 else hourOfDay
            val time = String.format("%02d:%02d %s", hour, minute, amPm)
            onTimeSet(time)
        }, 0, 0, is24HourView).show()
    }
}