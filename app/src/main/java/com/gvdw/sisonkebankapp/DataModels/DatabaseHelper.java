/**
 * Created by Gullian Van Der Walt 29-09-2020
 * SQLiteOpenHelper class, handles database and tables creation.
 */
package com.gvdw.sisonkebankapp.DataModels;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;



public class DatabaseHelper extends SQLiteOpenHelper {
  // Database Properties
  private static final String DATABASE_NAME = "SisonkeBankdb";
  private static final String TABLE_NAME = "users";
  // Database Columns
  public static final String COL_1 = "ID";
  private static final String COL_2 = "fName";
  private static final String COL_3 = "lName";
  private static final String COL_4 = "email";
  private static final String COL_5 = "mobile";
  private static final String COL_6 = "password";
  private static final String COL_7 = "gender";
  private static final String COL_8 = "current_account";
  private static final String COL_9 = "savings_account";

  public DatabaseHelper(@Nullable Context context) {
    super(context, DATABASE_NAME,null,1);

  }


// SQL Query
  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, fName TEXT," +
      "lName TEXT, email VARCHAR(55), mobile INTEGER, password VARCHAR(12), gender VARCHAR(12)," +
      "current_account DOUBLE, savings_account DOUBLE)");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
      onCreate(db);
  }

  // Insert Data into the DB
  public boolean addUser(String fName,String lName,String email,String mobile,String password,String gender,Double current_account,Double savings_account) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    // Insert Valuess
    contentValues.put(COL_2, fName);
    contentValues.put(COL_3, lName);
    contentValues.put(COL_4, email);
    contentValues.put(COL_5, mobile);
    contentValues.put(COL_6, password);
    contentValues.put(COL_7, gender);
    contentValues.put(COL_8, current_account);
    contentValues.put(COL_9, savings_account);

    // Bind
    long result = db.insert(TABLE_NAME, null, contentValues);
    if(result == -1) {
      return false;
    }
    return true;
  }

  public Boolean checkUser(String email,String password){
    String[] columns = {COL_1};
    SQLiteDatabase db = getReadableDatabase();
    String select = COL_4 + " =? " + " AND " + COL_6 +" =? ";
    String[] selectArgs = {email, password};
    Cursor cursor = db.query(TABLE_NAME,columns,select,selectArgs,null,null,null);
    int count = cursor.getCount();
    cursor.close();
    db.close();

    if(count > 0){
      return true;
    }
    return false;
  }
  // Check Email (Username)
  public Boolean checkEmail(String email){
    SQLiteDatabase db = getReadableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM users",null);

    // Check if table is not empty
    if(cursor != null && cursor.getCount() > 0){
      // Go to first row
      cursor.moveToFirst();
      // Check if password exists
      if(cursor.getString(cursor.getColumnIndex(COL_4)).equals(email)){
        return true;
      }else
        return false;
    }
    cursor.close();
    db.close();
    return false;
  }

  // Check Password
  public Boolean checkPassword(String password){
    SQLiteDatabase db = getReadableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM users",null);

    // Check if table is not empty
    if(cursor != null && cursor.getCount() > 0){
      // Go to first row
      cursor.moveToFirst();
      // Check if password exists
      if(cursor.getString(cursor.getColumnIndex(COL_6)).equals(password)){
        return true;

      }else
        return false;
    }
    cursor.close();
    db.close();

    return false;
  }

  public UserBank getUserDetails(String email){
    email = email.trim();
    System.out.println(email);
    SQLiteDatabase db = getReadableDatabase();
    // Get Logged In User
    String sql = "SELECT * FROM users WHERE users.email = " + "'"+email+"'";
    Cursor cursor = db.rawQuery(sql, null);
    // User Bank Class Instance
    UserBank user = new UserBank();
    // Check if result is not null
    if(cursor != null){
      cursor.moveToFirst();
      // Get Data and set variables
      user.setId(cursor.getString(cursor.getColumnIndex(COL_1)));
      user.setfName(cursor.getString(cursor.getColumnIndex(COL_2)));
      user.setlName(cursor.getString(cursor.getColumnIndex(COL_3)));
      user.setEmail(cursor.getString(cursor.getColumnIndex(COL_4)));
      user.setMobile(cursor.getString(cursor.getColumnIndex(COL_5)));
      user.setCurrAcc(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COL_8))));
      user.setSavingsAcc(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COL_9))));
    }

    cursor.close();
    db.close();

    return user;
  }
  // Method to update savings and current account balances
  public boolean updateBalance(Double currentAmount,Double savingsAmount,String[] emails){
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues updateValues = new ContentValues();

    updateValues.put(COL_9, savingsAmount);
    updateValues.put(COL_8,currentAmount);

    String where = "email =?";
    db = this.getReadableDatabase();


    // Bind
    long result = db.update(TABLE_NAME,updateValues,where,emails);
    if(result == -1) {
      return false;
    }
    db.close();
    return true;
  }



}
