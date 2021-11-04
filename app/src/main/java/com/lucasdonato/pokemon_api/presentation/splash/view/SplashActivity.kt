package com.lucasdonato.pokemon_api.presentation.splash.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.content.ContextCompat
import com.lucasdonato.pokemon_api.R
import com.lucasdonato.pokemon_api.databinding.ActivitySplashBinding
import com.lucasdonato.pokemon_api.mechanism.SPLASH_DISPLAY_TIME
import com.lucasdonato.pokemon_api.presentation.AppApplication.Companion.context
import com.lucasdonato.pokemon_api.presentation.base.view.BaseActivity
import com.lucasdonato.pokemon_api.presentation.home.presenter.HomePresenter
import com.lucasdonato.pokemon_api.presentation.onboarding.view.OnboardingActivity
import com.lucasdonato.pokemon_api.presentation.splash.presenter.SplashPresenter
import kotlinx.android.synthetic.main.activity_splash.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val presenter: SplashPresenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let { getRandomBackground(it) }
        Handler().postDelayed({
            startWelcomeScreen()
        }, SPLASH_DISPLAY_TIME.toLong())
    }

    private fun getRandomBackground(context: Context) {
        binding.splashBackground.background =
            ContextCompat.getDrawable(context, presenter.background.random())
    }

    private fun startWelcomeScreen() {
        startActivity(OnboardingActivity.getStartIntent(this))
        finish()
    }

}