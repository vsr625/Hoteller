package com.teamnamenotfoundexception.hoteller.Database;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DishRepository {

    public static final String TAG = "DishRepository";
    private static DishRepository mDishRepository = null;
    private static DatabaseHelper mDatabaseHelper;
    private static FirebaseHelper mFirebaseHelper;
    private static ArrayList<DishItem> mDishItemsList = null;


    private static HashMap<String, ArrayList<DishItem>> mRestDishItem;
    private Context mAppContext;
    private SharedPreferences mSharedPref;

    private DishRepository(Context context) {
        mAppContext = context;
        mDatabaseHelper = new DatabaseHelper(mAppContext);
        mFirebaseHelper = new FirebaseHelper(mAppContext);
        mDishItemsList = new ArrayList<>();
        mRestDishItem = new HashMap<>();
        mSharedPref = mAppContext.getSharedPreferences("user_data", Context.MODE_PRIVATE);
    }

    public static DishRepository get(Context c) {
        if (mDishRepository == null) {
            mDishRepository = new DishRepository(c);
        }
        return mDishRepository;
    }

    public static void setDishRepository(DishRepository dishRepository) {
        mDishRepository = dishRepository;
    }

    public void initializeDishItemsList(String RestId) {
        if(mRestDishItem.containsKey(RestId)){
            mDishItemsList = mRestDishItem.get(RestId);
        }else{
            mRestDishItem.put(RestId, mFirebaseHelper.fetchItems(RestId));
            mDishItemsList = mRestDishItem.get(RestId);
        }
    }

    public ArrayList<DishItem> getDishItemsList() {
        return mDishItemsList;
    }

    public HashMap<String, ArrayList<DishItem>> getmRestDishItem() {
        return mRestDishItem;
    }
}
