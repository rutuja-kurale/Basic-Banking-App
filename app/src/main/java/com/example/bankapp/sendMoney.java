package com.example.bankapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.xml.sax.ext.DeclHandler;

import java.net.CookieHandler;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class sendMoney extends AppCompatActivity {

    List<CustomerModel> sendMoneyList = new ArrayList<>();
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    SendMoneyAdapter adapter;

    String phone, name, currentAmount, transferredAmount, remainingAmount;
    String selectedCustomerPhone, selectedCustomerName, selectedCustomerBalance, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_money);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = findViewById(R.id.sendMoney_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy, hh:mm a");
        date = simpleDateFormat.format(calendar.getTime());

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            phone = bundle.getString("phonenumber");
            name = bundle.getString("name");
            currentAmount = bundle.getString("currentamount");
            transferredAmount = bundle.getString("transferamount");
            showData(phone);
        }
    }

    private void showData(String phonenumber) {
        sendMoneyList.clear();
        Log.d("DEMO",phonenumber);
        Cursor cursor = new CustomerDbHelper(this).readSelectedCustomerData(phonenumber);
        while(cursor.moveToNext()){
            String balancefromdb = cursor.getString(2);
            Double balance = Double.parseDouble(balancefromdb);

            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setGroupingUsed(true);
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            String price = nf.format(balance);

            CustomerModel model = new CustomerModel(cursor.getString(0), cursor.getString(1), price);
            sendMoneyList.add(model);
        }

        adapter = new SendMoneyAdapter(sendMoney.this, sendMoneyList);
        mRecyclerView.setAdapter(adapter);
    }
    public void selectuser(int position) {
        selectedCustomerPhone = sendMoneyList.get(position).getPhone();
        Cursor cursor = new CustomerDbHelper(this).readOnlyThis(selectedCustomerPhone);
        while(cursor.moveToNext()) {
            selectedCustomerName = cursor.getString(1);
            selectedCustomerBalance = cursor.getString(2);
            Double Dselectuser_balance = Double.parseDouble(selectedCustomerBalance);
            Double Dselectuser_transferamount = Double.parseDouble(transferredAmount);
            Double Dselectuser_remainingamount = Dselectuser_balance + Dselectuser_transferamount;

            new CustomerDbHelper(this).insertTransferData(date, name, selectedCustomerName, transferredAmount, "Success");
            new CustomerDbHelper(this).updateAmount(selectedCustomerPhone, Dselectuser_remainingamount.toString());
            calculateAmount();
            Toast.makeText(this, "Transaction Successful!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(sendMoney.this, Customers.class));
            finish();
        }
    }

    private void calculateAmount() {
        Double Dcurrentamount = Double.parseDouble(currentAmount);
        Double Dtransferamount = Double.parseDouble(transferredAmount);
        Double Dremainingamount = Dcurrentamount - Dtransferamount;
        remainingAmount = Dremainingamount.toString();
        new CustomerDbHelper(this).updateAmount(phone, remainingAmount);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder_exitbutton = new AlertDialog.Builder(sendMoney.this);
        builder_exitbutton.setTitle("Do you want to cancel the transaction?").setCancelable(false)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new CustomerDbHelper(sendMoney.this).insertTransferData(date, name, "Not selected", transferredAmount, "Failed");
                        Toast.makeText(sendMoney.this, "Transaction Cancelled!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(sendMoney.this, Customers.class));
                        finish();
                    }
                }).setNegativeButton("No", null);
        AlertDialog alertExit = builder_exitbutton.create();
        alertExit.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                ArrayList<CustomerModel> newList = new ArrayList<>();
                for(CustomerModel model : sendMoneyList){
                    String name = model.getName().toLowerCase();
                    if(name.contains(newText)){
                        newList.add(model);
                    }
                }
                adapter.setFilter(newList);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}

