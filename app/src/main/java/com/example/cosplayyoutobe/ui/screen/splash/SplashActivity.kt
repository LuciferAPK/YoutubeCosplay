package com.example.cosplayyoutobe.ui.screen.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import com.example.cosplayyoutobe.R
import com.example.cosplayyoutobe.base.BaseActivity
import com.example.cosplayyoutobe.databinding.ActivitySplashBinding
import com.example.cosplayyoutobe.ui.screen.main.MainActivity
import com.example.cosplayyoutobe.ui.screen.main.MainViewModel
import com.example.cosplayyoutobe.utils.CoroutineExt
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun getContentLayout(): Int {
        return R.layout.activity_splash
    }

    override fun initView() {
        animation()
        CoroutineExt.runOnMainAfterDelay(2000) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun initListener() {}

    override fun observerLiveData() {}

    override fun getLayoutLoading(): View? {
        return null
    }

    private fun animation() {
        binding.logo.animation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        binding.textLogo.animation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)
        binding.textBio.animation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)
    }
}