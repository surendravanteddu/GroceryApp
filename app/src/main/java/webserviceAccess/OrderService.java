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

/**
 * Created by surendra on 11/25/2016.
 */

public class OrderService {

    String IP =  "http://localhost:8080/webservices/webapi/";
    public List<String> getDropOffs(){
        try{
            URL url = new URL(IP+"dropOff");
            List<String> list = new ArrayList<>();
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "text/plain");
            int responseCode = conn.getResponseCode();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String response;
            response=rd.readLine();
            JSONArray jsonarray = new JSONArray(response);
            for(int j=0; j<jsonarray.length();j++){
                JSONObject jsonObject = jsonarray.getJSONObject(j);
                list.add(jsonObject.getString("address"));
            }
            return list;
        }catch(Exception ex){
            return null;
        }
    }
  public String placeOrder(String username,String droppoff){
      try{
          URL url = new URL(IP+"orders");
          HttpURLConnection conn = (HttpURLConnection) url.openConnection();
          conn.setRequestMethod("POST");
          conn.setDoOutput(true);
          conn.setDoInput(true);
          conn.setUseCaches(false);
          conn.setAllowUserInteraction(false);
          conn.setRequestProperty("Content-Type", "application/json");
          JSONObject user = new JSONObject();
          user.put("customerId",username);
          user.put("grocerId","100");
          user.put("dropOffId",droppoff);
          OutputStream out = conn.getOutputStream();
          Writer writer = new OutputStreamWriter(out, "UTF-8");
          writer.write(user.toString());
          writer.close();
          out.close();
          Log.d("response code",conn.getResponseCode()+"");
          BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
          String response=rd.readLine();
          return response;
      }catch(Exception ex){
          return "failed";
      }
  }

    public List<Map<String,String>> getOrders(String username){
        try{
            List<Map<String,String>>list = new ArrayList<>();
            URL url = new URL(IP+"orders/"+username);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "text/plain");
            int responseCode = conn.getResponseCode();
            Log.d("Response code",responseCode+"");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = rd.readLine();
            JSONArray jsonArray = new JSONArray(response);
            Map<String,String> map ;
            for(int j=0; j<jsonArray.length();j++) {
                JSONObject jsonObject = jsonArray.getJSONObject(j);
                map = new HashMap<>();
                map.put("orderId",  jsonObject.getString("orderId"));
                map.put("itemCount", jsonObject.getString("itemCount"));
                map.put("dropoff", jsonObject.getString("dropOffId"));
                map.put("cost",jsonObject.getString("cost"));
                map.put("date",jsonObject.getString("date"));
                map.put("status",jsonObject.getString("status"));
                list.add(map);
            }
            return list;
        }catch(Exception ex){
            return null;
        }
    }


    public List<Map<String,String>> getAllOrders(){
        try{
            List<Map<String,String>>list = new ArrayList<>();
            URL url = new URL(IP+"orders");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "text/plain");
            int responseCode = conn.getResponseCode();
            Log.d("Response code",responseCode+"");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = rd.readLine();
            JSONArray jsonArray = new JSONArray(response);
            Map<String,String> map ;
            for(int j=0; j<jsonArray.length();j++) {
                JSONObject jsonObject = jsonArray.getJSONObject(j);
                map = new HashMap<>();
                map.put("orderId",  jsonObject.getString("orderId"));
                map.put("itemCount", jsonObject.getString("itemCount"));
                map.put("dropoff", jsonObject.getString("dropOffId"));
                map.put("cost",jsonObject.getString("cost"));
                map.put("date",jsonObject.getString("date"));
                map.put("status",jsonObject.getString("status"));
                list.add(map);
            }
            return list;
        }catch(Exception ex){
            return null;
        }
    }
    public List<Map<String,String>> getOrderItems(String orderId){
        try{
            List<Map<String,String>>list = new ArrayList<>();
            URL url = new URL(IP+"orders/items/"+orderId);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "text/plain");
            int responseCode = conn.getResponseCode();
            Log.d("Response code",responseCode+"");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = rd.readLine();
            JSONArray jsonArray = new JSONArray(response);
            Map<String,String> map ;
            for(int j=0; j<jsonArray.length();j++) {
                JSONObject jsonObject = jsonArray.getJSONObject(j);
                map = new HashMap<>();
                map.put("itemName", jsonObject.getString("itemName"));
                map.put("cost",jsonObject.getString("cost"));
                map.put("quantity",jsonObject.getString("quantity"));
                list.add(map);
            }
            return list;

        }catch(Exception ex){
            return null;
        }
    }

}
