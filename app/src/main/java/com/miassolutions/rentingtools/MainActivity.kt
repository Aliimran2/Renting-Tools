package com.miassolutions.rentingtools

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.miassolutions.rentingtools.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val requiredPermissions = arrayOf(
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.READ_MEDIA_IMAGES
    )

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        if (!PermissionUtils.hasPermissions(this, requiredPermissions)) {
            PermissionUtils.requestPermissions(this, requiredPermissions)
        }



        setSupportActionBar(binding.toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.stockFragment,
                R.id.customersListFragment,
                R.id.addCustomerFragment,
                R.id.addToolFragment
            ), binding.drawerLayout
        )



        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.navigationView.setupWithNavController(navController)

        val aboutApp = binding.navigationView.menu.findItem(R.id.aboutApp)
        aboutApp.setOnMenuItemClickListener {
            Toast.makeText(this@MainActivity, "MIAS Solutions", Toast.LENGTH_SHORT).show()
            binding.drawerLayout.closeDrawers()
            true
        }

        val mockdb = binding.navigationView.menu.findItem(R.id.mockData)
        mockdb.setOnMenuItemClickListener {




            binding.drawerLayout.closeDrawers()
            true
        }

        val reset = binding.navigationView.menu.findItem(R.id.resetItem)

        reset.setOnMenuItemClickListener {

            binding.drawerLayout.closeDrawers()
            true

        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_home -> {
                Toast.makeText(this, "Home clicked", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_edit -> {
                Toast.makeText(this, "Edit clicked", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_delete -> {
                Toast.makeText(this, "Delete clicked", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)
        if (requestCode == PermissionUtils.REQUEST_CODE) {
            val deniedPermissions = permissions.zip(grantResults.toList())
                .filter { it.second != PackageManager.PERMISSION_GRANTED }
                .map { it.first }

            if (deniedPermissions.isEmpty()) {
                showToast(this, "All permissions granted")

            } else showToast(this, "Permission denied : ${deniedPermissions.joinToString()}")
        }
    }
}