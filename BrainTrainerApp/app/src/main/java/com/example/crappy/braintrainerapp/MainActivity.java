package com.example.crappy.braintrainerapp;

import android.os.CountDownTimer;
import android.support.constraint.Group;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ArrayList<Integer> answer=new ArrayList<Integer>();
    TextView result;
    Group grp;
    TextView points;
    boolean oneClick=true;
    int numberofquestions=0;
    TextView timer;
    int score=0;
    TextView sumtextview;
    Button b0,b1,b2,b3,goButton;
    int locationofcorrectanswer;
    Button playAgainbutton;

    public void start(View view){
        goButton.setVisibility(View.INVISIBLE);
        grp.setVisibility(View.VISIBLE);
        playAgain(findViewById(R.id.playAgain));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goButton=(Button)findViewById(R.id.startbutton);
        result=(TextView)findViewById(R.id.result);
        sumtextview=(TextView)findViewById(R.id.sumtext);
        b0=(Button)findViewById(R.id.button0);
        b1=(Button)findViewById(R.id.button1);
        playAgainbutton=(Button)findViewById(R.id.playAgain);
         b2=(Button)findViewById(R.id.button2);
         b3=(Button)findViewById(R.id.button3);
        points=(TextView)findViewById(R.id.pointstextview);
        timer=(TextView)findViewById(R.id.timertextview);
        grp=(Group)findViewById(R.id.group);




    }

    public void generateQuestion(){
        Random rand =new Random();
        int a=rand.nextInt(21);
        int b=rand.nextInt(21);
        sumtextview.setText(Integer.toString(a)+"+"+Integer.toString(b));
        int incorrectanswer;
        locationofcorrectanswer=rand.nextInt(4);
        answer.clear();
        for (int i=0;i<4;i++)
        {
            if (i==locationofcorrectanswer){
                answer.add(a+b);

            }
            else {
                incorrectanswer=rand.nextInt(41);
                while(incorrectanswer==a+b){
                    incorrectanswer=rand.nextInt(41);
                }

                answer.add(incorrectanswer);

            }

        }
        b0.setText(Integer.toString(answer.get(0)));
        b1.setText(Integer.toString(answer.get(1)));
        b2.setText(Integer.toString(answer.get(2)));
        b3.setText(Integer.toString(answer.get(3)));
    }
    public void chooseanswer(View view) {
        numberofquestions++;
        if (view.getTag().toString().equals(Integer.toString(locationofcorrectanswer))){
            score++;
            result.setText("Correct!");
            points.setText(Integer.toString(score)+"/"+Integer.toString(numberofquestions));
        }
        else {
            result.setText("Wrong!");
        }

        generateQuestion();

    }

    public void playAgain(View view) {
        if(oneClick) {
        score=0;
        numberofquestions=0;
        points.setText("0/0");
        timer.setText("30s");
        generateQuestion();
        result.setText("");
        playAgainbutton.setVisibility(View.INVISIBLE);
        b0.setEnabled(true);
        b1.setEnabled(true);
        b2.setEnabled(true);
        b3.setEnabled(true);

                oneClick=false;
            new CountDownTimer(30100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    timer.setText(String.valueOf(millisUntilFinished / 1000) + "s");

                }

                @Override
                public void onFinish() {
                    playAgainbutton.setVisibility(View.VISIBLE);
                    timer.setText("0s");
                    result.setText("Your Score: " + Integer.toString(score) + "/" + Integer.toString(numberofquestions));
                    b0.setEnabled(false);
                    b1.setEnabled(false);
                    b2.setEnabled(false);
                    b3.setEnabled(false);
                    oneClick=true;
                }
            }.start();
        }


    }
}
