import BankServer.BankServer;
import ATM.ATM;

import java.io.IOException;

/**
 * @author prathitpannase
 * Main Class to run the ATM program
 * There are 2 packages : ATM and BankServer
 * The user interacts with ATM package and the ATM package interacts with BankServer
 * Except for ATM & BankServer class rest of the classes are protected to maintain encapsulation
 */
public class Main {


    public static void main(String args[])throws IOException {

        ATM atm = new ATM();
        atm.handleTransaction();  // Starts Transaction

    }
}
