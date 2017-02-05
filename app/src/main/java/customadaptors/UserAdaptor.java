package customadaptors;

/**
 * Created by samsung-pc on 27-11-2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.surendra.groceryapp.R;

import java.util.List;
import java.util.Map;

import webserviceAccess.AppUserService;
import webserviceAccess.CartService;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.surendra.groceryapp.R;

import java.util.List;
import java.util.Map;

import webserviceAccess.CartService;
import webserviceAccess.ItemService;

/**
 * Created by surendra on 11/23/2016.
 */

public class UserAdaptor extends BaseAdapter {

    LayoutInflater layoutInflater = null;
    List<Map<String,String>> list ;
    ViewHolder viewHolder;
    Context context ;
    String username;
    AppUserService aus;
    public UserAdaptor(){

    }
    public UserAdaptor(Context context, List<Map<String,String>> list){
        this.list = list;
        this.context = context;
        this.username = username;
        layoutInflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        viewHolder = null;
        if(viewHolder==null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.grocer_list,null);
            viewHolder.username = (TextView) convertView.findViewById(R.id.username);
            viewHolder.firstname = (TextView) convertView.findViewById(R.id.firstname);
            viewHolder.deleteFromGrocer = (Button) convertView.findViewById(R.id.deleteFromGrocer);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        String username = list.get(position).get("username");
        String firstname = list.get(position).get("firstname");
        viewHolder.username.setText(username);
        viewHolder.firstname.setText(firstname);
        viewHolder.deleteFromGrocer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFromGrocer(position);
            }
        });
        return convertView;
    }


    public void deleteFromGrocer(int position){
        aus = new AppUserService();
        String msg="";
        if(aus.deleteFromGrocer(list.get(position).get("username")).equalsIgnoreCase("deleted")){
            msg=list.get(position).get("firstname") + " deleted  "  ;
            list.remove(position);
            notifyDataSetChanged();
        }else{
            msg= "failed to remove";
        }
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
    class ViewHolder {
        TextView username;
        TextView firstname;
        Button deleteFromGrocer;
    }
}
