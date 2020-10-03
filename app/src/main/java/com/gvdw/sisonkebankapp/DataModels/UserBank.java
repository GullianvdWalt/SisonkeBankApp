/**
 * Created by Gullian Van Der Walt 29-09-2020
 * User Bank Class
 */
package com.gvdw.sisonkebankapp.DataModels;

public class UserBank {
  // Variables
  private String id;
  private String fName;
  private String lName;
  private String email;
  private String mobile;
  private Double currAcc;
  private Double savingsAcc;

  // NoArgs Constructor
  public UserBank() {
  }
  // AllArgs Constructor
  public UserBank(String id,String fName, String lName, String email, String mobile, Double currAcc, Double savingsAcc) {
    this.id = id;
    this.fName = fName;
    this.lName = lName;
    this.email = email;
    this.mobile =  mobile;
    this.currAcc = currAcc;
    this.savingsAcc = savingsAcc;
  }

  // Getter and Setter Methods

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getfName() {
    return fName;
  }

  public void setfName(String fName) {
    this.fName = fName;
  }

  public String getlName() {
    return lName;
  }

  public void setlName(String lName) {
    this.lName = lName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public Double getCurrAcc() {
    return currAcc;
  }

  public void setCurrAcc(Double currAcc) {
    this.currAcc = currAcc;
  }

  public Double getSavingsAcc() {
    return savingsAcc;
  }

  public void setSavingsAcc(Double savingsAcc) {
    this.savingsAcc = savingsAcc;
  }
}
