package com.teamnamenotfoundexception.hoteller.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.teamnamenotfoundexception.hoteller.Database.DishItem;
import com.teamnamenotfoundexception.hoteller.Adapters.BillAdapter;
import com.teamnamenotfoundexception.hoteller.Database.CartManager;
import com.teamnamenotfoundexception.hoteller.R;

import java.util.ArrayList;
import java.util.Random;

public class BillActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView listView;
    TextView billAmt, billTax, billId;
    Button btn;
    ArrayList<DishItem> mBillList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        toolbar = findViewById(R.id.billTools);
        listView = findViewById(R.id.bill_dishes);
        billAmt = findViewById(R.id.billAmt);
        billTax = findViewById(R.id.billTax);
        btn = findViewById(R.id.goHome);
        billId = findViewById(R.id.billId);

        setItUp();
        copyList();

        // Display the items
        BillAdapter adapter = new BillAdapter(BillActivity.this, mBillList, R.id.bdentry);
        listView.setAdapter(adapter);

        // Back button on the toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void copyList() {
        ArrayList<DishItem> cartItems = CartManager.get(getApplication()).getCartItems();
        mBillList = new ArrayList<>();
        for (int i = 0; i < cartItems.size(); i++) {
            DishItem dishItem = new DishItem(cartItems.get(i));
            mBillList.add(dishItem);
        }
        CartManager.get(getApplication()).resetCartItems();
        MainActivity.notifyMe();
    }

    private void setItUp() {
        CartManager cartManager = CartManager.get(getApplicationContext());
        int total = cartManager.getTotalOrderPrice();
        double taxAmt = Math.round(0.18 * total);
        billAmt.setText(cartManager.getTotalOrderPrice() + taxAmt + "");
        billTax.setText(taxAmt + "");
        billId.setText((new Random()).nextInt(50000) + " " + (new Random()).nextInt(50000) + " " + (new Random()).nextInt(50000));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
