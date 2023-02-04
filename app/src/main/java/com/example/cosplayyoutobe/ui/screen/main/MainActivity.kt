package com.example.cosplayyoutobe.ui.screen.main

import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.cosplayyoutobe.R
import com.example.cosplayyoutobe.base.BaseActivity
import com.example.cosplayyoutobe.databinding.ActivityMainBinding
import com.example.cosplayyoutobe.databinding.LayoutHeaderNavBinding
import com.example.cosplayyoutobe.navigation.NavigationManager
import com.example.cosplayyoutobe.ui.viewholder.OnShowLeftMenu
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(), NavigationView.OnNavigationItemSelectedListener {

    @Inject
    lateinit var navigationManager: NavigationManager

    private lateinit var googleSignInOptions: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient

    private val headerNavBinding by lazy {
        LayoutHeaderNavBinding.bind(binding.navigationView.getHeaderView(0))
    }

    private val navController by lazy {
        findNavController(R.id.nav_host_fragment)
    }

    private val onShowLeftMenu = object : OnShowLeftMenu {
        override fun show() {
            binding.leftMenu.openDrawer(GravityCompat.START)
        }

        override fun hide() {
            binding.leftMenu.closeDrawer(GravityCompat.START)
        }
    }

    override fun getContentLayout(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        setUpLoginWithGoogle()
        binding.navigationView.setNavigationItemSelectedListener(this)
        setMenuBottomNavigation()
        setUpToolbar()
    }

    override fun initListener() {

    }

    override fun observerLiveData() {

    }

    override fun getLayoutLoading(): View? {
        return null
    }

    private fun setUpToolbar() {
        binding.toolBar.setNavigationIcon(R.drawable.ic_person)
        this.setSupportActionBar(binding.toolBar)
        this.supportActionBar?.title = ""
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onShowLeftMenu.show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpLoginWithGoogle() {
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            Glide.with(this)
                .load(account.photoUrl)
                .into(headerNavBinding.imgAvt)
            headerNavBinding.tvName.text = account.displayName
            headerNavBinding.tvEmail.text = account.email
        }
    }

    private fun setMenuBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.nav_home -> {
                    navController.navigateUp()
                    navController.navigate(R.id.homeFragment)
                }
                R.id.nav_search -> {
                    navController.navigateUp()
                    navController.navigate(R.id.searchFragment)
                }
                R.id.nav_lib -> {
                    navController.navigateUp()
                    navController.navigate(R.id.libFragment)
                }
                R.id.nav_channel -> {
                    navController.navigateUp()
                    navController.navigate(R.id.subFragment)
                }
            }
            true
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_profile ->{
                Toast.makeText(this, "nav_profile", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_logout -> {
                Toast.makeText(this, "nav_logout", Toast.LENGTH_SHORT).show()
                navigationManager.gotoLoginActivity()
            }
        }
        onShowLeftMenu.hide()
        return true
    }
}