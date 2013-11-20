/* **************
* Will Connell
* 9/12/2013
* HW03 - Playing with an array
************** */

import java.io.*;
import java.util.Arrays;

public class arrays
{
	public static void main( String[] args ) throws Exception
	{
		// Open a reader for stdio
		InputStreamReader stdio = new InputStreamReader( System.in );
		BufferedReader instream = new BufferedReader( stdio );
		
		// Get size of the array
		System.out.println( "Enter the size of the array: " );
		int count = 0;
		String input = instream.readLine();
		try
		{
			count = Integer.parseInt( input );
		}
		catch( NumberFormatException nfe )
		{
			System.out.println( "Input must be a number." );
			return;
		}
		if( count < 1 )
		{
			System.out.println( "Input must be > 0.");
			return;
		}
		
		// Make array and read input
		int inputArray[] = new int[count];
		System.out.println( "Enter " + Integer.toString( count ) + " number(s)" );
		for( int i=0; i<count; i++ )
		{
			input = instream.readLine();
			try
			{
				inputArray[i] = Integer.parseInt( input );
			}
			catch( NumberFormatException nfe )
			{
				System.out.println( "Input must be a number." );
				i--;
			}
		}
		
		// Make a sorted duplicate of our inputArray and compare it to inputArray
		int sortedArray[] = new int[count];
		System.arraycopy( inputArray, 0, sortedArray, 0, inputArray.length );
		Arrays.sort( sortedArray );
		if( Arrays.equals( inputArray, sortedArray ) )
		{
			System.out.println( "You entered those in order" );
		}
		else
		{
			System.out.println( "You did not enter those in order" );
		}
		
		return;
	}
}
