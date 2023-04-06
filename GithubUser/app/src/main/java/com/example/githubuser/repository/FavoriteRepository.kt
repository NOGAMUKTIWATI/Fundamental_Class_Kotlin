package com.example.githubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuser.data.local.FavoriteDao
import com.example.githubuser.data.local.FavoriteRoomDatabase
import com.example.githubuser.data.local.FavoriteUser
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.FavoriteDao()
    }


    suspend fun userExist(username: String): Boolean = mFavoriteDao.isUserIsExist(username)

    fun getFavoriteUser() : LiveData<List<FavoriteUser>> = mFavoriteDao.getFavoriteUser()

    fun insert(favorite: FavoriteUser) {
        executorService.execute { mFavoriteDao.insert(favorite) }
    }

    fun delete(favorite: FavoriteUser) {
        executorService.execute { mFavoriteDao.delete(favorite) }
    }

}