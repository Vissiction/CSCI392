/* ***************************
* Will Connell
* Tests the Crawler2 class
*************************** */

import java.io.*;
import java.net.*;

public class test {
	public static void main( String[] args ) {
		String fileName = "";
		String path = "";
		Crawler2 crawler = new Crawler2();


		if(args.length == 0) {
            System.out.println("Improper usage: java hw09 [path] [fileName]");
            System.exit(1);
        } else {
			path = args[0];
            fileName = args[1];
        }

		crawler.setPath( path );
		crawler.readPage( fileName );
	}
}
