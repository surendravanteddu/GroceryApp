package com.example.surendra.groceryapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;
import java.util.Map;

import customadaptors.CartAdaptor;
import customadaptors.NavigationCustom;
import webserviceAccess.CartService;

public class Cart extends AppCompatActivity implements View.OnClickListener {
    private ListView mDrawerList;
    ListView lv;
    NavigationCustom n = new NavigationCustom();
    CartService cs = null;
    GlobalState gs;
    String username;
    Button checkout ;
    Button totalQuantity ;
    Button totalCost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        mDrawerList = (ListView)findViewById(R.id.navList);
        n.addDrawerItems(mDrawerList,Cart.this);
        navigateActivity(this);
        gs = (GlobalState) getApplication();
        username = gs.getUsername();
        cs=new CartService();
        findViewsById();
    }

    public void findViewsById(){
        List<Map<String,String>> list = cs.getCart(username);
        int totalno =0;
        int totalprice=0;
        for(Map<String,String> map : list){
            totalno += 1;
            totalprice += (Integer.parseInt(map.get("quantity"))*Integer.parseInt(map.get("itemCost")));
        }
        lv = (ListView) findViewById(R.id.cartList);
        totalQuantity =  (Button)findViewById(R.id.totalquantity);
        totalCost = (Button) findViewById(R.id.totalcost);
        lv.setAdapter(new CartAdaptor(Cart.this,list,username,totalCost));
        totalQuantity.setText("Total No. of Items  "+totalno);
        totalCost.setText("Total Price  $"+totalprice);
        checkout = (Button) findViewById(R.id.checkout);
        checkout.setOnClickListener(this);
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


    @Override
    public void onClick(View v) {
    if(v == checkout){
       proceedTopayment();
    }
    }

    public void proceedTopayment(){
        Intent intent = new Intent(Cart.this,Payment.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }
}
