/* *********************
* Will Connell
* Contains main for HW08
********************** */

import java.io.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;

public class hw08_test {
	public static void main(String[] args) throws IOException {	// If we can't read from keyboard, just vomit all over the user
		// Info to give to greenscreen
		String inline = "";
		String fgName = "";
		String bgName = "";
		int tolerance = 0;
		int x = 0;
		int y = 0;

		greenscreen galactus = new greenscreen();

		// Open a reader for keyboard
		InputStreamReader keyboard = new InputStreamReader(System.in);
		BufferedReader instream = new BufferedReader(keyboard);

		// Get file names
		while(true) {
			System.out.print("Enter name of foreground image (must be .jpg): ");
			fgName = instream.readLine();
			System.out.print("Enter name of background image (must be .jpg): ");
			bgName = instream.readLine();

			// Check input validity
			if(galactus.setImages(fgName, bgName)) {
				break;
			} else {
				// If images didn't load, user may want to quit and make sure
				// his files exist/work/etc.
				System.out.print("Images failed to load. Try again (y/Y)? ");
				if(instream.readLine() != "y" && instream.readLine() != "Y") {
					return;
				}
			}
		}

		// Get sample
		while(true) {
			while(true) {
				System.out.print("Enter x-coordinate of desired sample pixel (top-left corner is (0,0)): ");
				inline = instream.readLine();
				try {	// Check validity
					x = Integer.parseInt(inline);
					break;
				} catch(NumberFormatException e) {	// If invalid, try again
					System.out.println("Input must be an integer.");
				}
			}
			while(true) {
				System.out.print("Enter y-coordinate of desired sample pixel: ");
				inline = instream.readLine();
				try {	// Check validity
					y = Integer.parseInt(inline);
					break;
				} catch(NumberFormatException e) {	// If invalid, try again
					System.out.println("Input must be an integer.");
				}
			}
			if(galactus.setSample(x, y)) {	// Check validity
				break;
			} else {	// If invalid, user may wish to exit to check file dimensions
				System.out.print("Coordinates are out-of-bounds. Try again (y/Y)? ");
				if(instream.readLine() != "y" && instream.readLine() != "Y") {
					return;
				}
			}
		}

		// Get tolerance
		while(true) {
			System.out.print("Enter tolerance (higher is more lenient): ");
			inline = instream.readLine();
			try {	// Check validity
				tolerance = Integer.parseInt(inline);
				galactus.setTolerance(tolerance);
				break;
			} catch(NumberFormatException e) {	// If invalid, try again
				System.out.println("Input must be an integer.");
			}
		}

		// Greenscreen that thing
		BufferedImage screenedImage = galactus.screen();

		// Ask for file to save as
		while(true) {
			System.out.print("Enter name of file to save as (must be .jpg): ");
			inline = instream.readLine();
			if(inline.indexOf(".jpg") >= 1) {
				break;
			} else {
				System.out.println("Input was not .jpg.");
			}
		}

		// Save file
		File outfile = new File(inline);
		try {
			ImageIO.write(screenedImage, "jpg", outfile);
		} catch(IOException e) {
			System.out.println("Error saving image.");
		}
	}
}
