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
import java.util.StringTokenizer;

public class HomeViewModel extends ViewModel {

    private URL Url;
    private String strUrl,strCookie,result, strUrl_sec, strUrl_th;
    private MutableLiveData<String> mText;
    private MutableLiveData<String> Temperature;
    private MutableLiveData<String> Humidity;
    private MutableLiveData<String> Detect;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        Temperature = new MutableLiveData<>();
        Humidity = new MutableLiveData<>();
        Detect = new MutableLiveData<>();

        strUrl_sec = "http://192.168.1.100:8090/getjson_sec.php";
        strUrl_th = "http://192.168.1.104";

        NetWorkTask networkTask = new NetWorkTask(strUrl_th, strUrl_sec, null);
        networkTask.execute();
    }
    public LiveData<String> getTemperature(){ return Temperature; }
    public LiveData<String> getHumidity(){ return Humidity; }
    public LiveData<String> getDetect(){ return Detect; }
    public LiveData<String> getTest(){ return mText; }

    public void Refresh(){
        NetWorkTask netWorkTask = new NetWorkTask(strUrl_th, strUrl_sec, null);
        netWorkTask.execute();
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
            String result, html; // ?????? ????????? ????????? ??????.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            html = requestHttpURLConnection.request(url_th, values); // ?????? URL??? ?????? ???????????? ????????????.
            if(html!=null){
                result = html.substring(39);
            }else{
                result = "Connection Error,Connection Error";
            }
            html = requestHttpURLConnection.request(url_sec, values); // ?????? URL??? ?????? ???????????? ????????????.
            if(html!=null){
                ParseJson_secure json_secure = new ParseJson_secure(html);
                result = result + "," + json_secure.getLength();
            }else{
                result = result + ",Connection Error";
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mText.setValue(s);
            if (s != null) {
                StringTokenizer st = new StringTokenizer(s,",");
                Temperature.setValue(st.nextToken()+"??C");
                Humidity.setValue(st.nextToken()+"%");
                Detect.setValue(st.nextToken()+"Times");
            }
        }
    }
}
