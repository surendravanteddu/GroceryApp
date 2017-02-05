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

/**
 * Created by surendra on 11/23/2016.
 */

public class ItemsAdaptor extends BaseAdapter{

    LayoutInflater layoutInflater = null;
    List<Map<String,String>> list ;
    ViewHolder viewHolder;
    Context context ;
    String username;
    CartService cs;
public ItemsAdaptor(){

}
    public ItemsAdaptor(Context context, List<Map<String,String>> list,String username){
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
            convertView = layoutInflater.inflate(R.layout.items_list,null);
            viewHolder.itemName = (TextView) convertView.findViewById(R.id.itemName);
            viewHolder.itemCost = (TextView) convertView.findViewById(R.id.itemCost);
            viewHolder.addToCart = (Button) convertView.findViewById(R.id.addToCart);
            viewHolder.itemImage = (ImageView) convertView.findViewById(R.id.itemImage);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        String itemname = list.get(position).get("itemName");
        String units = veggies.contains(itemname)?"lb" : "unit";
        int i = images.indexOf(itemname);
        viewHolder.itemName.setText(itemname);
        viewHolder.itemCost.setText("$"+list.get(position).get("itemCost")+"/"+units);
        if(i>-1) {
            int imageindex = Integer.parseInt(images.charAt(i-1)+"");
            viewHolder.itemImage.setImageResource(imageSrc[imageindex]);
        }
        viewHolder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             addToCart(position);
            }
        });
        return convertView;
    }


 public String images = "0Apple1Tomato2Cabbage3Carrot4Potato";
 public int imageSrc[] = {R.drawable.apple,R.drawable.tomato,R.drawable.cabbage,R.drawable.carrot,R.drawable.potato};
 public String veggies = "0Apple1Tomato2Cabbage3Carrot4Potato";

    public void addToCart(int position){
       cs = new CartService();
       String response = cs.addToCart(list.get(position),username);
       String msg = response.equalsIgnoreCase("success")? list.get(position).get("itemName")+" Added To Cart" : response+" to add";
       Toast.makeText(context,msg, Toast.LENGTH_LONG).show();
   }
    class ViewHolder {
      TextView itemName;
      TextView itemCost;
      Button addToCart;
      EditText quantity;
      ImageView itemImage;
    }
}
