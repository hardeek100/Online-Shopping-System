import java.util.Scanner;

class SelectItems {
	
	SelectItems (Scanner in, Customer cus)  {
		char done = 'y';
		Catalog cat = new Catalog();
		Scanner sc = new Scanner(System.in);
        double price;
        int quantity;
        String name;
		do {
			int line;
			String item;
			cat.display();
			System.out.print("Select Item by #: ");
			line = sc.nextInt();
            System.out.print("\nEnter quantity: ");
            quantity = sc.nextInt();
			item = cat.getItem(line);
            String[] str = item.split(" ");
            name = str[1];
            price = Double.parseDouble(str[3]);
            price = price * quantity;
            Items items_ = new Items(name, quantity, price);
			if(item != null) 
				cus.addItems(in, items_);
			System.out.print("\nKeep Shopping (Y/N) ? ");
			done = sc.next().charAt(0);
		}while (done == 'y' || done == 'Y');	
	}
}