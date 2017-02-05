package com.example.surendra.groceryapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;
import java.util.Map;

import customadaptors.ANavigationCustom;
import customadaptors.CrewAdapter;
import customadaptors.ViewItemsAdaptor;
import webserviceAccess.CrewService;
import webserviceAccess.ItemService;

public class CrewMembers extends AppCompatActivity {
    private ListView mDrawerList;
    ListView lv;
    ANavigationCustom n = new ANavigationCustom();
    GlobalState gs = (GlobalState) getApplication();
    CrewService cs=null;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crew_members);
        mDrawerList = (ListView)findViewById(R.id.navList);
        cs=new CrewService();
        n.addDrawerItems(mDrawerList,this);
        navigateActivity(this);
        lv = (ListView) findViewById(R.id.listView);
        List<Map<String,String>> list = cs.getCrewList();
        gs = (GlobalState) getApplication();
        username =  gs.getUsername();
        Log.d("crew fn",list.get(0).get("firstname"));
        lv.setAdapter(new CrewAdapter(CrewMembers.this,list));
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
