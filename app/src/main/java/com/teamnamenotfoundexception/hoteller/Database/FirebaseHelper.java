package com.teamnamenotfoundexception.hoteller.Database;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.teamnamenotfoundexception.hoteller.Activities.MainActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class FirebaseHelper {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference ;
    private Context mAppContext ;

    FirebaseHelper(Context c) {
        mFirebaseDatabase = null;
        mAppContext = c;
    }

    public void setFirebaseDatabase(FirebaseDatabase firebaseDatabase) {
        mFirebaseDatabase = firebaseDatabase;
        if(firebaseDatabase != null)
            mDatabaseReference = mFirebaseDatabase.getReference("CustomerData/" );
    }

    public void placeOrder(ArrayList<DishItem> itemsInCart, FirebaseUser user) {
        Log.i("order", "placeOrder called");
        OrderObject orderObject= new OrderObject(itemsInCart);
        String emailId = getEmailStripped(user.getEmail());

        try {
            mDatabaseReference.child(user.getUid()).child(emailId).child("orders").push().setValue(orderObject);
        } catch(Exception e) {
            Log.i("orderError", "error in placing the order");
        }

    }

    public void updateFavoriteList(ArrayList favoriteListItem, FirebaseUser user) {

        Log.i("favorite", "update favorite caleld");
        String emailId = getEmailStripped(user.getEmail());
        Map<String, ArrayList<Integer> > favoriteListMap = new HashMap<>();
        favoriteListMap.put("item_ids", favoriteListItem);
        try {
            mDatabaseReference.child(user.getUid()).child(emailId).child("favorites").setValue(favoriteListMap);
        } catch(Exception e) {
            Log.i("favoriteError", "error in placing the order");
        }
    }

    public String getEmailStripped(String emailId) {
        String emailIdSplit[] = emailId.split("@");
        String _emailId = emailIdSplit[0];
        return _emailId;
    }

    public void fetchFavoriteList(FirebaseUser user) {
        String emailId = getEmailStripped(user.getEmail());
        DatabaseReference databaseReference = mDatabaseReference.child(user.getUid()).child(emailId).child("favorites").child("item_ids");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Integer>> t = new GenericTypeIndicator<List<Integer>>() {};
                ArrayList<Integer> favoriteListIds = (ArrayList<Integer>) dataSnapshot.getValue(t);
                if(favoriteListIds != null) {
                    CartManager.get(mAppContext).setFavoriteList(new ArrayList<>(favoriteListIds));
                    System.out.println("size of favorite list " + CartManager.get(mAppContext).getFavoriteIdList().size());
                    MainActivity.notifyMe();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    class OrderObject {
        public UUID orderId;
        public ArrayList<DishItem> orderedItems ;

        public OrderObject() {
           orderId = UUID.randomUUID();
           orderedItems = new ArrayList<>();
        }

        public OrderObject (ArrayList<DishItem> orderedItems) {
            orderId = UUID.randomUUID();
            this.orderedItems = orderedItems;
        }
    }
}
