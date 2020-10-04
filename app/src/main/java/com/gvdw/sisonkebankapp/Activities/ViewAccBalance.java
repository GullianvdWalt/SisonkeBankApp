/*
  Created By Gullian Van Der Walt
  View Account Balance Class
 */
package com.gvdw.sisonkebankapp.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.gvdw.sisonkebankapp.DataModels.DatabaseHelper;
import com.gvdw.sisonkebankapp.DataModels.UserBank;
import com.gvdw.sisonkebankapp.R;

public class ViewAccBalance extends AppCompatActivity {
  // Layout Elements
  private String name;
  private String email;
  private String surname;
  private Double currAccount;
  private Double savingsAccount;
  private TextView accHolderNameTxtV;
  private TextView accHolderSurnameTxtV;
  private TextView curAccTxtV;
  private TextView savingsAccTxtV;
  private Toolbar toolBar;
  // Database Helper Class
  DatabaseHelper db;
  UserBank user;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_acc_balance);
    toolBar =  (androidx.appcompat.widget.Toolbar) findViewById(R.id.viewAccToolBar);
    setSupportActionBar(toolBar);
    db = new DatabaseHelper(this);
    Intent goToMainPage = getIntent();
    Bundle b = goToMainPage.getExtras();
    email = (String) b.get("email");

    // Get Logged In User
    user = new UserBank();

    if(email.isEmpty()){


    }else

    user = db.getUserDetails(email);
    name = user.getfName();
    surname = user.getlName();
    currAccount = user.getCurrAcc();
    savingsAccount = user.getSavingsAcc();
    // Get TextView Elements
    accHolderNameTxtV = (TextView)findViewById(R.id.txtAccHName);
    accHolderSurnameTxtV = (TextView)findViewById(R.id.txtAccHSurname);
    curAccTxtV = (TextView)findViewById(R.id.txtCurrentAccBalance);
    savingsAccTxtV = (TextView)findViewById(R.id.txtSavingsAccBalance);
    // Set TextViews
    accHolderNameTxtV.setText("Account Holder Name: " + name);
    System.out.println(name);
    accHolderSurnameTxtV.setText("Account Holder Surname: " + surname);
    curAccTxtV.setText("Current Account Balance: " + currAccount);
    savingsAccTxtV.setText("Savings Account Balance: " + savingsAccount);

}

  public void setSupportActionBar(Toolbar toolBar) {
    toolBar.setNavigationIcon(R.drawable.ic_action_name);
    toolBar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(ViewAccBalance.this,MainPage.class);
        intent.putExtra("email",email);
        startActivity(intent);
      }
    });
  }

}




