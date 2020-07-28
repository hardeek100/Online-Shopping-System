
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class OnlineShoppingSystem implements Welcome{
    private Scanner input = new Scanner(System.in);
    private HashMap<String, String> rawID;
    private HashMap<String, Customer> customers;

    // private ArrayList<Order> orders;
   // private ArrayList<Order> processedOrder;

    private User usr;
    private Supplier supplier = new Supplier();

    private User visitor = new User(){
        @Override
        int useCase(Scanner in) {
            int use_case = -1;
            while (use_case == -1) {
                System.out.print(MENU);
                System.out.print(INSTR);
                int choice = in.nextInt();
                switch (choice) {
                    case 1:
                        use_case = 3;
                        break;
                    case 2:
                        use_case = 1;
                        break;
                    case 3:
                        use_case = 0;
                        break;
                    default:
                        System.out.print("Invalid input!");
                }
            }
            return use_case;
        }
    };

    public OnlineShoppingSystem(){
        this.rawID = importRawID();
        this.customers = importCustomerData();
        this.usr = visitor;
    }


    public void run(){
        int c = 0;
        while (c == 0){
            int choice = usr.useCase(this.input);

/*
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

            switch(choice){
                case 0:     //Exit
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
                    System.out.println("Logged out...");
                    break;
                case 3:     //Create Account
                    this.usr.createAccount(this.input, this.rawID, this.customers);
                    exportRawID();
                    exportCustomerData();
                    System.out.print("Congratulation! your account has been created.");
                    break;
                case 4:
                    //Select items class
                    System.out.println("Select your items...");
                    break;
                case 5:
                    //View cart class
                    System.out.println("Viewing cart items...");
                    break;
                case 6:
                    //Remove items
                    System.out.println("Removing items....");
                    break;
                case 7:
                    //Make order req
                    System.out.println("Making order request....");
                    break;
                case 8:
                    //View order
                    System.out.println("Viewing order....");
                    break;
                case 9:
                    //Process order
                    System.out.println("Processing order...");
//                    if(orders.isEmpty()){
//                        System.out.println("No order to process.");
//                        break;
//                    }else {
//                        supplier.processOrder(input,orders, processedOrder);
//                    }

                    break;
                case 10:
                    System.out.println("Shipping order....");
//                    if(processedOrder.isEmpty()){
//                        System.out.println("No orders ready to ship");
//                        break;
//                    }
//                    else{
//                        supplier.ConfirmOrder(input, processedOrder);
//                    }
                    break;

            }
            //c = 1;
        }

    }

    private  void exportRawID(){
        try{
            PrintWriter fileOut = new PrintWriter(IDS);
            for(String key:this.rawID.keySet()){
                fileOut.println(key + (char)31 + this.rawID.get(key));
            }
            fileOut.close();
            System.out.println("RAWID exported.");
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
            System.out.println("Customer data added to file " + INFO);
        }catch (Exception e){
            System.out.println("Cannot export customer data");
            //System.out.println(e);
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
            //System.out.println(e);
            return new HashMap<String, Customer>();
        }
    }


}
