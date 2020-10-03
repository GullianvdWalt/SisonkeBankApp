package com.gvdw.sisonkebankapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.gvdw.sisonkebankapp.DataModels.DatabaseHelper;
import com.gvdw.sisonkebankapp.R;

public class MainActivity extends AppCompatActivity {

  DatabaseHelper myDb;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    myDb = new DatabaseHelper(this);
    Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        startActivity(new Intent(MainActivity.this, Login.class));
        finish();
      }
    }, 1000);
  }
}
