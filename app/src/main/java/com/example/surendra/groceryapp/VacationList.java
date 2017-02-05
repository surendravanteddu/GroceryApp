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
import customadaptors.VacationListAdapter;
import customadaptors.ViewItemsAdaptor;
import webserviceAccess.CrewService;
import webserviceAccess.ItemService;

public class VacationList extends AppCompatActivity {
    private ListView mDrawerList;
    ANavigationCustom n = new ANavigationCustom();
    ListView lv;
    CrewService cs=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_list);
        mDrawerList = (ListView)findViewById(R.id.navList);

        n.addDrawerItems(mDrawerList,this);
        navigateActivity(this);
        cs=new CrewService();
        lv = (ListView) findViewById(R.id.listView);
        List<Map<String,String>> list = cs.getVacationList();
      //  Log.d("name","");
        lv.setAdapter(new VacationListAdapter(VacationList.this,list));
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
