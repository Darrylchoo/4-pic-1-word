package com.example.darryl.assignment2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MediumLevel extends AppCompatActivity {
    //References for the GUI components (widgets)
    TextView txtTitle;
    TextView txtCoin;
    TextView txtHint;
    EditText txtAnswer;
    ImageView img;
    static ImageView audio;
    ImageView imgHint;
    ImageView imgNext;
    ImageView imgPrev;
    Button btnCheck;
    MediaPlayer player1;
    MediaPlayer player2;
    ViewGroup layout;

    int coin;
    int level;
    int done;
    String answer;
    String correct;
    String hint;

    CoinStorage cs = CoinStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medium_level);

        coin = cs.getCoin();

        //set references
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtCoin = (TextView) findViewById(R.id.txtCoin);
        txtHint = (TextView) findViewById(R.id.txtHint);
        txtAnswer = (EditText) findViewById(R.id.txtAnswer);
        img = (ImageView) findViewById(R.id.img);
        audio = (ImageView) findViewById(R.id.audio);
        imgHint = (ImageView) findViewById(R.id.imgHint);
        imgNext = (ImageView) findViewById(R.id.imgNext);
        imgPrev = (ImageView) findViewById(R.id.imgPrev);
        btnCheck = (Button) findViewById(R.id.btnCheck);
        player1 = MediaPlayer.create(MediumLevel.this, R.raw.correct);
        player2 = MediaPlayer.create(MediumLevel.this, R.raw.wrong);
        layout = (ViewGroup) btnCheck.getParent();

        final Bundle bundle = getIntent().getExtras();//fetches data and returns the intent that started this activity
        if (bundle != null) {
            level = bundle.getInt("medium_level");
            txtTitle.setText("Level " + level);

            img.setImageResource(bundle.getInt("medium_imgId"));
            txtCoin.setText("" + coin);

            imgHint.setClickable(true);
            imgHint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animation animation = new AlphaAnimation(1.0f, 0.5f);
                    animation.setDuration(1000);
                    imgHint.setAnimation(animation);

                    hint = getString(bundle.getInt("medium_hints"));
                    txtHint.setText(hint);

                    if (coin < 5) {
                        txtHint.setText("Sorry, not enough coin.");//this message will appear if there are not enough coins
                    }

                    if (coin >= 5) {
                        cs.setCoin(coin -= 5);
                        coin = cs.getCoin();
                        txtCoin.setText("" + coin);//if there are enough coins, coins will decrease by 5
                        imgHint.setClickable(false);//after clicking hint, the image will not be clickable
                    }
                }
            });

            imgNext.setVisibility(View.INVISIBLE);//image will be invisible
            imgPrev.setVisibility(View.INVISIBLE);

            answer = getString((bundle.getInt("medium_answer")));
            btnCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (txtAnswer.getText().toString().equalsIgnoreCase(answer)) {
                        Toast.makeText(getApplicationContext(), "Congratulations!!!", Toast.LENGTH_SHORT).show();

                        cs.setCoin(coin += 3);
                        coin = cs.getCoin();
                        txtCoin.setText("" + coin);

                        correct = txtAnswer.getText().toString();
                        txtHint.setText(correct);
                        txtHint.setAllCaps(true);

                        //remove widgets
                        if (layout != null) {
                            layout.removeView(btnCheck);
                            layout.removeView(imgHint);
                            layout.removeView(txtAnswer);
                        }

                        done = 1;
                        MediumLevelSelection.medium_done[level - 1] = 1;

                        player1.start();

                        if (level - 1 == 0) {
                            imgPrev.setVisibility(View.INVISIBLE);

                            imgNext.setVisibility(View.VISIBLE);
                            imgNext.setClickable(true);
                            imgNext.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (level - 1 <= 10) {
                                        level++;

                                        if (MediumLevelSelection.medium_done[level - 1] == 1) {
                                            level = MediumLevelSelection.medium_level[level - 1];
                                            txtTitle.setText("Level " + level);

                                            img.setImageResource(MediumLevelSelection.medium_imgId[level - 1]);

                                            correct = getString(MediumLevelSelection.medium_answers[level - 1]);
                                            txtHint.setText(correct);
                                            txtHint.setAllCaps(true);

                                            if (layout != null) {
                                                layout.removeView(btnCheck);
                                                layout.removeView(imgHint);
                                                layout.removeView(txtAnswer);
                                            }
                                        } else {
                                            if (level - 1 > 9) {
                                                Intent i = new Intent(MediumLevel.this, MediumLevelSelection.class);
                                                startActivity(i);
                                            } else {
                                                imgNext.setVisibility(View.INVISIBLE);
                                                imgPrev.setVisibility(View.INVISIBLE);

                                                //add widgets
                                                if (layout != null) {
                                                    layout.addView(btnCheck);
                                                    layout.addView(imgHint);
                                                    layout.addView(txtAnswer);
                                                }

                                                txtHint.setText("");
                                                txtHint.setAllCaps(false);
                                                txtAnswer.setText("");

                                                level = MediumLevelSelection.medium_level[level - 1];
                                                txtTitle.setText("Level " + level);

                                                img.setImageResource(MediumLevelSelection.medium_imgId[level - 1]);

                                                imgHint.setClickable(true);
                                                imgHint.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        Animation animation = new AlphaAnimation(1.0f, 0.5f);
                                                        animation.setDuration(1000);
                                                        imgHint.setAnimation(animation);

                                                        hint = getString(MediumLevelSelection.medium_hints[level - 1]);
                                                        txtHint.setText(hint);

                                                        if (coin < 5) {
                                                            txtHint.setText("Sorry, not enough coin.");
                                                        }

                                                        if (coin >= 5) {
                                                            cs.setCoin(coin -= 5);
                                                            coin = cs.getCoin();
                                                            txtCoin.setText("" + coin);
                                                            imgHint.setClickable(false);
                                                        }
                                                    }
                                                });

                                                answer = getString(MediumLevelSelection.medium_answers[level - 1]);
                                            }
                                        }
                                    }
                                }
                            });
                        } else {
                            imgPrev.setVisibility(View.VISIBLE);
                            imgPrev.setClickable(true);
                            imgPrev.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (level - 1 <= 10) {
                                        level--;

                                        if (level - 1 == 0) {
                                            imgPrev.setVisibility(View.INVISIBLE);
                                        }

                                        if (MediumLevelSelection.medium_done[level - 1] != 1) {
                                            imgNext.setVisibility(View.INVISIBLE);
                                            imgPrev.setVisibility(View.INVISIBLE);

                                            if (layout != null) {
                                                layout.addView(btnCheck);
                                                layout.addView(imgHint);
                                                layout.addView(txtAnswer);
                                            }

                                            txtHint.setText("");
                                            txtHint.setAllCaps(false);
                                            txtAnswer.setText("");

                                            level = MediumLevelSelection.medium_level[level - 1];
                                            txtTitle.setText("Level " + level);

                                            img.setImageResource(MediumLevelSelection.medium_imgId[level - 1]);

                                            imgHint.setClickable(true);
                                            imgHint.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Animation animation = new AlphaAnimation(1.0f, 0.5f);
                                                    animation.setDuration(1000);
                                                    imgHint.setAnimation(animation);

                                                    hint = getString(MediumLevelSelection.medium_hints[level - 1]);
                                                    txtHint.setText(hint);

                                                    if (coin < 5) {
                                                        txtHint.setText("Sorry, not enough coin.");
                                                    }

                                                    if (coin >= 5) {
                                                        cs.setCoin(coin -= 5);
                                                        coin = cs.getCoin();
                                                        txtCoin.setText("" + coin);
                                                        imgHint.setClickable(false);
                                                    }
                                                }
                                            });

                                            answer = getString(MediumLevelSelection.medium_answers[level - 1]);
                                        } else {
                                            level = MediumLevelSelection.medium_level[level - 1];
                                            txtTitle.setText("Level " + level);

                                            img.setImageResource(MediumLevelSelection.medium_imgId[level - 1]);

                                            correct = getString(MediumLevelSelection.medium_answers[level - 1]);
                                            txtHint.setText(correct);
                                            txtHint.setAllCaps(true);

                                            if (layout != null) {
                                                layout.removeView(btnCheck);
                                                layout.removeView(imgHint);
                                                layout.removeView(txtAnswer);
                                            }
                                        }
                                    }
                                }
                            });

                            //if level is between 1 and 9, imgNext will be visible
                            if(level >= 1 && level <= 9) {
                                imgNext.setVisibility(View.VISIBLE);
                            }
                            imgNext.setClickable(true);
                            imgNext.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (level - 1 <= 10) {
                                        level++;

                                        if (MediumLevelSelection.medium_done[level - 1] == 1) {
                                            level = MediumLevelSelection.medium_level[level - 1];
                                            txtTitle.setText("Level " + level);

                                            img.setImageResource(MediumLevelSelection.medium_imgId[level - 1]);

                                            correct = getString(MediumLevelSelection.medium_answers[level - 1]);
                                            txtHint.setText(correct);
                                            txtHint.setAllCaps(true);

                                            if (layout != null) {
                                                layout.removeView(btnCheck);
                                                layout.removeView(imgHint);
                                                layout.removeView(txtAnswer);
                                            }
                                        } else {
                                            if (level - 1 > 9) {
                                                Intent i = new Intent(MediumLevel.this, MediumLevelSelection.class);
                                                startActivity(i);
                                            } else {
                                                imgNext.setVisibility(View.INVISIBLE);
                                                imgPrev.setVisibility(View.INVISIBLE);

                                                if (layout != null) {
                                                    layout.addView(btnCheck);
                                                    layout.addView(imgHint);
                                                    layout.addView(txtAnswer);
                                                }

                                                txtHint.setText("");
                                                txtHint.setAllCaps(false);
                                                txtAnswer.setText("");

                                                level = MediumLevelSelection.medium_level[level - 1];
                                                txtTitle.setText("Level " + level);

                                                img.setImageResource(MediumLevelSelection.medium_imgId[level - 1]);

                                                imgHint.setClickable(true);
                                                imgHint.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        Animation animation = new AlphaAnimation(1.0f, 0.5f);
                                                        animation.setDuration(1000);
                                                        imgHint.setAnimation(animation);

                                                        hint = getString(MediumLevelSelection.medium_hints[level - 1]);
                                                        txtHint.setText(hint);

                                                        if (coin < 5) {
                                                            txtHint.setText("Sorry, not enough coin.");
                                                        }

                                                        if (coin >= 5) {
                                                            cs.setCoin(coin -= 5);
                                                            coin = cs.getCoin();
                                                            txtCoin.setText("" + coin);
                                                            imgHint.setClickable(false);
                                                        }
                                                    }
                                                });

                                                answer = getString(MediumLevelSelection.medium_answers[level - 1]);
                                            }
                                        }
                                    }
                                }
                            });
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Incorrect!!!", Toast.LENGTH_SHORT).show();
                        player2.start();
                    }
                }
            });
        }

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
        Log.d("TAG1", "Level done = " + Integer.toString(done));
        super.onBackPressed();

        if (MainMenu.mp.isPlaying()) {
            MediumLevelSelection.audio.setImageResource(R.drawable.audio);//if user press back while audio is playing,
                                                                          //the image in main menu will be set to unmute
        } else {
            MediumLevelSelection.audio.setImageResource(R.drawable.mute);//if user press back while audio is not playing,
                                                                         //the image in main menu will be set to mute
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        //when user returns to listview and back to the level which is answered, the answer will be displayed instead of \
        //prompting user to answer again
        final Bundle bundle = getIntent().getExtras();
        if(bundle.getInt("medium_done") == 1) {
            correct = getString(bundle.getInt("medium_answer"));
            txtHint.setText(correct);
            txtHint.setAllCaps(true);

            if (layout != null) {
                layout.removeView(btnCheck);
                layout.removeView(imgHint);
                layout.removeView(txtAnswer);
            }

            if (level - 1 == 0) {
                imgPrev.setVisibility(View.INVISIBLE);

                imgNext.setVisibility(View.VISIBLE);
                imgNext.setClickable(true);
                imgNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (level - 1 <= 10) {
                            level++;

                            if (MediumLevelSelection.medium_done[level - 1] == 1) {
                                level = MediumLevelSelection.medium_level[level - 1];
                                txtTitle.setText("Level " + level);

                                img.setImageResource(MediumLevelSelection.medium_imgId[level - 1]);

                                correct = getString(MediumLevelSelection.medium_answers[level - 1]);
                                txtHint.setText(correct);
                                txtHint.setAllCaps(true);

                                if (layout != null) {
                                    layout.removeView(btnCheck);
                                    layout.removeView(imgHint);
                                    layout.removeView(txtAnswer);
                                }
                            } else {
                                if (level - 1 > 9) {
                                    Intent i = new Intent(MediumLevel.this, MediumLevelSelection.class);
                                    startActivity(i);
                                } else {
                                    imgNext.setVisibility(View.INVISIBLE);
                                    imgPrev.setVisibility(View.INVISIBLE);

                                    if (layout != null) {
                                        layout.addView(btnCheck);
                                        layout.addView(imgHint);
                                        layout.addView(txtAnswer);
                                    }

                                    txtHint.setText("");
                                    txtHint.setAllCaps(false);
                                    txtAnswer.setText("");

                                    level = MediumLevelSelection.medium_level[level - 1];
                                    txtTitle.setText("Level " + level);

                                    img.setImageResource(MediumLevelSelection.medium_imgId[level - 1]);

                                    imgHint.setClickable(true);
                                    imgHint.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Animation animation = new AlphaAnimation(1.0f, 0.5f);
                                            animation.setDuration(1000);
                                            imgHint.setAnimation(animation);

                                            hint = getString(MediumLevelSelection.medium_hints[level - 1]);
                                            txtHint.setText(hint);

                                            if (coin < 5) {
                                                txtHint.setText("Sorry, not enough coin.");
                                            }

                                            if (coin >= 5) {
                                                cs.setCoin(coin -= 5);
                                                coin = cs.getCoin();
                                                txtCoin.setText("" + coin);
                                                imgHint.setClickable(false);
                                            }
                                        }
                                    });

                                    answer = getString(MediumLevelSelection.medium_answers[level - 1]);
                                }
                            }
                        }
                    }
                });
            } else {
                imgPrev.setVisibility(View.VISIBLE);
                imgPrev.setClickable(true);
                imgPrev.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (level - 1 <= 10) {
                            level--;

                            if (level - 1 == 0) {
                                imgPrev.setVisibility(View.INVISIBLE);
                            }

                            if (MediumLevelSelection.medium_done[level - 1] != 1) {
                                imgNext.setVisibility(View.INVISIBLE);
                                imgPrev.setVisibility(View.INVISIBLE);

                                if (layout != null) {
                                    layout.addView(btnCheck);
                                    layout.addView(imgHint);
                                    layout.addView(txtAnswer);
                                }

                                txtHint.setText("");
                                txtHint.setAllCaps(false);
                                txtAnswer.setText("");

                                level = MediumLevelSelection.medium_level[level - 1];
                                txtTitle.setText("Level " + level);

                                img.setImageResource(MediumLevelSelection.medium_imgId[level - 1]);

                                imgHint.setClickable(true);
                                imgHint.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Animation animation = new AlphaAnimation(1.0f, 0.5f);
                                        animation.setDuration(1000);
                                        imgHint.setAnimation(animation);

                                        hint = getString(MediumLevelSelection.medium_hints[level - 1]);
                                        txtHint.setText(hint);

                                        if (coin < 5) {
                                            txtHint.setText("Sorry, not enough coin.");
                                        }

                                        if (coin >= 5) {
                                            cs.setCoin(coin -= 5);
                                            coin = cs.getCoin();
                                            txtCoin.setText("" + coin);
                                            imgHint.setClickable(false);
                                        }
                                    }
                                });

                                answer = getString(MediumLevelSelection.medium_answers[level - 1]);
                            } else {
                                level = MediumLevelSelection.medium_level[level - 1];
                                txtTitle.setText("Level " + level);

                                img.setImageResource(MediumLevelSelection.medium_imgId[level - 1]);

                                correct = getString(MediumLevelSelection.medium_answers[level - 1]);
                                txtHint.setText(correct);
                                txtHint.setAllCaps(true);

                                if (layout != null) {
                                    layout.removeView(btnCheck);
                                    layout.removeView(imgHint);
                                    layout.removeView(txtAnswer);
                                }
                            }
                        }
                    }
                });

                if(level >= 1 && level <= 9) {
                    imgNext.setVisibility(View.VISIBLE);
                }
                imgNext.setClickable(true);
                imgNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (level - 1 <= 10) {
                            level++;

                            if (MediumLevelSelection.medium_done[level - 1] == 1) {
                                level = MediumLevelSelection.medium_level[level - 1];
                                txtTitle.setText("Level " + level);

                                img.setImageResource(MediumLevelSelection.medium_imgId[level - 1]);

                                correct = getString(MediumLevelSelection.medium_answers[level - 1]);
                                txtHint.setText(correct);
                                txtHint.setAllCaps(true);

                                if (layout != null) {
                                    layout.removeView(btnCheck);
                                    layout.removeView(imgHint);
                                    layout.removeView(txtAnswer);
                                }
                            } else {
                                if (level - 1 > 9) {
                                    Intent i = new Intent(MediumLevel.this, MediumLevelSelection.class);
                                    startActivity(i);
                                } else {
                                    imgNext.setVisibility(View.INVISIBLE);
                                    imgPrev.setVisibility(View.INVISIBLE);

                                    if (layout != null) {
                                        layout.addView(btnCheck);
                                        layout.addView(imgHint);
                                        layout.addView(txtAnswer);
                                    }

                                    txtHint.setText("");
                                    txtHint.setAllCaps(false);
                                    txtAnswer.setText("");

                                    level = MediumLevelSelection.medium_level[level - 1];
                                    txtTitle.setText("Level " + level);

                                    img.setImageResource(MediumLevelSelection.medium_imgId[level - 1]);

                                    imgHint.setClickable(true);
                                    imgHint.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Animation animation = new AlphaAnimation(1.0f, 0.5f);
                                            animation.setDuration(1000);
                                            imgHint.setAnimation(animation);

                                            hint = getString(MediumLevelSelection.medium_hints[level - 1]);
                                            txtHint.setText(hint);

                                            if (coin < 5) {
                                                txtHint.setText("Sorry, not enough coin.");
                                            }

                                            if (coin >= 5) {
                                                cs.setCoin(coin -= 5);
                                                coin = cs.getCoin();
                                                txtCoin.setText("" + coin);
                                                imgHint.setClickable(false);
                                            }
                                        }
                                    });

                                    answer = getString(MediumLevelSelection.medium_answers[level - 1]);
                                }
                            }
                        }
                    }
                });
            }
        }
    }
}
