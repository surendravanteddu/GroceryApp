package customadaptors;

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
import webserviceAccess.CrewService;

/**
 * Created by surendra on 11/23/2016.
 */

public class DropOffAdapter extends BaseAdapter{

    LayoutInflater layoutInflater = null;
    List<Map<String,String>> list ;
    ViewHolder viewHolder;
    Context context ;
    String username;
    CrewService cs;
    public DropOffAdapter(){

    }
    public DropOffAdapter(Context context, List<Map<String,String>> list){
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
            convertView = layoutInflater.inflate(R.layout.dropofflist,null);
            viewHolder.address = (TextView) convertView.findViewById(R.id.address);
            viewHolder.deleteFromDropOff = (Button) convertView.findViewById(R.id.deleteFromDropOff);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        String address = list.get(position).get("address");
        viewHolder.address.setText(address);
        viewHolder.deleteFromDropOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFromDropOff(position);
            }
        });
        return convertView;
    }

    public void deleteFromDropOff(int position){
        cs = new CrewService();

        String msg="";
        if(cs.deleteFromDropOff(list.get(position).get("id")).equalsIgnoreCase("deleted")){
            msg=list.get(position).get("address") + " deleted from drop off "  ;
            list.remove(position);
            notifyDataSetChanged();
        }else{
            msg= "failed to remove";
        }
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
    class ViewHolder {
        TextView id;
        TextView address;
        Button deleteFromDropOff;
    }
}
