/* **********************
* Will Connell
* HW 10 - Crawler 2 class
* Project not working
********************** */

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Crawler2 {
	// Variables controlling depth of crawl
	// Max of 0 just prints root's links,
	// 1 is just root and children, etc.
	private int		maxDepth = 1;
	private int		currDepth = 0;

	// Path to local files
	private String	path = "";

	// Self-explanatory
	public String setPath( String inPath ) {
		path = inPath;
		return path;
	}

	// Returns the link address in inLine if one exists
	// Returns null otherwise
	private String isolateLink( String inLine ) {
		String address = "";
		int linkIndex = inLine.indexOf( "<a " );

		// If there's no anchor tag, linkIndex will be -1
		if( linkIndex >= 0 ) {
			// Start tearing the string apart
			linkIndex = inLine.indexOf( "href=\"" ) + 6;
			address = inLine.substring( linkIndex );
			linkIndex = address.indexOf( "\"" );
			address = address.substring( 0, linkIndex );

			return address;
		} else {
			// If there's no link
			return null;
		}
	}

	// Reads and prints the links in the page. Recursive.
	public void readPage( String fileName ) {
		currDepth++;
		ArrayList<Address> links = new ArrayList<Address>();
		
		try {
			// Connect to server, get IO streams
			Socket socket = new Socket( "faculty.winthrop.edu", 0 );
			InputStream instream = socket.getInputStream();
			BufferedReader fromServer = new BufferedReader( new InputStreamReader( instream ) );
			OutputStream outstream = socket.getOutputStream();
			PrintWriter toServer = new PrintWriter( new OutputStreamWriter( outstream ) );

			// Send the message off into the aether
			String outMsg = "GET " + path + fileName + " HTTP/1.0\r\n\r\n";
			toServer.print( outMsg );
			toServer.flush();

			// Read the response we get back
			String inLine = fromServer.readLine();
			Address address = new Address( path, isolateLink( inLine ) );
			while( inLine != null ) {
				// If there's a new internal link, add it to the ArrayList
				if( address.isInternalLink() ) {
					if( links.indexOf( address ) < 0 ) {
						links.add( address );
					}
				}

				// And on to the next iteration
				inLine = fromServer.readLine();
				address.set( path, isolateLink( inLine ) );
			}
		} catch( Exception e ) {
			if(e.getMessage() != null) {
                System.out.println(e.getMessage());
            }
		}

		// This page's output
		System.out.println( fileName + ":" );

		for( int i=0; i < links.size(); i++ ) {
			// Print the children
			if( links.get( i ).isGoodLink() ) {
				System.out.println( "\t" + links.get( i ).getFullAddress() );
			} else {
				System.out.println( "\t" + links.get( i ).getFullAddress() + " -- BAD LINK --" );
			}
		}

		System.out.println( " " );
			
		// Crawl the children, if applicable
		if( currDepth <= maxDepth ) {
			for( int i=0; i < links.size(); i++ ) {
				if( links.get( i ).isGoodLink() ) {
					readPage( links.get( i ).getFileName() );
				}
			}
		}

		currDepth--;
	}
}
