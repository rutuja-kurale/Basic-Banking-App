package com.example.bankapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CustomerDbHelper  extends SQLiteOpenHelper {
    private String TABLE_NAME = "user_table";
    private String TABLE_NAME1 = "transfers_table";

    public CustomerDbHelper(@Nullable Context context) {
        super(context, "User.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (PHONENUMBER INTEGER PRIMARY KEY ,NAME TEXT,BALANCE DECIMAL,EMAIL VARCHAR,ACCOUNT_NO VARCHAR,IFSC_CODE VARCHAR)");
        db.execSQL("create table " + TABLE_NAME1 +" (TRANSACTIONID INTEGER PRIMARY KEY AUTOINCREMENT,DATE TEXT,FROMNAME TEXT,TONAME TEXT,AMOUNT DECIMAL,STATUS TEXT)");
        db.execSQL("insert into user_table values(9198454561644,'Meera ',250000.00,'Meerakumar38@gmail.com','1234567890','ABC09876543')");
        db.execSQL("insert into user_table values(9184104563567,'Ojas',20582.67,'Ojas2000@gmail.com','1234567891','BCA98765432')");
        db.execSQL("insert into user_table values(9194561568524,'Raghavi',2500.56,'Raghavi99@gmail.com','1234567892','CAB87654321')");
        db.execSQL("insert into user_table values(9170415562685,'Siddhant ',1800.01,'Siddhant@gmail.com','1234567893','ABC76543210')");
        db.execSQL("insert into user_table values(9190124563473,'Nikita',3420.48,'nikki2000@gmail.com','1234567894','BCA65432109')");
        db.execSQL("insert into user_table values(9175234562567,'Gunjan',845.16,'gujan99@gmail.com','1234567895','CAB54321098')");
        db.execSQL("insert into user_table values(9191234562463,'Nishant',7836.00,'nishantchahr@gmail.com','1234567896','ABC43210987')");
        db.execSQL("insert into user_table values(9180234563573,'Anshika',457.22,'anshika99@gmail.com','1234567897','BCA32109876')");
        db.execSQL("insert into user_table values(9172234563672,'Tanisha',43980.46,'tanisha@gmail.com','1234567898','CAB21098765')");
        db.execSQL("insert into user_table values(9170234563664,'Shiravlli',130.90,'shrivalii99@gmail.com','1234567899','ABC10987654')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1);
        onCreate(db);
    }

    public Cursor readAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user_table", null);
        return cursor;
    }

    public Cursor readOnlyThis(String phonenumber){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user_table where phonenumber = " +phonenumber, null);
        return cursor;
    }

    public Cursor readSelectedCustomerData(String phonenumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user_table except select * from user_table where phonenumber = " +phonenumber, null);
        return cursor;
    }

    public void updateAmount(String phonenumber, String amount){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update user_table set balance = " + amount + " where phonenumber = " +phonenumber);
    }

    public Cursor readTransactionData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from transfers_table", null);
        return cursor;
    }

    public boolean insertTransferData(String date,String from_name, String to_name, String amount, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("DATE", date);
        contentValues.put("FROMNAME", from_name);
        contentValues.put("TONAME", to_name);
        contentValues.put("AMOUNT", amount);
        contentValues.put("STATUS", status);
        Long result = db.insert(TABLE_NAME1, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
}

