/* **********************
* Will Connell
* HW 10 - Crawler 2 class
********************** */

public class crawler2 {
	// Variables controlling depth of crawl
	// Max of 0 just prints root's links,
	// 1 is just root and children, etc.
	private int		maxDepth = 1;
	private int		currDepth = 0;

	private boolean isGoodLink( String fileName ) {		// True if server gives 200
		// Connect to server, get IO streams
		Socket socket = new Socket( "faculty.winthrop.edu" );
		InputStream instream = socket.getInputStream();
		BufferedReader fromServer = new BufferedReader( new InputStreamReader( instream ) );
		OutputStream outstream = socket.getInputStream();
		PrintWriter toServer = new PrintWriter( new OutputStreamWriter( outstream ) );

		// Send the request
		toServer.print( "Get " + fileName + " HTTP/1.0\r\n\r\n";
		toServer.flush();

		// Is it good?
		String inline = fromServer.readLine();
		if( inline.indexOf( "200" ) < 0 ) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isInternalLink( String address ) {	// Self-explanatory
		if( address.matches( "https?://.+" ) ) {
			return false;
		} else {
			return true;
		}
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
		ArrayList<String> links = new ArrayList<String>();
		
		// Connect to server, get IO streams
		Socket socket = new Socket( "faculty.winthrop.edu" );
		InputStream instream = socket.getInputStream();
		BufferedReader fromServer = new BufferedReader( new InputStreamReader( instream ) );
		OutputStream outstream = socket.getInputStream();
		PrintWriter toServer = new PrintWriter( new OutputStreamWriter( outstream ) );

		// Send the message off into the aether
		String outMsg = "GET " + fileName + " HTTP/1.0\r\n\r\n";
		toServer.print( outMsg );
		toServer.flush();

		try {
			// Read the response we get back
			String inLine = fromServer.readLine();
			String address = isolateLink( inLine );
			while( inLine != null ) {
				// If there's a new internal link, add it to the ArrayList
				if( address != null && isInternalLink( address ) ) {
					if( links.indexOf( address ) == 1 ) {
						links.add( address );
					}
				}

				// And on to the next iteration
				inLine = fromServer.readLine();
				isolateLink( inLine );
			}
		} catch( Exception e ) { }

		// Print child names
		for( int i=0; i < links.size(); i++ ) {
			if( isGoodLink( links.get( i ) ) {
				System.out.println( links.get( i ) );
			} else {
				System.out.println( links.get( i ) + " -- BAD LINK --" );
			}
		}
			
			
}
