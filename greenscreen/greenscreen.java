/* **********************
* Will Connell
* Green screen class
* Assignment 8
********************** */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;

public class greenscreen {
	// Data
	BufferedImage 	foreground = null;
	BufferedImage 	background = null;
	int		tolerance = 20;
	int		sampleRGB = 0;

	// Constructors
	greenscreen() {
		// Everything stays the same
		// Why did I even type this?
	}

	greenscreen(int tolerance) {	// Overrides tolerance
		setTolerance(tolerance);
	}

	greenscreen(String fgName, String bgName) {	// Load images
		foreground = loadImage(fgName);
		background = loadImage(bgName);
	}

	greenscreen(String fgName, String bgName, int x, int y) {	// Load images & sample
		setImages(fgName, bgName);
		setSample(x, y);
	}

	greenscreen(int tolerance, String fgName, String bgName,	// Load, sample, & override tolerance
			int x, int y) {
		setTolerance(tolerance);
		setImages(fgName, bgName);
		setSample(x, y);
	}

	// Protected methods
	protected BufferedImage loadImage(String file) {
		// Loads an image
		BufferedImage image = null;

		try {
			image = ImageIO.read(new File(file));
		} catch(IOException e) {
			System.out.println("Eror opening image: " + file);
			return null;
		}

		// Wait for image to load
		MediaTracker tracker = new MediaTracker(new Component() {});
		tracker.addImage(image, 0);
		try {
			tracker.waitForID(0);
		} catch(InterruptedException e) {}

		return image;
	}

	// Public methods
	public void setTolerance(int tolerance) {	// Sets tolerance
		this.tolerance = tolerance;
	}

	public boolean setSample(int x, int y) {
		if(x >= 0 && x < foreground.getWidth()) {
			if(y >= 0 && y < foreground.getHeight()) {
				sampleRGB = foreground.getRGB(x, y);
				return true;
			}
		}
		return false;
	}

	public boolean setImages(String fgName, String bgName) {	// Sets images, true if successful
		setForeground(fgName);
		setBackground(bgName);

		if(!testImages()) {
			return false;
		} else {
			return true;
		}
	}

	public boolean setForeground(String fgName) {	// Sets foreground, true if successful
		foreground = loadImage(fgName);

		if(foreground == null) {
			return false;
		} else {
			return true;
		}
	}

	public boolean setBackground(String bgName) {	// Sets background, true if successful
		background = loadImage(bgName);

		if(background == null) {
			return false;
		} else {
			return true;
		}
	}

	public boolean testImages() {	// Makes sure that images loaded correctly
		if(foreground == null || background == null) {
			return false;
		} else {
			return true;
		}
	}

	public BufferedImage screen() {		// Returns the greenscreened image
		// Will hold RGB values at a particular pixel
		int xyRGB;
		int alpha;
		int red;
		int green;
		int blue;

		// RGB values of our sample
		int sampleAlpha = (sampleRGB >> 24) & 0xFF;
		int sampleRed = (sampleRGB >> 16) & 0xFF;
		int sampleGreen = (sampleRGB >> 8) & 0xFF;
		int sampleBlue = sampleRGB & 0xFF;

		// Total difference between two colors
		int totalDiff = 0;

		// Was the last pixel replaced?
		boolean leftReplaced = false;

		// Iterate through each pixel
		for(int y=0; y<foreground.getHeight(); y++) {
			for(int x=0; x<foreground.getWidth(); x++) {
				xyRGB = foreground.getRGB(x, y);
				alpha = (xyRGB >> 24) & 0xFF;
				red = (xyRGB >> 16) & 0xFF;
				green = (xyRGB >> 8) & 0xFF;
				blue = xyRGB & 0xFF;

				// If we just did xyRGB - sampleRGB,
				// Alpha differences would be weighted more than red, etc.
				totalDiff = Math.abs(sampleAlpha-alpha) + 
					Math.abs(sampleRed - red) + 
					Math.abs(sampleGreen - green) + 
					Math.abs(sampleBlue - blue);

				if(leftReplaced) {
					if(totalDiff < tolerance) {
						foreground.setRGB(x, y, background.getRGB(x, y));
						leftReplaced = true;
					} else {
						leftReplaced = false;
					}
				} else {
					if(1.5*totalDiff < tolerance) {
						foreground.setRGB(x, y, background.getRGB(x, y));
						leftReplaced = true;
					} else {
						leftReplaced = false;
					}
				}
			}
		}

		return foreground;
	}
}
