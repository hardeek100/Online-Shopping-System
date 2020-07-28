public class Order {
    public String status, name, address, phone, cardNum,  date;
    long invoiceNum;
    Order(String stat, String fname, String lname, String add, String phone, String cN, long inv, String date){
        this.status = stat;
        this.name = fname + lname;
        this.address = add;
        this.phone = phone;
        this.cardNum = cN;
        this.invoiceNum = inv;
        this.date = date;
    }

    public void setStatus(String st){
        this.status = st;
    }

    public String shortDetails(){
        return "\nOrder " + this.invoiceNum + " from " + this.date + " status " + this.status;
    }

    public String details(){
        return "\nName: " + this.name + "\nAddress: " + this.address + "\nPhone: " + this.phone + "\n Card Number: " + this.cardNum + "\n Invoice: " + this.invoiceNum;
    }
}
