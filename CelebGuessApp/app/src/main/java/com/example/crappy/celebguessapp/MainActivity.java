package com.example.crappy.celebguessapp;

import android.content.AbstractThreadedSyncAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.R.attr.data;
import static android.R.id.button1;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.support.v7.widget.AppCompatDrawableManager.get;

public class MainActivity extends AppCompatActivity {

        ArrayList<String> celeburls =new ArrayList<String>();
                int chosenceleb=0;
                ImageView imageView;
                int locationofcorrect=0;
    Button b1,b2,b3,b4;
    String[] answers = new String[4];
    ArrayList<String> celebnames =new ArrayList<String>();

    public void celebchosen(View view) {
        if(view.getTag().toString().equals(Integer.toString(locationofcorrect))){
            Toast.makeText(getApplicationContext(),"Correct!",Toast.LENGTH_LONG).show();
        }
            else {
            Toast.makeText(getApplicationContext(),"Wrong! It was "+celebnames.get(chosenceleb),Toast.LENGTH_LONG).show();
        }
        createnewQuestion();
    }

    public class DownloadImage extends AsyncTask<String,Void,Bitmap>{

            @Override
            protected Bitmap doInBackground(String... urls) {
                try {
                    URL url =new URL(urls[0]);
                    HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                    connection.connect();
                    InputStream in=connection.getInputStream();
                    Bitmap myBitmap= BitmapFactory.decodeStream(in);
                    return myBitmap;


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                return null;
            }
        }
    public class DownloadTask extends AsyncTask<String, Void ,String>{

        @Override
        protected String doInBackground(String... urls) {
            String result="";
            URL url;
            HttpURLConnection urlConnection=null;
            try{
                url=new URL(urls[0]);
                urlConnection=(HttpURLConnection)url.openConnection();
                InputStream in =urlConnection.getInputStream();
                InputStreamReader reader=new InputStreamReader(in);
                int data= reader.read();
                while (data!=-1)
                {
                    char current=(char)data;
                    result+=current;
                    data=reader.read();


                }
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    public void  createnewQuestion(){
        Random rand=new Random();
        chosenceleb=rand.nextInt(celeburls.size());
        DownloadImage imagetask= new DownloadImage();
        Bitmap celebimage;
        try {
            celebimage=imagetask.execute(celeburls.get(chosenceleb)).get();

            imageView.setImageBitmap(celebimage);
            locationofcorrect=rand.nextInt(4);
            int incorectanswerlocation;
            for (int i=0;i<4;i++){
                if (i==locationofcorrect)
                {
                    answers[i]=celebnames.get(chosenceleb);
                }
                else{
                    incorectanswerlocation=rand.nextInt(celeburls.size());
                    while (incorectanswerlocation==chosenceleb){
                        incorectanswerlocation=rand.nextInt(celeburls.size());
                    }
                    answers[i]=celebnames.get(incorectanswerlocation);

                }

            }
            b1.setText(answers[0]);
            b2.setText(answers[1]);
            b3.setText(answers[2]);
            b4.setText(answers[3]);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DownloadTask task=new DownloadTask();
        String result="";
         b1=(Button)findViewById(R.id.button);
        b2=(Button)findViewById(R.id.button2);
        b3=(Button)findViewById(R.id.button3);
        b4=(Button)findViewById(R.id.button4);
        imageView=(ImageView)findViewById(R.id.imageView);

        try {
            result= task.execute("http://www.posh24.se/lady_gaga/time_top_100_list").get();

            String[] splitresult=result.split("<div class=\"tags\">");
            Pattern p=Pattern.compile("img src=\"(.*?)\"");
            Matcher m= p.matcher(splitresult[0]);
            while (m.find()){
                celeburls.add(m.group(1));
                Log.i("1st",m.group(1));
            }
            p=Pattern.compile("alt=\"(.*?)\"");
             m=p.matcher(splitresult[0]);
            while (m.find()){
                celebnames.add(m.group(1));
                Log.i("2d",m.group(1));
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        createnewQuestion();
    }

}
