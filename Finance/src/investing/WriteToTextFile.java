package investing;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteToTextFile {

	private static FileWriter outFile = null;
	private static PrintWriter out = null;
	
	public WriteToTextFile() {
		
		try {
			outFile = new FileWriter("C:\\Users\\denman\\Desktop\\outfile.txt");
			out = new PrintWriter(outFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public static void writeToTextFile(String line){
		
		out.println(line);
		
	}
	
	public static void closeFile(){
		
		out.close();
	}
}
