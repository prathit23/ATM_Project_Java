package ATM;

/**
 * importing BankServer class from BankServer package
 */

import BankServer.BankServer;
import java.io.IOException;

/**
 * @author prathitpannase
 */

public class ATM {

    private String filename;

    /**
     * Composition
     *
     */
    private CardReader cardReader;
    private IOUnit ioUnit;
    private DepositUnit depositUnit;
    private ReceiptPrinter receiptPrinter;
    private CashDispenser cashDispenser;
    private BankServer bankServer;


    /**
     * Constructor to start BankServer and load all the details of customers
     */
    public ATM()  {

        filename = "accounts.dat";  // Filename to load account details
        try {

            bankServer = new BankServer(filename);  // loading file
        }
        catch (IOException e){

            System.out.println("Filename is incorrect, please try again");
        }

         cardReader = new CardReader();
         ioUnit = new IOUnit();
         depositUnit = new DepositUnit();
         receiptPrinter = new ReceiptPrinter();
         cashDispenser = new CashDispenser();
    }

    /**
     * Main function to handle ATM Transactions
     */
    public void handleTransaction(){

       String cardNumber  = cardReader.readCard();
       String pin ;

       boolean cardVerificationResult = bankServer.verifyCardNumber(cardNumber); // Calls the cardVerification method in BankServer class

        /**
         * Checks if the card number mentioned is valid or if the card was previously confiscated
         */
       if(!cardVerificationResult)
       {
           System.out.println("Sorry, the card number you entered does not exist. Please start your transaction again.");
           System.exit(0);
       }

       int noOfTries =1;
       boolean success = false;

       while (noOfTries<4) // Allows the customer 3 tries for entering the correct pin before confiscating the card
       {
           pin = ioUnit.obtainCustomerPin(); // System input to enter the pin

           if(bankServer.verifyPin(cardNumber,pin)){

               success = true;
               break;
           }

           if(noOfTries==2)
               System.out.println("----- Last try before the card gets confisticated -----");

           noOfTries++;
       }

       if(success == false) // If wrong pin was entered more than 3 times then the card gets confiscated
       {
           bankServer.confiscate(cardNumber);
           cardReader.confiscateCard();
           System.exit(0);
       }

       Transaction transactionType = ioUnit.obtainTransaction(); // Using enum class type

       if(transactionType == Transaction.Deposit){

           System.out.println("You've chosen to Deposit money");
           double amount = ioUnit.obtainAmount();
           depositUnit.takeDepositEnvelope();
           bankServer.doDeposit(cardNumber,amount);
           receiptPrinter.printReceipt("\nBalance after deposit is "+bankServer.doQuery(cardNumber));
       }

        else if(transactionType == Transaction.Withdraw){

            System.out.println("You've chosen to Withdraw money");
            double amount = ioUnit.obtainAmount();
            boolean fundsAvailable = bankServer.verifyFunds(cardNumber,amount);

            if(fundsAvailable){  // Checking with BankServerenough if enough funds are available for withdrawal

                bankServer.doWithdraw(cardNumber,amount);
                cashDispenser.dispenseCash((int)amount);
                receiptPrinter.printReceipt("\nBalance after withdrawal is "+bankServer.doQuery(cardNumber));
            }

            else if(!fundsAvailable)
                receiptPrinter.printReceipt("\nNot enough funds. Balance in the account is "+bankServer.doQuery(cardNumber));
        }


       else if(transactionType == Transaction.Query) {
           receiptPrinter.printReceipt("\nBalance is " + bankServer.doQuery(cardNumber));

       }

       else if(transactionType == Transaction.ChangePin) {


           receiptPrinter.printReceipt("\nEnter new pin");
           String newPin = ioUnit.obtainCustomerPin();
           receiptPrinter.printReceipt("\nEnter new pin again");
           String newPin2 = ioUnit.obtainCustomerPin();
           boolean pinChanged =false ;
           if(newPin.equalsIgnoreCase(newPin2))   // Making sure that the new pin is correctly entered by the user
               pinChanged = bankServer.changePin(cardNumber,newPin);

           if(pinChanged)
               receiptPrinter.printReceipt("\nPin changed successfully");
           else
               receiptPrinter.printReceipt("\nPin could not be changed");

       }

       else if(transactionType == Transaction.Cancel) {
           receiptPrinter.printReceipt("\nTransaction is cancelled");

       }
       cardReader.releaseCard();

       System.out.println("\n---- Thank you for Banking with us -----");

       bankServer.endTransaction(filename); // Prints the modified bank details back to text file
    }
}
