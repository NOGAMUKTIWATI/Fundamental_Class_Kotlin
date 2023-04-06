package com.example.githubuser.data.detail

import com.google.gson.annotations.SerializedName

data class DetailResponse(

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("followers_url")
	val followersUrl: String,

	@field:SerializedName("following_url")
	val followingUrl: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("following")
	val following: Int,

	@field:SerializedName("followers")
	val followers: Int,
)
