package me.project.smartiothome.ui.curtain;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import me.project.smartiothome.R;

public class CurtainFragment extends Fragment {

    private CurtainViewModel curtainViewModel;
    String control_Url = "http://192.168.1.108";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        curtainViewModel =
                new ViewModelProvider(this).get(CurtainViewModel.class);
        View root = inflater.inflate(R.layout.fragment_curtain, container, false);
        Button light_On = (Button) root.findViewById(R.id.light_On);
        Button light_Off = (Button) root.findViewById(R.id.light_Off);
        Button curtain_Up = (Button) root.findViewById(R.id.curtain_up);
        Button curtain_Down = (Button) root.findViewById(R.id.curtain_down);
        WebView webview = (WebView) root.findViewById(R.id.switchwebview);
        webview.setVisibility(View.INVISIBLE);
        Handler handler = new Handler();
        light_On.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {webview.loadUrl(control_Url + "/led?pinD8=on");}
        });
        light_Off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {webview.loadUrl(control_Url + "/led?pinD8=off");}
        });
        curtain_Up.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                webview.loadUrl(control_Url + "/blind?blind=up");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() { webview.loadUrl(control_Url + "/blind?blind=stop"); }
                },2000);
            }
        });
        curtain_Down.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                webview.loadUrl(control_Url + "/blind?blind=down");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() { webview.loadUrl(control_Url + "/blind?blind=stop"); }
                },2000);
            }
        });
        return root;
    }
}