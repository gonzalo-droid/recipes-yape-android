package com.gonzalolozg.yape.presentation.features.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.gonzalolozg.yape.databinding.ActivitySplashBinding
import com.gonzalolozg.yape.presentation.features.home.HomeActivity

class CustomSplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()

        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)

        val view = binding.root

        screenSplash.setKeepOnScreenCondition { true }

        setContentView(view)

        goToHome()
    }

    private fun goToHome() {
        startActivity(
            Intent(this, HomeActivity::class.java)
        )
    }
}
