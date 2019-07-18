package BankServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author prathitpannase
 *
 * BankServer class has a Hashmap to store all accounts from input file
 */
public class BankServer {

    private static Map<String,Account> accountList= new HashMap<String,Account>();

    /**
     *
     * @param filename
     * @throws IOException
     */


    public BankServer(String filename) throws IOException {

        loadAccounts(filename);
    }

    public double doQuery(String cardNumber){

        Account a = accountList.get(cardNumber);

        if(a == null)
            System.out.println("Account Not Found");
        else
        {
            double balance = a.getBalance();
      //      System.out.println("Account Present and Balance is "+balance);
            return balance;
        }
        return 0;
    }

    public void doDeposit(String cardNumber, double amount){

        Account a = accountList.get(cardNumber);

        if(a == null)
            System.out.println("Account Not Found");
        else
        {
            double oldBalance = a.getBalance();
            a.deposit(amount);
            double newBalance = a.getBalance();
       //     System.out.println("Old Balance = "+oldBalance+" || New Balance = "+newBalance);
        }


    }

    public void doWithdraw(String cardNumber, double amount){

        Account a = accountList.get(cardNumber);

        if(a == null) // Verifies if the cardnumber exists
            System.out.println("Account Not Found");
        else
        {
            double oldBalance = a.getBalance();
            a.withdraw(amount);
            double newBalance = a.getBalance();
            System.out.println("Old Balance = "+oldBalance);  //Print Old Balance before withdrawing
        }


    }

    /**
     *
     * @param cardNumber Cardnumber sent by ATM class
     * @return Returns true if the card was previously blocked
     */
    public boolean verifyCardNumber(String cardNumber ){

        Account a = accountList.get(cardNumber);

        if(a == null)  // Verifies if the cardnumber exists
            return false;

        else if(a.isConfiscate())
        {
            System.out.println("Your card was conficated previously. Please contact Bank");
            System.exit(0);
            return false;
        }
        return true;

    }

    /**
     *
     * @return Returns true if the pin is correct
     */

    public boolean verifyPin(String cardNumber, String pin){

        Account a = accountList.get(cardNumber);

        if(a.getPin().equals(pin))
            return true;

        else
            System.out.println("Incorrect Pin");

        return false;
    }

    public boolean changePin(String cardNumber,  String newPin){

        Account a = accountList.get(cardNumber);

        a.setPin(newPin);  // No need to verify again since the pin was validated at the start of the transaction

        if(a.getPin()==newPin)
            return true;
        return false;
    }

    public boolean verifyFunds(String cardNumber, double amount){

        Account a = accountList.get(cardNumber);

        if(a.getBalance()>= amount)
            return true;

        return false;
    }

    public void confiscate(String cardNumber){

        Account a = accountList.get(cardNumber);
        a.setConfiscate(true);

    }

     private void loadAccounts(String filename) throws IOException{

        File file = new File(filename);
        Scanner sc = new Scanner(file);
        String s;

        try {
            while ((s = sc.next()) != null) {
                String sArray [] = s.split("\\|");

//                printArray(sArray);

             //   System.out.println(s);
                if(sArray[0].equals("S"))
                 accountList.put(sArray[1],new SavingsAccount(sArray[1],sArray[2],sArray[3],Double.parseDouble(sArray[4]),Double.parseDouble(sArray[6]),Boolean.parseBoolean(sArray[5])));

                else if (sArray[0].equals("C")){


                    SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy HH:mm");

                    String day = sArray[9];String month = sArray[8];String year = sArray[7];String hour = sArray[10];String minute = sArray[11];
                    String Date =day+"-"+month+"-"+year+" "+hour+":"+minute;

                    Date date = null;

                    try {
                         date = formatter.parse(Date);
                    }
                    catch (ParseException e){
                        System.out.println("Exception while Reading Date");
                    }

                    accountList.put(sArray[1] ,new CheckingAccount(sArray[1],sArray[2],sArray[3],Double.parseDouble(sArray[4]),Boolean.parseBoolean(sArray[5]),date,Double.parseDouble(sArray[6])));
                }
            }
        }

        catch (NoSuchElementException e){
            System.out.println("Starting Transaction");
            sc.close();
        }

    }

    public void endTransaction(String filename) {
       try {
           writeToFile(filename);
       }

       catch (Exception e){
           System.out.println("Could not save results");
       }
    }

    private void writeToFile(String filename)throws  Exception{


        PrintWriter pw = new PrintWriter(filename); // Writing the data of all accounts to the file name passed

        for(String i: accountList.keySet())  //
        {
            Account account = accountList.get(i);
            String stringToWrite="";

            if(account instanceof SavingsAccount) { // Checking if the account object is of SavingsAccount type

                SavingsAccount savingsAccount = (SavingsAccount) account; // Typecasting account to access SavingsAccount methods

                stringToWrite   = "S|"+savingsAccount.getAccountId()+"|"+savingsAccount.getCustomerId()+"|"
                        +savingsAccount.getPin()+"|"+savingsAccount.getBalance()+"|"+savingsAccount.isConfiscate()+"|"+savingsAccount.getInterestRate();
            }
            else if(account instanceof CheckingAccount){  // Checking if the account object is of Checkingaccount type

                CheckingAccount checkingAccount = (CheckingAccount) account; // Typecasting account to access Checkingaccount methods

                stringToWrite = "C|"+checkingAccount.getAccountId()+"|"+checkingAccount.getCustomerId()+"|"
                        +checkingAccount.getPin()+"|"+checkingAccount.getBalance()+"|"+checkingAccount.isConfiscate()+"|"+checkingAccount.getLastDepositAmount()
                        +"|"+dateToString(checkingAccount.getLastDepositDateChecking());
            }

            pw.write(stringToWrite);
            pw.write("\n");
        }

        pw.close(); // Closing the file
    }

    /**
     *
     * @return : Returns the string format of the date passed
     */

    String dateToString( Date date){

        String year = new SimpleDateFormat("yyyy").format(date);
        String month = new SimpleDateFormat("MM").format(date);
        String day = new SimpleDateFormat("dd").format(date);
        String hour = new SimpleDateFormat("HH").format(date);
        String minute = new SimpleDateFormat("mm").format(date);
        String dateSplit =year+"|"+month+"|"+day+"|"+hour+"|"+minute;

        return dateSplit;
    }

}
