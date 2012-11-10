package investing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class ReadTextFile {

	public ReadTextFile() {
		
	}
	
	public static Vector<String> readFile(String path){
		
		BufferedReader br = null;
		FileReader fr = null;
		
		try {
			fr = new FileReader(path);
			br = new BufferedReader(fr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String aLine = null;
		Vector<String> returnString = new Vector<String>();
		
		try {
			while((aLine = br.readLine()) != null){
				
				returnString.add(aLine);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		return returnString;
	}

}
