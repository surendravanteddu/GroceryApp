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
 * Created by surendra on 11/28/2016.
 */

public class CrewService {
    String IP =  "http://localhost:8080/webservices/webapi/";
    String TAG = "TAG";
    public List<List<String>> getAssignCrew(){
        try{
            URL url = new URL(IP+"crew/toAssign");
            List<List<String>> list = new ArrayList<>();
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "text/plain");
            int responseCode = conn.getResponseCode();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String response;
            response=rd.readLine();
            List<String> driver = new ArrayList<>();
            List<String> sm = new ArrayList<>();
            JSONArray jsonarray = new JSONArray(response);
            for(int j=0; j<jsonarray.length();j++){
                JSONObject jsonObject = jsonarray.getJSONObject(j);
                if(jsonObject.get("role").toString().equals("dr")){
                    driver.add(jsonObject.get("firstname").toString());
                }else{
                    sm.add(jsonObject.get("firstname").toString());
                }
            }
            list.add(driver);
            list.add(sm);
            return list;
        }catch(Exception ex){
            return null;
        }
    }


    public String assignCrew(String orderId,String dr,String sm){
        try{
            URL url = new URL(IP+"orders");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setRequestProperty("Content-Type", "application/json");
            JSONObject user = new JSONObject();
            user.put("orderId",orderId);
            user.put("driverId",dr);
            user.put("storeMemeberId",sm);
            OutputStream out = conn.getOutputStream();
            Writer writer = new OutputStreamWriter(out, "UTF-8");
            writer.write(user.toString());
            writer.close();
            out.close();
            Log.d("response code",conn.getResponseCode()+"");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response=rd.readLine();
            Log.d("response ----->", response);
            return response.equalsIgnoreCase("success") ? "Crew Assigned" : "failed ";

        }catch(Exception ex){
            return "failed";
        }
    }

    public String addCrew(String crewId,String role,String firstname,String lastname,String email,String phone,String address,String date){
        String status="begin";
        try{
            URL url = new URL(IP+"crew");
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
            user.put("crewId",crewId);
            user.put("role",role);
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
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response=rd.readLine();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                if (response.contains("failed")) {

                    status="Crew already esists";

                    Log.d(TAG,"Crew already exists");

                } else {
                    status="Crew added";
                    Log.d(TAG,status+"* 1");
                }
            } else {
                status="Crew could not be added";
                Log.d(TAG,status);
            }
        }catch (Exception ex) {
            Log.d(TAG,ex.getMessage()+"* 2");
            ex.getMessage();
        }
        return status;
    }

    public List<Map<String,String>> getCrewList(){
        List<Map<String,String>> list = new ArrayList<>();
        try{
            URL url = new URL(IP+"crew");
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
                map.put("crewId",jsonObject.getString("crewId"));
                map.put("role", jsonObject.getString("role"));
                map.put("firstname", jsonObject.getString("firstname"));
                map.put("lastname", jsonObject.getString("lastname"));
                map.put("email", jsonObject.getString("lastname"));
                map.put("phone", jsonObject.getString("lastname"));
                map.put("address", jsonObject.getString("lastname"));
                map.put("dob", jsonObject.getString("lastname"));
                list.add(map);
            }
            return list;
        }catch(Exception ex){
            return null;
        }
    }

    public String removefromCrew(String crewId){
        try{
            URL url = new URL(IP+"crew/"+crewId);
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

    public String addToVacationList(String crewId){
        String status="begin";
        try{
            URL url = new URL(IP+"vacationList");
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
            item.put("crewId",crewId);
            OutputStream out = conn.getOutputStream();
            Writer writer = new OutputStreamWriter(out, "UTF-8");
            writer.write(item.toString());
            writer.close();
            out.close();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response=rd.readLine();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                if (response.contains("failed")) {

                    status="Could Not Add To VacationList";

                    Log.d(TAG,"User already exists on vacation list");

                } else {
                    status="Crew Member Added";
                    Log.d(TAG,status+"* 1");
                }
            } else {
                status="No Crew Member Added";
                Log.d(TAG,status);
            }
        }catch (Exception ex) {
            Log.d(TAG,ex.getMessage()+"* 2");
            ex.getMessage();
        }
        return status;
    }

    public String deleteFromVacationList(String crewId){
        try{
            URL url = new URL(IP+"vacationList/"+crewId);
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

    public List<Map<String,String>> getVacationList(){
        List<Map<String,String>> list = new ArrayList<>();
        try{
            URL url = new URL(IP+"vacationList");
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
                map.put("crewId",jsonObject.getString("crewId"));
                map.put("firstname",jsonObject.getString("firstname"));
                list.add(map);
            }
            return list;
        }catch(Exception ex){
            return null;
        }
    }


    public String addToDropOff(String id,String address){
        String status="begin";
        try{
            URL url = new URL(IP+"dropOff");
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
            item.put("id",id);
            item.put("address",address);
            OutputStream out = conn.getOutputStream();
            Writer writer = new OutputStreamWriter(out, "UTF-8");
            writer.write(item.toString());
            writer.close();
            out.close();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response=rd.readLine();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                if (response.contains("failed")) {

                    status="Could Not Add To DropOff";

                    Log.d(TAG,"DropOff already exists");

                } else {
                    status="DropOff added";
                    Log.d(TAG,status+"* 1");
                }
            } else {
                status="No Drop Off Added";
                Log.d(TAG,status);
            }
        }catch (Exception ex) {
            Log.d(TAG,ex.getMessage()+"* 2");
            ex.getMessage();
        }
        return status;
    }

    public String deleteFromDropOff(String id){
        try{
            URL url = new URL(IP+"dropOff/"+id);
            Log.d("id",id);
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

    public List<Map<String,String>> getDropOffList(){
        List<Map<String,String>> list = new ArrayList<>();
        try{
            URL url = new URL(IP+"dropOff");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "text/plain");
            int responseCode = conn.getResponseCode();
            Log.d("response Code dropoff",responseCode+"");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response;
            response=rd.readLine();
            Log.d("dropoffs****",response);
            JSONArray jsonarray = new JSONArray(response);;
            Map<String,String> map ;
            for(int j=0; j<jsonarray.length();j++){
                JSONObject jsonObject = jsonarray.getJSONObject(j);
                map = new HashMap<>();
                map.put("id",jsonObject.getString("id"));
                map.put("address", jsonObject.getString("address"));
                list.add(map);
            }
            return list;
        }catch(Exception ex){
            return null;
        }
    }
}
