package com.imasha.hydrateme.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.imasha.hydrateme.R
import com.imasha.hydrateme.databinding.ActivityHomeBinding
import com.imasha.hydrateme.databinding.ActivityProfileBinding
import com.imasha.hydrateme.ui.base.BaseActivity

class ProfileActivity : BaseActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_profile)

        setUpToolbar(binding.toolbar, R.string.profile, true)
    }
}