package com.kamabod.tech;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Runner class
 * 
 * @author Kama
 *
 */
public class Runner {

	/**
	 * Define class variables
	 */
	static FileHandler fileHandler = null;
	static String inputPath = null;
	static String plainText = null;
	static Cipher cipher = null;
	static int[] doubleKey = null;
	static String cipherText = null;
	static String decipherText = null;
	static Scanner scanner = null;
	static char[][] matrix = null;

	/**
	 * 
	 * Main method
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		int choice = 0;

		scanner = new Scanner(System.in);
		cipher = new Cipher(scanner);
		fileHandler = new FileHandler(scanner);
		Menu m = new Menu();

		// Loop to display menu and enable user to select an option from the menu, uses
		// try/catch statement and validation
		while (choice != 6) {

			try {
				// We display menu, prompt user to select an option, scan the input, depending
				// on the user choice the selected option is displayed
				m.displayMenu();
				System.out.println("Select option:");
				choice = scanner.nextInt();
				switchMethod(choice);
			} catch (InputMismatchException exception) {
				// Validation, if the selected option is invalid the below prompt comes up and
				// the invalid char is discarded
				System.out.println("Invalid option. Please select a number between 1-6.");
				scanner.next();
			}
		}

		/**
		 * Closing scanner to avoid resource leak
		 */
		scanner.close();

		/**
		 * Printing on the screen
		 */
		System.out.println("End of the program");
	}

	/**
	 * Method to switch between menu options depending on user input
	 * 
	 * @param choice value entered by user
	 * @throws IOException When file/url not found
	 */
	private static void switchMethod(int choice) throws IOException {

		switch (choice) {
		case 1:
			// Option to select a file or an url, then they are assigned to variables
			System.out.println("Option 1 file or url");
			inputPath = fileHandler.userInput();
			plainText = fileHandler.readText(inputPath);
			break;
		case 2:
			// Option to enter a double key, including no of rows and offset, result is
			// assigned to a variable
			System.out.println("Option 2 key");

			// Validation of user entry
			if (inputPath == null) {
				System.out.println("Please select a file or url first.");
			} else {
				doubleKey = cipher.enterDoubleKey();
			}
			break;
		case 3:
			// Option to encrypt plain text, encrypted text is captured in a variable
			System.out.println("Encrypt selected");

			// Validation of user entry
			if (plainText == null) {
				System.out.println("Please select a file or url first");
			} else if (doubleKey == null) {
				System.out.println("Please enter a double key first");
			} else {
				cipherText = cipher.encrypt(plainText, doubleKey[0], doubleKey[1]);
			}
			break;
		case 4:
			// Option to decipher encrypted text, result captured into a variable
			System.out.println("Decrypt selected");

			// Validation of user entry
			if (plainText == null) {
				System.out.println("Please select a file or url first");
			} else if (doubleKey == null) {
				System.out.println("Please enter a double key first");
			} else if (cipherText == null) {
				System.out.println("Please select option  3 first");
			} else {
				decipherText = cipher.decrypt(cipherText, doubleKey[0], doubleKey[1]);
			}
			break;
		case 5:
			
			System.out.println("Display selected");

			// Validation of user entry
			if (plainText == null) {
				System.out.println("Please select a file or url first");
			} else if (doubleKey == null) {
				System.out.println("Please enter a double key first");
			} else if (cipherText == null) {
				System.out.println("Please select option 3 first");
			} else if (decipherText == null) {
				System.out.println("Please select option 4 first");
			} else {
				// Print plainText on the screen
				System.out.println("Plain Text:" + plainText);

				// Display matrix on the screen
				cipher.printMatrix(plainText, doubleKey[0], doubleKey[1], null);

				// Print cipherText on the screen
				System.out.println("Cipher Text:" + cipherText);

				// Print in the file
				OutputFile outputfile = new OutputFile();
				// Create a new file
				outputfile.createFile();

				// BufferedWriter output for file
				BufferedWriter output = outputfile.getBufferedWriter();

				// Output matrix in the file
				cipher.printMatrix(plainText, doubleKey[0], doubleKey[1], output);

				// Output plain and cipherText in the file
				output.write("\nPlain text:" + plainText);
				output.write("\nCipher text:" + cipherText);

				// Close the file
				outputfile.closeFile();
			}
			break;
		case 6:
			// Option to exit the program
			System.out.println("Quit selected");
			break;
		default:
			// If the selected option is invalid, this prompt comes up
			System.out.println("Invalid option.Please select a number between 1-6.");
			break;
		}
	}
}