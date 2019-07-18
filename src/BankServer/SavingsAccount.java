package BankServer;

/**
 * @author prathitpannase
 */

 class SavingsAccount extends Account{

     private double interestRate;


     public SavingsAccount(String accountId, String customerId, String pin, double balance, double interestRate, boolean confiscate) {
         super(accountId, customerId, pin, balance,confiscate);
         this.interestRate = interestRate;
     }

     void applyMonthlyInterest(){

         double interest = this.getBalance()*0.06;
         deposit(interest);
     }

     public double getInterestRate() {
         return interestRate;
     }


 }
