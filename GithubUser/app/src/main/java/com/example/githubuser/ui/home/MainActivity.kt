package com.example.githubuser.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.adapter.UserAdapter
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.ui.detail.favorite.FavoriteActivity


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Home"//splash screen

        val pref = SettingPreferences.getInstance(dataStore)
        viewModel = ViewModelProvider(this, DarkModeViewModelFactory(pref)).get(MainViewModel::class.java)
        initThemeSetting()

        viewModel.userlivedata.observe(this) { data ->
            val adapter = UserAdapter(data)
            val layoutManager = LinearLayoutManager(this)
            binding.rvUser.layoutManager = layoutManager
            binding.rvUser.adapter = adapter
        }

        viewModel.isLoading.observe(this) {
            Log.d("check_data", it.toString())
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        binding.apply {
            svUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String): Boolean {
                    viewModel.getQuery(p0)
                    progressBar.visibility = View.VISIBLE
                    return true
                }

                override fun onQueryTextChange(p0: String): Boolean {
                    return false
                }
            })
            btnFavorite.setOnClickListener {
                val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun initThemeSetting(){
        viewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }
        }

        binding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            viewModel.saveThemeSetting(isChecked)
        }
    }
}
