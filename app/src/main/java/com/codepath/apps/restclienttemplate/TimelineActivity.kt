package com.codepath.apps.restclienttemplate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

private const val TAG = "TimelineActivity"
class TimelineActivity : AppCompatActivity() {
    // Instance of the TwitterCLient needed in order to populate the timeline
    lateinit var client: TwitterClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)
        Log.i(TAG, "Oncreate")
        client = TwitterApplication.getRestClient(this)
        populateHomeTimeline()
    }
    fun populateHomeTimeline(){
        client.getHomeTimeline(object: JsonHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.i(TAG, "Request successful: $statusCode")
                //json
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.i(TAG, "Request failed: $statusCode")
                // TODO my API keys returned a 403 status code, but the ones prvided by codepath did not...
            }



        })
    }
}