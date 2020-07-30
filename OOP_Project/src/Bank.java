import java.util.Random;

class Bank {
	
	int isValid(String card, double total) {
		if(card.length() != 16) {
			System.out.println("Invalid card number");
			return -1;
		}
		if(total > 1000){
			System.out.println("Insufficient funds");
			return -1;
		}
		Random ran = new Random();
		int authorization = ran.nextInt(); // generates random integer for the authorization number
		if(authorization < 0)
			authorization = authorization * (-1);
		return authorization;
	}
	
}