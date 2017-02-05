package com.example.surendra.groceryapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import customadaptors.NavigationCustom;
import webserviceAccess.AppUserService;

public class UserProfile extends AppCompatActivity implements View.OnClickListener {

    EditText utaId = null;
    EditText firstname = null;
    EditText lastname = null;
    EditText email=null;
    EditText phone = null;
    EditText address = null;
    EditText dob = null;
    Button save=null;
    DatePickerDialog dpd = null;
    DateFormat dateFormatter = null;
    private ListView mDrawerList;
    GlobalState gs = null;
    NavigationCustom n = new NavigationCustom();
    AppUserService aus = new AppUserService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        mDrawerList = (ListView)findViewById(R.id.navList);
        gs = (GlobalState) getApplication();
        String username = gs.getUsername();
        Log.d("username",username);
        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        n.addDrawerItems(mDrawerList,this);
        navigateActivity(UserProfile.this);
        findViewsById();
       setProfileValues(username);
        setDateTimeField();

    }

    public void navigateActivity(final Context context){
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(context,n.renderPage(position));
                startActivity(intent);
              //  Toast.makeText(context, "Position "+position, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void findViewsById(){
        dob = (EditText) findViewById(R.id.dob);
        utaId = (EditText) findViewById(R.id.utaId);
        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        address = (EditText) findViewById(R.id.address);
        save = (Button) findViewById(R.id.button);
        save.setOnClickListener(this);
    }

    public void setProfileValues(String username){
        try {
    Map<String,String> map =  aus.getCustomerProfile(username);
        utaId.setText(map.get("username"));
        firstname.setText(map.get("firstname"));
        lastname.setText(map.get("lastname"));
        email.setText(map.get("email"));
        phone.setText(map.get("phone"));
        address.setText(map.get("address"));
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = df.parse(map.get("dob"));
        dob.setText(dateFormatter.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

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

    public void updateProfile(){
        try{
          String result =  aus.updateProfile(gs.getUsername(),firstname.getText().toString(),lastname.getText().toString(),
                    email.getText().toString(),phone.getText().toString(),address.getText().toString(),dob.getText().toString());

            Toast.makeText(UserProfile.this,result,Toast.LENGTH_SHORT).show();

        }catch(Exception ex){

        }
    }

    @Override
    public void onClick(View v) {
         if(v==dob){
             dpd.show();
         }else if(v == save){
             updateProfile();
         }
    }


}
