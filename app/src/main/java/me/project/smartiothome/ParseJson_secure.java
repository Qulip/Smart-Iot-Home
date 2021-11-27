package me.project.smartiothome;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParseJson_secure {
    private String time;
    private String place;
    private ArrayList<String> all_reg;
    private int length;

    public String getTime(){ return time; }
    public String getPlace(){ return place; }
    public ArrayList<String> getAll(){return all_reg;}
    public int getLength(){return length;}

    public ParseJson_secure(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            String rst = jsonObject.getString("result");
            JSONArray jsonArray = new JSONArray(rst);
            all_reg = new ArrayList<>();
            length = jsonArray.length();
            for(int i =0; i<jsonArray.length(); i++){
                JSONObject myhouseObject = jsonArray.getJSONObject(i);
                time = myhouseObject.getString("reg_time");
                place = myhouseObject.getString("loc");//위치에 따라서 수정 필요
                if(place.equals("1")){
                    all_reg.add(time+" : 현관");
                }else if(place.equals("2")){
                    all_reg.add(time+" : 창문");
                }
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }
}
