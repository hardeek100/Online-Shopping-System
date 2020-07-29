import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

public class Customer extends User implements Serializable, CustomerInterface{
    private String fname, lname, phone, address, creditCard;
    ArrayList<Items> itemList = new ArrayList<>();

    public Customer(String id, String pw, String f, String l, String ph, String ad, String crC){
        super(id, pw);
        this.fname = f;
        this.lname = l;
        this.phone = ph;
        this.address = ad;
        this.creditCard = crC;
    }

    public void addItems(Scanner in, Items item_) {//Set<Items> items_){
        //ArrayList<Items> itemList = new ArrayList<>(items_);
        itemList.add(item_);
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
            if(num == q)
                itemList.remove(choice);
            else 
                itemList.get(choice).update(num);
        }
        else 
           itemList.remove(choice); 

    }

    public void showCart(){
        for (int i = 0; i < itemList.size(); i++){
            System.out.print(i+1 + ". ");
            itemList.get(i).display();
        }
    }

    public void MakeOrderRequest(){
        System.out.print("Verifying with bank....");
        // If verified by the bank, make order request else print cannot make order and call change card number method here to let user edit their card number.

        // this.creditCard = changeCard(input);
    }

    public void ViewOrder(){
        System.out.print("Viewing order....");
        // Add your view order class here....
    }

    public String changeCard(Scanner in){
        String cc = "";
        int c = 0;
        while (c == 0){
            System.out.print("Enter new credit card number (enter 'x' to exit.): ");
            cc = in.nextLine();
            try {
                if (cc == "x") {
                    return this.creditCard;
                }
                Long.parseLong(cc);
            }catch (Exception e){
                System.out.print("Invalid credit card number!");
                cc = "";
                continue;
            }
            c =1;
        }
        return cc;
    }

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
            int choice = in.nextInt();
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