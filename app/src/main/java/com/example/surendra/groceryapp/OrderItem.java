package com.example.surendra.groceryapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import customadaptors.OrderItemsAdaptor;
import webserviceAccess.OrderService;

public class OrderItem extends AppCompatActivity {
    OrderService os ;
    TextView orderId;
    TextView cost;
    TextView itemCount;
    TextView dropoff;
    TextView date;
    TextView status;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_item);

        Bundle details = getIntent().getExtras();
        os = new OrderService();
        //Log.d("Response",os.getOrderItems(details.getString("orderId")).toString());
        List<Map<String,String>> list= os.getOrderItems(details.getString("orderId"));
        lv = (ListView) findViewById(R.id.ordersItemList);
        lv.setAdapter(new OrderItemsAdaptor(OrderItem.this,list));
        findViewsById();
        setValues(details);

    }

    public void findViewsById(){
        orderId = (TextView)findViewById(R.id.oiId);
        cost = (TextView)findViewById(R.id.oicost);
        itemCount = (TextView)findViewById(R.id.oiitemCount);
        dropoff= (TextView)findViewById(R.id.oilocation);
        date = (TextView)findViewById(R.id.oidate);
        status = (TextView)findViewById(R.id.oistatus);

    }

    public void setValues(Bundle bundle){
        orderId.setText("Order Id: "+bundle.getString("orderId"));
        cost.setText("Total Cost: $"+bundle.getString("cost"));
        itemCount.setText("No. of Items: "+bundle.getString("itemCount"));
        dropoff.setText("Location: "+bundle.getString("dropoff"));
        date.setText("Date: "+bundle.getString("date"));
        status.setText("Status: "+bundle.getString("status"));

    }
}
