package com.example.githubuser.ui.home

import androidx.lifecycle.*
import com.example.githubuser.network.ApiConfig
import com.example.githubuser.data.search.SearchResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel (private val pref: SettingPreferences) : ViewModel() {
    private val _userlivedata = MutableLiveData<SearchResponse>()
    val userlivedata: LiveData<SearchResponse> = _userlivedata

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init{
        _isLoading.value = true
        getQuery("noga")
    }
    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }

    fun getQuery(query:String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().search(query)
        client.enqueue(object:Callback<SearchResponse>{
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful){
                    _userlivedata.value = response.body()
                    _isLoading.value =false
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {

            }
        })
    }
    companion object{
        private const val TAG = "MainViewModel"
        private const val USERNAME_GITHUB = "uewq1zg2zlskfw1e867"
    }
}
