import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {
    public String status, name, address, phone, cardNum,  date;
    long invoiceNum;
    ArrayList<Items> orderItems;
    Order(String name, String add, String phone, String cN, long inv, String date, ArrayList<Items> orderItems){
        this.name = name;
        this.address = add;
        this.phone = phone;
        this.cardNum = cN;
        this.invoiceNum = inv;
        this.date = date;
        this.orderItems = orderItems;
    }

    public double getTotal(){
        double total =0;
        for(int i = 0; i<this.orderItems.size(); ++i){
            total += this.orderItems.get(i).price;
        }
        return total;
    }
    public void setStatus(String st){
        this.status = st;
    }

    public String getStatus() {
        return status;
    }

    public String shortDetails(){
        return "\nOrder " + this.invoiceNum + " from " + this.date + " status " + this.status;
    }


    public void view(){
        for(int i = 0; i<orderItems.size(); ++i){
            System.out.println("\t" + orderItems.get(i).name + "\t\t  " + orderItems.get(i).quantity + "            \t" + orderItems.get(i).price);
        }
    }
    public String details(){
        return "\nName: " + this.name + "\nAddress: " + this.address + "\nPhone: " + this.phone + "\n Card Number: " + this.cardNum + "\n Invoice: " + this.invoiceNum;
    }
}
