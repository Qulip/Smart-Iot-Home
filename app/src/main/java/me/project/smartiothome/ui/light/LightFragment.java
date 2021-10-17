package me.project.smartiothome.ui.light;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import me.project.smartiothome.R;

public class LightFragment extends Fragment {

    private LightViewModel lightViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        lightViewModel =
                new ViewModelProvider(this).get(LightViewModel.class);
        View root = inflater.inflate(R.layout.fragment_light, container, false);
        ArrayList<String> test_regs = lightViewModel.getAll();
        String[] all_reg = {"11","22","33","",""};
        if(test_regs!=null){
            all_reg[3] = "true";
        }else{
            all_reg[3] = "false";
        }
        all_reg[4] = lightViewModel.getNull();
        if(all_reg.length>0) {//error occur in here
            ArrayAdapter<String> Adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, all_reg);

            ListView listview = (ListView) root.findViewById(R.id.noti_list);
            listview.setAdapter(Adapter);
        }
        return root;
    }
}