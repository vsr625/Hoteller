package com.teamnamenotfoundexception.hoteller.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.teamnamenotfoundexception.hoteller.Adapters.RestaurantAdapter;
import com.teamnamenotfoundexception.hoteller.Database.CartManager;
import com.teamnamenotfoundexception.hoteller.Database.DishItem;
import com.teamnamenotfoundexception.hoteller.Database.DishRepository;
import com.teamnamenotfoundexception.hoteller.Database.Restaurant;
import com.teamnamenotfoundexception.hoteller.Database.RestaurantRepository;
import com.teamnamenotfoundexception.hoteller.Database.UpdateNotificationCount;
import com.teamnamenotfoundexception.hoteller.Login.LoginActivity;
import com.teamnamenotfoundexception.hoteller.R;
import com.teamnamenotfoundexception.hoteller.TutorialActivity;
import com.teamnamenotfoundexception.hoteller.Adapters.CustomAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, UpdateNotificationCount {
    private static HashMap<String, CustomAdapter> customAdapterHashMap;
    private static ArrayList<DishItem> dishItems;
    private static ArrayList<Restaurant> restItems;
    private static CartManager mCartManager;
    private FirebaseAuth mAuth;
    private static CustomAdapter mCustomAdapter = null;
    private static RestaurantAdapter mRestAdapter;
    private static DishRepository mDishRepository;
    private static RestaurantRepository mRestRepository;
    private TextView notifyCount;
    private DrawerLayout drawer;
    private static ProgressBar progressBar;
    public static RecyclerView recyclerView;
    public static LinearLayoutManager linearLayoutManager;
    private static Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This is for NetworkOnMainThread Exception
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // First check if the user has completed the tutorial
        SharedPreferences tutorialFlag = getSharedPreferences("tutorialFlag", getApplicationContext().MODE_PRIVATE);
        Boolean state = tutorialFlag.getBoolean("shown", false);
        if (!state) {
            startActivity(new Intent(MainActivity.this, TutorialActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
            return;
        }

        // Check if the user is logged in
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        if (mUser == null) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
            return;
        }
        customAdapterHashMap = new HashMap<>();
        // If logged in, initialize the Cart and Dish Repositories
        mCartManager = CartManager.get(getApplicationContext());
        mCartManager.setAuth(FirebaseAuth.getInstance());
        mCartManager.setUser(FirebaseAuth.getInstance().getCurrentUser());
        mCartManager.setFirebaseDatabase(FirebaseDatabase.getInstance());

        // Set listener for UpdateNotifyCount
        mCartManager.setListenerInterface(this);

        // Initialize Restaurant Repository with NearBy restaurants
        mRestRepository = RestaurantRepository.get(getApplicationContext());
        if (mRestRepository.getmRestList().size() == 0)
            mRestRepository.initializeNearByRestaurants();
        restItems = new ArrayList<>(mRestRepository.getmRestList());

        mDishRepository = DishRepository.get(getApplicationContext());

        // Draw Activity
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);

        // The recycler view
        recyclerView = findViewById(R.id.recycle);
        linearLayoutManager = new LinearLayoutManager(this.getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        // Animator for the dishItem list
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Display the Items
        mRestAdapter = new RestaurantAdapter(MainActivity.this, restItems);
        recyclerView.setAdapter(mRestAdapter);

        // Hide the progress bar
        progressBar = findViewById(R.id.progressCircle);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        // ActionBar toggle button settings
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Action Bar stuff
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    // Display Items from a particular Restaurant
    public static void displayItems(String restId, Context context) {
        toolbar.setTitle("List of Items");
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        mDishRepository.initializeDishItemsList(restId);
        if(customAdapterHashMap.containsKey(restId)){
            mCustomAdapter = customAdapterHashMap.get(restId);
            recyclerView.setAdapter(customAdapterHashMap.get(restId));
        }else{
            dishItems = mDishRepository.getDishItemsList();
            mCustomAdapter = new CustomAdapter(context, dishItems);
            recyclerView.setAdapter(mCustomAdapter);
            customAdapterHashMap.put(restId, mCustomAdapter);
        }
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    // Update the CustomAdapter for new changes
    public static void notifyMe() {
        if (mCustomAdapter != null) {
            mCustomAdapter.notifyDataSetChanged();
        } else {
            mRestAdapter.notifyDataSetChanged();
        }
    }

    // Update the count in Cart Icon in MainActivity
    public void updateCount(final int newCount) {
        if (notifyCount == null) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (newCount == 0)
                    notifyCount.setVisibility(View.INVISIBLE);
                else {
                    notifyCount.setVisibility(View.VISIBLE);
                    notifyCount.setText(Integer.toString(newCount));
                }
            }
        });
    }

    // Top ActionBar for the MainActivity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu, this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem cartMenu = menu.findItem(R.id.cartIcon);
        View cartAction = cartMenu.getActionView();
        notifyCount = cartAction.findViewById(R.id.notifyCount);
        // Set the hint count
        updateCount(mCartManager.getCartItems().size());
        // onClickListener for the Cart button on the top
        cartAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
            }
        });
        return true;
    }

    @Override
    public void updateNotifyCount() {
        updateCount(CartManager.get(getApplicationContext()).getCartItems().size());
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if(mCustomAdapter != null)
//            mCustomAdapter.setData(dishItems);
//        else
//            mRestAdapter.setData(restItems);
//    }

    // For handling Events from Navigational Drawer
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.favs:
                startActivity(new Intent(getApplicationContext(), FavoriteActivity.class));
                break;
            case R.id.cart:
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
                break;
            case R.id.logout:
                try {
                    mAuth.signOut();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Trouble logging you out. Check your connection!", Toast.LENGTH_SHORT).show();
                } finally {
                    mCartManager.setAuth(null);
                    mCartManager.setFirebaseDatabase(null);
                    mCartManager.setUser(null);
                    mCartManager.setCartManagerToNull();
                    DishRepository.setDishRepository(null);
                }
                startActivity(new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (mCustomAdapter != null) {
                toolbar.setTitle("Nearby Restaurants");
                mRestAdapter = new RestaurantAdapter(MainActivity.this, restItems);
                recyclerView.setAdapter(mRestAdapter);
                mCustomAdapter = null;
            } else
                super.onBackPressed();
        }
    }
}