package com.teamnamenotfoundexception.hoteller.Database;

import android.content.Context;

import java.util.ArrayList;

public class RestaurantRepository {
    private double lat, log;
    private static RestaurantRepository mRestRepository = null;
    private static FirebaseHelper mFirebaseHelper;
    private static ArrayList<Restaurant> mRestList = null;


    public ArrayList<Restaurant> getmRestList() {
        return mRestList;
    }
    private Context mAppContext;

    private RestaurantRepository(Context c){
        mAppContext = c;
        mFirebaseHelper = new FirebaseHelper(c);
        mRestList = new ArrayList<>();
    }
    public static RestaurantRepository get(Context c){
        if(mRestRepository == null)
            mRestRepository = new RestaurantRepository(c);
        return mRestRepository;
    }

    public void initializeNearByRestaurants(){
        mRestList = new ArrayList<>();
        getGpsCoordinates();
        mRestList.addAll(mFirebaseHelper.fetchNearByRestaurants(lat, log));
    }

    void getGpsCoordinates(){
        lat = log = 0;
    }
}
