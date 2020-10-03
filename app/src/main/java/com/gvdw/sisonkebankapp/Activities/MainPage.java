/*
  Created By Gullian Van Der Walt
  Main Page Class
 */
package com.gvdw.sisonkebankapp.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gvdw.sisonkebankapp.DataModels.DatabaseHelper;
import com.gvdw.sisonkebankapp.DataModels.UserBank;
import com.gvdw.sisonkebankapp.R;

public class MainPage extends AppCompatActivity {
  // Get Layout Elements
  private TextView welcomeTxtView;
  private String email;
  private String name;
  private Button viewAccBtn;
  private Button transferBtn;
  private Button logoutBtn;
  private UserBank user;
  // Database Helper Class
  DatabaseHelper db;



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_page);
    db = new DatabaseHelper(this);
    Intent goToMainPage = getIntent();
    Bundle b = goToMainPage.getExtras();
    email = (String) b.get("email");

    if(b!=null){
      getUser(email);

    }

    // Initiate elements
    viewAccBtn = findViewById(R.id.btnViewAccBalance);
    transferBtn = findViewById(R.id.btnTransfer);
    logoutBtn = findViewById(R.id.btnLogout);

    // Button Methods
    ViewAccountBalance();
    TransferBetweenAccounts();
    Logout();
  }
  // NoArgsConstructor
  public MainPage() {

  }

  // View Account Balance
  public void ViewAccountBalance(){
    viewAccBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MainPage.this,ViewAccBalance.class);
        intent.putExtra("email",email);
        startActivity(intent);
      }
    });
  }
  // Transfer Between Accounts
  public void TransferBetweenAccounts(){
    transferBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MainPage.this,TransferFunds.class);
        intent.putExtra("email",email);
        startActivity(intent);
      }
    });
  }
  // Method that handles Logout Button
  public void Logout(){
    logoutBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MainPage.this, Login.class);
        startActivity(intent);
        showMessage("You have been logged out.");
        finish();
      }
    });
  }

  private void showMessage(String msg){
    Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
  }



  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void getUser(String emailCur){
    // Get Logged In User
    user = new UserBank();
    user = db.getUserDetails(emailCur);
    name = user.getfName();
    // Get TextView Element
    welcomeTxtView = (TextView)findViewById(R.id.mainPageWelcome);
    // Set TextView
    welcomeTxtView.setText("Welcome " + name);


  }
}
