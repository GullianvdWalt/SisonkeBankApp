/*
  Created By Gullian Van Der Walt
  Login Class
 */
package com.gvdw.sisonkebankapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gvdw.sisonkebankapp.DataModels.DatabaseHelper;
import com.gvdw.sisonkebankapp.R;

public class Login extends AppCompatActivity {
  // Get layout elements
TextView regBtn;
Button loginBtn;
EditText emailEditTxt;
EditText passwordEditTxt;
String email = "";
String password = "";
String email1, password1;
DatabaseHelper db;
MainPage mainPage;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    regBtn = findViewById(R.id.loginRegBtn);
    regBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          openRegister();
      }
    });

    // Login Button
    loginBtn = (Button) findViewById(R.id.loginBtn);

    db = new DatabaseHelper(this);



    // Get EditText Values
    emailEditTxt = (EditText) findViewById(R.id.emailEditTxt);
    passwordEditTxt = (EditText) findViewById(R.id.passwordEditTxt);

    loginBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          email = emailEditTxt.getText().toString().trim();
          password = passwordEditTxt.getText().toString().trim();
          Boolean res = isValid(email,password);

          if(res == true) {
            Boolean result = db.checkUser(email, password);
            Boolean password_exists = db.checkPassword(password);
            Boolean email_exists = db.checkEmail(email);
            if (result == true){
              Toast.makeText(Login.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
              Intent goToMainPage = new Intent(Login.this, MainPage.class);
              goToMainPage.putExtra("email",email);
              startActivity(goToMainPage);

            }else if (password_exists == false){
              showMessage("Password does not match our records!");
            } else if(email_exists == false){
              showMessage("Email does not match our records!");
            } else {
              Toast.makeText(Login.this, "Login Error", Toast.LENGTH_SHORT).show();
            }
          }
      }
    });
  }
  // Launch Register Activity
  public void openRegister(){
      Intent intent = new Intent(this, Registration.class);
      startActivity(intent);
  }

  private boolean isValid(String email, String password){
    if(email.isEmpty()) {
      showMessage("Email cannot be empty!");
      return false;
    }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
      showMessage("Email is not valid");
      return false;
    }else if(password.isEmpty() ){
      showMessage("Password field is empty!");
      return false;
    }else if(password.length() < 5){
      showMessage("Password must be more than 5 characters!");
      return false;
    }else


    return true;
  }

  private void showMessage(String msg){
    Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
  }
}
