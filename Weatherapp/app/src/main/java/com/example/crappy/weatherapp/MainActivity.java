package com.example.crappy.weatherapp;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static android.R.attr.codes;
import static android.R.id.message;
import static android.support.v7.widget.AppCompatDrawableManager.get;
import static com.example.crappy.weatherapp.R.id.textView;

public class MainActivity extends AppCompatActivity {
AutoCompleteTextView t1;
    TextView t2;
    public class DownloadTask extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... params) {
            String result = "";
            URL url;
            HttpURLConnection connection = null;
            try {
                url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                InputStream in = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    result += current;

                    data = reader.read();

                }
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected void onPostExecute (String s){
                super.onPostExecute(s);

                try {
                        String message="";
                    JSONObject jObject = new JSONObject(s);

                        Log.i("JSON",jObject.toString());
                    JSONArray arr = jObject.getJSONArray("list");
                    for (int i = 0; i < arr.length(); i++) {
                        JSONArray weather= new JSONArray(arr.getJSONObject(i).getString("weather"));
                        JSONObject jsonpart = weather.getJSONObject(i);
                        String main ="";
                        String desc="";
                        Log.i("LENGTH",String.valueOf(arr.length()));
                      main=jsonpart.getString("main");
                        desc=jsonpart.getString(("description"));
                        if(main!="" && desc!=""){
                            message+= main +":"+desc +"\r\n";

                        }
                        if(message!="")
                            t2.setText(message);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }



public  void findlocation(){
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_dropdown_item_1line, COUNTRIES);

            t1.setAdapter(adapter);

}

    public static final String[] COUNTRIES = new String[] {
            "Ludhiana", "Jalandher", "Delhi", "UP", "Rajpura","Chandigarh"
    };
    public static final int[] Codes= new int[] {
        1264728, 1268782, 1273294, 1253626, 1258803, 1274746 };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1=(AutoCompleteTextView)findViewById(R.id.editText);
        t2=(TextView)findViewById(R.id.textView);
        final DownloadTask task= new DownloadTask();
        findlocation();
        t1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId== EditorInfo.IME_ACTION_DONE){
                for (int i=0;i<6;i++){
                if (COUNTRIES[i].equals(v.getText().toString())) {

                    task.execute("http://api.openweathermap.org/data/2.5/forecast?id=" + Codes[i] + "&APPID=1f7f6158e1d317b446eccb7914f65c4b");

                }
                }
                }
                return false;
            }


        });
    }


}
