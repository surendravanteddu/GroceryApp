package customadaptors;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.surendra.groceryapp.R;

import java.util.List;
import java.util.Map;

public class OrderItemsAdaptor extends BaseAdapter{
    LayoutInflater layoutInflater = null;
    List<Map<String,String>> list ;
    ViewHolder viewHolder;
    Context context ;
    public OrderItemsAdaptor(){

    }
    public OrderItemsAdaptor(Context context, List<Map<String,String>> list){
        this.list = list;
        this.context = context;
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
            convertView = layoutInflater.inflate(R.layout.order_items_list,null);
            viewHolder.itemName = (TextView) convertView.findViewById(R.id.oiitemName);
            viewHolder.itemCost = (TextView) convertView.findViewById(R.id.oiitemCost);
            viewHolder.itemImage = (ImageView) convertView.findViewById(R.id.oiitemImage);
            viewHolder.qunatity = (TextView)  convertView.findViewById(R.id.oiquantity);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        String itemname = list.get(position).get("itemName");
        String units = veggies.contains(itemname)?"lb" : "unit";
        int i = images.indexOf(itemname);
        viewHolder.itemName.setText(itemname);
        viewHolder.itemCost.setText("$"+list.get(position).get("cost")+"/"+units);
        viewHolder.qunatity.setText(list.get(position).get("quantity")+units);
        if(i>-1) {
            int imageindex = Integer.parseInt(images.charAt(i-1)+"");
            viewHolder.itemImage.setImageResource(imageSrc[imageindex]);
        }
        return convertView;
    }


    public String images = "0Apple1Tomato2Cabbage3Carrot4Potato";
    public int imageSrc[] = {R.drawable.apple,R.drawable.tomato,R.drawable.cabbage, R.drawable.carrot,R.drawable.potato};
    public String veggies = "0Apple1Tomato2Cabbage3Carrot4Potato";


    class ViewHolder {
        TextView itemName;
        TextView itemCost;
        ImageView itemImage;
        TextView qunatity;
    }
}
