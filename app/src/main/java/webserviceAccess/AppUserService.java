package webserviceAccess;

/**
 * Created by surendra on 11/22/2016.
 */
import android.content.Intent;
import android.util.Log;

import com.example.surendra.groceryapp.Login;
import com.example.surendra.groceryapp.MyOrder;

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

public class AppUserService {
    private static final String TAG = "Database";
    String IP =  "http://localhost:8080/webservices/webapi/";

    //Signup
    public String signUp(String utaId,String firstname,String lastname,String password,String email,String phone,String address,String date,String role){
        String status="begin";
        try{
            URL url = new URL(IP+"AppUser");
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
            user.put("username",utaId);
            user.put("role",role);
            user.put("firstname",firstname);
            user.put("lastname",lastname);
            user.put("password",password);
            user.put("email",email);
            user.put("phone",phone);
            user.put("address",address);
            user.put("dob",date);
            OutputStream out = conn.getOutputStream();
            Writer writer = new OutputStreamWriter(out, "UTF-8");
            writer.write(user.toString());
            writer.close();
            out.close();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response=rd.readLine();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                if (response.contains("failed")) {

                    status="User already exists";

                    Log.d(TAG,"User already exists");

                } else {
                    status="Registration complete";
                    Log.d(TAG,status+"* 1");
                }
            } else {
                status="Registration failed";
                Log.d(TAG,status);
            }
        }catch (Exception ex) {
            Log.d(TAG,ex.getMessage()+"* 2");
            ex.getMessage();
        }
        return status;
    }

    //login
    public String login(String username,String password,String role){
        try{
           URL url = new URL(IP+"AppUser/login");
           HttpURLConnection conn = (HttpURLConnection)url.openConnection();
           conn.setRequestMethod("POST");
           conn.setDoOutput(true);
           conn.setDoInput(true);
           conn.setUseCaches(false);
           conn.setAllowUserInteraction(false);
           conn.setRequestProperty("Content-Type", "application/json");
           JSONObject user = new JSONObject();
           user.put("username",username);
           user.put("role",role);
           user.put("password",password);
           OutputStream out = conn.getOutputStream();
           Writer writer = new OutputStreamWriter(out, "UTF-8");
           writer.write(user.toString());
           writer.close();
           out.close();
           BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
           String response=rd.readLine();
            int responseCode = conn.getResponseCode();
              if(response.equalsIgnoreCase("success")){
                  return role;
              }else{
                  return response;
              }
       }catch(Exception ex){
           Log.d(TAG,ex.getMessage());
            return "failed";
       }

    }

    //getCustomerProfile
    public  Map<String,String> getCustomerProfile(String username){
        Map<String,String> map = new HashMap<>();
        try{
            URL url = new URL(IP+"AppUser/customer/"+username);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "text/plain");
            int responseCode = conn.getResponseCode();
            //Log.d("ResponseCode",responseCode+"-->");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder responseStrBuilder = new StringBuilder();
            String response = rd.readLine();

                responseStrBuilder.append(response);
                JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());
                map.put("username",jsonObject.getString("username"));
                map.put("address",jsonObject.getString("address"));
                map.put("dob",jsonObject.getString("dob"));
                map.put("email",jsonObject.getString("email"));
                map.put("firstname",jsonObject.getString("firstname"));
                map.put("lastname",jsonObject.getString("lastname"));
                map.put("phone",jsonObject.getString("phone"));

            return map;
        }catch(Exception ex){
            return null;
        }
    }

    //updateProfile
    public String updateProfile(String utaId,String firstname,String lastname,String email,String phone,String address,String date){
        try{
            URL url = new URL(IP+"AppUser");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setRequestProperty("Content-Type", "application/json");
            JSONObject user = new JSONObject();
            user.put("username",utaId);
            user.put("firstname",firstname);
            user.put("lastname",lastname);
            user.put("email",email);
            user.put("phone",phone);
            user.put("address",address);
            user.put("dob",date);
            OutputStream out = conn.getOutputStream();
            Writer writer = new OutputStreamWriter(out, "UTF-8");
            writer.write(user.toString());
            writer.close();
            out.close();
            //Log.d("TAG","Response code"+conn.getResponseCode());
            //Log.d("request sent", "request sent "+user.toString());
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response=rd.readLine();
            //Log.d("response ----->", response);
            return response.equalsIgnoreCase("success") ? "successfully updated" : "failed to update";

        }catch(Exception ex){
           return "failed to update";
        }
    }


  public String changePassword(String oldPassword,String newPassword,String username){
      try{
          URL url = new URL(IP+"AppUser/changePassword");
          HttpURLConnection conn = (HttpURLConnection)url.openConnection();
          conn.setRequestMethod("PUT");
          conn.setDoOutput(true);
          conn.setDoInput(true);
          conn.setUseCaches(false);
          conn.setAllowUserInteraction(false);
          conn.setRequestProperty("Content-Type", "application/json");
          JSONObject user = new JSONObject();
          user.put("username",username+":"+newPassword);
          user.put("password",oldPassword);
          OutputStream out = conn.getOutputStream();
          Writer writer = new OutputStreamWriter(out, "UTF-8");
          writer.write(user.toString());
          writer.close();
          out.close();
          Log.d("response code",conn.getResponseCode()+"");
          BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
          String response=rd.readLine();
          Log.d("response ----->", response);
          return response.equalsIgnoreCase("success") ? "Password changed" : "failed to update";
      }catch(Exception ex){
          return "failed";
      }
  }

    public String addGrocer(String grocerId,String firstname,String lastname,String password,String email,String phone,String address,String date){
        String status="begin";
        try{
            URL url = new URL(IP+"AppUser");
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
            user.put("username",grocerId);
            user.put("role","grocer");
            user.put("firstname",firstname);
            user.put("lastname",lastname);
            user.put("password",password);
            user.put("email",email);
            user.put("phone",phone);
            user.put("address",address);
            user.put("dob",date);
            OutputStream out = conn.getOutputStream();
            Writer writer = new OutputStreamWriter(out, "UTF-8");
            writer.write(user.toString());
            writer.close();
            out.close();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response=rd.readLine();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                if (response.contains("failed")) {

                    status="User already exists";

                    Log.d(TAG,"User already exists");

                } else {
                    status="Registration complete";
                    Log.d(TAG,status+"* 1");
                }
            } else {
                status="Registration failed";
                Log.d(TAG,status);
            }
        }catch (Exception ex) {
            Log.d(TAG,ex.getMessage()+"* 2");
            ex.getMessage();
        }
        return status;
    }
    public String deleteFromGrocer(String username){
        try{
            URL url = new URL(IP+"AppUser/"+username);
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

    public List<Map<String,String>> getGrocerList(){
        List<Map<String,String>> list = new ArrayList<>();
        try{
            URL url = new URL(IP+"AppUser/grocerList");
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
                map.put("username",jsonObject.getString("username"));
                map.put("firstname",jsonObject.getString("firstname"));
                list.add(map);
            }
            return list;
        }catch(Exception ex){
            return null;
        }
    }
    public String deleteFromCustomer(String username){
        try{
            URL url = new URL(IP+"AppUser/"+username);
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

    public List<Map<String,String>> getCustomerList(){
        List<Map<String,String>> list = new ArrayList<>();
        try{
            URL url = new URL(IP+"AppUser/customerList");
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
                map.put("username",jsonObject.getString("username"));
                map.put("firstname",jsonObject.getString("firstname"));
                list.add(map);
            }
            return list;
        }catch(Exception ex){
            return null;
        }
    }
}
