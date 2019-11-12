package com.example.darryl.assignment2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.Arrays;

public class HardLevelSelection extends AppCompatActivity {
    //References for the GUI components (widgets)
    static ImageView audio;
    static ListView list;

    static int done;
    static int[] hard_done = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    static int[] hard_level = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    static int[] hard_imgId = {R.drawable.hard1, R.drawable.hard2, R.drawable.hard3, R.drawable.hard4, R.drawable.hard5, R.drawable.hard6, R.drawable.hard7, R.drawable.hard8, R.drawable.hard9, R.drawable.hard10};
    static int[] hard_hints = {R.string.hard_level_hint_1, R.string.hard_level_hint_2, R.string.hard_level_hint_3, R.string.hard_level_hint_4, R.string.hard_level_hint_5, R.string.hard_level_hint_6, R.string.hard_level_hint_7, R.string.hard_level_hint_8, R.string.hard_level_hint_9, R.string.hard_level_hint_10};
    static int[] hard_answers = {R.string.hard_level_answer_1, R.string.hard_level_answer_2, R.string.hard_level_answer_3, R.string.hard_level_answer_4, R.string.hard_level_answer_5, R.string.hard_level_answer_6, R.string.hard_level_answer_7, R.string.hard_level_answer_8, R.string.hard_level_answer_9, R.string.hard_level_answer_10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*SharedPreferences sp = getSharedPreferences("Data", MODE_PRIVATE);
        done = sp.getInt("isDone", 0);*/

        Log.d("TAG2", Arrays.toString(hard_done));
        Log.d("TAG", "Selection Done = " + Integer.toString(done));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hard_level_selection);

        //set references
        audio = (ImageView) findViewById(R.id.audio);
        list = (ListView) findViewById(R.id.levelList);

        MyAdapterHard hardAdapter = new MyAdapterHard(this, hard_level, hard_imgId);
        list.setAdapter(hardAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(HardLevelSelection.this, HardLevel.class);
                i.putExtra("hard_done", hard_done[position]);
                i.putExtra("hard_level", hard_level[position]);
                i.putExtra("hard_imgId", hard_imgId[position]);
                i.putExtra("hard_hints", hard_hints[position]);
                i.putExtra("hard_answer", hard_answers[position]);//adds extended data to the intent
                startActivity(i);//if user click on item in listview, direct to HardLevel.class
            }
        });//end listview onItemClickListener

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
            DifficultySelection.audio.setImageResource(R.drawable.audio);//if user press back while audio is playing,
                                                                         //the image in difficulty selection will be set to unmute
        } else {
            DifficultySelection.audio.setImageResource(R.drawable.mute);//if user press back while audio is not playing,
                                                                        //the image in difficulty selection will be set to mute
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.d("TAG", "saved");
    }
}
