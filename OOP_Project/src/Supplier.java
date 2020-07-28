import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Supplier extends User implements SupplierInterface{

    public Supplier(){
        super();
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
                    use_case = 9;
                    break;
                case 2:
                    use_case = 10;
                    break;
                case 3:
                    use_case = 2;
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

    public void processOrder(Scanner in, ArrayList<Order> orders_, ArrayList<Order> processedOrder){
        while(true) {
            System.out.println("*****Available orders*****");
            for (int i = 0; i < orders_.size(); ++i) {
                System.out.println(i+1 + ". " + orders_.get(i).shortDetails());
            }
            System.out.println(WHICH);
            int choice = in.nextInt();
            if(choice < 0 || choice > orders_.size()){
                System.out.println("invalid input!");
                continue;
            }
            if(choice == 0){
                return;
            }
            orders_.get(choice-1).setStatus("Ready");
            processedOrder.add(orders_.remove(choice-1));
            System.out.println("Order has been processed and ready to ship.");
            break;
        }
    }
    public void ConfirmOrder(Scanner in, ArrayList<Order> processedOrder){
        while(true){
            System.out.println("*****Orders ready to ship******");
            for (int i = 0; i< processedOrder.size(); ++i){
                System.out.println(i+1+ ". " + processedOrder.get(i).shortDetails());
            }
            System.out.println(WHICH);
            int choice = in.nextInt();
            if(choice < 0 || choice > processedOrder.size()){
                System.out.println("invalid input!");
                continue;
            }
            if(choice == 0){
                return;
            }
            processedOrder.get(choice-1).setStatus("Shipped");
            processedOrder.remove(choice-1);
            System.out.println("Order has been ship.");
            break;
        }

    }

}
