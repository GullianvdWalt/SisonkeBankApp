/**
 * Created by Gullian Van Der Walt 29-09-2020
 * Registration Class.
 */

package com.gvdw.sisonkebankapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gvdw.sisonkebankapp.DataModels.DatabaseHelper;
import com.gvdw.sisonkebankapp.R;

public class Registration extends AppCompatActivity {
  private EditText fNameEt, lNameEt, emailEt, mobileEt, passwordEt;
  private String gender;
  private double currentAcc;
  private double savingsAcc;
  private Button createAccBtn;
  private RadioGroup genderRadioGroup;
  private RadioButton radioGenButton;
  private TextView loginBtn;

  public static DatabaseHelper myDb;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_registration);
    // Bind XML
    fNameEt = findViewById(R.id.regTxtFName);
    lNameEt = findViewById(R.id.regTxtLName);
    emailEt = findViewById(R.id.regTxtEmail);
    mobileEt = findViewById(R.id.regTxtMobile);
    passwordEt = findViewById(R.id.regTxtPassword);
    genderRadioGroup = (RadioGroup)findViewById(R.id.genderRadioGroup);

    myDb = new DatabaseHelper(this);

    createAccBtn = findViewById(R.id.regBtn);

    addData();

    // Login Button
    loginBtn = findViewById(R.id.regLoginBtn);
    loginBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        openLogin();
      }
    });

  }

  public void addData(){
    createAccBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Variables
        String fName, lName, email, mobile, password;
        // Get values
        fName = fNameEt.getText().toString();
        lName = lNameEt.getText().toString();
        email = emailEt.getText().toString();
        String email1 = emailEt.getText().toString().trim();
        mobile = mobileEt.getText().toString();
        password = passwordEt.getText().toString();
        currentAcc = 1000;
        savingsAcc = 1000;
        int selectedId = genderRadioGroup.getCheckedRadioButtonId();
        radioGenButton = (RadioButton) findViewById(selectedId);
        gender = (String) radioGenButton.getText();
        Boolean checkCurEmail = myDb.checkEmail(email1);
        // Check if inputs are valid
        if(isValid(fName,lName,email,mobile,password,gender)){
          // Check if email exists
          if(checkCurEmail == false){
           // Call DatabaseHelper
          Boolean isInserted = myDb.addUser(fName,lName,email,mobile,password,gender,currentAcc,savingsAcc);
          // If Inserted Successfully
          if(isInserted == true){
              System.out.println(email1);
              Toast.makeText(Registration.this, "Data successfully inserted, Please Log In",Toast.LENGTH_LONG).show();
            showMessage("Data successfully inserted, Please Log In.");
              Intent goToLogin = new Intent(Registration.this,Login.class);
              startActivity(goToLogin);
            }else{
            showMessage("Data not inserted");
          }
          }else{
            showMessage("Email already exists!");
          }

        }
      }
    });

  }
// Go to Login Activity
  public void openLogin(){
    Intent intent = new Intent(this, Login.class);
    startActivity(intent);
  }
// Validation
  private boolean isValid(String fName, String lName, String email, String mobile, String password, String gender){
      if(fName.isEmpty()){
        showMessage("First Name Cannot Be Empty!");
        return false;
      }else if(lName.isEmpty()){
        showMessage("Last Name Cannot Be Empty!");
        return false;
      }else if(email.isEmpty()) {
        showMessage("Email cannot be empty!");
        return false;
      }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
        showMessage("Email is not valid");
        return false;
      }else if(mobile.isEmpty()){
        showMessage("Mobile number is required!");
        return false;
      }else if(password.isEmpty() ){
        showMessage("Password field is empty!");
        return false;
      }else if(password.length() < 5){
        showMessage("Password must be more than 5 characters!");
        return false;
      }else if(gender.isEmpty()){
        showMessage("Choose a gender!");
        return false;
      }

      return true;
  }
// Method to display toast messages
  private void showMessage(String msg){
    Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
  }


}
