
package com.kamabod.tech;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

/**
 * Class handling output file
 * 
 * @author Kama
 *
 */
public class OutputFile {

	private File file = null;
	private BufferedWriter output = null;

	/**
	 * Method to create a file
	 */
	public void createFile() {

		String fileName = this.randomFileName();
		file = new File(fileName);
	}

	/**
	 * Method to generate random file name
	 * 
	 * @return random name
	 */
	private String randomFileName() {
		UUID uuid = UUID.randomUUID();
		return "kamila_" + uuid.toString() + ".txt";
	}

	/**
	 * Method to create a buffered writer output for the file
	 */
	public BufferedWriter getBufferedWriter() {
		try {
			// Create an output writer
			output = new BufferedWriter(new FileWriter(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
	}

	/**
	 * Method to close the file
	 */
	public void closeFile() {
		if (output != null) {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
