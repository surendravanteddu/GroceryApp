package com.example.surendra.groceryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import webserviceAccess.CartService;
import webserviceAccess.OrderService;

public class Payment extends AppCompatActivity implements View.OnClickListener {
    String username;
    CartService cs ;
    OrderService os;
    Button userid ;
    Button totalQuantity;
    Button totalCost;
    EditText carddetials;
    Spinner dropoffs;
    Button finishPayment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        cs = new CartService();
        os = new OrderService();
        username = getIntent().getStringExtra("username");
        findViewsById();
        setValues();
    }

    public void setValues(){
        List<Map<String,String>> list = cs.getCart(username);
        userid.setText(username);
        int totalno =0;
        int totalprice=0;
        for(Map<String,String> map : list){
            totalno += 1;
            totalprice += (Integer.parseInt(map.get("quantity"))*Integer.parseInt(map.get("itemCost")));
        }
        totalCost.setText("Total Price $"+totalprice);
        totalQuantity.setText("Total No. of Items "+ totalno);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, os.getDropOffs());
        //set the view for the Drop down list
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //set the ArrayAdapter to the spinner
        dropoffs.setAdapter(dataAdapter);
        finishPayment.setOnClickListener(this);


    }
    public void findViewsById(){
        userid = (Button)findViewById(R.id.username);
        totalQuantity = (Button)findViewById(R.id.totalquantity);
        totalCost = (Button)findViewById(R.id.totalcost);
        carddetials = (EditText) findViewById(R.id.carddetails);
        dropoffs  = (Spinner) findViewById(R.id.dropoff);
        finishPayment = (Button) findViewById(R.id.finishpayment);
    }

    @Override
    public void onClick(View v) {
        if(v == finishPayment ){
        placeOrder();
        }
    }

    public void placeOrder(){
          String dropoff = dropoffs.getSelectedItem().toString();
         String response =  os.placeOrder(username,dropoff);
         String msg = response.equalsIgnoreCase("success") ? "Order Placed successfully" : "Failed";
         Intent i = new Intent(Payment.this,MyOrder.class);
         startActivity(i);
        Toast.makeText(Payment.this,msg,Toast.LENGTH_SHORT).show();
    }
}
