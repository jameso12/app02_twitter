package com.codepath.apps.restclienttemplate.models

import com.codepath.apps.restclienttemplate.TimeFormatter.getTimeDifference
import org.json.JSONArray
import org.json.JSONObject

class Tweet {
    var body :String = ""
    var dateCreated = ""
    var user :User? = null
    var relativeTime = "" // TODO change
    companion object{
        fun fromJSON(jsonObj : JSONObject):Tweet{
            val tweet = Tweet()
            tweet.body = jsonObj.getString("text")
            tweet.dateCreated = jsonObj.getString("created_at")
            tweet.relativeTime = getFormattedTime(tweet.dateCreated)
            tweet.user = User.fromJSON(jsonObj.getJSONObject("user"))
            return  tweet
        }
        fun getFormattedTime(dateCreated:String):String{
            // Gets date string from the json object and returns the relative time
            var relTime: String = ""
            relTime = getTimeDifference(dateCreated)
            // Calls the respective TimeFormatter functions
            return relTime
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