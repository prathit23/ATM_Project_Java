package ATM;

import java.util.Scanner;

/**
 * @author prathitpannase
 */
public class IOUnit {

    /**
     *
     * @return Validates if the pin enetered is 4 digit and returns the pin
     */
    public String obtainCustomerPin(){

        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter your 4 digit ATM Pin");
        String pin = sc.next();

        boolean success =false;
        while(!success) {

            if (pin.length() != 4 || Integer.parseInt(pin) < 0) {
                System.out.println("Pin must be a positive 4 digit number \nPlease enter the Pin again");
                pin = sc.next();
            } else
                break;

        }
        String returnPin = pin+"";

        return returnPin;
    }

    /**
     *
     * @return Asks user to enter a positive amount between 0 and 200,000
     */
    public double obtainAmount(){

        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter the Amount");
        double amount = sc.nextDouble();

        boolean success =false;
        while(!success) {

            if (amount <= 0 || amount > 200000) {
                System.out.println("Amount must be greater than 0 and upto 200,000 USD \nPlease enter the Amount again");
                amount = sc.nextDouble();
            } else
                break;

        }
        return amount;
    }

    /**
     *
     * @return Returns the type of transaction user wants to perform
     */
    public Transaction obtainTransaction(){

        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter: \n1 for Deposit \n2 for Withdraw \n3 for Query \n4 for ChangePin \n5 for Cancel");
        int choice = sc.nextInt();

        switch (choice){
            case 1: return Transaction.Deposit;
            case 2: return Transaction.Withdraw;
            case 3: return Transaction.Query;
            case 4: return Transaction.ChangePin;
            case 5: return Transaction.Cancel;

            default: return null;
        }
    }
}


