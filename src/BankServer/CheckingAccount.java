package BankServer;

import java.text.SimpleDateFormat;
import java.util.Date;

class CheckingAccount extends Account{

     private Date lastDepositDate;
     private double lastDepositAmount;


    public CheckingAccount(String accountId, String customerId, String pin, double balance,boolean confiscate, Date lastDepositDate, double lastDepositAmount) {
        super(accountId, customerId, pin, balance,confiscate);
        this.lastDepositDate = lastDepositDate;
        this.lastDepositAmount = lastDepositAmount;
    }

    /**
     * lastdeposit date is updated with system time and lastDepositAmount is updated with current deposit amount
     *calls super deposit method to update balance in Accounts class
     */
    @Override
    void deposit(double amount){

        lastDepositDate = new Date(System.currentTimeMillis());
        lastDepositAmount = amount;
        super.deposit(amount);
    }

    public Date getLastDepositDateChecking() {
        return lastDepositDate;
    }

    public double getLastDepositAmount() {
        return lastDepositAmount;
    }
}
