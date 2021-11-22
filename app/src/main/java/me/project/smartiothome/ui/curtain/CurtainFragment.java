package me.project.smartiothome.ui.curtain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

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
        WebView webview = (WebView) root.findViewById(R.id.switchwebview);
        webview.setVisibility(View.INVISIBLE);
        light_On.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webview.loadUrl(control_Url + "/led?pinD8=on");

            }
        });
        light_Off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webview.loadUrl(control_Url + "/led?pinD8=off");
            }
        });

        return root;
    }
}