package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class GameActivity extends AppCompatActivity {
    private static final String TAG = "GameActivity";
    private String artist;
    private String song;
    private TextView lyrics;
    private static RequestQueue requestQueue;
    private Button newSong;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        //Log.i(TAG, "Creating");
        // The "super" call is required for all activities
        super.onCreate(savedInstanceState);
        // Create the UI from the activity_game.xml layout file (in src/main/res/layout)
        setContentView(R.layout.activity_game);
        //
        lyrics = findViewById(R.id.lyrics);
        Intent intent = getIntent();
        artist = intent.getStringExtra("artist");
        song = intent.getStringExtra("song");
        String test = artist + ": " + song;
        lyrics.setText(test);
        Log.i(TAG, "About to call API");
        System.out.println("About to call API");

        newSong = findViewById(R.id.nsButton);
        newSong.setOnClickListener(unused -> finish());

        String url = "https://api.lyrics.ovh/v1/";
        requestQueue = Volley.newRequestQueue(this);
        try {
            JsonObjectRequest jsonOR = new JsonObjectRequest(
                    Request.Method.GET, url + artist + "/" + song, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            try {
                                lyrics.setText(response.getString("lyrics"));
                                lyrics.setMovementMethod(new ScrollingMovementMethod());
                                Log.d(TAG, response.toString());
                            } catch (Exception e) {
                                Log.w(TAG, e.toString());
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                    Log.w(TAG, error.toString());
                    CharSequence text = "Song not found!";
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    finish();
                }
            });
            requestQueue.add(jsonOR);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}