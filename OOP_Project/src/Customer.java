/*
        Class Customer
            This class would run use cases for the customer.
            1. Add items
            2. Remove items
            3. Show cart
            4. Make Order Request
            5. View Order
            6. Exit

 */


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

public class Customer extends User implements Serializable, CustomerInterface{
    private String fname, lname, phone, address, creditCard, ID;
    ArrayList<Items> itemList = new ArrayList<>();                      // Arraylist to store customers cart items.
    double total;                                                       // Total cost of cart items.

    //Constructor
    public Customer(String id, String pw, String f, String l, String ph, String ad, String crC){
        super(id, pw);                  // Calling super class "User"
        this.ID = id;
        this.fname = f;
        this.lname = l;
        this.phone = ph;
        this.address = ad;
        this.creditCard = crC;
    }

    //------------------------------------------------ Use cases --------------------------------------------------

    public void addItems(Scanner in, Items item_) {
            if(itemList.size() == 0) {
            itemList.add(item_);
            total = item_.price;
        }
        else {
            int flag = 0;
            for(int i = 0; i < itemList.size(); i++) {
                if(item_.name.equals(itemList.get(i).name)) {
                    itemList.get(i).update(item_.quantity, item_.price);
                    total += item_.price;
                    flag = 1;
                }
            }
            if (flag == 0) {
                itemList.add(item_);
                total += item_.price;
            }
        }
        System.out.println("The item has been added");  //Just to check this method. Add your class addItems here.
    }

    public void RemoveItems(Scanner in){
        int choice;
        int num;
        int q;
        showCart();
        System.out.println("Select item to remove: ");
        choice = in.nextInt();
        choice--;
        q = itemList.get(choice).quantity;
        if(q > 1){
            System.out.println("how many would you like to delete?");
            num = in.nextInt();
            if(num == q) {
                total -= itemList.get(choice).price;
                itemList.remove(choice);
            }
            else {
                total -= ((itemList.get(choice).price/itemList.get(choice).quantity) * num);
                itemList.get(choice).update(num);
            }
        }
        else {
            total -= itemList.get(choice).price;
            itemList.remove(choice); 
        }
        System.out.println("The item has been removed");
    }

    public void showCart(){
        for (int i = 0; i < itemList.size(); i++){
            System.out.print(i+1 + ". ");
            itemList.get(i).display();
        }
        System.out.println("Total: $" + total);
    }

    public Order MakeOrderRequest(Scanner in, Bank bank_) {
        Order requestedOrder = null;
        Date date = new Date();
        if (itemList.isEmpty()) {
            System.out.println("No items in cart to make order.");
        } else {
            int auth = 0;
            while (auth == 0) {
            System.out.println("Verifying with bank....");

                auth = bank_.isValid(this.creditCard, total);
                if (auth == -1) {
                    System.out.println("Your card got declined. Do you have another card? (1 for yes)");
                    int anotherCard = in.nextInt();
                    in.nextLine();
                    if (anotherCard == 1) {
                        this.creditCard = changeCard(in);
                        auth = 0;
                    } else {
                       System.out.println("Cannot make an order with this card.");
                    }
                }

            }
            System.out.println(auth);
            if (auth > 0) {
                SimpleDateFormat date_ = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                ArrayList<Items> orderList  = this.itemList;
                requestedOrder = new Order(this.fname + ' ' + this.lname, this.address, this.phone, this.creditCard, auth, date_.format(date), orderList);
                requestedOrder.setStatus("ORDERED");

            } else {
                System.out.println("Cannot make order.");
            }
        }

        return requestedOrder;
    }

    public void ViewOrder(Scanner in, HashMap<String, ArrayList<Order>> orders){
        System.out.println("Viewing order....");
        if(orders.isEmpty()){
            System.out.println("No orders to view...");
        }
        else{
            System.out.println("*****Available Orders******");
            for(int i = 0; i < orders.get(this.ID).size(); ++i){
                System.out.println(i + ". " + orders.get(this.ID).get(i).shortDetails());
            }

            System.out.println("Which order would you like to view?: ");
            int o = in.nextInt();
            in.nextLine();
            System.out.println("Order date: " + orders.get(this.ID).get(o).date);
            System.out.println("Status: " + orders.get(this.ID).get(o).getStatus());
            orders.get(this.ID).get(o).details();
            System.out.println("\t\t Items \t Quantity \t Cost");
            orders.get(this.ID).get(o).view();
            System.out.println("\t Total \t\t ----------------- " + orders.get(this.ID).get(o).getTotal());
        }

    }

    public String changeCard(Scanner in){
        String cc = "";
        while (cc.equals("")){
            System.out.print("Enter new credit card number (enter 'x' to exit.): ");
            cc = in.nextLine();
            try {
                if (cc.equals("x")) {
                    return this.creditCard;
                }
                Long.parseLong(cc);
            }catch (Exception e){
                System.out.print("Invalid credit card number!");
                cc = "";
                continue;
            }
        }
        return cc;
    }

    //---------------------------------------------- getset methods ---------------------------------------------------------

    public String getName(){
        return this.fname + " " + this.lname;
    }

    public String getFname(){
        return this.fname;
    }

    public String getLname() {
        return lname;
    }

    public String getAddress() {
        return address;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public String getPhone() {
        return phone;
    }


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
                choice = -10;
            }
            switch (choice) {
                case 1:
                    use_case = 4;
                    break;
                case 2:
                    use_case = 6;
                    break;
                case 3:
                    use_case = 5;
                    break;
                case 4:
                    use_case = 7;
                    break;
                case 5:
                    use_case = 8;
                    break;
                case 0:
                    use_case = 0;
                    break;
                default:
                    System.out.print("Invalid input!");
            }
        }
        return use_case;
    }

}