package com.example.surendra.groceryapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.surendra.groceryapp.R;

import customadaptors.ANavigationCustom;
import webserviceAccess.ItemService;

import static com.example.surendra.groceryapp.R.id.utaId;

public class AddItems extends AppCompatActivity implements View.OnClickListener {
    private ListView mDrawerList;
    ANavigationCustom n = new ANavigationCustom();
    ItemService is=new ItemService();
    TextView itemName;
    TextView cost;
    TextView quantity;
    Button addItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);
        mDrawerList = (ListView)findViewById(R.id.navList);

        n.addDrawerItems(mDrawerList,this);
        navigateActivity(this);
        findviewsById();
    }

    private void addItems(){
        String itemName = this.itemName.getText().toString();
        String cost = this.cost.getText().toString();
        String quantity = this.quantity.getText().toString();
        String status = is.addItems(itemName,cost,quantity);
        Toast.makeText(getApplicationContext(),status,Toast.LENGTH_SHORT).show();
    }

    public void findviewsById(){

        itemName = (TextView) findViewById(R.id.itemName);
        cost  = (TextView) findViewById(R.id.itemCost);
        quantity = (TextView) findViewById(R.id.quantity);
        addItem = (Button) findViewById(R.id.addItem);
        addItem.setOnClickListener(this);

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
        if(v==addItem){
            addItems();
        }
    }
}
