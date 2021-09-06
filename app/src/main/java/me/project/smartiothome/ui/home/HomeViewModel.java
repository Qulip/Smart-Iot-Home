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
    private String strUrl,strCookie,result;
    private MutableLiveData<String> mText;
    private MutableLiveData<String> Temperature;
    private MutableLiveData<String> Humidity;

    Handler handler = new Handler();

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        Temperature = new MutableLiveData<>();
        Humidity = new MutableLiveData<>();
        strUrl = "http://192.168.0.6:8090/getjson.php";      //내부 라즈베리 파이 json 추후 외부 IP로 변경
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
                Temperature.setValue(json.getDate());       //일단 온도 가 아님 다른 값 출력
                //Temperature.setValue(s);
                //Temperature.setValue("온도 : " + s.substring(172, 174) + "'C");
                Humidity.setValue("습도 : " + s.substring(198, 200) + "%");
            }else{
                Temperature.setValue("Connection Error");
                Humidity.setValue("Connection Error");
            }
        }
    }
    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<String> getTemperature(){ return Temperature; }
    public LiveData<String> getHumidity(){ return Humidity; }
}
