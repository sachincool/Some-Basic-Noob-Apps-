package com.example.crappy.basicphrasis;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void tabbedbutton(View view) {
       String ourId=view.getResources().getResourceEntryName(view.getId());

        int resourseid=getResources().getIdentifier(ourId,"raw",getPackageName());
        MediaPlayer mplayer =MediaPlayer.create(this,resourseid);
        mplayer.start();
    }
}
