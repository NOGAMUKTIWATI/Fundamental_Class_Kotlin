package com.example.githubuser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.R
import com.example.githubuser.data.local.FavoriteUser
import com.example.githubuser.data.search.SearchResponse
import com.example.githubuser.data.search.User
import com.example.githubuser.ui.detail.DetailActivity
import com.example.githubuser.ui.detail.DetailActivity.Companion.EXTRA_USERNAME

class FavoriteAdapter(private val listUser: List<FavoriteUser>) : RecyclerView.Adapter<FavoriteAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.cardlist, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(listUser[position].avatarUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.imgPhoto)
        holder.tvName.text = listUser[position].username
        holder.itemView.setOnClickListener {
           val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(EXTRA_USERNAME, listUser[position].username)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listUser.size

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        val tvName: TextView = itemView.findViewById(R.id.tv_item_name)
    }
}
