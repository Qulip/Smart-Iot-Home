package me.project.smartiothome.ui.curtain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import me.project.smartiothome.R;

public class CurtainFragment extends Fragment {

    private CurtainViewModel curtainViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        curtainViewModel =
                new ViewModelProvider(this).get(CurtainViewModel.class);
        View root = inflater.inflate(R.layout.fragment_curtain, container, false);

        return root;
    }
}