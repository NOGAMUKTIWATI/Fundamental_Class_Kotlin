package com.example.githubuser.ui.detail.follow

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.adapter.FollowAdapter
import com.example.githubuser.databinding.FragmentFollowerTestingBinding
import com.example.githubuser.ui.detail.DetailActivity.Companion.EXTRA_FRAGMENT


class FollowerTestingFragment : Fragment() {
    private var _binding : FragmentFollowerTestingBinding?=null
    private val binding get()=_binding!!
    private lateinit var viewModel: FollowViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowerTestingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[FollowViewModel::class.java]
        viewModel.follower(arguments?.getString(EXTRA_FRAGMENT).toString())
        viewModel.followerlivedata.observe(viewLifecycleOwner){

            val adapter = FollowAdapter(it)//manggil adapter
            val layoutManager = LinearLayoutManager(requireContext())
            binding.rvUser.layoutManager = layoutManager
            binding.rvUser.adapter = adapter
        }
        viewModel.isLoadingFollower.observe(viewLifecycleOwner) {
            Log.d("check_data", it.toString())
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

    }
}