/* ************************
* Will Connell
* Test for histogram class
************************ */

import java.io.*;
import java.util.Scanner;

public class hw06_test
{
	public static void main(String[] args) throws Exception
	{
		// Open a reader for stdio
		InputStreamReader stdio = new InputStreamReader(System.in);
		BufferedReader instream = new BufferedReader(stdio);

		// Get the file name and open the file
		System.out.print("Enter the name of the file: ");
		String fileName = instream.readLine();

		FileReader freader = new FileReader(fileName);
		BufferedReader inFile = new BufferedReader(freader);
		
		// Count the number of lines in the file
		int lineCount = 0;

		Scanner lineCounter = new Scanner(new File(fileName));

		while(lineCounter.hasNextLine())
		{
			lineCounter.nextLine();
			lineCount++;
		}

		// Instantiate a new histogram and get ready to read
		verticalHistogram galactus = new verticalHistogram(lineCount);
		String nextLine = inFile.readLine();
		int nextValue;

		while(nextLine != null)
		{
			try
			{
				nextValue = Integer.parseInt(nextLine);
				galactus.addValue(nextValue);
			}
			catch(NumberFormatException nfe)
			{
				// Do nothing
			}

			nextLine = inFile.readLine();
		}

		inFile.close();
		galactus.write();
		return;
	}
}
