package com.example.surendra.groceryapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;
import java.util.Map;

import customadaptors.ANavigationCustom;
import customadaptors.CrewAdapter;
import customadaptors.DropOffAdapter;
import customadaptors.ViewItemsAdaptor;
import webserviceAccess.CrewService;

public class DropOffLocations extends AppCompatActivity {
    private ListView mDrawerList;
    ListView lv;
    ANavigationCustom n = new ANavigationCustom();
    CrewService cs=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop_off_locations);
        mDrawerList = (ListView)findViewById(R.id.navList);
        cs = new CrewService();
        n.addDrawerItems(mDrawerList,this);
        navigateActivity(this);
       lv = (ListView) findViewById(R.id.listView);
        List<Map<String,String>> list = cs.getDropOffList();
        lv.setAdapter(new DropOffAdapter(DropOffLocations.this,list));
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
