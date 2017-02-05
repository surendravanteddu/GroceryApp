package com.example.surendra.groceryapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import customadaptors.NavigationCustom;
import customadaptors.OrdersAdaptor;
import webserviceAccess.OrderService;

public class MyOrder extends AppCompatActivity {
    private ListView mDrawerList;
    NavigationCustom n = new NavigationCustom();
    ListView lv;
    GlobalState gs = (GlobalState) getApplication();
    String username;
    OrderService os ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        mDrawerList = (ListView)findViewById(R.id.navList);
        n.addDrawerItems(mDrawerList,this);
        navigateActivity(this);
        gs = (GlobalState) getApplication();
        username = gs.getUsername();
        os = new OrderService();
        lv = (ListView) findViewById(R.id.ordersList);
       lv.setAdapter(new OrdersAdaptor(MyOrder.this,os.getOrders(username)));

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
