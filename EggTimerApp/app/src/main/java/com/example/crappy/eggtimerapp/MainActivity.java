package com.example.crappy.eggtimerapp;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    SeekBar timer;
     TextView t1;
    Boolean countertime=false;
    Button b1;
    CountDownTimer countdown;
        public void updateTimer(int secondsleft){

        int minutes = (int) secondsleft/60;
        String seconds= Integer.toString(secondsleft -minutes*60);

        if(seconds.equals("0"))
        {seconds="00";}
        else {
            if( Integer.parseInt(seconds) <= 9){seconds ="0"+seconds;}
        }

        Log.i("SECONDS",seconds);

        t1.setText(Integer.toString(minutes)+":"+seconds);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer=(SeekBar)findViewById(R.id.timerseekbar);
        t1=(TextView)findViewById(R.id.textView);
        timer.setMax(600);
        timer.setProgress(30);
        b1=(Button)findViewById(R.id.button);
        timer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }});




    }

    public void controltimer(View view) {
        if (countertime == false) {
            countdown=new CountDownTimer(timer.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                    countertime = true;
                    timer.setEnabled(false);
                    b1.setText("Stop!");
                }

                @Override
                public void onFinish() {
                    t1.setText("0:00");
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mplayer.start();
                    Log.i("finished", "timer done");

                }
            }.start();

        }
        else{
            t1.setText("0:30");
            timer.setProgress(30);
            countdown.cancel();
            b1.setText("Go!");
            timer.setEnabled(true);
            countertime=false;
        }
    }
}
