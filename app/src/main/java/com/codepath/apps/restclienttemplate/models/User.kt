package com.codepath.apps.restclienttemplate.models

import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import org.json.JSONObject

class User {
    var name :String = ""
    var screenName :String = ""
    var profilePictureURL :String = ""
    companion object {
        fun fromJSON(jsonObj :JSONObject): User{
            val user = User()
            user.name = jsonObj.getString("name")
            user.screenName = jsonObj.getString("screen_name")
            user.profilePictureURL = jsonObj.getString("profile_image_url_https")
            return user
        }
    }
}