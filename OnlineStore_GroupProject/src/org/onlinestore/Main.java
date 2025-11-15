package org.onlinestore;

//DOCUMENTATION OF TESTED SCENARIOS
//manager logs in with default credentials:                PASS
//manager enters incorrect username or password:           PASS
//customer creates account followed by login:              PASS
//customer enters incorrect username or password:          PASS
//customer creates account but username is unavailable:    PASS
//close window (exit program):                             PASS
//user selects an invalid entry:                           PASS


import java.util.Scanner;
import org.onlinestore.software.OnlineStore;
import org.onlinestore.people.Manager;
import org.onlinestore.people.Person;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        OnlineStore store = new OnlineStore();

        System.out.println("Welcome to the Online Store.");

        while (true) {
            System.out.println("Home Page");
            System.out.println("1. Login");
            System.out.println("2. Create Account");
            System.out.println("3. Close Window");
            System.out.print("Please select from the options shown above: ");

            String usersSelection = scanner.nextLine();

            switch (usersSelection) {

                case "1":
                    System.out.print("Enter username: ");
                    String loginUsername = scanner.nextLine();

                    System.out.print("Enter password: ");
                    String loginPassword = scanner.nextLine();

                    Person loggedIn = store.login(loginUsername, loginPassword);

                    if (loggedIn == null) {
                        break; 
                    }

                    if (loggedIn instanceof Manager) {
                        System.out.println("Manager login successful. Welcome " + loggedIn.getName() + ".");
                        // Display manager home page options
                        return;
                    } 
                    else {
                        System.out.println("Customer login successful. Welcome " + loggedIn.getName() + ".");
                        // Display customer home page options
                        return;
                    }

                case "2":
                    System.out.print("Enter your name: ");
                    String name = scanner.nextLine();

                    System.out.print("Choose a username: ");
                    String newUsername = scanner.nextLine();

                    System.out.print("Choose a password: ");
                    String newPassword = scanner.nextLine();

                    store.createAccount(name, newUsername, newPassword);
                    break;

                case "3":
                    System.out.println("Online store has been exited.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid entry.");
            }
        }
    }
}