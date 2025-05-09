package com.example.colorsgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.colorsgame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            Log.d(TAG, "onCreate started")

            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
            Log.d(TAG, "View inflated successfully")

            setupNavigation()
        } catch (e: Exception) {
            Log.e(TAG, "Fatal error in onCreate: ${e.message}")
            e.printStackTrace()
        }
    }

    private fun setupNavigation() {
        try {
            // Configurar el Navigation Component
            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment)

            if (navHostFragment == null) {
                Log.e(TAG, "NavHostFragment not found! ID: ${R.id.nav_host_fragment}")
                // Intenta listar todos los fragments en el supportFragmentManager para debug
                listFragments()
                return
            }

            if (navHostFragment !is NavHostFragment) {
                Log.e(TAG, "Fragment found is not a NavHostFragment: ${navHostFragment.javaClass.simpleName}")
                return
            }

            Log.d(TAG, "NavHostFragment found successfully")

            try {
                navController = (navHostFragment as NavHostFragment).navController
                Log.d(TAG, "NavController obtained successfully")

                // Configurar la Action Bar con Navigation
                setupActionBarWithNavController(navController)
                Log.d(TAG, "Action bar setup complete")
            } catch (e: Exception) {
                Log.e(TAG, "Error getting NavController: ${e.message}")
                e.printStackTrace()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in navigation setup: ${e.message}")
            e.printStackTrace()
        }
    }

    private fun listFragments() {
        try {
            Log.d(TAG, "Listing fragments in supportFragmentManager:")
            val fragments = supportFragmentManager.fragments
            if (fragments.isEmpty()) {
                Log.d(TAG, "No fragments found in supportFragmentManager")
            } else {
                fragments.forEach { fragment ->
                    Log.d(TAG, "Fragment: ${fragment.javaClass.simpleName}, ID: ${fragment.id}, Tag: ${fragment.tag}")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error listing fragments: ${e.message}")
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called")
    }

    override fun onSupportNavigateUp(): Boolean {
        try {
            Log.d(TAG, "onSupportNavigateUp called")
            return if (::navController.isInitialized) {
                navController.navigateUp() || super.onSupportNavigateUp()
            } else {
                Log.e(TAG, "navController not initialized in onSupportNavigateUp")
                super.onSupportNavigateUp()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in onSupportNavigateUp: ${e.message}")
            e.printStackTrace()
            return super.onSupportNavigateUp()
        }
    }
}