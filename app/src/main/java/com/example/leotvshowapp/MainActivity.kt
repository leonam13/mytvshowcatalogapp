package com.example.leotvshowapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.leotvshowapp.databinding.ActivityMainBinding
import com.example.leotvshowapp.utils.setVisible
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bottomNavView).setupWithNavController(navController)
        findViewById<Toolbar>(R.id.toolbar).setupWithNavController(navController, AppBarConfiguration(navController.graph))

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.toolbar.setVisible(
                destination.id != R.id.tvShowAllFragment &&
                        destination.id != R.id.tvShowPeopleFragment &&
                        destination.id != R.id.tvShowFavoriteListFragment
            )
        }
    }
}