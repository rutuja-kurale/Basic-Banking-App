package com.example.bankapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Customers extends AppCompatActivity {
    ArrayList<CustomerModel> customerModelList = new ArrayList<>();
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    CustomerAdapter adapter;

    String phonenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = findViewById(R.id.customer_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        showData();
    }

    private void showData() {
        customerModelList.clear();
        Cursor cursor = new CustomerDbHelper(this).readAllData();
        while(cursor.moveToNext()){
            String balancefromdb = cursor.getString(2);
            Double balance = Double.parseDouble(balancefromdb);

            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setGroupingUsed(true);
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            String price = nf.format(balance);

            CustomerModel model = new CustomerModel(cursor.getString(0), cursor.getString(1), price);
            customerModelList.add(model);
        }

        adapter = new CustomerAdapter(Customers.this, customerModelList);
        mRecyclerView.setAdapter(adapter);

    }

    public void nextActivity(int position) {
        phonenumber = customerModelList.get(position).getPhone();
        Intent intent = new Intent(Customers.this, CustomerData.class);
        intent.putExtra("phonenumber",phonenumber);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.transaction_history, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.action_history){
            startActivity(new Intent(Customers.this, Transactions.class));
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(Customers.this, MainActivity.class));
        finish();
    }
}