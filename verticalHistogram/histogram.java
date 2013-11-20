/* *************************
* Will Connell
* Histogram class
************************* */

import java.util.Arrays;

public class histogram
{
	protected int maxValues;		// Maximum size of values[]
	protected int values[];			// Will hold the count for each group
	protected int groupSize = 10;		// Size of each group
	protected int groupsToPrint = 0;	// Size of values[], up to the largest nonzero index

	public histogram(int maxValues)
	{
		this.maxValues = maxValues;
		values = new int[this.maxValues];
		Arrays.fill(values, 0);	// Initialize with all zeros
	}

	public histogram(int maxValues, int groupSize)
	{
		this.maxValues = maxValues;
		values = new int[this.maxValues];
		Arrays.fill(values, 0);
		this.groupSize = groupSize;
	}

	public void addValue(int newValue)
	{
		// If newValue = 5 and groupSize = 10, it contributes to index 0 for 0-9, for example
		int index = newValue/groupSize;
		if(values[index] == 0 && index+1 > groupsToPrint)
			groupsToPrint = index+1;	// Update groupsToPrint if necessary
		values[index]++;
	}

	public void write()
	{
		for(int i=0; i<groupsToPrint; i++)
		{
			System.out.print(Integer.toString(i*groupSize) + "-" +Integer.toString(i*groupSize + groupSize-1) + ":\t");
			for(int j=0; j<values[i]; j++)
			{
				System.out.print("*");
			}
			System.out.println(" ");
		}
	}
}
