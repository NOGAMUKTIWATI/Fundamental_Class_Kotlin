package com.example.githubuser.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.githubuser.R
import com.example.githubuser.databinding.ActivityDetailBinding
import com.example.githubuser.util.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    //deklarasi binding
    private lateinit var binding: ActivityDetailBinding

    //deklarasi View Model
    private lateinit var viewModel: DetailViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //menangkap intent yang ada di main activity
        val username = intent.getStringExtra(EXTRA_USERNAME)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this.application)
        val viewModel: DetailViewModel by viewModels {
            factory
        }

        // viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
        if (username != null) {
            viewModel.setUserDetail(username)
            viewModel.userExist(username)
        }
        viewModel.isFavorite.observe(this) {
            if (it) {
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.baseline_favorite_active
                    )
                )
            } else {
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.baseline_favorite_disable
                    )
                )
            }
        }
        binding.fab.setOnClickListener {
            viewModel.handleFavoriteAction()
        }

        viewModel.getUserDetail().observe(this) {
            if (it != null) {
                binding.apply {
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvFollowers.text = "${it.followers}"
                    tvFollowing.text = "${it.following}"
                    Glide.with(this@DetailActivity)
                        .load(it.avatarUrl)
                        .into(ivProfile)
                }
            }
        }

        viewModel.isLoading.observe(this) {
            Log.d("check_data", it.toString())
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        val bundle = Bundle()
        bundle.putString(EXTRA_FRAGMENT, username)
        val sectionPagerAdapter = SectionPagerAdapter(this, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITTLES[position])
            }.attach()

        }
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        private val TAB_TITTLES = intArrayOf(
            R.string.tab_1, R.string.tab_2
        )
        const val EXTRA_FRAGMENT = "extra_fragment"
    }
}