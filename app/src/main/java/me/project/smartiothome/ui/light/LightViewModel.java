package me.project.smartiothome.ui.light;

import android.content.ContentValues;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import me.project.smartiothome.ParseJson_secure;
import me.project.smartiothome.RequestHttpURLConnection;

public class LightViewModel extends ViewModel {

    private String strUrl_sec;
    private ArrayList<String> reg_times;
    private MutableLiveData<ArrayList<String>> liveData;

    public LightViewModel() {
        strUrl_sec = "http://192.168.0.6:8090/getjson_sec.php";
        reg_times = new ArrayList<>();
        liveData = new MutableLiveData<ArrayList<String>>();
        NetWorkTask_Light networkTask = new NetWorkTask_Light(strUrl_sec,null);   //온
        networkTask.execute();

    }

    public class NetWorkTask_Light extends AsyncTask<Void, Void, String> {
        private String url_sec;
        private ContentValues values;

        public NetWorkTask_Light(String url_sec, ContentValues values) {
            this.values = values;
            this.url_sec = url_sec;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url_sec, values); // 해당 URL로 부터 결과물을 얻어온다.
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ParseJson_secure sec = new ParseJson_secure(s);
            reg_times = sec.getAll();
            liveData.setValue(reg_times);
        }
    }

    public ArrayList<String> getAll() { return reg_times; }
    public String getNull() {
        if(reg_times!=null){
            return "true";
        }
        return "false";
    }
    public LiveData<ArrayList<String>> getData(){ return liveData; }
}