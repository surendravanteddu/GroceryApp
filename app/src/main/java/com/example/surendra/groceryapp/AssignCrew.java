package com.example.surendra.groceryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import customadaptors.OrderItemsAdaptor;
import webserviceAccess.CrewService;
import webserviceAccess.OrderService;

public class AssignCrew extends AppCompatActivity implements View.OnClickListener {
    OrderService os ;
    TextView orderId;
    TextView cost;
    TextView itemCount;
    TextView dropoff;
    TextView date;
    TextView status;
    ListView lv;
    Spinner smid;
    Spinner driverId;
    CrewService cs ;
    Button assignCrew;
    String oid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_crew);

        Bundle details = getIntent().getExtras();
        cs = new CrewService();
        os = new OrderService();
        //Log.d("Response",os.getOrderItems(details.getString("orderId")).toString());
        List<Map<String,String>> list= os.getOrderItems(details.getString("orderId"));
        oid = details.getString("orderId");
        lv = (ListView) findViewById(R.id.ordersItemList);
        lv.setAdapter(new OrderItemsAdaptor(AssignCrew.this,list));
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
        smid = (Spinner) findViewById(R.id.smid);
        driverId = (Spinner) findViewById(R.id.driverid);
        assignCrew = (Button) findViewById(R.id.assigncrew);
    }

    public void setValues(Bundle bundle){
        orderId.setText("Order Id: "+bundle.getString("orderId"));
        cost.setText("Total Cost: $"+bundle.getString("cost"));
        itemCount.setText("No. of Items: "+bundle.getString("itemCount"));
        dropoff.setText("Location: "+bundle.getString("dropoff"));
        date.setText("Date: "+bundle.getString("date"));
        status.setText("Status: "+bundle.getString("status"));
        List<List<String>> list = cs.getAssignCrew();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list.get(1));
        //set the view for the Drop down list
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //set the ArrayAdapter to the spinner
        smid.setAdapter(dataAdapter);
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,list.get(0));
        //set the view for the Drop down list
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //set the ArrayAdapter to the spinner
        driverId.setAdapter(dataAdapter1);
        assignCrew.setOnClickListener(this);

    }

    public void assignCrewMember(){
        String dr = driverId.getSelectedItem().toString();
        String sm = smid.getSelectedItem().toString();
        String msg = cs.assignCrew(oid,dr,sm);
        Intent i = new Intent(AssignCrew.this,GrocerOrders.class);
        startActivity(i);
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onClick(View v) {
        if(v==assignCrew){
            assignCrewMember();
        }
    }
}
