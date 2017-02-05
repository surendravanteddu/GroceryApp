package com.example.surendra.groceryapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import customadaptors.GNavigationCustom;

public class GrocerHome extends AppCompatActivity {

    private ListView mDrawerList;
    GNavigationCustom n = new GNavigationCustom();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocer_home);

        mDrawerList = (ListView)findViewById(R.id.navList);
        n.addDrawerItems(mDrawerList,GrocerHome.this);
        navigateActivity(GrocerHome.this);
        
    }

    public void navigateActivity(final Context context){
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context,n.renderPage(position));
                startActivity(intent);
            }
        });
    }
}
