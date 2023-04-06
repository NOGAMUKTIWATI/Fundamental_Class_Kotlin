package com.example.githubuser.ui.detail.follow

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.adapter.FollowAdapter
import com.example.githubuser.databinding.FragmentFollowingTestBinding
import com.example.githubuser.ui.detail.DetailActivity


class FollowingTestFragment : Fragment() {
    private var _binding : FragmentFollowingTestBinding?=null
    private val binding get()=_binding!!
    private lateinit var viewModel: FollowViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingTestBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[FollowViewModel::class.java]

        viewModel.following(arguments?.getString(DetailActivity.EXTRA_FRAGMENT).toString())
        viewModel.followinglivedata.observe(viewLifecycleOwner){

            val adapter = FollowAdapter(it)//manggil adapter
            val layoutManager = LinearLayoutManager(requireContext())
            binding.rvUser.layoutManager = layoutManager
            binding.rvUser.adapter = adapter
        }
        viewModel.isLoadingFollowing.observe(viewLifecycleOwner) {
            Log.d("check_data", it.toString())
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

    }
}