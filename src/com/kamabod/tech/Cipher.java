package com.kamabod.tech;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

//'When I wrote this, only God and I understood what I was doing
//Now, God only knows' ;)

/**
 * Cipher class handles all cipher logic
 * 
 * @author Kama
 */
public class Cipher {

	/**
	 * Number of rows is entered by user
	 */
	private int maxrows = 0;

	/**
	 * Number of columns, column length is the size of a string plus offset
	 */
	private int maxcolumns = 0;

	/**
	 * Scanner will be used to read the user input
	 */
	private Scanner scanner = null;

	/**
	 * Constructor for cipher class
	 * 
	 * @param scanner dependency
	 */
	public Cipher(Scanner scanner) {
		// Set scanner in this class
		this.scanner = scanner;
	}

	/**
	 * Method to capture the user input values
	 * 
	 * @return array with key and offset
	 */
	public int[] enterDoubleKey() {
		int key1 = -1;

		// Prompt the user to enter number of row
		System.out.println("Enter the Number of rows:");

		// Assigning value entered by user to a variable
		key1 = this.readIntWithValidation(1, 99);

		// Prompt the user to enter the offset
		System.out.println("Enter the Offset:");

		int offset = -1;

		// Assigning value entered by user to a variable
		offset = this.readIntWithValidation(0, 99);

		// Create 1D array to capture the values entered by the user
		int[] myArray = new int[2];
		myArray[0] = key1;
		myArray[1] = offset;

		// Display the array
		System.out.println(Arrays.toString(myArray));

		return myArray;
	}

	/**
	 * Method to scan user input and do validations of the input
	 * 
	 * @param min minimum allowable value
	 * @param max maximum allowable value
	 * @return integer userInput
	 */
	private int readIntWithValidation(int min, int max) {
		int userInput = -1;

		// Using a do/while loop with try/catch block to read the integer entered by
		// user and do necessary validations
		do {
			try {
				// If the user input is valid, the application returns it
				userInput = scanner.nextInt();
			} catch (InputMismatchException exception) {
				// If the user input is an invalid character,
				// the invalid character is discarded
				scanner.next();
			}
			if (userInput < min || userInput > max) {
				// If the user input is invalid, info shows up on the screen
				System.out.println("Please enter a number between :" + min + "-" + max);
			}

		} while (userInput < min || userInput > max);

		return userInput;
	}

	/**
	 * This method has a shared code, it is used by other methods
	 * 
	 * 
	 * @param sharedText string that contains text
	 * @param key1       integer with number of rows
	 * @param offset     integer with an offset
	 * @param isDecrypt  boolean checking if decrypt method is used
	 * @return cipherArray
	 */
	private char[][] createPattern(String sharedText, int key1, int offset, boolean isDecrypt) {
		// No of rows 
		maxrows = key1;

		// Column length is the size of string plus offset
		maxcolumns = sharedText.length() + offset;

		// Boolean to check if we are going down or up on the matrix
		boolean checkDown = true;

		// Create 2D array
		char[][] cipherArray = new char[maxrows][maxcolumns];
		int r = 0;
		// For loop to create  matrix
		for (int c = 0; c < maxcolumns; c++) {
			// If decrypt method is used we assign * to a position in a pattern, if other
			// methods are used we assign a char
			if (c > offset - 1) {
				cipherArray[r][c] = isDecrypt ? '*' : sharedText.charAt(c - offset);
			}
			// If we go down row index goes up, if we go up row index goes down
			if (checkDown == true) {
				++r;
			} else {
				r--;
			}
			// If we reach max rows, we stop going down and start going up
			if (r == maxrows) {
				checkDown = false;
				r = r - 2;
			}
			// If we reach row 0 we stop going up and start going down
			if (r == 0) {
				checkDown = true;
			}
		}
		return cipherArray;
	}

	/**
	 * Method to encrypt plain text, uses shared method to generate a pattern
	 * 
	 * @param plainText string containing text from file or url
	 * @param key1      integer with a number of rows
	 * @param offset    integer with offset
	 * @return encrypted text
	 */
	public String encrypt(String plainText, int key1, int offset) {
		char[][] cipherArray = this.createPattern(plainText, key1, offset, false);
		String cipherText = this.getCipherText(cipherArray);
		return cipherText;
	}

	/**
	 * Method to print matrix, uses shared method, we can print on the screen or in
	 * the file, uses 2 for loops and if/else statement
	 * 
	 * @param plainText string containing test from file or url
	 * @param key1      integer with a number of rows
	 * @param offset    integer with offset
	 * @param output    BufferedWriter object used in the output file
	 */
	public void printMatrix(String plainText, int key1, int offset, BufferedWriter output) {
		char[][] matrix = this.createPattern(plainText, key1, offset, false);

		for (int r = 0; r < maxrows; r++) {
			for (int c = 0; c < maxcolumns; c++) {
				if (matrix[r][c] == Character.MIN_VALUE) {
					// if char not there print '_'
					writeStuff('_', output);
				} else {
					// otherwise print char
					writeStuff(matrix[r][c], output);
				}
			}
			writeStuff('\n', output);
		}
	}

	/**
	 * Method to write on the screen or in a file
	 * 
	 * @param character char to be displayed
	 * @param output    BufferedWriter object for output file
	 */
	private void writeStuff(char character, BufferedWriter output) {
		if (output != null) {
			// Writing in a file, using try/catch block to catch exceptions
			try {
				output.write(character);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			// Writing on the screen
			System.out.print(character);
		}
	}

	/**
	 * Method to get encrypted text, it reads matrix and builds a string containing
	 * encrypted text
	 * 
	 * @param matrix 2D array with a pattern
	 * @return string with encrypted text
	 */
	private String getCipherText(char[][] matrix) {
		String cipherText = null;
		StringBuilder stringBuilder = new StringBuilder();
		char ch = Character.MIN_VALUE;

		// 2 for loops, read array, get chars and put them into stringBuilder
		for (int r = 0; r < maxrows; r++) {
			for (int c = 0; c < maxcolumns; c++) {
				if (matrix[r][c] != Character.MIN_VALUE) {
					ch = (char) (matrix[r][c]);
					stringBuilder.append(ch);
				}
			}
		}
		System.out.println();

		// Convert stringBuilder to string and assign it to variable
		cipherText = stringBuilder.toString();

		// Print encrypted text
		System.out.println(cipherText);

		return cipherText;
	}

	/**
	 * Method to decipher an encrypted text
	 * 
	 * @param cipherText encrypted text in a string
	 * @param key1       number of rows
	 * @param offset     value of offset
	 * @return string plainText
	 */
	public String decrypt(String cipherText, int key1, int offset) {
		int r = 0;
		int cipherTextIndex = 0;
		char[][] matrix = this.createPattern(cipherText, key1, offset, true);

		// 2 for loops, to decrypt we replace * for a text received in input
		for (r = 0; r < maxrows; r++) {
			for (int c = 0; c < maxcolumns; c++) {
				if (matrix[r][c] == '*' && cipherTextIndex < cipherText.length()) {
					matrix[r][c] = cipherText.charAt(cipherTextIndex++);
				}
			}
		}

		// Get plain text and return it
		return this.getPlainText(matrix, key1, offset);
	}

	/**
	 * Method to get plain text from matrix
	 * 
	 * @param matrix 2D array
	 * @param key1   number of rows
	 * @param offset value of offset
	 * @return string plaintText
	 */
	private String getPlainText(char[][] matrix, int key1, int offset) {
		StringBuilder sb = new StringBuilder();
		char ch = Character.MIN_VALUE;
		maxrows = matrix.length;
		maxcolumns = matrix[0].length;
		boolean checkDown = true;
		int r = 0;

		// For loop and if/else statements to read matrix and get plain text
		for (int c = 0; c < maxcolumns; c++) {

			if (matrix[r][c] != Character.MIN_VALUE) {
				ch = (char) (matrix[r][c]);
				sb.append(ch);
			}

			if (checkDown == true) {
				++r;
			} else {
				r--;
			}

			if (r == maxrows) {
				checkDown = false;
				r = r - 2;
			}

			if (r == 0) {
				checkDown = true;
			}
		}

		String plainText = sb.toString();

		System.out.println(plainText);

		return plainText;
	}
}