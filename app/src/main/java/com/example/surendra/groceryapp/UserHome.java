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
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import customadaptors.ItemsAdaptor;
import customadaptors.NavigationCustom;
import webserviceAccess.ItemService;

public class UserHome extends AppCompatActivity implements View.OnClickListener {

    private ListView mDrawerList;
    ListView lv;
    GlobalState gs = (GlobalState) getApplication();
    NavigationCustom n = new NavigationCustom();
    ItemService is = null;
    Button searchbutton;
    EditText searchitem;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        mDrawerList = (ListView)findViewById(R.id.navList);
        n.addDrawerItems(mDrawerList,this);
        navigateActivity(UserHome.this);
        is = new ItemService();
        lv = (ListView) findViewById(R.id.listView);
        List<Map<String,String>> list = is.getItemsList();
        gs = (GlobalState) getApplication();
        username =  gs.getUsername();

        lv.setAdapter(new ItemsAdaptor(UserHome.this,list,username));
        searchitem = (EditText) findViewById(R.id.searchitem);
        searchbutton = (Button) findViewById(R.id.searchbutton);
        searchbutton.setOnClickListener(this);
    }

    public void navigateActivity(final Context context){
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(context,n.renderPage(position));
                startActivity(intent);
                if(position>4)
                Toast.makeText(context, "Logout Successful", Toast.LENGTH_SHORT).show();
            }
        });
    }

     public void getSearchResults(){
         String itemname = searchitem.getText().toString();
         lv.setAdapter(new ItemsAdaptor(UserHome.this,is.searchItemsList(itemname),username));

     }
    @Override
    public void onClick(View v) {
        if(v==searchbutton){
            getSearchResults();
        }
    }
}
