package com.codepath.apps.restclienttemplate.models

import org.json.JSONArray
import org.json.JSONObject

class Tweet {
    var body :String = ""
    var dateCreated = ""
    var user :User? = null
    companion object{
        fun fromJSON(jsonObj : JSONObject):Tweet{
            val tweet = Tweet()
            tweet.body = jsonObj.getString("text")
            tweet.dateCreated = jsonObj.getString("created_at")
            tweet.user = User.fromJSON(jsonObj.getJSONObject("user"))
            return  tweet
        }

        fun fromJsonArrray(jsonArray: JSONArray):List<Tweet>{
            val tweets = ArrayList<Tweet>()
            for(i in 0 until jsonArray.length()){
                tweets.add(fromJSON(jsonArray.getJSONObject(i)))
            }
            return  tweets
        }
    }
}