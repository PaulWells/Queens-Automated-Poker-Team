import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class readFiles 
{

	public static ArrayList<String> readLinesAsStrings(String fileName) 
	{
		  String line = "";
		  ArrayList<String> data = new ArrayList<String>();
		  try {
		   FileReader fr = new FileReader(fileName);
		   BufferedReader br = new BufferedReader(fr);//Can also use a Scanner to read the file
		   while((line = br.readLine()) != null) {
			   
			   data.add(line);
			   
		   }
		  }
		  catch(FileNotFoundException fN) {
		   fN.printStackTrace();
		  }
		  catch(IOException e) {
		   System.out.println(e);
		  }
		  return data;
	}   
}
