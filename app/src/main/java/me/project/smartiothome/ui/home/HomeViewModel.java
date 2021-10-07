package me.project.smartiothome.ui.home;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import me.project.smartiothome.RequestHttpURLConnection;
import me.project.smartiothome.ParseJson;

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
        //strUrl = "http://218.39.125.134:3415/getjson.php";    //외부 접속
        NetWorkTask networkTask = new NetWorkTask(strUrl, null);
        networkTask.execute();
    }

    public class NetWorkTask extends AsyncTask<Void, Void, String> {
        private String url;
        private ContentValues values;

        public NetWorkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mText.setValue(s);
            if(s!=null) {
                ParseJson json = new ParseJson(s);
                Temperature.setValue(json.getTemp()+"'C");
                Humidity.setValue(json.getHumi()+"%");
                //detect.setValue(json.getDetect());

                if(json.getDetect().equals("0")){
                    Detect.setValue("No");
                }else{
                    Detect.setValue("Check Now");
                }

            }else{
                Temperature.setValue("Connection Error");
                Humidity.setValue("Connection Error");
            }
        }
    }
    public LiveData<String> getTemperature(){ return Temperature; }
    public LiveData<String> getHumidity(){ return Humidity; }
    public LiveData<String> getDetect(){ return Detect; }
}
