package com.example.surendra.groceryapp;

import android.app.DatePickerDialog;
import android.content.Intent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import webserviceAccess.AppUserService;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    DatePicker dp = null;
    EditText utaId = null;
    EditText firstname = null;
    EditText lastname = null;
    EditText password = null;
    EditText email=null;
    EditText phone = null;
    EditText address = null;
    EditText dob = null;
    DateFormat dateFormatter = null;
    Button signUp=null;
    DatePickerDialog dpd = null;
    AppUserService wsa = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);
        wsa = new AppUserService();
        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        findViewsById();
        setDateTimeField();

    }
    private void setSignupvalues(){
        String utaId = this.utaId.getText().toString();
        String firstname = this.firstname.getText().toString();
        String lastname = this.lastname.getText().toString();
        String password = this.password.getText().toString();
        String email = this.email.getText().toString();
        String phone = this.phone.getText().toString();
        String address = this.address.getText().toString();
        String date = this.dob.getText().toString();
        String status = wsa.signUp(utaId,firstname,lastname,password,email,phone,address,date,"customer");
        Toast.makeText(getApplicationContext(),status,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SignUp.this,Login.class);
        startActivity(intent);
    }

    private void findViewsById() {
        dob = (EditText) findViewById(R.id.dob);
        utaId = (EditText) findViewById(R.id.utaId);
        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        address = (EditText) findViewById(R.id.address);
        signUp = (Button) findViewById(R.id.button);
        signUp.setOnClickListener(this);
    }


    public void setDateTimeField() {
        dob.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dob.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }


    @Override
    public void onClick(View v) {
        if(v == dob){
            dpd.show();
        }else if(v == signUp){
            setSignupvalues();
        }
    }
}