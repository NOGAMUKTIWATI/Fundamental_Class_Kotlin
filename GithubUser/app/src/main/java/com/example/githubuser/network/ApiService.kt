package com.example.githubuser.network

import com.example.githubuser.data.detail.DetailResponse
import com.example.githubuser.data.search.SearchResponse
import com.example.githubuser.data.search.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token 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")
     fun search (
        @Query("q") query:String
    ): Call<SearchResponse>

     //api detail
     @GET("users/{username}")
     @Headers("Authorization: token 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")
     fun getUserDetail(
         @Path("username") username:String
     ):Call<DetailResponse>

     //api followers
     @GET("users/{username}/followers")
     @Headers("Authirization: token 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")
     fun getFollowers(
         @Path("username") username:String
     ): Call<List<User>>

    //api following
    @GET("users/{username}/following")
    @Headers("Authirization: token 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")
    fun getFollowing(
        @Path("username") username:String
    ): Call<List<User>>
}