/* **********************
* Will Connell
* Crawler #1
* Crawls a webpage and prints the links
********************** */

import java.net.*;
import java.io.*;

public class hw09 {
	public static void main(String[] args) {
		String destination = "faculty.winthrop.edu";
		String fileName = "";

		if(args.length == 0) {
			System.out.println("Improper usage: java hw09 [path]");
			System.exit(1);
		} else {
			fileName = args[0];
		}

		try {
			// Connect to server
			System.out.println("Connecting to server...\n");
			Socket socket = new Socket(destination, 80);

			// Get I/O streams
			InputStream instream = socket.getInputStream();
			BufferedReader fromServer = new BufferedReader(new InputStreamReader(instream));
			OutputStream outstream = socket.getOutputStream();
			PrintWriter toServer = new PrintWriter(new OutputStreamWriter(outstream));

			// Build the request and send it
			String outmsg = "GET " + args[0] + " HTTP/1.0\r\n\r\n";
			toServer.print(outmsg);
			toServer.flush();

			// Read response
			String inline = fromServer.readLine();
			String linkName = "";
			int linkIndex = inline.indexOf("<a ");
			while(inline != null && inline != "null") {
				// If there's a link, print it
				if(linkIndex >= 0) {
					linkIndex = inline.indexOf("href=\"");
					linkName = inline.substring(linkIndex+6);
					linkIndex = linkName.indexOf("\"");
					linkName = linkName.substring(0, linkIndex);

					System.out.println(linkName);
				}

				// And on to the next iteration
				inline = fromServer.readLine();
				linkIndex = inline.indexOf("<a ");
			}
		} catch(Exception e) {
			if(e.getMessage() != null) {
				System.out.println(e.getMessage());
			}
		}

		System.out.println("\nDone crawling.");
	}
}
