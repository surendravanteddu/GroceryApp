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
import webserviceAccess.ItemService;

/**
 * Created by surendra on 11/23/2016.
 */

public class CrewAdapter extends BaseAdapter {

    LayoutInflater layoutInflater = null;
    List<Map<String,String>> list ;
    ViewHolder viewHolder;
    Context context ;
    String username;
    CrewService cs;
    public CrewAdapter(){

    }
    public CrewAdapter(Context context, List<Map<String,String>> list){
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
            convertView = layoutInflater.inflate(R.layout.crew_list,null);
            viewHolder.crewId = (TextView) convertView.findViewById(R.id.crewId);
            viewHolder.role = (TextView) convertView.findViewById(R.id.role);
            viewHolder.deleteCrew = (Button) convertView.findViewById(R.id.deleteCrew);
            viewHolder.addToVacationList = (Button) convertView.findViewById(R.id.addToVacationList);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        String crewId = list.get(position).get("crewId");
        String role = list.get(position).get("firstname");
        viewHolder.crewId.setText(crewId);
        viewHolder.role.setText(role);
        viewHolder.deleteCrew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCrew(position);
            }
        });
        viewHolder.addToVacationList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToVacationList(position);
            }
        });
        return convertView;
    }


    public String images = "0Apple1Tomato2Cabbage3Carrot4Potato";
    public int imageSrc[] = {R.drawable.apple,R.drawable.tomato,R.drawable.cabbage,R.drawable.carrot,R.drawable.potato};
    public String veggies = "0Apple1Tomato2Cabbage3Carrot4Potato";

    public void deleteCrew(int position){
        cs = new CrewService();
        String msg="";
        if(cs.removefromCrew(list.get(position).get("crewId")).equalsIgnoreCase("deleted")){
            msg=list.get(position).get("crewId") + " deleted "  ;
            list.remove(position);
            notifyDataSetChanged();
        }else{
            msg= "failed to remove";
        }
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    public void addToVacationList(int position){
        cs = new CrewService();
        String msg="";
        String crewId = list.get(position).get("crewId");
        String status = cs.addToVacationList(crewId);

        Toast.makeText(context, status, Toast.LENGTH_LONG).show();
    }


    class ViewHolder {
        TextView crewId;
        TextView role;
        Button deleteCrew;
        Button addToVacationList;
    }
}
