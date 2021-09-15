package me.project.smartiothome;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseJson {
    private String date;
    private String detect;
    private String temp;
    private String humi;
    public String getDate(){
        return date;
    }
    public String getDetect(){
        return detect;
    }
    public String getTemp(){ return temp; }
    public String getHumi(){ return humi; }
    public ParseJson(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            String rst = jsonObject.getString("result");
            JSONArray jsonArray = new JSONArray(rst);
            for(int i =0; i<jsonArray.length(); i++){
                JSONObject myhouseObject = jsonArray.getJSONObject(i);
                date = myhouseObject.getString("now_date");
                detect = myhouseObject.getString("detect");
                temp = myhouseObject.getString("temp");
                humi = myhouseObject.getString("humi");
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }
}
