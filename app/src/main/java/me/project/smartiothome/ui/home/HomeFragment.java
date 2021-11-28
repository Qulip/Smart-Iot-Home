package me.project.smartiothome.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import org.w3c.dom.Text;

import me.project.smartiothome.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        final TextView text_temp = root.findViewById(R.id.text_temperature);
        final TextView text_humi = root.findViewById(R.id.text_humidity);
        final TextView text_detect = root.findViewById(R.id.text_Siren);
        final Button refresh = root.findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                homeViewModel.Refresh();
            }
        });
        homeViewModel.getTemperature().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {text_temp.setText(s);}
        });
        homeViewModel.getHumidity().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {text_humi.setText(s);}
        });
        homeViewModel.getDetect().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {text_detect.setText(s);}
        });
        return root;
    }
}