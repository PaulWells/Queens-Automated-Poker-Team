
/*
 * This is a "helper" utility class by Alan McLeod.
 * It contains robust methods that can be used to:
 * 		- obtain an int from the user
 * 		- obtain a double from the user
 * 		- obtain a String from the user
 * 		- save and write text tiles using String arrays.
 * The methods to obtain a number from the user can optionally
 * accept the following parameters:
 * 		- the low legal limit
 * 		- the high legal limit
 * 		- a String prompt
 */
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileReader;

public class IOHelper {

    private static Scanner screenInput = new Scanner(System.in);

    public static int getInt(int low, String prompt, int high) {

        int numFromUser = 0;
        String dummy;
        boolean numericEntryOK;

        do {
            System.out.print(prompt);
            numericEntryOK = false;
            try {
                numFromUser = screenInput.nextInt();
                numericEntryOK = true;
            } catch (InputMismatchException e) {
                dummy = screenInput.nextLine();
                System.out.println(dummy + " is not an integer!");
                numFromUser = low;
            } // end try-catch
            // Indicate to the user why he is being prompted again.
            if (numFromUser < low || numFromUser > high) {
                System.out.println("The number is outside the legal limits.");
            }
        } while (!numericEntryOK || numFromUser < low || numFromUser > high);

        return numFromUser;

    } // end full parameter getInt method

    public static int getInt() {

        int low = Integer.MIN_VALUE;
        int high = Integer.MAX_VALUE;
        String prompt = "Please enter any integer: ";

        return getInt(low, prompt, high);

    } // end no parameter getInt method

    public static int getInt(String prompt) {

        int low = Integer.MIN_VALUE;
        int high = Integer.MAX_VALUE;

        return getInt(low, prompt, high);

    } // end one parameter getInt method

    public static int getInt(int low, String prompt) {

        int high = Integer.MAX_VALUE;

        return getInt(low, prompt, high);

    } // end two parameter getInt method

    public static int getInt(String prompt, int high) {

        int low = Integer.MIN_VALUE;

        return getInt(low, prompt, high);

    } // end two parameter getInt method

    public static double getDouble(double low, String prompt, double high) {

        double numFromUser = 0;
        String dummy;
        boolean numericEntryOK;

        do {
            System.out.print(prompt);
            numericEntryOK = false;
            try {
                numFromUser = screenInput.nextDouble();
                numericEntryOK = true;
            } catch (InputMismatchException e) {
                dummy = screenInput.nextLine();
                System.out.println(dummy + " is not a double!");
                numFromUser = low;
            } // end try-catch
            // Indicate to the user why he is being prompted again.
            if (numFromUser < low || numFromUser > high) {
                System.out.println("The number is outside the legal limits.");
            }
        } while (!numericEntryOK || numFromUser < low || numFromUser > high);

        return numFromUser;

    } // end full parameter getDouble method

    public static double getDouble() {

        double low = -Double.MAX_VALUE;
        double high = Double.MAX_VALUE;
        String prompt = "Please enter any double: ";

        return getDouble(low, prompt, high);

    } // end no parameter getDouble method

    public static double getDouble(String prompt) {

        double low = -Double.MAX_VALUE;
        double high = Double.MAX_VALUE;

        return getDouble(low, prompt, high);

    } // end one parameter getDouble method

    public static double getDouble(double low, String prompt) {

        double high = Double.MAX_VALUE;

        return getDouble(low, prompt, high);

    } // end two parameter getDouble method

    public static double getDouble(String prompt, double high) {

        double low = -Double.MAX_VALUE;

        return getDouble(low, prompt, high);

    } // end two parameter getDouble method

    public static String getString(String prompt) {

        String userText;

        System.out.print(prompt);
        userText = screenInput.next();

        return userText;

    } // end one parameter getString method

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
} // end IOHelper class


