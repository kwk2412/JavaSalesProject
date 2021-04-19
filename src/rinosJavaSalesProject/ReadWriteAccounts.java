package rinosJavaSalesProject;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ReadWriteAccounts {

	private static BufferedReader openRead() {
		Frame f = new Frame();
		// decide from where to read the file
		FileDialog foBox = new FileDialog(f, "Pick location for reading your file", FileDialog.LOAD);
		System.out.println(
				"The dialog box will appear behind Eclipse.  " + "\n   Choose where you would like to read from.");
		foBox.setVisible(true);
		// get the absolute path to the file
		String foName = foBox.getFile();
		String dirPath = foBox.getDirectory();

		// create a file instance for the absolute path
		File inFile = new File(dirPath + foName);
		if (!inFile.exists()) {
			System.out.println("That file does not exist");
			System.exit(0);
		}
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(inFile));
		} catch (IOException e) {
			System.out.println("You threw an exception. ");
		}
		return in;
	}

	private static PrintWriter openWrite() {
		Frame f = new Frame();
		// decide from where to read the file
		FileDialog foBox = new FileDialog(f, "Pick location for writing your file", FileDialog.SAVE);
		System.out.println("The dialog box will appear behind Eclipse.  "
				+ "\n   Choose where you would like to write your data.");
		foBox.setVisible(true);
		// get the absolute path to the file
		String foName = foBox.getFile();
		String dirPath = foBox.getDirectory();

		// create a file instance for the absolute path
		File outFile = new File(dirPath + foName);
		PrintWriter out = null;

		try {
			out = new PrintWriter(outFile);
		} catch (IOException e) {
			System.out.println("You threw an exception");
		}
		return out;
	}

	// Inputs accounts from a text file
	public static void inputAccounts() {
		Scanner scan = new Scanner(System.in);

		BufferedReader bf = null;
		try {
			// Open the file.
			bf = openRead();

			// read in the first line
			String line = "";
			try {
				line = bf.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

			String delim = "|";

			// while there is more data in the file, process it
			while (line != null) { // more lines
				StringTokenizer st = new StringTokenizer(line, "|");
				String username = st.nextToken().trim();
				String password = st.nextToken().trim();
				int id = Integer.parseInt(st.nextToken().trim());
				String privileges = st.nextToken().trim().toLowerCase();
				if (privileges.equals("admin")) {
					new Admin(username, password, id, privileges);
				} else if (privileges.equals("customer")) {
					new Customer(username, password, id, privileges);
				}
				// read in the next line
				try {
					line = bf.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} // end of reading in the data.

		}
		// catch any other type of exception
		catch (Exception e) {
			System.out.println("Other weird things happened");
			e.printStackTrace();
		} finally {
			try {
				bf.close();
			} catch (Exception e) {
			}
		}
	}

	// Outputs accounts to a text file
	public static void outputAccounts() {
		PrintWriter out = null;
		try {
			out = openWrite();
			for (Account acc : Driver.accounts) {
				if (acc.getUserID() != 1) {
					out.print(acc.getUsername());
					out.print("|");
					out.print(acc.getPassword());
					out.print("|");
					out.print(acc.getUserID());
					out.print("|");
					out.println(acc.getPrivileges());
				}
			}
		} finally {
			try {
				out.close();
			} catch (Exception e) {
			}
		}
	}

}
