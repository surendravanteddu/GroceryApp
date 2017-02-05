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
import com.example.surendra.groceryapp.VacationList;

import java.util.List;
import java.util.Map;

import webserviceAccess.CartService;
import webserviceAccess.CrewService;

/**
 * Created by surendra on 11/23/2016.
 */

public class VacationListAdapter extends BaseAdapter{

    LayoutInflater layoutInflater = null;
    List<Map<String,String>> list ;
    ViewHolder viewHolder;
    Context context ;
    String username;
    CrewService cs;
    public VacationListAdapter(){

    }
    public VacationListAdapter(Context context, List<Map<String,String>> list){
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
            convertView = layoutInflater.inflate(R.layout.vacation_list,null);
            viewHolder.crewId = (TextView) convertView.findViewById(R.id.crewId);
            viewHolder.firstname = (TextView) convertView.findViewById(R.id.firstname);
            viewHolder.deleteFromVacationList = (Button) convertView.findViewById(R.id.deleteFromVacationList);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        String crewId = list.get(position).get("crewId");
        String firstname = list.get(position).get("firstname");
        viewHolder.crewId.setText(crewId);
        viewHolder.firstname.setText(firstname);
        viewHolder.deleteFromVacationList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFromVacationList(position);
            }
        });
        return convertView;
    }


    public void deleteFromVacationList(int position){
        cs = new CrewService();
        String msg="";
        if(cs.deleteFromVacationList(list.get(position).get("crewId")).equalsIgnoreCase("deleted")){
            msg=list.get(position).get("firstname") + " deleted from Vacation List "  ;
            list.remove(position);
            notifyDataSetChanged();
        }else{
            msg= "failed to remove";
        }
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
    class ViewHolder {
        TextView crewId;
        TextView firstname;
        Button deleteFromVacationList;
    }
}
