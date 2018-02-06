package com.sstanislavsky.acts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DeclensionNameFragment extends Fragment {

    public DeclensionNameFragment(){}

    public static DeclensionNameFragment newInstance() {
        return new DeclensionNameFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_declension_name, container, false);
    }
}
