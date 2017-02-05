package com.example.surendra.groceryapp;

import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import webserviceAccess.AppUserService;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private Button signupButton = null;
    GlobalState gs = null;
    private Button loginButton = null;
    private EditText username = null;
    private EditText password  = null;
    private Spinner role =  null;
    AppUserService aus = null;
    TextView forgotPassword ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.activity_login);
        gs = (GlobalState) getApplication();
        aus =  new AppUserService();
        findViewsById();

    }

    public void findViewsById(){
        signupButton = (Button)findViewById(R.id.signUp);
        loginButton = (Button)findViewById(R.id.login);
        username = (EditText) findViewById(R.id.usernameLogin);
        password = (EditText) findViewById(R.id.loginPassword);
        role = (Spinner) findViewById(R.id.usersSpinner);
        signupButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        forgotPassword = (TextView)findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v == signupButton){
            Intent intent = new Intent(Login.this, SignUp.class);
            startActivity(intent);
        }else if(v==loginButton){
            redirectingLogin();
        }else if(v==forgotPassword){
            redirecttoforgotpassword();
        }
    }
    public void redirecttoforgotpassword(){
        Intent i = new Intent(Login.this,Forgotpassword.class);
        startActivity(i);
    }

    public void redirectingLogin(){
        String userType = role.getSelectedItem().toString();
        String username = this.username.getText().toString();
        String password = this.password.getText().toString();
        String result= aus.login(username,password,userType );
        if(result.equalsIgnoreCase("customer")){
            gs.setUsername(username);
          Intent intent = new Intent(Login.this,UserHome.class);
          startActivity(intent);
           result = "Login Successful";
        }else if(result.equalsIgnoreCase("grocer")){
            gs.setUsername(username);
            Intent intent = new Intent(Login.this,GrocerHome.class);
            startActivity(intent);
            result = "Login Successful";
        }else if(result.equalsIgnoreCase("admin")){
            gs.setUsername(username);
            Intent intent = new Intent(Login.this,AdminHome.class);
            startActivity(intent);
            result = "Login Successful";
        }
        else{
            result = "Login Failed";
        }
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }
}
