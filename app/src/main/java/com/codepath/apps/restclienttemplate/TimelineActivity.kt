package com.codepath.apps.restclienttemplate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.codepath.apps.restclienttemplate.models.Tweet
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

private const val TAG = "TimelineActivity"
class TimelineActivity : AppCompatActivity() {
    // Instance of the TwitterCLient needed in order to populate the timeline
    lateinit var client: TwitterClient
    lateinit var rvTimeline: RecyclerView
    lateinit var adapter: TweetsAdapter
    lateinit var swipeContainer: SwipeRefreshLayout
    val tweets = ArrayList<Tweet>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)
        Log.i(TAG, "Oncreate")

        client = TwitterApplication.getRestClient(this)

        swipeContainer = findViewById(R.id.swipeContainer)
        swipeContainer.setOnRefreshListener {
            Log.i(TAG,"On Refresh listener")
            populateHomeTimeline()
        }
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light
        );

        rvTimeline = findViewById(R.id.rvTimeline)

        adapter = TweetsAdapter(tweets)
        rvTimeline.layoutManager = LinearLayoutManager(this)
        rvTimeline.adapter = adapter


        populateHomeTimeline()
    }
    // makes menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    // handles menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.compose){
            Toast.makeText(this,"compose clicked", Toast.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }
    fun populateHomeTimeline(){
        client.getHomeTimeline(object: JsonHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.i(TAG, "Request successful: $statusCode")
                val jsonArray = json.jsonArray
                try {
                    adapter.clear()
                    val listNewTweets = Tweet.fromJsonArrray(jsonArray)
                    tweets.addAll(listNewTweets)
                    adapter.notifyDataSetChanged()
                    // Now we call setRefreshing(false) to signal refresh has finished
                    swipeContainer.setRefreshing(false)
                } catch (e:JSONException){
                    Log.e(TAG,"JSON Failure: $e")
                }
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