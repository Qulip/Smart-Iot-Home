package me.project.smartiothome.ui.home;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import me.project.smartiothome.RequestHttpURLConnection;
import me.project.smartiothome.ParseJson;
import me.project.smartiothome.ParseJson_th;
import me.project.smartiothome.ParseJson_secure;

import java.net.URL;

public class HomeViewModel extends ViewModel {

    private URL Url;
    private String strUrl,strCookie,result, strUrl_sec, strUrl_th;
    private MutableLiveData<String> mText;
    private MutableLiveData<String> Temperature;
    private MutableLiveData<String> Humidity;
    private MutableLiveData<String> Detect;

    Handler handler = new Handler();

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        Temperature = new MutableLiveData<>();
        Humidity = new MutableLiveData<>();
        Detect = new MutableLiveData<>();
        strUrl = "http://192.168.0.6:8090/getjson.php";      //내부 라즈베리 파이 json 추후 외부 IP로 변경
        strUrl_sec = "http://192.168.0.6:8090/getjson_sec.php";      //내부 라즈베리 파이 json 추후 외부 IP로 변경
        strUrl_th = "http://192.168.0.6:8090/getjson_th.php";      //내부 라즈베리 파이 json 추후 외부 IP로 변경
        /*
        strUrl = "http://218.39.125.134:3415/getjson.php";    //외부 접속
        strUrl_sec = "http://218.39.125.134:3415/getjson_sec.php";      //내부 라즈베리 파이 json 추후 외부 IP로 변경
        strUrl_th = "http://218.39.125.134:3415/getjson_th.php";      //내부 라즈베리 파이 json 추후 외부 IP로 변경
        */
        NetWorkTask networkTask = new NetWorkTask(strUrl_th, null, true);   //온
        networkTask.execute();
        networkTask = new NetWorkTask(strUrl_sec, null, false);
        networkTask.execute();
    }

    public class NetWorkTask extends AsyncTask<Void, Void, String> {
        private String url_th;
        private String url_sec;
        private ContentValues values;

        public NetWorkTask(String url_th, String url_sec, ContentValues values) {

            this.url_th = url_th;
            this.values = values;
            this.url_sec = url_sec;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result, temp, humi, sec; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url_th, values); // 해당 URL로 부터 결과물을 얻어온다.
            if(result!=null){
                ParseJson_th json_th = new ParseJson_th(s);
                temp = json_th.getTemp() + "'C";
                humi = json_th.getHumi() + "%";
            }else{
                temp = "Connection Error";
                humi = "Connection Error";
            }
            result = requestHttpURLConnection.request(url_sec, values); // 해당 URL로 부터 결과물을 얻어온다.
            if(result!=null){
                ParseJson_th json_secure = new ParseJson_th(result);
                if (json_secure.getPlace().equals("0")) {
                    Detect.setValue("No");
                } else {
                    Detect.setValue("Check Now");
                }
            }else{
                temp = "Connection Error";
                humi = "Connection Error";
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mText.setValue(s);
            if(s!=null) {
                if(place) {
                    ParseJson_th json_th = new ParseJson_th(s);
                    Temperature.setValue(json_th.getTemp() + "'C");
                    Humidity.setValue(json_th.getHumi() + "%");
                }else {
                    ParseJson_secure json_secure = new ParseJson_secure(s);
                    if (json_secure.getPlace().equals("0")) {
                        Detect.setValue("No");
                    } else {
                        Detect.setValue("Check Now");
                    }
                }

            }else{
                if(place) {
                    Temperature.setValue("Connection Error");
                    Humidity.setValue("Connection Error");
                }else{
                    Detect.setValue("Connection Error");
                }
            }
        }
    }
    public LiveData<String> getTemperature(){ return Temperature; }
    public LiveData<String> getHumidity(){ return Humidity; }
    public LiveData<String> getDetect(){ return Detect; }
}
