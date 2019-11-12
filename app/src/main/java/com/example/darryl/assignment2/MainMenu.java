package com.example.darryl.assignment2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {
    //References for the GUI components (widgets)
    Button btnPlay;
    Button btnQuit;
    static ImageView audio;
    static MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //set references
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnQuit = (Button) findViewById(R.id.btnQuit);
        audio = (ImageView) findViewById(R.id.audio);
        mp = MediaPlayer.create(MainMenu.this, R.raw.song);//create media player

        mp.start();//start the media player
        mp.setLooping(true);//the media player will loop
        mp.setVolume(100, 100);

        btnQuit.setOnClickListener(this);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenu.this, DifficultySelection.class);
                startActivity(i);//if btnPlay is clicked, direct to MainActivity.class
            }//end onClick method
        });//end button onClickListener

        if (MainMenu.mp.isPlaying()) {
            audio.setImageResource(R.drawable.audio);//if the audio is playing, the image will be set to unmute
        } else {
            audio.setImageResource(R.drawable.mute);//if the audio is not playing, the image will be set to mute
        }

        audio.setClickable(true);
        audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mp.isPlaying()) {
                    audio.setImageResource(R.drawable.mute);
                    mp.pause();//if user clicks on the image while the audio is playing, the image will be set to
                               //mute and the audio will pause
                } else {
                    audio.setImageResource(R.drawable.audio);
                    mp.start();//if user clicks on the image while the audio is not playing, the image will be set to
                               //unmute and the audio will continue
                }
            }
        });
    }

    //if the app is destroyed, the media player will be released
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mp != null) {
            mp.release();
        }
    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.btnQuit){
            showAlertBox();
        }//if btnQuit is clicked, an alert box will appeared
    }//end onClick

    public void showAlertBox(){
        AlertDialog.Builder box = new AlertDialog.Builder(this);

        box.setTitle("Confirmation");
        box.setMessage("Do you want to quit?");
        box.setIcon(R.mipmap.ic_launcher_round);

        box.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                finish();//setPositiveButton is clicked, the app will terminate
            }//end onClick method
        });//end setPositiveButton

        box.setNegativeButton("No", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                //no implementation
            }//end onClick method
        });//end setNegativeButton

        box.create();//create the box
        box.show();//display the box
    }//end showAlert
}//end class
