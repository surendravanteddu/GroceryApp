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

public class ItemService {
    String IP =  "http://localhost:8080/webservices/webapi/";

    public List<Map<String,String>> getItemsList(){
        List<Map<String,String>> list = new ArrayList<>();
        try{
            URL url = new URL(IP+"item/itemList");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "text/plain");
            int responseCode = conn.getResponseCode();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response;
            response=rd.readLine();
            JSONArray jsonarray = new JSONArray(response);;
            Map<String,String> map ;
            for(int j=0; j<jsonarray.length();j++){
                JSONObject jsonObject = jsonarray.getJSONObject(j);
                 map = new HashMap<>();
                map.put("itemName",jsonObject.getString("itemName"));
                map.put("itemCost", jsonObject.getString("cost"));
                map.put("itemId", jsonObject.getString("itemId"));
                map.put("quantity", jsonObject.getString("quantity"));
                list.add(map);
            }
            return list;
        }catch(Exception ex){
            return null;
        }
    }

    public List<Map<String,String>> searchItemsList(String itemName){
        List<Map<String,String>> list = new ArrayList<>();
        try{
            URL url = new URL(IP+"item/search/"+itemName);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "text/plain");
            int responseCode = conn.getResponseCode();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response;
            response=rd.readLine();
            JSONArray jsonarray = new JSONArray(response);;
            Map<String,String> map ;
            for(int j=0; j<jsonarray.length();j++){
                JSONObject jsonObject = jsonarray.getJSONObject(j);
                map = new HashMap<>();
                map.put("itemName",jsonObject.getString("itemName"));
                map.put("itemCost", jsonObject.getString("cost"));
                map.put("itemId", jsonObject.getString("itemId"));
                map.put("quantity", jsonObject.getString("quantity"));
                list.add(map);
            }
            return list;
        }catch(Exception ex){
            return null;
        }
    }

    public String removefromItems(String itemId){
        try{
            URL url = new URL(IP+"item/"+itemId);
            Log.d("itemID:",itemId);
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

    public String addItems(String itemName,String cost,String quantity){
        String status="begin";
        try{
            URL url = new URL(IP+"item");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            //conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setRequestProperty("Content-Type", "application/json");
            //conn.connect();
            JSONObject item = new JSONObject();
            item.put("itemName",itemName);
            item.put("cost",cost);
            item.put("quantity",quantity);
            OutputStream out = conn.getOutputStream();
            Writer writer = new OutputStreamWriter(out, "UTF-8");
            writer.write(item.toString());
            writer.close();
            out.close();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response=rd.readLine();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                if (response.contains("failed")) {

                    status="Could Not Add Item";

                    Log.d("TAG","User already exists");

                } else {
                    status="Item Added";
                    Log.d("TAG",status+"* 1");
                }
            } else {
                status="No Item Added";
                Log.d("TAG",status);
            }
        }catch (Exception ex) {
            Log.d("TAG",ex.getMessage()+"* 2");
            ex.getMessage();
        }
        return status;
    }

}
