package customadaptors;


import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.surendra.groceryapp.AddDropOff;
import com.example.surendra.groceryapp.AdminHome;
import com.example.surendra.groceryapp.AllOrders;
import com.example.surendra.groceryapp.Customers;
import com.example.surendra.groceryapp.DropOffLocations;
import com.example.surendra.groceryapp.Grocers;
import com.example.surendra.groceryapp.Login;
import com.example.surendra.groceryapp.UserProfile;
import com.example.surendra.groceryapp.AddCrew;
import com.example.surendra.groceryapp.AddGrocer;
import com.example.surendra.groceryapp.AddGrocer;
import com.example.surendra.groceryapp.AddItems;

import com.example.surendra.groceryapp.ChangePassword;
import com.example.surendra.groceryapp.CrewMembers;

import com.example.surendra.groceryapp.VacationList;
import com.example.surendra.groceryapp.ViewItems;


/**
 * Created by samsung-pc on 09-11-2016.
 */
public  class ANavigationCustom  {

    private ArrayAdapter<String> mAdapter;
    public void addDrawerItems(ListView mDrawerList,final Context context) {
        String[] osArray = {"Home","update profile","view items","add items","add crew member","register grocer","view crew member list","remove from vacation","add dropoff location","view drop off location","view grocer list","view customer list","view orders","change password","log out"};
        mAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);


    }


    public Class renderPage(int i){

        switch(i){
            case 0:
                return AdminHome.class;

            case 1:
                return UserProfile.class;

            case 2:
                return ViewItems.class;

            case 3:
                return AddItems.class;

            case 4:
                return AddCrew.class;

            case 5:
                return AddGrocer.class;

            case 6:
                return CrewMembers.class;

            case 7:
                return VacationList.class;

            case 8:
                return AddDropOff.class;

            case 9:
                return DropOffLocations.class;

            case 10:
                return Grocers.class;

            case 11:
                return Customers.class;

            case 12:
                return AllOrders.class;

            case 13:
                return ChangePassword.class;

            default:
                return Login.class;

        }
    }
}
