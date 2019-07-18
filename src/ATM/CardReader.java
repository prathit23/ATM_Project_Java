package ATM;

import java.util.Scanner;

class CardReader {



     public String readCard(){

         Scanner sc = new Scanner(System.in);

         System.out.println("Enter Card Number ");
         String cardNumber = sc.next();

         return cardNumber;
     }

     public void confiscateCard()
     {
         System.out.println("The card has been confiscated");
     }

    public void releaseCard()
    {
        System.out.println("\nPlease collect your card");
    }

}
