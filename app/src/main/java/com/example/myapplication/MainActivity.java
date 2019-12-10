package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button startButton;
    private EditText songName;
    private EditText artistName;
    private String songString;
    private String artistString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton = findViewById(R.id.startButton);
        songName = findViewById(R.id.songText);
        artistName = findViewById(R.id.artistText);
        startButton.setOnClickListener(unused -> startButtonClicked());
    }
    private void startButtonClicked() {
        songString = songName.getText().toString();
        artistString = artistName.getText().toString();
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("artist", artistString);
        intent.putExtra("song", songString);
        startActivity(intent);
    }
}
