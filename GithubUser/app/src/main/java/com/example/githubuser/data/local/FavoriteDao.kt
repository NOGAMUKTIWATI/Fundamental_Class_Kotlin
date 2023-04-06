package com.example.githubuser.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: FavoriteUser)

    @Delete
    fun delete(favorite: FavoriteUser)

    @Query("SELECT EXISTS(SELECT * FROM favoriteuser WHERE username = :username)")
    suspend fun isUserIsExist(username : String) : Boolean

    @Query("SELECT * FROM favoriteuser")
    fun getFavoriteUser(): LiveData<List<FavoriteUser>>
}
