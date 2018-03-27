package com.teamnamenotfoundexception.hoteller;

import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;


import com.teamnamenotfoundexception.hoteller.TutorialFragments.Tutorial1Fragment;
import com.teamnamenotfoundexception.hoteller.TutorialFragments.Tutorial2Fragment;
import com.teamnamenotfoundexception.hoteller.TutorialFragments.Tutorial3Fragment;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class TutorialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        // Create the Adapter for the tutorial activity
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set the adapter to the tutorial activity
        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        // Set the tutorial page indicator
        CircleIndicator indicator = findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        ArrayList<Fragment> fragments;

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments = new ArrayList<>();
            fragments.add(new Tutorial1Fragment());
            fragments.add(new Tutorial2Fragment());
            fragments.add(new Tutorial3Fragment());
        }

        @Override
        public Fragment getItem(int position) {
            // Return a fragment object in the corresponding position
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            // There are 3 pages in the tutorial activity
            return 3;
        }
    }
}
