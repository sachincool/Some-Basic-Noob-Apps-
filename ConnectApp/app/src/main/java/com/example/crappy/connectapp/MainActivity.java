package com.example.crappy.connectapp;

import android.media.Image;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    int activeplayer=0;
boolean gameisactive=true;

    //activeplayer=yellow
    int[] gamestate={2,2,2,2,2,2,2,2,2};
    int[][] winning={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dododo(View view) {

        int tappedcounter = Integer.parseInt(view.getTag().toString());


        ImageView counter = (ImageView) view;

        if (gamestate[tappedcounter] == 2 && gameisactive) {
            gamestate[tappedcounter]=activeplayer;
            counter.setTranslationY(-1000f);
            if (activeplayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activeplayer = 1;

            } else {
                counter.setImageResource(R.drawable.red);
                activeplayer = 0;
            }
            counter.animate().translationYBy(1000f).rotation(360).setDuration(300);
            String message="Red";
            for(int[] winningposition:winning)
            {
                if(gamestate[winningposition[0]]==gamestate[winningposition[1]] && gamestate[winningposition[1]]==gamestate[winningposition[2]] && gamestate[winningposition[0]]!=2)
                {
                    System.out.println(gamestate[winningposition[0]]);
                    TextView t1=(TextView)findViewById(R.id.textView2);
                    if(gamestate[winningposition[0]]==0)
                        message="Yellow";

                    t1.setText(message+" has won!");

                    LinearLayout layout=(LinearLayout)findViewById(R.id.playagainlay);

                    layout.setVisibility(View.VISIBLE);
                    gameisactive=false;



                }
                else {
                    boolean gameisover=true;
                    for(int counterstate:gamestate)
                    {
                    if(counterstate==2) gameisover=false;

                    }
                    if(gameisover){
                        TextView t1=(TextView)findViewById(R.id.textView2);


                        t1.setText("It's a  Draw!!!");

                        LinearLayout layout=(LinearLayout)findViewById(R.id.playagainlay);
                        layout.setVisibility(View.VISIBLE);

                    }
                }
            }
        }
    }

    public void playagain(View view) {
        LinearLayout layout=(LinearLayout)findViewById(R.id.playagainlay);
        layout.setVisibility(View.INVISIBLE);
         activeplayer=0;
        GridLayout grid=(GridLayout)findViewById(R.id.gridLayout);
        gameisactive=true;
        //activeplayer=yellow
       for(int i=0;i<gamestate.length;i++) {
           gamestate[i] = 2;
           ((ImageView) grid.getChildAt(i)).setImageResource(0);
       }
       }
}
