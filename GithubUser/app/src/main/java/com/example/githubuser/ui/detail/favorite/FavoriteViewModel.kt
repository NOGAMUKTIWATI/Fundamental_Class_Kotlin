package com.example.githubuser.ui.detail.favorite

import androidx.lifecycle.*
import com.example.githubuser.data.detail.DetailResponse
import com.example.githubuser.data.local.FavoriteUser
import com.example.githubuser.repository.FavoriteRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {
    val user = MutableLiveData<DetailResponse>()
    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    init {
        _isFavorite.value = false
    }

    //fungsi get
    fun getFavoriteUser() : LiveData<List<FavoriteUser>>{
        return favoriteRepository.getFavoriteUser()
    }

    //user exist
    fun userExist(username: String) {
        viewModelScope.launch {
            _isFavorite.value = favoriteRepository.userExist(username)
        }
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






}