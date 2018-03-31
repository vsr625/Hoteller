package com.teamnamenotfoundexception.hoteller.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.teamnamenotfoundexception.hoteller.Database.DishItem;
import com.teamnamenotfoundexception.hoteller.Adapters.CustomAdapter;
import com.teamnamenotfoundexception.hoteller.Database.CartManager;
import com.teamnamenotfoundexception.hoteller.R;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    Toolbar tools;
    RelativeLayout empty, nempty;
    RecyclerView myrecycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        // Top Action Bar setup
        tools =  findViewById(R.id.favTools);
        setSupportActionBar(tools);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get Favorite Items
        CartManager cartManager = CartManager.get(getApplicationContext());
        ArrayList<DishItem> dishItems = cartManager.getAllFavItems();

        empty =  findViewById(R.id.fempty);
        nempty = findViewById(R.id.fnempty);

        // Display Favorite Items
        if (dishItems.isEmpty()) {
            empty.setVisibility(View.VISIBLE);
            nempty.setVisibility(View.GONE);
        } else {
            nempty.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
            myrecycle = findViewById(R.id.favRecycle);
            CustomAdapter myAdapter = new CustomAdapter(FavoriteActivity.this, dishItems);
            LinearLayoutManager llm = new LinearLayoutManager(this.getApplicationContext());
            myrecycle.setLayoutManager(llm);
            myrecycle.setItemAnimator(new DefaultItemAnimator());
            myrecycle.setAdapter(myAdapter);
        }
    }

    // On press of the back arrow
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }
}
