package me.project.smartiothome;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseJson_secure {
    private String time;
    private String place;
    public String getTime(){
        return time;
    }
    public String getPlace(){ return place; }
    public ParseJson_secure(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            String rst = jsonObject.getString("result");
            JSONArray jsonArray = new JSONArray(rst);
            for(int i =0; i<jsonArray.length(); i++){
                JSONObject myhouseObject = jsonArray.getJSONObject(i);
                time = myhouseObject.getString("reg_time");
                place = myhouseObject.getString("loc");//수정 필요
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }
}
