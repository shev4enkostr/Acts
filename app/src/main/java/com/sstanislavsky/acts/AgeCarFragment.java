package com.sstanislavsky.acts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by stanislav on 2/6/18.
 */

public class AgeCarFragment extends Fragment {

    public AgeCarFragment() {}

    public static AgeCarFragment newInstance() {
        return new AgeCarFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_age_car, container, false);
    }
}
