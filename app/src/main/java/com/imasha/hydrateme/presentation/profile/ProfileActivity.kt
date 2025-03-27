package com.imasha.hydrateme.presentation.profile

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.activity.viewModels
import com.imasha.hydrateme.R
import com.imasha.hydrateme.data.enums.Gender
import com.imasha.hydrateme.data.model.User
import com.imasha.hydrateme.databinding.ActivityProfileBinding
import com.imasha.hydrateme.presentation.base.BaseActivity
import com.imasha.hydrateme.utils.AppConstants.BED_TIME
import com.imasha.hydrateme.utils.AppConstants.NAME_DIALOG
import com.imasha.hydrateme.utils.AppConstants.WAKE_UP_TIME
import com.imasha.hydrateme.utils.AppConstants.WEIGHT_DIALOG
import com.imasha.hydrateme.utils.AppDialog
import com.imasha.hydrateme.utils.AppDialog.showGoalUpdateDialog
import com.imasha.hydrateme.utils.AppDialog.showSelectionDialog
import com.imasha.hydrateme.utils.AppDialog.showUpdateDialog
import com.imasha.hydrateme.utils.AppLogger
import com.imasha.hydrateme.utils.DateUtils.HH_MM
import com.imasha.hydrateme.utils.DateUtils.convertTo12
import com.imasha.hydrateme.utils.SharedPrefManager.savePrefString
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ProfileActivity : BaseActivity() {

    private val className = this::class.java.simpleName

    private lateinit var binding: ActivityProfileBinding
    private val profileViewModel: ProfileViewModel by viewModels()

    private var currentUser: User = User()
    private var currentUserGoal: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModels()

        showLoading()

        profileViewModel.getUserId();

        setUpToolbar(binding.toolbar, R.string.profile_title, true)

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

        binding.btnGoal.setOnClickListener {
            showGoalUpdateDialog(currentUserGoal, this) { newGoal ->
                currentUser.goal = newGoal
                binding.goal.text = getString(R.string.size_ml, currentUser.goal)
                profileViewModel.saveProfile(currentUser)
            }
        }

        binding.btnWakeUpTime.setOnClickListener {
            showTimePicker(currentUser.wakeUpTime) { selectedTime ->
                currentUser.wakeUpTime = selectedTime
                binding.wakeUpTime.text = convertTo12(currentUser.wakeUpTime)
                profileViewModel.saveProfile(currentUser)
                savePrefString(WAKE_UP_TIME, currentUser.wakeUpTime)
            }
        }

        binding.btnBedTime.setOnClickListener {
            showTimePicker(currentUser.bedTime) { selectedTime ->
                currentUser.bedTime = selectedTime
                binding.bedTime.text = convertTo12(currentUser.bedTime)
                profileViewModel.saveProfile(currentUser)
                savePrefString(BED_TIME, currentUser.bedTime)
            }
        }
    }

    private fun initViewModels() {
        profileViewModel.userId.observe(this) { userId ->
            if (userId != null) {
                currentUser.id = userId
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

        currentUserGoal = calculateWaterIntake(currentUser)
        binding.goal.text = getString(R.string.size_ml, currentUserGoal)

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
            binding.wakeUpTime.text = convertTo12(wakeUpTime)
        }

        if (bedTime.isNotEmpty()) {
            binding.bedTime.text = convertTo12(bedTime)
        }

        hideLoading()
    }

    private fun showTimePicker(currentTime24H: String?, onTimeSet: (String) -> Unit) {
        val inputFormat = SimpleDateFormat(HH_MM, Locale.getDefault())

        val currentTime = if (currentTime24H.isNullOrEmpty()) {
            Date()
        } else {
            inputFormat.parse(currentTime24H) ?: Date()
        }

        val calendar = Calendar.getInstance().apply {
            time = currentTime
        }

        val hour = calendar.get(Calendar.HOUR)
        val minute = calendar.get(Calendar.MINUTE)

        TimePickerDialog(
            this,
            { _, selectedHour, selectedMinute ->
                val selectedTime24H = String.format("%02d:%02d", selectedHour, selectedMinute)
                onTimeSet(selectedTime24H)
            },
            if (hour == 0) 12 else hour,
            minute,
            false
        ).show()
    }
}