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

import org.w3c.dom.Text;

import customadaptors.NavigationCustom;
import webserviceAccess.AppUserService;

public class ChangePassword extends AppCompatActivity implements View.OnClickListener {
    private ListView mDrawerList;
    NavigationCustom n = new NavigationCustom();
    TextView oldPassword ;
    TextView newPassword;
    TextView rnewPassword;
    Button changePasswordButton;
    String username;
    AppUserService aus;
    GlobalState gs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        mDrawerList = (ListView)findViewById(R.id.navList);

        n.addDrawerItems(mDrawerList,this);
        navigateActivity(this);
        findviewsById();
    }
    public void changePass(){
        aus = new AppUserService();
        String oldpass = oldPassword.getText().toString();
        String newpass = newPassword.getText().toString();
        String rnewpass = rnewPassword.getText().toString();
        String msg = "";
        if(newpass.equals(rnewpass)){
            msg = aus.changePassword(oldpass,newpass,username);
        }else{
           msg = "Passwords mismatch";
        }
        Toast.makeText(ChangePassword.this,msg,Toast.LENGTH_SHORT).show();

    }
    public void findviewsById(){

         oldPassword = (TextView) findViewById(R.id.oldpassword);
        newPassword  = (TextView) findViewById(R.id.newpassword);
        rnewPassword = (TextView) findViewById(R.id.rnewpassword);
        changePasswordButton = (Button) findViewById(R.id.changePassword);
        gs = (GlobalState) getApplication();
        username = gs.getUsername();
        changePasswordButton.setOnClickListener(this);

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
        if(v==changePasswordButton){
            changePass();
        }
    }
}
