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

public class CartActivity extends AppCompatActivity {

    RecyclerView myRecycle;
    RelativeLayout empty, notEmpty;
    Toolbar tools;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        tools =  findViewById(R.id.cartTools);
        empty = findViewById(R.id.empty);
        notEmpty = findViewById(R.id.nempty);

        // Back button
        setSupportActionBar(tools);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get dishItems from CartManager
        CartManager cartManager = CartManager.get(getApplicationContext());
        ArrayList<DishItem> dishItems = cartManager.getCartItems();
        // Display the items in cart
        if (dishItems == null || dishItems.size() == 0){
            empty.setVisibility(View.VISIBLE);
            notEmpty.setVisibility(View.GONE);
        } else {
            notEmpty.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
            myRecycle = findViewById(R.id.cartRecycle);
            CustomAdapter myAdapter = new CustomAdapter(CartActivity.this, dishItems);
            LinearLayoutManager llm = new LinearLayoutManager(this.getApplicationContext());
            myRecycle.setLayoutManager(llm);
            myRecycle.setItemAnimator(new DefaultItemAnimator());
            myRecycle.setAdapter(myAdapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void checkout(View v) {
        startActivity(new Intent(getApplicationContext(),BillActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }
}
