package com.example.githubuser.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuser.ui.detail.follow.FollowerTestingFragment
import com.example.githubuser.ui.detail.follow.FollowingTestFragment

class SectionPagerAdapter(activity: DetailActivity, private val login: Bundle) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowerTestingFragment()
            1 -> fragment = FollowingTestFragment()
        }
        fragment?.arguments = login
        return fragment as Fragment
    }

}