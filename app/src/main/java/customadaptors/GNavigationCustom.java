package customadaptors;


import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.surendra.groceryapp.Cart;
import com.example.surendra.groceryapp.ChangePassword;
import com.example.surendra.groceryapp.GrocerHome;
import com.example.surendra.groceryapp.GrocerOrders;
import com.example.surendra.groceryapp.Login;
import com.example.surendra.groceryapp.MyOrder;
import com.example.surendra.groceryapp.UserHome;
import com.example.surendra.groceryapp.UserProfile;


/**
 * Created by samsung-pc on 09-11-2016.
 */
public  class GNavigationCustom  {

    private ArrayAdapter<String> mAdapter;
    public void addDrawerItems(ListView mDrawerList,final Context context) {
        String[] osArray = {"Home"," update profile","view orders","change password","log out"};
        mAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
    }


    public Class renderPage(int i){

        switch(i){
            case 0:
                return GrocerHome.class;

            case 1:
                return UserProfile.class;

            case 2:
                return GrocerOrders.class;

            case 3:
                return ChangePassword.class;

            default:
                return Login.class;

        }
    }
}
