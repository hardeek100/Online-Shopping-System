/*
    Class: OnlineShopping
    This class runs all the use cases chosen by the user.

    Here the use cases are numbered as:
    0: Exit
    1: Login
    2: Logout
    3: Create Account
    4: Select items
    5: Show cart
    6: Remove items
    7: Make Order request
    8: View Order
    9: Process order
    10: Confirm order

 */



import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class OnlineShoppingSystem implements Welcome{           // Implementing interface from Welcome.
    private Scanner input = new Scanner(System.in);
    private HashMap<String, String> rawID;                      // Login credentials of user.
    private HashMap<String, Customer> customers;                // Customer information.

     private ArrayList<Order> unprocessedOrders;                // List of requested orders by the customer.
    private ArrayList<Order> processedOrder;                    // List of processed orders by the supplier.
    private HashMap<String, ArrayList<Order>> allOrders;        // List of all available orders.

    Bank bank_ = new Bank();
    private User usr;
    private Supplier supplier = new Supplier();

    /*
        Welcome screen for the visitor:
            Available use cases for visitor:
                    1. Create an Account (Use case no. 3)
                    2. Login (Use case no. 1)
                    3. Exit (Use case no. 0)
     */
    private User visitor = new User(){
        @Override
        int useCase(Scanner in) {
            int use_case = -1;
            while (use_case == -1) {
                System.out.print(MENU);
                System.out.print(INSTR);
                String choiceChar = in.nextLine();
                int choice = 0;
                try {
                    choice = Integer.parseInt(choiceChar);
                }catch (Exception e){
                    choice = -11;
                }
                switch (choice) {
                    case 1:
                        use_case = 3;                       // Create Account
                        break;
                    case 2:
                        use_case = 1;                       // Login
                        break;
                    case 3:
                        use_case = 0;                       // Exit
                        break;
                    default:
                        System.out.print(choiceChar + " is an invalid input!");
                }
            }
            return use_case;
        }
    };

    // Constructor
    public OnlineShoppingSystem(){
        this.rawID = importRawID();                             //Importing user's credentials.
        this.customers = importCustomerData();                  //Importing customer's data.
        this.allOrders = importOrders();                        //Importing available orders.
        this.usr = visitor;                                     //User would be a visitor by default.

    }


    public void run(){
        int c = 0;                              // Loop key
        while (c == 0){
            int choice = usr.useCase(this.input);
            switch(choice){
                case 0:     //Exit
                    exportOrders();
                    System.out.print("*********Thank you for using this online shopping system.*********");
                    c = 1;
                    break;

                case 1:     //Login
                    String[] info = this.usr.login(this.input);
                    String id_ = info[0];

                    if(this.rawID.containsKey(id_) && this.rawID.get(id_).equals(info[1])){
                        if(customers.containsKey(id_)){
                            this.usr = customers.get(id_);
                            System.out.println(CUSTOMER);
                            System.out.println("\t\tWelcome " + ((Customer)this.usr).getFname());
                        }
                        else {
                            System.out.println(SUPPLIER);
                            this.usr = this.supplier;
                        }
                    }
                    else {
                        System.out.println("Wrong password or username. Try again");
                    }
                    break;

                case 2:     //Logout
                    this.usr = visitor;
                    exportOrders();
                    System.out.println("Logged out...");
                    break;

                case 3:     //Create Account
                    this.usr.createAccount(this.input, this.rawID, this.customers, this.allOrders);
                    exportRawID();
                    exportCustomerData();
                    exportOrders();
                    System.out.print("Congratulation! your account has been created.");
                    break;

                case 4:  //Select items
                    SelectItems sel = new SelectItems(input, (Customer)this.usr);
                    exportCustomerData();
                    break;

                case 5:      //Show cart
                    ((Customer)this.usr).showCart();
                    break;

                case 6:         //Remove items
                    ((Customer)this.usr).RemoveItems(input);
                    exportCustomerData();
                    break;

                case 7:         //Make order request.
                    Order uO = ((Customer)this.usr).MakeOrderRequest(input, bank_);
                    System.out.println(uO.shortDetails());
                    unprocessedOrders.add(uO);
                    allOrders.get(usr.id).add(uO);
                    exportOrders();
                    break;

                case 8:         //View Order
                    ((Customer)this.usr).ViewOrder(input, allOrders);
                    break;

                case 9:     //Process order
                    if(unprocessedOrders.isEmpty()){
                        System.out.println("No orders to process");
                    }else {
                        supplier.processOrder(input, unprocessedOrders, processedOrder);
                    }
                    break;
                case 10:    //Confirm order.
                    if(processedOrder.isEmpty()){
                        System.out.println("No orders ready to ship");
                        break;
                    }
                    else{
                        supplier.ConfirmOrder(input, processedOrder);
                    }
                    break;

            }
        }
    }

    //--------------------------------------------------------------- Import and Export Files ----------------------------------------------

    private  void exportRawID(){
        try{
            PrintWriter fileOut = new PrintWriter(IDS);
            for(String key:this.rawID.keySet()){
                fileOut.println(key + (char)31 + this.rawID.get(key));
            }
            fileOut.close();
            //System.out.println("RAWID exported.");
        }catch (Exception e){
            System.out.println("Cannot export rawid");
        }
    }

    private HashMap<String, String> importRawID(){
        try{
            HashMap<String, String> rID = new HashMap<>();
            Scanner file = new Scanner(new File(IDS));
            while(file.hasNextLine()){
                String[] info = file.nextLine().split(""+(char)31);
                rID.put(info[0], info[1]);

            }
            file.close();
            return rID;
        }catch (Exception e){
            System.out.println("Cannot import rawid");
            return new HashMap<String, String>();
        }
    }

    private void exportCustomerData(){
        try {
            PrintWriter ffOut = new PrintWriter(INFO);
            FileOutputStream fileOut = new FileOutputStream(INFO_OBJECTS);
            ObjectOutputStream obOut = new ObjectOutputStream(fileOut);
            for(String key:customers.keySet()){
                ffOut.println(key);
                obOut.writeObject(customers.get(key));
            }
            ffOut.close();
            obOut.close();
            //System.out.println("Customer data added to file " + INFO);
        }catch (Exception e){
            System.out.println("Cannot export customer data");

        }
    }

    private HashMap<String, Customer> importCustomerData(){
        try{
            HashMap<String, Customer> cust = new HashMap<>();
            Scanner file = new Scanner(new File(INFO));
            FileInputStream inFile = new FileInputStream(INFO_OBJECTS);
            ObjectInputStream oin = new ObjectInputStream(inFile);
            while(file.hasNextLine()){
                cust.put(file.nextLine(), (Customer)oin.readObject());
            }
            return cust;
        }catch (Exception e){
            System.out.println("Cannot import customer data");
            return new HashMap<String, Customer>();
        }
    }

    private HashMap<String, ArrayList<Order>> importOrders(){
        processedOrder = new ArrayList<>();
        unprocessedOrders = new ArrayList<>();
        try{
            HashMap<String, ArrayList<Order>> orrr = new HashMap<>();
            Scanner file = new Scanner(new File(ORDERS));
            FileInputStream FileIN = new FileInputStream(ORDER_OBJECT);
            ObjectInputStream Obin = new ObjectInputStream(FileIN);

            while (file.hasNextLine()){
                String id = file.nextLine();
                ArrayList<Order> orders_ = (ArrayList<Order>)Obin.readObject();
                orrr.put(id, orders_);

                for(Order o: orders_){
                    if(o.getStatus().equals("ORDERED"))
                        unprocessedOrders.add(o);
                    else if(o.getStatus().equals("READY"))
                        processedOrder.add(o);
                }
            }
            file.close();
            Obin.close();
            return orrr;
        }catch (Exception e){
            System.out.println("Unable to import Orders");
            return new HashMap<String, ArrayList<Order>>();

        }


    }
    private void exportOrders(){
        try {
            PrintWriter ffOut = new PrintWriter(ORDERS);
            FileOutputStream fileOut = new FileOutputStream(ORDER_OBJECT);
            ObjectOutputStream obOut = new ObjectOutputStream(fileOut);
            for(String key:allOrders.keySet()){
                ffOut.println(key);
                obOut.writeObject(allOrders.get(key));
            }
            ffOut.close();
            obOut.close();
            //System.out.println("Orders added to file " + ORDERS);
        }catch (Exception e){
            System.out.println("Cannot export Orders");

        }
    }

}
