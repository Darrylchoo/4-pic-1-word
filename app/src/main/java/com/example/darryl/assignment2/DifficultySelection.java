package com.example.darryl.assignment2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class DifficultySelection extends AppCompatActivity {
    //References for the GUI components (widgets)
    Button btnEasy;
    Button btnMedium;
    Button btnHard;
    static ImageView audio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_selection);

        //set references
        btnEasy = (Button) findViewById(R.id.btnEasy);
        btnMedium = (Button) findViewById(R.id.btnMedium);
        btnHard = (Button) findViewById(R.id.btnHard);
        audio = (ImageView) findViewById(R.id.audio);

        btnEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(DifficultySelection.this, EasyLevelSelection.class);
                startActivity(i1);//if btnEasy is clicked, direct to EasyLevelSelection.class
            }
        });

        btnMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(DifficultySelection.this, MediumLevelSelection.class);
                startActivity(i2);//if btnMedium is clicked, direct to MediumLevelSelection.class
            }
        });

        btnHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3 = new Intent(DifficultySelection.this, HardLevelSelection.class);
                startActivity(i3);//if btnHard is clicked, direct to HardLevelSelection.class
            }
        });

        if (MainMenu.mp.isPlaying()) {
            audio.setImageResource(R.drawable.audio);//if the audio is playing, the image will be set to unmute
        } else {
            audio.setImageResource(R.drawable.mute);//if the audio is not playing, the image will be set to mute
        }

        audio.setClickable(true);
        audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainMenu.mp.isPlaying()) {
                    audio.setImageResource(R.drawable.mute);
                    MainMenu.mp.pause();//if user clicks on the image while the audio is playing, the image will be set to
                                        //mute and the audio will pause
                } else {
                    audio.setImageResource(R.drawable.audio);
                    MainMenu.mp.start();//if user clicks on the image while the audio is not playing, the image will be set to
                                        //unmute and the audio will continue
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (MainMenu.mp.isPlaying()) {
            MainMenu.audio.setImageResource(R.drawable.audio);//if user press back while audio is playing,
                                                              //the image in main menu will be set to unmute
        } else {
            MainMenu.audio.setImageResource(R.drawable.mute);//if user press back while audio is not playing,
                                                             //the image in main menu will be set to mute
        }

        Intent i = new Intent(DifficultySelection.this, MainMenu.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);//this intent will directly return user to main menu without going to previous activities
                         //on back pressed
    }
}
