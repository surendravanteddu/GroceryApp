package webserviceAccess;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartService {

    String IP =  "http://localhost:8080/webservices/webapi/";

    public String addToCart(Map<String,String> map,String customerId){
        try{
            URL url = new URL(IP+"cart/"+customerId);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            //conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setRequestProperty("Content-Type", "application/json");
            //conn.connect();
            JSONObject user = new JSONObject();
            user.put("itemId",map.get("itemId"));
            user.put("cost",map.get("itemCost"));
            Log.d("item cost  ==>",map.get("itemCost"));
            user.put("quantity",1);
            OutputStream out = conn.getOutputStream();
            Writer writer = new OutputStreamWriter(out, "UTF-8");
            writer.write(user.toString());
            writer.close();
            out.close();
            Log.d("TAG","###########################"+conn.getResponseCode());
            Log.d("request sent", "request sent "+user.toString());
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response=rd.readLine();
            return response;
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            return "failed";
        }
    }

    public List<Map<String,String>> getCart(String customerId){
        List<Map<String,String>> list;
        try{
            list = new ArrayList<>();
            URL url = new URL(IP+"cart/"+customerId);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "text/plain");
            int responseCode = conn.getResponseCode();

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String response = rd.readLine();
            JSONObject jsonObj = new JSONObject(response);
            JSONArray jsonArray = jsonObj.getJSONArray("cart");

            Map<String,String> map ;
            for(int j=0; j<jsonArray.length();j++) {
                JSONObject jsonObject = jsonArray.getJSONObject(j);
                map = new HashMap<>();
                map.put("itemName",  jsonObject.getString("itemName"));
                map.put("itemId", jsonObject.getString("itemId"));
                map.put("quantity", jsonObject.getString("quantity"));
                map.put("itemCost",jsonObject.getString("cost"));
                list.add(map);
            }
       return list;

        }catch (Exception ex){
            return null;
        }
    }

    public String removefromCart(String customerId,String itemId){
        try{
            URL url = new URL(IP+"cart/"+customerId+"/"+itemId);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Content-Type", "text/plain");
            int responseCode = conn.getResponseCode();
            Log.d("Response code ==>",responseCode+"");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = rd.readLine();
            return response;
        }catch (Exception ex){
            return "failed";
        }
    }

}
