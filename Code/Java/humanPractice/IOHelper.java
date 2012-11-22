import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileReader;


public class IOHelper {
		
	public static int getInt (int low, String prompt, int high) {
			
		 int userNum = 0;
		 	Scanner screen = new Scanner(System.in);
	        boolean inputOK = false;
	        String dump;
	            
	        while (!inputOK) {
	        	System.out.print(prompt);
	        	try {
	        		userNum = screen.nextInt();
	        		
	        		if (userNum <= high && userNum >= low) 
	        			inputOK = true;
	        		else
	        		System.out.println("\"" + userNum + "\" is not a legal integer, " +
	        				"please try again!");
	        
	        	} catch (InputMismatchException e) {
	        		dump = screen.nextLine();
	        		System.out.println("\"" + dump + "\" is not a legal integer, " +
	        				"please try again!");
	        	} // end try-catch block
	        } // end input loop
	        return userNum;
		} // end getInt (3 parameters)
		
	public static int getInt (int low, String prompt) {
		
		 int high = Integer.MAX_VALUE;
		 int userNum = getInt(low, prompt, high);
	        
		 return userNum;
		} // end getInt (prompt + low)
		
	public static int getInt (String prompt, int high) {
		
		 int low = Integer.MIN_VALUE;
	     int userNum = getInt(low, prompt, high); 
	     
	     return userNum;
		} // end getInt (prompt + high)
	
	public static int getInt (String prompt) {
		
		 int low = Integer.MIN_VALUE;
		 int high = Integer.MAX_VALUE;
		 int userNum = getInt(low, prompt, high);
	        
		 return userNum;
		} // end getInt (only prompt)
	
	public static int getInt () {
		
		int low = Integer.MIN_VALUE;
		 int high = Integer.MAX_VALUE;
		 String prompt = "Enter an integer: ";
		 int userNum = getInt(low, prompt, high);
	        
		 return userNum;
		
	} //end getInt (default parameters)
	
	public static double getDouble (double low, String prompt, double high) {
		
		 double userNum = 0;
	        Scanner screen = new Scanner(System.in);
	        boolean inputOK = false;
	        String dump;
	            
	        while (!inputOK) {
	        	System.out.print(prompt);
	        	try {
	        		userNum = screen.nextDouble();
	        		if (userNum <= high && userNum >= low) 
	        			inputOK = true;
	        		else
	        		System.out.println("\"" + userNum + "\" is not a legal number, " +
		        		"please try again!");
	        	} catch (InputMismatchException e) {
	        		dump = screen.nextLine();
	        		System.out.println("\"" + dump + "\" is not a legal number, " +
	        				"please try again!");
	        	} // end try-catch block
	        } // end input loop
	        return userNum;
		} // end getDouble (3 parameters)
	
	public static double getDouble (double low, String prompt) {
		double high = Double.MAX_VALUE;
		double userNum = getDouble(low, prompt, high);
		
		return userNum;
		
		} // end getDouble (missing high)
	
	public static double getDouble (String prompt, double high) {
		double low = Double.MIN_VALUE;
		double userNum = getDouble(low, prompt, high);
		
		return userNum;
		
		} // end getDouble (missing low)
	
	public static double getDouble (String prompt) {
		double low = -Double.MAX_VALUE; //if not -MAX 0 is lower than double.MIN
		double high = Double.MAX_VALUE;
		double userNum = getDouble(low, prompt, high);
		
		return userNum;
		
		} // end getDouble (only prompt)
	
	public static double getDouble () {
		double low = -Double.MAX_VALUE;
		double high = Double.MAX_VALUE;
		String prompt = "Enter a number: ";
		double userNum = getDouble(low, prompt, high);
		
		return userNum;
		
		} // end getDouble (no parameters)
	
	public static String getString (String prompt) {
		
		Scanner screen = new Scanner(System.in);
		System.out.println(prompt);
		String userInput = screen.nextLine();
		return userInput;
		
	}
	
	 public static void saveText(String filename, String[] textArray) {

	        int line;
	        int size = textArray.length;

	        if (size == 0) {
	            System.out.println("Empty text array!");
	            return;
	        } // end if

	        PrintWriter fileOut = null;

	        try {
	            fileOut = new PrintWriter(new FileOutputStream(filename));
	        } catch (FileNotFoundException e) {
	            System.out.println("Unable to open file: " + filename);
	            return;
	        } // end try/-catch

	        for (line = 0; line < size; line++) {
	            fileOut.println(textArray[line]);
	        }

	        fileOut.close();

	    } // end saveText

	    public static String[] readText(String filename) {

	        int size = 0;
	        int lineCount = 0;
	        String line;
	        String[] textArray;

	        FileReader fileIn = null;

	        try {
	            fileIn = new FileReader(filename);
	        } catch (FileNotFoundException e) {
	            System.out.println("Unable to open file: " + filename);
	            return null;
	        } // end try-catch
	        Scanner fileInput = new Scanner(fileIn);

	        while (fileInput.hasNextLine()) {
	            line = fileInput.nextLine();
	            size++;
	        } // end while

	        try {
	            fileIn.close();
	        } catch (IOException e) {
	            System.out.println("Unable to close file: " + filename);
	            return null;
	        } // end try-catch

	        textArray = new String[size];

	        try {
	            fileIn = new FileReader(filename);
	        } catch (FileNotFoundException e) {
	            System.out.println("Unable to open file: " + filename);
	            return null;
	        } // end try-catch
	        fileInput = new Scanner(fileIn);

	        while (fileInput.hasNextLine()) {
	            line = fileInput.nextLine();
	            textArray[lineCount] = line;
	            lineCount++;
	        } // end while

	        try {
	            fileIn.close();
	        } catch (IOException e) {
	            System.out.println("Unable to close file: " + filename);
	            return null;
	        } // end try-catch

	        return textArray;

	    } // end saveText
}

