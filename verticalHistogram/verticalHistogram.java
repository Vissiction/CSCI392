/* *********************
* Will Connell
* 9/26/2013
* Vertical histogram class
* extends histogram
********************* */

public class verticalHistogram extends histogram
{
	private int maxCount = 0;	// Count for the biggest category

	public verticalHistogram(int maxValues)
	{
		super(maxValues);
	}

	public verticalHistogram(int maxValues, int groupSize)
	{
		super(maxValues, groupSize);
	}

	public void addValue(int newValue)
	{
		// If newValue = 5 and groupSize = 10, it contributes to index 0 for 0-9, for example
                int index = newValue/groupSize;

                if(values[index] == 0 && index+1 > groupsToPrint)
                        groupsToPrint = index+1;        // Update groupsToPrint if necessary

		values[index]++;

		if(values[index] > maxCount)
			maxCount = values[index];	// Update maxCount if necessary
	}

	public void write()
	{
		// Prints a vertical histogram
		for(int i=maxCount; i>0; i--)
		{
			// Iterate through rows of histogram
			for(int j=0; j<groupsToPrint; j++)
			{
				// Iterate through columns
				if(values[j] >= i)
				{
					System.out.print("*\t");
				}
			}
			System.out.println(" ");	// End the line
		}

		for(int i=0; i<groupsToPrint; i++)
		{
			// Print categories
			System.out.print(Integer.toString(i*groupSize) + "-" +Integer.toString(i*groupSize + groupSize-1) + "\t");
		}
		System.out.println(" ");
	}
}
