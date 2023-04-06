package com.example.githubuser.ui.detail.follow

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.search.SearchResponse
import com.example.githubuser.data.search.User
import com.example.githubuser.network.ApiConfig
import com.example.githubuser.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel(){
    private val _isLoading = MutableLiveData<Boolean>()
    private val _isLoadingFollower= MutableLiveData<Boolean>()
    private val _isLoadingFollowing = MutableLiveData<Boolean>()

    val isLoadingFollower: LiveData<Boolean> = _isLoadingFollower
    val isLoadingFollowing: LiveData<Boolean> = _isLoadingFollowing

    init{
        _isLoadingFollower.value =true
        _isLoadingFollowing.value = true

    }


    private val _followerlivedata = MutableLiveData<List<User>>()
    val followerlivedata: LiveData<List<User>> = _followerlivedata

    private val _followinglivedata = MutableLiveData<List<User>>()
    val followinglivedata: LiveData<List<User>> = _followinglivedata

    fun follower(username:String){
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object:Callback<List<User>>{
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if(response.isSuccessful){
                    _followerlivedata.value=response.body()
                    _isLoadingFollower.value = false
                }
            }
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
            }
        })
    }
    fun following(username:String){
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object:Callback<List<User>>{
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if(response.isSuccessful){
                    _followinglivedata.value=response.body()
                    _isLoadingFollowing.value = false
                }
            }
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
            }
        })
    }
}