package BankServer;

import java.util.Date;

class Account {

    /**
     * Declaring all the variables required to define an account
     * All variables are private to maintain encapsulation
     * Except for pin and confiscate, all variables have no setters. They are set only when the object is created
     */
     private String accountId;
     private String customerId;
     private String pin;
     private double balance;
     private boolean confiscate;

     public Account(String accountId, String customerId, String pin, double balance,boolean confiscate) {
         this.accountId = accountId;
         this.customerId = customerId;
         this.pin = pin;
         this.balance = balance;
         this.confiscate = confiscate;
     }

     void deposit(double amount){ // Increase balance after withdrawal of money

         balance += amount;
     }

     void withdraw(double amount){ // Reduce balance after withdrawal of money

         balance -= amount;
     }

     boolean equals( Account obj){ // Checks if the account ID matches the account ID of object present

         if(obj.getAccountId().equalsIgnoreCase(this.getAccountId()))
             return true;
         else
             return false;
     }

    public void setPin(String pin) { // The set method is used to change pin during transaction
        this.pin = pin;
    }

    public void setConfiscate(boolean confiscate) { // The set method is used to block card during transaction
        this.confiscate = confiscate;
    }

    public boolean isConfiscate() {
        return confiscate;
    }

     public String getAccountId() {
         return accountId;
     }

     public String getCustomerId() {
         return customerId;
     }

     public String getPin() {
         return pin;
     }

     public double getBalance() {
         return balance;
     }

}
