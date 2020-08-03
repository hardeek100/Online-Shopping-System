import java.io.Serializable;

public class Items implements Serializable, CustomerInterface {
    String name;
    int quantity;
    double price;
    Items(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    void update (int q, double price) {
        this.price += price;
        this.quantity += q;
    }
    void update(int q) {
        this.price = this.price - ((this.price/this.quantity) * q);
        this.quantity = this.quantity - q;
    }

    void display() {
        System.out.println("x" + quantity + " " + name + " $" + price);
    }
}
