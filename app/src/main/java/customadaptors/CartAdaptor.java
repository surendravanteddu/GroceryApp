package customadaptors;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.surendra.groceryapp.R;

import java.util.List;
import java.util.Map;

import webserviceAccess.CartService;

public class CartAdaptor extends BaseAdapter {

    LayoutInflater layoutInflater = null;
    List<Map<String,String>> list ;
    ViewHolder viewHolder;
    Context context ;
    String username;
    CartService cs;
    Button totalCost;

    public CartAdaptor(){

    }
    public CartAdaptor(Context context, List<Map<String,String>> list,String username,Button totalCost){
        this.list = list;
        this.context = context;
        this.username = username;
        layoutInflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        totalCost = totalCost;
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
            convertView = layoutInflater.inflate(R.layout.cart_list,null);
            viewHolder.itemName = (TextView) convertView.findViewById(R.id.itemName);
            viewHolder.itemCost = (TextView) convertView.findViewById(R.id.itemCost);
            viewHolder.removefromCart = (Button) convertView.findViewById(R.id.addToCart);
            viewHolder.itemImage = (ImageView) convertView.findViewById(R.id.itemImage);
            viewHolder.quantity = (TextView) convertView.findViewById(R.id.quantity);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }


        String itemname = list.get(position).get("itemName");
        String units = veggies.contains(itemname)?"lb" : "unit";
        int i = images.indexOf(itemname);

        viewHolder.itemName.setText(itemname);
        viewHolder.itemCost.setText("$"+list.get(position).get("itemCost")+"/"+units);
        viewHolder.quantity.setText(list.get(position).get("quantity")+units);

        if(i>-1) {
            int imageindex = Integer.parseInt(images.charAt(i-1)+"");
            viewHolder.itemImage.setImageResource(imageSrc[imageindex]);
        }

        viewHolder.removefromCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   removefromCart(position);
            }
        });
        return convertView;
    }

    public void removefromCart(int position){
        cs = new CartService();

       String msg="";
        if(cs.removefromCart(username,list.get(position).get("itemId")).equalsIgnoreCase("success")){
                    msg=list.get(position).get("itemName") + " deleted from cart "  ;
                     list.remove(position);
                     notifyDataSetChanged();
       }else{
            msg= "failed to remove";
        }
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    class ViewHolder {
        TextView itemName;
        TextView itemCost;
        Button removefromCart;
        TextView quantity;
        ImageView itemImage;
    }

    public String images = "0Apple1Tomato2Cabbage3Carrot4Potato";
    public String veggies = "0Apple1Tomato2Cabbage3Carrot4Potato";
    public int imageSrc[] = {R.drawable.apple,R.drawable.tomato,R.drawable.cabbage,R.drawable.carrot,R.drawable.potato};

}
