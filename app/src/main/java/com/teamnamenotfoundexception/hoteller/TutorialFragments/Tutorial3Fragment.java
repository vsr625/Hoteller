package com.teamnamenotfoundexception.hoteller.TutorialFragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.teamnamenotfoundexception.hoteller.Activities.MainActivity;
import com.teamnamenotfoundexception.hoteller.R;

public class Tutorial3Fragment extends Fragment {
    public Tutorial3Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tut3, container, false);
        Button proceed = v.findViewById(R.id.bProceed);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set the tutorialFlag = true
                SharedPreferences tutorialFlag = getActivity().getSharedPreferences("tutorialFlag",getActivity().getApplicationContext().MODE_PRIVATE);
                tutorialFlag.edit().putBoolean("shown", true).apply();
                // Start MainActivity
                startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                // Finish the activity related to tutorial
                getActivity().finish();
            }
        });
        return v;
    }
}
