package com.example.surendra.groceryapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;
import java.util.Map;

import customadaptors.ANavigationCustom;
import customadaptors.ItemsAdaptor;
import customadaptors.ViewItemsAdaptor;
import webserviceAccess.ItemService;

import static com.example.surendra.groceryapp.R.id.username;

public class ViewItems extends AppCompatActivity {
    private ListView mDrawerList;
    ListView lv;
    ANavigationCustom n = new ANavigationCustom();
    GlobalState gs = (GlobalState) getApplication();
    ItemService is=null;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_items);
        mDrawerList = (ListView)findViewById(R.id.navList);

        n.addDrawerItems(mDrawerList,this);
        navigateActivity(ViewItems.this);
        is=new ItemService();
        lv = (ListView) findViewById(R.id.listView);
        List<Map<String,String>> list = is.getItemsList();
        gs = (GlobalState) getApplication();
        username =  gs.getUsername();

        lv.setAdapter(new ViewItemsAdaptor(ViewItems.this,list,username));
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
