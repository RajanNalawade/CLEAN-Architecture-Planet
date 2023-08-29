package com.example.planetcleanarchitecture

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.planetcleanarchitecture.databinding.ActivityMainBinding
import com.example.planetcleanarchitecture.ui_layer.utils.NetworkConnection
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var checkConnection: NetworkConnection

    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        checkConnection.observe(this, Observer {
            if (it) {
                binding.errorNetwork.visibility = View.GONE
                binding.navHostFragment.visibility = View.VISIBLE
            } else {
                binding.errorNetwork.visibility = View.VISIBLE
                binding.navHostFragment.visibility = View.GONE
            }
        })

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        //setupActionBarWithNavController(navController)
    }


    /*override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }*/
}