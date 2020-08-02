import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Catalog {
	
	void display() {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("src/catalog.csv"));
			String line = reader.readLine();
			while (line != null) {
				System.out.println(line);
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	String getItem(int line){
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("src/catalog.csv"));
			for(int i = 0; i < line-1; i++)
				reader.readLine();
			String item = reader.readLine();
			if(item == null)
				System.out.println("This item does not exist in catalog");
			reader.close();
			return item; 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return " ";
	}
}