package me.project.smartiothome;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseJson_th {

    private String time;
    private String temp;
    private String humi;
    public String getTime(){
        return time;
    }
    public String getTemp(){ return temp; }
    public String getHumi(){ return humi; }
    public ParseJson_th(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            String rst = jsonObject.getString("result");
            JSONArray jsonArray = new JSONArray(rst);
            for(int i =0; i<jsonArray.length(); i++){
                JSONObject myhouseObject = jsonArray.getJSONObject(i);
                time = myhouseObject.getString("reg_time");
                temp = myhouseObject.getString("temp");
                humi = myhouseObject.getString("humi");
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }
}
