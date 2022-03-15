package com.codepath.apps.restclienttemplate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.codepath.apps.restclienttemplate.models.Tweet
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

const val MAX_WORD_COUNT = 140
private const val TAG = "ComposeActivity"
class ComposeActivity : AppCompatActivity() {
    lateinit var btnTweetCompose: Button
    lateinit var etTweet: EditText
    lateinit var client:TwitterClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose)
        etTweet = findViewById(R.id.etMultiLineTweet)
        btnTweetCompose = findViewById(R.id.buttonTweet)
        client = TwitterApplication.getRestClient(this)
        // Handling user click
        btnTweetCompose.setOnClickListener{
            // Grab content
            val tweetContent = etTweet.text.toString()
            // Make sure not empty
            if(tweetContent.isEmpty()){
                Toast.makeText(this, "Tweet cannot be empty!", Toast.LENGTH_SHORT).show()
                // TODO use snackbar ...? lest?
            }
            // Make sure tweet is under allowed count
            else if(tweetContent.length> MAX_WORD_COUNT){
                Toast.makeText(this, "Tweet too long!", Toast.LENGTH_SHORT).show()
                // TODO use snackbar ...? tricky decisions can be in the Lands in Between...(Lands in between are the map in Elden Ring)
            }
            // Make API call
            else {
                client.putTweet(tweetContent,object:JsonHttpResponseHandler(){
                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        response: String?,
                        throwable: Throwable?
                    ) {
                        Log.i(TAG,"Tweet Post failed! Status code: $statusCode", throwable)
                    }

                    override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                        Log.i(TAG, "Tweet added!")
                        val tweet = Tweet.fromJSON(json.jsonObject)
                        val intent = Intent()
                        intent.putExtra("newTweet",tweet)
                        setResult(RESULT_OK, intent)
                        finish() // closes this activity
                    }
                })
            }

        }
    }
}