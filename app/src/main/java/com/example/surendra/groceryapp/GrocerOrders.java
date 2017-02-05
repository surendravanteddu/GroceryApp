package com.example.surendra.groceryapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import customadaptors.GNavigationCustom;
import customadaptors.GOrdersAdaptor;
import webserviceAccess.OrderService;

public class GrocerOrders extends AppCompatActivity {
    private ListView mDrawerList;
    GNavigationCustom n = new GNavigationCustom();
    ListView lv;
    GlobalState gs = (GlobalState) getApplication();
    String username;
    OrderService os ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocer_orders);
        mDrawerList = (ListView)findViewById(R.id.navList);
        n.addDrawerItems(mDrawerList,GrocerOrders.this);
        navigateActivity(GrocerOrders.this);
        gs = (GlobalState) getApplication();
        username = gs.getUsername();
        os = new OrderService();
        lv = (ListView) findViewById(R.id.ordersList);
        lv.setAdapter(new GOrdersAdaptor(GrocerOrders.this,os.getAllOrders()));
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
