package com.codepath.apps.restclienttemplate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.apps.restclienttemplate.models.Tweet

class TweetsAdapter(val tweets:List<Tweet>): RecyclerView.Adapter<TweetsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetsAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_tweet, parent, false)
        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TweetsAdapter.ViewHolder, position: Int) {
        val tweet = tweets.get(position)
        holder.tvUserName.text = tweet.user?.screenName
        holder.tvTweetBody.text = tweet.body
        Glide.with(holder.ivProfileView).load(tweet.user?.profilePictureURL).into(holder.ivProfileView)

    }

    override fun getItemCount(): Int {
        return tweets.size
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var ivProfileView = itemView.findViewById<ImageView>(R.id.ivProfilePicture)
        var tvUserName = itemView.findViewById<TextView>(R.id.tvUsername)
        var tvTweetBody = itemView.findViewById<TextView>(R.id.tvTweetBody)

    }

}