package com.teamnamenotfoundexception.hoteller.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
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

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class FirebaseHelper {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private Context mAppContext;

    FirebaseHelper(Context c) {
        mFirebaseDatabase = null;
        mAppContext = c;
    }

    public void setFirebaseDatabase(FirebaseDatabase firebaseDatabase) {
        mFirebaseDatabase = firebaseDatabase;
        if (firebaseDatabase != null)
            mDatabaseReference = mFirebaseDatabase.getReference("CustomerData/");
    }

//    public void updateFavoriteList(ArrayList favoriteListItem, FirebaseUser user) {
//        String emailId = getEmailStripped(user.getEmail());
//        Map<String, ArrayList<Integer>> favoriteListMap = new HashMap<>();
//        favoriteListMap.put("item_ids", favoriteListItem);
//        try {
//            mDatabaseReference.child(user.getUid()).child(emailId).child("favorites").setValue(favoriteListMap);
//        } catch (Exception e) {
//
//        }
//    }

    @SuppressLint("StaticFieldLeak")
    public ArrayList<Restaurant> fetchNearByRestaurants(double lat, double log) {
        ArrayList<Restaurant> res = new ArrayList<>();
        String response;
        JSONObject json = new JSONObject();
        try {
            json.put("gps-lat", 11.11);
            json.put("gps-long", 11.22);
            json.put("curr-time", 1000);
            String request = json.toString();
            HttpPost post = new HttpPost("https://us-central1-mad-project-cb7a6.cloudfunctions.net/getrest");
            StringEntity entity = new StringEntity(request);
            post.setEntity(entity);
            post.setHeader("Content-type", "application/json");
            DefaultHttpClient client = new DefaultHttpClient();
            BasicResponseHandler handler = new BasicResponseHandler();
            response = client.execute(post, handler);
            JSONArray js = new JSONArray(response);
            for (int i = 0; i < js.length(); i++) {
                JSONObject ob = js.getJSONObject(i);
                String id = ob.getString("id");
                String name = ob.getString("name");
                String area = ob.getString("area");
                String lLat = String.valueOf(ob.get("gps-lat"));
                String lLong = String.valueOf(ob.get("gps-long"));
                String mImagePath = ob.getString("rest-image");
                res.add(new Restaurant(id, name, area, lLat, lLong, mImagePath));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public ArrayList<DishItem> fetchItems(String restId) {
        ArrayList<DishItem> res = new ArrayList<>();
        JSONObject json = new JSONObject();
        try {
            json.put("rest-id", restId);
            String request = json.toString();
            HttpPost post = new HttpPost("https://us-central1-mad-project-cb7a6.cloudfunctions.net/getdetails");
            StringEntity entity = new StringEntity(request);
            post.setEntity(entity);
            post.setHeader("Content-type", "application/json");
            DefaultHttpClient client = new DefaultHttpClient();
            BasicResponseHandler handler = new BasicResponseHandler();
            String response = client.execute(post, handler);
            String id = String.valueOf(new JSONObject(response).get("id"));
            JSONArray items = new JSONObject(response).getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                String name = item.getString("name");
                int price = item.getInt("price");
                String tags = item.getString("tags");
                String desc = item.getString("desc");
                String imgLink = item.getString("img-link");
                String type = item.getString("type");
                res.add(new DishItem(0, name, type, price, desc, imgLink, tags, id));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }


//    public String getEmailStripped(String emailId) {
//        String emailIdSplit[] = emailId.split("@");
//        String _emailId = emailIdSplit[0];
//        return _emailId;
//    }

//    public void fetchFavoriteList(FirebaseUser user) {
//        String emailId = getEmailStripped(user.getEmail());
//        DatabaseReference databaseReference = mDatabaseReference.child(user.getUid()).child(emailId).child("favorites").child("item_ids");
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                GenericTypeIndicator<List<Integer>> t = new GenericTypeIndicator<List<Integer>>() {
//                };
//                ArrayList<Integer> favoriteListIds = (ArrayList<Integer>) dataSnapshot.getValue(t);
//                if (favoriteListIds != null) {
//                    CartManager.get(mAppContext).setFavoriteList(new ArrayList<>(favoriteListIds));
//                    System.out.println("size of favorite list " + CartManager.get(mAppContext).getFavoriteIdList().size());
//                    MainActivity.notifyMe();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//    class OrderObject {
//        public UUID orderId;
//        public ArrayList<DishItem> orderedItems;
//
//        public OrderObject() {
//            orderId = UUID.randomUUID();
//            orderedItems = new ArrayList<>();
//        }
//
//        public OrderObject(ArrayList<DishItem> orderedItems) {
//            orderId = UUID.randomUUID();
//            this.orderedItems = orderedItems;
//        }
//    }
}
