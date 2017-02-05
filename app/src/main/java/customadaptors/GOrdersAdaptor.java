package customadaptors;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.surendra.groceryapp.AssignCrew;
import com.example.surendra.groceryapp.OrderItem;
import com.example.surendra.groceryapp.R;

import java.util.List;
import java.util.Map;

/**
 * Created by surendra on 11/27/2016.
 */

public class GOrdersAdaptor extends BaseAdapter{
    Context context;
    List<Map<String,String>> list;
    LayoutInflater layoutInflater = null;
    ViewHolder viewHolder;

    public GOrdersAdaptor(){

    }

    public GOrdersAdaptor(Context context,List<Map<String,String>> list){
        this.context = context;
        this.list = list;
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
            convertView = layoutInflater.inflate(R.layout.orders_list,null);
            viewHolder.orderId = (TextView) convertView.findViewById(R.id.orderId);
            viewHolder.cost = (TextView) convertView.findViewById(R.id.ordercost);
            viewHolder.itemCount = (TextView) convertView.findViewById(R.id.orderitemCount);
            viewHolder.dropoff = (TextView) convertView.findViewById(R.id.orderdropoff);
            viewHolder.date = (TextView) convertView.findViewById(R.id.orderdate);
            viewHolder.status = (TextView) convertView.findViewById(R.id.orderstatus);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)  convertView.getTag();
        }
        viewHolder.orderId.setText(list.get(position).get("orderId"));
        viewHolder.cost.setText("$"+list.get(position).get("cost"));
        viewHolder.itemCount.setText(list.get(position).get("itemCount"));
        viewHolder.dropoff.setText(list.get(position).get("dropoff"));
        viewHolder.date.setText(list.get(position).get("date"));
        viewHolder.status.setText("Status: "+list.get(position).get("status"));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showText(position,v);
            }
        });
        return convertView;
    }

    public void showText(int position,View v){
        Intent i = new Intent(v.getContext(), AssignCrew.class);
        Bundle details = new Bundle();
        details.putString("orderId",list.get(position).get("orderId"));
        details.putString("date",list.get(position).get("date"));
        details.putString("status",list.get(position).get("status"));
        details.putString("dropoff",list.get(position).get("dropoff"));
        details.putString("cost",list.get(position).get("cost"));
        details.putString("itemCount",list.get(position).get("itemCount"));
        i.putExtras(details);
        v.getContext().startActivity(i);
    }

    public class ViewHolder{
        TextView orderId;
        TextView cost;
        TextView itemCount;
        TextView dropoff;
        TextView date;
        TextView status;
    }
}
