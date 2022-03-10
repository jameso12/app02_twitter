package com.codepath.apps.restclienttemplate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.apps.restclienttemplate.models.Tweet
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

private const val TAG = "TimelineActivity"
class TimelineActivity : AppCompatActivity() {
    // Instance of the TwitterCLient needed in order to populate the timeline
    lateinit var client: TwitterClient
    lateinit var rvTimeline: RecyclerView
    lateinit var adapter: TweetsAdapter
    val tweets = ArrayList<Tweet>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)
        Log.i(TAG, "Oncreate")

        client = TwitterApplication.getRestClient(this)

        rvTimeline = findViewById(R.id.rvTimeline)

        adapter = TweetsAdapter(tweets)
        rvTimeline.layoutManager = LinearLayoutManager(this)
        rvTimeline.adapter = adapter


        populateHomeTimeline()
    }
    fun populateHomeTimeline(){
        client.getHomeTimeline(object: JsonHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.i(TAG, "Request successful: $statusCode")
                val jsonArray = json.jsonArray
                val listNewTweets = Tweet.fromJsonArrray(jsonArray)
                tweets.addAll(listNewTweets)
                adapter.notifyDataSetChanged()
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