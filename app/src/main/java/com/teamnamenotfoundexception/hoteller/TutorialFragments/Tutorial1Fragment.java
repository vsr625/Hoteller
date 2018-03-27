package com.teamnamenotfoundexception.hoteller.TutorialFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamnamenotfoundexception.hoteller.R;

public class Tutorial1Fragment extends Fragment {

    public Tutorial1Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tut1, container, false);
    }
}
