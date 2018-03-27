package com.teamnamenotfoundexception.hoteller.Database;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.teamnamenotfoundexception.hoteller.Activities.MainActivity;

import java.util.ArrayList;


public class CartManager {

    public static final String TAG = "CartManager";
    private FirebaseAuth mAuth = null;
    private FirebaseUser mUser = null;
    private static FirebaseDatabase mFirebaseDatabase = null;
    private static FirebaseHelper mFirebaseHelper = null;
    private ArrayList<DishItem> mCartItems;
    private Context mAppContext;
    private static CartManager mCartManager = null;
    private ArrayList<Integer> mFavoriteList;
    private int mTotalOrderPrice = 0;
    private UpdateNotificationCount mUpdateNotificationCount;

    private CartManager(Context context) {
        // Initialize all variables
        mAppContext = context;
        mAuth = null;
        mUser = null;

        mFavoriteList = new ArrayList<>();
        mCartItems = new ArrayList<>();

        // Firebase related
        mFirebaseDatabase = null;
        mFirebaseHelper = new FirebaseHelper(mAppContext);
        mUpdateNotificationCount = null;
    }

    public static CartManager get(Context c) {
        // Simpleton Pattern
        if (mCartManager == null) {
            mCartManager = new CartManager(c);
        }
        return mCartManager;
    }

    public void setFavoriteList(ArrayList<Integer> arrayList) {
        this.mFavoriteList = arrayList;
    }

    public void initializeFavoriteList() {
        try {
            mFirebaseHelper.fetchFavoriteList(mUser);
        } catch (Exception e) {
            Log.i("CartManager Exception:", "error fetching favs");
        }
    }

    public void addToFavorites(DishItem item) {
        try {
            CartManager.get(mAppContext).getFavoriteIdList().add(item.getDishId());
            mFirebaseHelper.updateFavoriteList(mFavoriteList, mUser);
        } catch (Exception e) {
            Log.i("CartManager Exception:", "cannot update favorite list");
        }
    }

    public void removeFromFavorites(DishItem item) {
        try {
            CartManager.get(mAppContext).getFavoriteIdList().remove(Integer.valueOf(item.getDishId()));
            mFirebaseHelper.updateFavoriteList(mFavoriteList, mUser);
        } catch (Exception e) {
            Log.i("CartManager Exception:", "cannto update after removing");
        }
    }

    public void setListenerInterface(Activity activity) {
        if (activity != null) {
            mUpdateNotificationCount = (MainActivity) activity;
        }
    }

    public ArrayList<DishItem> getFavItems() {
        ArrayList<DishItem> favoriteItems = new ArrayList<DishItem>();
        ArrayList<DishItem> allDishList = DishRepository.get(mAppContext).getDishItemsList();
        for (int i = 0; i < allDishList.size(); i++)
            if (allDishList.get(i).isDishFav() == 1)
                favoriteItems.add(allDishList.get(i));
        return favoriteItems;
    }

    public ArrayList<Integer> getFavoriteIdList() {
        return mFavoriteList;
    }

    public ArrayList<DishItem> getCartItems() {
        return mCartItems;
    }

    public void addDishToCart(DishItem item) {
        mCartItems.add(item);
        mTotalOrderPrice += item.getTotalPrice();
        mUpdateNotificationCount.updateNotifyCount();
    }

    public void removeDishFromCart(DishItem item) {
        mCartItems.remove(item);
        mUpdateNotificationCount.updateNotifyCount();
        mTotalOrderPrice -= item.getTotalPrice();
        item.setQuantity(0);
    }

    public int getTotalOrderPrice() {
        return mTotalOrderPrice;
    }

    public void setUser(FirebaseUser user) {
        this.mUser = user;
    }

    public void setCartManagerToNull() {
        mCartManager = null;
    }

    public void setAuth(FirebaseAuth auth) {
        this.mAuth = auth;
    }

    public void setFirebaseDatabase(FirebaseDatabase firebaseDatabase) {
        mFirebaseDatabase = firebaseDatabase;
        mFirebaseHelper.setFirebaseDatabase(mFirebaseDatabase);
    }

    public void resetCartItems() {
        mCartItems = new ArrayList<>();
        mTotalOrderPrice = 0;
        ArrayList<DishItem> dishes = DishRepository.get(mAppContext).getDishItemsList();
        for (int i = 0; i < dishes.size(); i++) dishes.get(i).setIsCart(0);
    }
}
