package me.project.smartiothome;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParseJson_secure {
    private String time;
    private String place;
    private ArrayList<String> all_reg;

    public String getTime(){ return time; }
    public String getPlace(){ return place; }
    public ArrayList<String> getAll(){return all_reg;}

    public ParseJson_secure(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            String rst = jsonObject.getString("result");
            JSONArray jsonArray = new JSONArray(rst);
            all_reg = new ArrayList<>();
            for(int i =0; i<jsonArray.length(); i++){
                JSONObject myhouseObject = jsonArray.getJSONObject(i);
                time = myhouseObject.getString("reg_time");
                place = myhouseObject.getString("loc");//위치에 따라서 수정 필요
                all_reg.add(time+" : "+place);
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }
}
