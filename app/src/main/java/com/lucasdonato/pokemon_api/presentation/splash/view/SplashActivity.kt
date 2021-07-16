package com.lucasdonato.pokemon_api.presentation.splash.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.content.ContextCompat
import com.lucasdonato.pokemon_api.R
import com.lucasdonato.pokemon_api.mechanism.SPLASH_DISPLAY_TIME
import com.lucasdonato.pokemon_api.presentation.AppApplication.Companion.context
import com.lucasdonato.pokemon_api.presentation.onboarding.view.OnboardingActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    val background = intArrayOf(
        R.drawable.a1,
        R.drawable.a2,
        R.drawable.a3,
        R.drawable.a4,
        R.drawable.a5,
        R.drawable.a6,
        R.drawable.a7,
        R.drawable.a8,
        R.drawable.a9,
        R.drawable.a10,
        R.drawable.a11,
        R.drawable.a12,
        R.drawable.a13,
        R.drawable.a14,
        R.drawable.a16,
        R.drawable.a17,
        R.drawable.a18,
        R.drawable.a19,
        R.drawable.a20,
        R.drawable.a21,
        R.drawable.a22,
        R.drawable.a23,
        R.drawable.a24,
        R.drawable.a25,
        R.drawable.a26,
        R.drawable.a27,
        R.drawable.a28,
        R.drawable.a29
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        context?.let { getRandomBackground(it) }
        Handler().postDelayed({
            startWelcomeScreen()
        }, SPLASH_DISPLAY_TIME.toLong())
    }

    private fun getRandomBackground(context: Context) {
        splash_background.background = ContextCompat.getDrawable(context, background.random())
    }

    private fun startWelcomeScreen() {
        startActivity(OnboardingActivity.getStartIntent(this))
        finish()
    }
}