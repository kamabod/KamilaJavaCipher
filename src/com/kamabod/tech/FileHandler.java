package com.kamabod.tech;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

/**
 * FileHandler class handles files
 * 
 * @author Kama
 *
 */
public class FileHandler {

	/**
	 * Scanner will be used to read the user input
	 */
	private Scanner scanner = null;

	/**
	 * Class constructor
	 * 
	 * @param scanner dependency
	 */
	public FileHandler(Scanner scanner) {
		this.scanner = scanner;
	}

	/**
	 * Method to capture user input into a string
	 * 
	 * @implNote Input example:
	 *           https://raw.githubusercontent.com/kamabod/phonetech/master/README.md
	 * @implNote Input example: D:\test.txt
	 * @return string with inputPath
	 */
	public String userInput() {
		// Prompt user to enter file/url
		System.out.println("Enter file or url path:");

		// Return scan user input
		return scanner.next();
	}

	/**
	 * Method to read file or url entered by user, catches sources into stream,
	 * converts to readers and reads the contents
	 * 
	 * @param path string with file/url path
	 * @return string with file/url contents
	 * @throws IOException exception if file/url is not found
	 */
	public String readText(String path) throws IOException {
		InputStream inStream = null;
		StringBuilder response = new StringBuilder();

		// Use embedded try/catch statements
		try {
			// Load file contents into inputStream
			inStream = new FileInputStream(path);
		} catch (Exception e) {
			try {
				// Load url contents into inputStream
				inStream = new URL(path).openStream();
			} catch (Exception e1) {
				// Throw exception if file or url is not found
				System.out.println("File or URL not found. Please enter file or URL path again.");
			}
		}

		// Check if inputStream has contents
		if (inStream != null) {
			// If stream has contents, we read it using a reader object
			BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));

			String inputLine = null;

			// While loop to read contents, creates a stringBuilder, prints it
			// converting to string
			while ((inputLine = reader.readLine()) != null) {
				response.append(inputLine);
				System.out.println(response.toString());
			}
			// Closing reader to avoid resource leak
			reader.close();
		}

		return response.toString();
	}
}