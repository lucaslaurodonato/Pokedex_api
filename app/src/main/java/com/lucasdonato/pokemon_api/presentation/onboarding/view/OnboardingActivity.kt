package com.lucasdonato.pokemon_api.presentation.onboarding.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lucasdonato.pokemon_api.R
import com.lucasdonato.pokemon_api.databinding.ActivityOnboardingBinding
import com.lucasdonato.pokemon_api.presentation.base.view.BaseActivity
import com.lucasdonato.pokemon_api.presentation.home.view.HomeActivity
import kotlinx.android.synthetic.main.activity_onboarding.*

class OnboardingActivity : BaseActivity<ActivityOnboardingBinding>(R.layout.activity_onboarding) {

    companion object {
        fun getStartIntent(context: Context) = Intent(context, OnboardingActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        binding.startHomeButton.setOnClickListener {
            startHomeActivity()
        }
    }

    private fun startHomeActivity() {
        startActivity(HomeActivity.getStartIntent(this))
        finish()
    }

}