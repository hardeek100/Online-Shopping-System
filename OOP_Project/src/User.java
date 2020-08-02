/*
    Abstract class to run use cases for users
            1. CreateAccount
            2. Login
 */


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

abstract class User implements Serializable {
    protected String id, password;
    public User(){ this("", "");};

    public User(String id, String password) {
        this.id = id;
        this.password = password;
    }

    // Constructor
    final void createAccount(Scanner in, HashMap<String, String> rawID, HashMap<String, Customer> custs, HashMap<String, ArrayList<Order>> orders){
        int c = 0;              //Loop key
        String role, pw, ID;
        pw =ID = role = "";

        //Select role
        while(c == 0){
            System.out.println("\nSelect your role.\n1.Customer \n2.Supplier");
            String choiceChar = in.nextLine();
            int choice = 0;
            try {
                choice = Integer.parseInt(choiceChar);
            }catch (Exception e){
                choice = -11;
            }
            if (choice == 1){
                role = "Customer";
                c = 1;
            }
            else if(choice == 2){
                role = "Supplier";
                c = 1;
            }
            else{
                System.out.println("Invalid input!");
            }
        }

        // Get user id
        c = 0;
        while(c == 0) {
            System.out.print("Enter an ID: ");
            ID = in.nextLine();
            if (rawID.get(ID) != null) {
                System.out.println("User ID already taken. Please try again.\n");
                continue;
            }
            c = 1;
        }

        //verify and get user's password.
        c = 0;
        while(c == 0){
            System.out.print("Enter a password: ");
            String pw1 = in.nextLine();
            System.out.print("Verify your password: ");
            String pw2 = in.nextLine();
            if(pw1.equals(pw2)){
                pw = pw1;
            }
            else{
                System.out.println("Password do not match!");
                continue;
            }
            c = 1;
        }

        // For customer.
        if(role.equals("Customer")){
            System.out.print("Enter your first name: ");
            String Fname = in.nextLine();

            System.out.print("Enter your last name: ");
            String Lname = in.nextLine();

            String phone = "";
            while(phone == ""){
                System.out.print("Enter your phone number: ");
                phone = in.nextLine();
                try{
                    Long.parseLong(phone);
                } catch (Exception e){
                    System.out.print("Invalid Phone number!");
                    phone = "";
                }
            }

            System.out.print("Enter your address: ");
            String address = in.nextLine();
            String creditCard = "";

            while(creditCard == ""){
                System.out.print("Enter your credit card number: ");
                creditCard = in.nextLine();
                try{
                    Long.parseLong(creditCard);
                }catch(Exception e){
                    System.out.println("Invalid credit card number!");
                    creditCard = "";
                }
            }

            // Add user's credentials in arraylist and hashmaps
            rawID.put(ID, pw);
            custs.put(ID, new Customer(ID, pw, Fname, Lname, phone, address, creditCard));
            orders.put(ID, new ArrayList<Order>());

        }
        else{
            // For supplier.
            rawID.put(ID, pw);
        }
    }

    // User logins.
    public String[] login(Scanner in){
        in.nextLine();
        String[] logs = new String[2];
        System.out.print("Enter your user id: ");
        logs[0] = in.nextLine();
        System.out.print("Enter your Password: ");
        logs[1] = in.nextLine();
        return logs;
    }

    abstract int useCase(Scanner in);

}
