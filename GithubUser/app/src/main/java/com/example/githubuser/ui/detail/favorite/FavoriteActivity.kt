package com.example.githubuser.ui.detail.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.adapter.FavoriteAdapter
import com.example.githubuser.adapter.UserAdapter
import com.example.githubuser.data.local.FavoriteUser
import com.example.githubuser.databinding.ActivityFavoriteBinding
import com.example.githubuser.ui.detail.DetailViewModel
import com.example.githubuser.util.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFavoriteBinding
    private lateinit var adapter : FavoriteAdapter
    private lateinit var viewModel : FavoriteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this.application)
        val viewModel: FavoriteViewModel by viewModels {
            factory
        }
        viewModel.getFavoriteUser().observe(this){
            adapter = FavoriteAdapter(it)
            val layoutManager = LinearLayoutManager(this)
            binding.rvUser.layoutManager = layoutManager
            binding.rvUser.adapter = this.adapter
        }



    }
}