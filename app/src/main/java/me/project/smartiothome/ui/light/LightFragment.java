package me.project.smartiothome.ui.light;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import me.project.smartiothome.R;

public class LightFragment extends Fragment {

    private LightViewModel lightViewModel;
    private ArrayList<String> testData;
    private WebView webview;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        lightViewModel =
                new ViewModelProvider(this).get(LightViewModel.class);
        View root = inflater.inflate(R.layout.fragment_light, container, false);

        webview = root.findViewById(R.id.webView);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);     //자바스크립트 허용
        webSettings.setUseWideViewPort(true);       //wide ViewPort 사용 허용
        webSettings.setLoadWithOverviewMode(true);  //컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정

        webview.setWebViewClient(new WebViewClient());
        //webView.loadUrl("http://192.168.0.6:8091/?action=stream");//error in this website
        webview.loadUrl("http://218.39.125.134:3214/?action=stream");//error in this website
        webview.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN){
                    if(keyCode == KeyEvent.KEYCODE_BACK){
                        if(webview != null){
                            if(webview.canGoBack()){
                                webview.goBack();
                            }else{
                                getActivity().onBackPressed();
                            }
                        }
                    }
                }
                return true;
            }
        });

        ListView listview = (ListView) root.findViewById(R.id.noti_list);
        lightViewModel.getData().observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(@Nullable ArrayList<String> s) {
                testData = s;
                ArrayAdapter<String> Adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, testData);
                listview.setAdapter(Adapter);
            }
        });
        return root;
    }
}