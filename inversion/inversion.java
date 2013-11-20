/* ***********************
* Will Connell
* Assignment 7
* Reads an image from command line
* and makes an inverted version of it
*********************** */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;
import java.util.Arrays;

public class inversion
{
	public BufferedImage readImage(String file)
	{
		// Loads an image
		BufferedImage image = null;

		try
		{
			image = ImageIO.read(new File(file));
		}
		catch(IOException e)
		{
			System.out.println("Error opening image.");
			return null;
		}

		MediaTracker tracker = new MediaTracker(new Component() {});
		tracker.addImage(image, 0);
		try
		{
			tracker.waitForID(0);
		}
		catch(InterruptedException e)
		{
		}

		return image;
	}

	public static void main(String[] args)
	{
		// Check input validity
		if(args.length != 1)
		{
			System.out.println("Improper usage: java inversion [filename].jpg");
			return;
		}
		if(!args[0].matches(".*\\.jpg"))
		{
			System.out.println("Improper usage: java inversion [filename].jpg");
			return;
		}
		
		// Load image from command line
		BufferedImage image = readImage(args[0]);
		if(image == null)
			return;

		// Image information
		int height = image.getHeight();
		int width = image.getWidth();
		int rgb;
		int alpha;
		int red;
		int green;
		int blue;

		// Iterate through each pixel
		for(int x = 0; x < width; x++)
		{
			for(int y = 0; y < height; y++)
			{
				// Retrieve this pixel's RGB info
				rgb = image.getRGB(x, y);
				alpha = ((rgb >> 24) & 0xff);
				red = ((rgb >> 16) & 0xff);
				green = ((rgb >> 8) & 0xff);
				blue = (rgb & 0xff);

				// Invert the colors
				red = 255-red;
				green = 255-green;
				blue = 255-blue;
				rgb = (alpha << 24) | (red << 16) | (green << 8) | blue;

				image.setRGB(x, y, rgb);
			}
		}

		// Write the BufferedImage
		File inverted = new File("inv_" + args[0]);
		try
		{
			ImageIO.write(image, "jpg", inverted);
		}
		catch(Exception e)
		{
			System.out.println("Error writing inverted image.");
		}
	}
}
