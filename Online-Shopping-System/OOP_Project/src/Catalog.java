import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

class Catalog {
	
	void display() {
		BufferedReader reader;
		String filename = Paths.get(".").toAbsolutePath().normalize().toString() + "\\OOP_Project\\src\\catalog.csv";

		try {
			reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();
			while (line != null) {
				System.out.println(line);
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("Cannot find catalog.csv file.");
			System.out.println(filename);
		}
	}
	
	String getItem(int line){
		BufferedReader reader;
		String filename = Paths.get(".").toAbsolutePath().normalize().toString() + "\\OOP_Project\\src\\catalog.csv";
		try {
			reader = new BufferedReader(new FileReader(filename));
			for(int i = 0; i < line-1; i++)
				reader.readLine();
			String item = reader.readLine();
			if(item == null)
				System.out.println("This item does not exist in catalog");
			reader.close();
			return item; 
		} catch (IOException e) {
			System.out.println("Cannot find catalog.csv file");
			System.out.println(filename);
		}
		return " ";
	}
}