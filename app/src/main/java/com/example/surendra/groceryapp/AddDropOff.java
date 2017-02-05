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

import customadaptors.ANavigationCustom;
import webserviceAccess.CrewService;

public class AddDropOff extends AppCompatActivity implements View.OnClickListener {
    private ListView mDrawerList;
    EditText address=null;
    Button add=null;
    CrewService cs=null;
    ANavigationCustom n = new ANavigationCustom();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drop_off);
        mDrawerList = (ListView) findViewById(R.id.navList);
        n.addDrawerItems(mDrawerList, this);
        navigateActivity(this);
        cs=new CrewService();
        findViewsById();
    }

    private void setAddupvalues(){
        String address = this.address.getText().toString();
        String status = cs.addToDropOff("",address);
        Toast.makeText(getApplicationContext(),status,Toast.LENGTH_SHORT).show();
    }

    private void findViewsById() {
        address = (EditText) findViewById(R.id.address);
        add=(Button) findViewById(R.id.addToDropOff);
        add.setOnClickListener(this);
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
        if(v==add){
            setAddupvalues();
        }
    }
}
