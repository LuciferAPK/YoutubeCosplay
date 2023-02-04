package com.example.cosplayyoutobe.ui.screen.login

import android.content.Intent
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import com.example.cosplayyoutobe.R
import com.example.cosplayyoutobe.base.BaseActivity
import com.example.cosplayyoutobe.databinding.ActivityLoginBinding
import com.example.cosplayyoutobe.databinding.ActivityMainBinding
import com.example.cosplayyoutobe.databinding.LayoutHeaderNavBinding
import com.example.cosplayyoutobe.navigation.NavigationManager
import com.example.cosplayyoutobe.ui.viewholder.OnShowLeftMenu
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    @Inject
    lateinit var navigationManager: NavigationManager

    private lateinit var googleSignInOptions: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun initView() {
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
    }

    override fun initListener() {
        binding.imgGoogle.setOnClickListener {
            signIn()
        }
    }

    override fun observerLiveData() {

    }

    override fun getLayoutLoading(): View? {
        return null
    }

    override fun getContentLayout(): Int {
        return R.layout.activity_login
    }

    private fun signIn() {
        val intent = googleSignInClient.signInIntent
        startActivityForResult(intent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                task.getResult(ApiException::class.java)
                navigationManager.gotoMainActivity()
                finish()
            } catch (e: ApiException) {
                Toast.makeText(this, "${e.status}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}