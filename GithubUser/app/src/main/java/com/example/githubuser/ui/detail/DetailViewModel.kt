package com.example.githubuser.ui.detail

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.adapter.UserAdapter
import com.example.githubuser.data.detail.DetailResponse
import com.example.githubuser.data.local.FavoriteUser
import com.example.githubuser.network.ApiConfig
import com.example.githubuser.repository.FavoriteRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {
    val user = MutableLiveData<DetailResponse>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = this._isLoading

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    init {
        _isLoading.value = true
        _isFavorite.value = false
    }

    //create
    fun insertFavorite(favoriteUser: FavoriteUser) {
        favoriteRepository.insert(favoriteUser)
        _isFavorite.value = true
    }

    //delete
    fun deleteFavorite(favoriteUser: FavoriteUser) {
        favoriteRepository.delete(favoriteUser)
        _isFavorite.value = false
    }

    //user exist
    fun userExist(username: String) {
        viewModelScope.launch {
            _isFavorite.value = favoriteRepository.userExist(username)
        }
    }

    //fungsi handling
    fun handleFavoriteAction() {
        val favoriteUser = user.value?.let { FavoriteUser(it.login, it.avatarUrl) } ?: return
        if (_isFavorite.value == true) {
            deleteFavorite(favoriteUser)
        } else {
            insertFavorite(favoriteUser)
        }
    }

    //Set data dengan parameter username
    fun setUserDetail(username: String) {
        ApiConfig.getApiService()
            .getUserDetail(username)
            .enqueue(object : Callback<DetailResponse> {
                override fun onResponse(
                    call: Call<DetailResponse>,
                    response: Response<DetailResponse>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                        _isLoading.value = false
                    }
                }

                override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }
            })
    }

    //Read data DetailResponse
    fun getUserDetail(): LiveData<DetailResponse> {
        return user
    }
}