package com.example.surendra.groceryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Forgotpassword extends AppCompatActivity implements View.OnClickListener {
    Button forgotpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        forgotpassword = (Button) findViewById(R.id.fpforgotPassword);
        forgotpassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == forgotpassword){
            Toast.makeText(Forgotpassword.this,"Password sent to Email",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Forgotpassword.this,Login.class);
            startActivity(i);
        }
    }
}
