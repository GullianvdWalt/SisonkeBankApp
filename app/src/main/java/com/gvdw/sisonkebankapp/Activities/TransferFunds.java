// Created By Gullian Van Der Walt
package com.gvdw.sisonkebankapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.gvdw.sisonkebankapp.DataModels.DatabaseHelper;
import com.gvdw.sisonkebankapp.DataModels.UserBank;
import com.gvdw.sisonkebankapp.R;

import java.util.ArrayList;
import java.util.List;

public class TransferFunds extends AppCompatActivity implements OnItemSelectedListener {
  // Variables
  private Double currAccount;
  private Double savingsAccount;
  private Double amount;
  int selectedItem;
  private Toolbar toolbar;
  private String email;
  private TextView txtVCurrAcc;
  private TextView txtVSavings;
  private Button transferBtn;
  private EditText tAmountEditTxt;

  // Database Helper Class
  DatabaseHelper db;
  UserBank user;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_transfer_funds);
    // Get Toolbar
    toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.transferFundsToolBar);
    setSupportActionBar(toolbar);
    db = new DatabaseHelper(this);
    Intent goToMainPage = getIntent();
    Bundle b = goToMainPage.getExtras();
    email = (String) b.get("email");

    // Get Logged In User
    user = new UserBank();

    if(email.isEmpty()){


    }else
      // Get User Details
      user = db.getUserDetails(email);
      // Set Account Amounts
      currAccount = user.getCurrAcc();
      savingsAccount = user.getSavingsAcc();

      txtVCurrAcc = (TextView)findViewById(R.id.txt_Cur_Acc_Balance);
      txtVSavings = (TextView)findViewById(R.id.txt_Sav_Acc_Bal);

      txtVCurrAcc.setText("Current Account Balance: " + currAccount);
      txtVSavings.setText("Savings Account Balance: " + savingsAccount);

    // Spinner Element
    Spinner spinner = (Spinner) findViewById(R.id.spinner);

    // Spinner Click listener
    spinner.setOnItemSelectedListener(this);

    // Spinner Elements
    List<String> transferOptions = new ArrayList<String>();
    transferOptions.add("Current to savings");
    transferOptions.add("Savings to current");

    // Spinner Adapter
    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,transferOptions);

    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    //add to spinner
    spinner.setAdapter(dataAdapter);

    selectedItem = spinner.getSelectedItemPosition();


    transferBtn = findViewById(R.id.transferBtn);
    transferBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        transferFunds(selectedItem,currAccount,savingsAccount);
      }
    });

  }
  // Toolbar Back Button
  public void setSupportActionBar(Toolbar toolbar) {
    toolbar.setNavigationIcon(R.drawable.ic_action_name);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(TransferFunds.this,MainPage.class);
        intent.putExtra("email",email);
        startActivity(intent);
      }
    });
  }

  // Spinner
  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
      // On Spinner Select
      selectedItem = parent.getSelectedItemPosition();


  }

  @Override
  public void onNothingSelected(AdapterView<?> parent) {

  }
  // Method that handles transfer between accounts
  public void transferFunds(int selectedIndex,Double currAmount,Double savingsAmount){
    tAmountEditTxt = (EditText)findViewById(R.id.editTxtTransferAmount);
    String getAmount = tAmountEditTxt.getText().toString();
    amount = Double.parseDouble(getAmount);

    if(getAmount.isEmpty()){
      Toast.makeText(this,"Enter a transfer amount!",Toast.LENGTH_SHORT).show();
    }else{
        if(selectedIndex == 0 ){
            if(amount > currAmount){
              Toast.makeText(this,"Insufficient funds in current account please enter a smaller amount",Toast.LENGTH_LONG).show();
            }else{
              currAmount -= amount;
              savingsAmount+=amount;
              String[] emails = {email};
              Boolean transferred = db.updateBalance(currAmount,savingsAmount,emails);
              if(transferred == true){
                Toast.makeText(this,"Balance Updated Successfully!",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(TransferFunds.this,TransferFunds.class);
                intent.putExtra("email",email);
                startActivity(intent);

              }else{
                Toast.makeText(this,"There was a problem while updating the balance",Toast.LENGTH_LONG).show();
              }
            }
        }else if(selectedIndex == 1){
          if(amount > currAmount){
            Toast.makeText(this,"Insufficient funds in savings account account please enter a smaller amount",Toast.LENGTH_LONG).show();
          }else{
            currAmount += amount;
            savingsAmount -=amount;
            String[] emails = {email};
            Boolean transferred = db.updateBalance(currAmount,savingsAmount,emails);
            if(transferred == true){
              Toast.makeText(this,"Balance Updated Successfully!",Toast.LENGTH_LONG).show();
              Intent intent = new Intent(TransferFunds.this,TransferFunds.class);
              intent.putExtra("email",email);
              startActivity(intent);

            }else{
              Toast.makeText(this,"There was a problem while updating the balance",Toast.LENGTH_LONG).show();
            }
          }
        }
    }
  }

}
