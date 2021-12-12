package ru.ksart.tmdb_movies.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.preference.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import ru.ksart.tmdb_movies.R
import ru.ksart.tmdb_movies.databinding.ActivityMainBinding
import ru.ksart.tmdb_movies.ui.extension.setTheme

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        // select theme
        PreferenceManager.getDefaultSharedPreferences(this).setTheme(this)

        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).also { binding = it }.root)

        initAppBar()
        initToolbarMenu()
    }

    private fun initAppBar() {
        navController =
            (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.topRatedMoviesFragment)
        )
        setupWithNavController(binding.toolbar, navController, appBarConfiguration)
    }

    private fun initToolbarMenu() {
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_settings -> {
                    // show Settings Fragment
                    navController.navigate(R.id.settingsFragment)
                    true
                }
                else -> false
            }
        }
        initSearch()
    }

    private fun initSearch() {
        val searchItem = binding.toolbar.menu.findItem(R.id.action_search)
        (searchItem.actionView as SearchView).setOnQueryTextListener(
            object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let {
                        // search
                    }
                    return true
                }
            }
        )
    }

}
