package com.example.crappy.memorableplaces;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import static android.R.attr.id;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> places;
     static ArrayAdapter arrayAdapter;
    static ArrayList<LatLng> locations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView)findViewById(R.id.listview);
        places=new ArrayList<>();

        places.add("Add a new place...");
        locations =new ArrayList<>();
        locations.add(new LatLng(0,0));

       arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,places);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(getApplicationContext(),yourPlaces.class);
                i.putExtra("locationinfo",position);
                startActivity(i);
            }
        });
    }
}
