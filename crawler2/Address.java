/* **************************
* Will Connell
* Address class
* Stores information about an 
* HTML link
************************** */

import java.io.*;
import java.net.*;

public class Address {
	private String 		fullAddress = "";
	private String 		fileName = "";
	private String		path = "";
	private boolean 	isGood = false;
	private boolean 	isInternal = false;

	public Address() {
		// I don't even need to write this, honestly
	}

	public Address( String path, String address ) {
		this.path = path;
		fullAddress = address;

		if( isInternalLink( address ) ) {
			isInternal = true;
			fileName = address;

			if( isGoodLink( address ) ) {
				isGood = true;
			}
		}
	}

	// Private version of isInternalLink
	// Used to set private data
	private boolean isInternalLink( String address ) {
		if( address.matches( "https?://.+" ) ) {
            return false;
        } else {
            return true;
        }
	}

	// Public version of isInternalLink
	// Simply returns isInternal
	public boolean isInternalLink() {
		return isInternal;
	}

	// Private version of isGoodLink
	// Used to set private data
	// Only called if the link is internal
	private boolean isGoodLink( String address ) {
		boolean retVal = false;

		// Connect to server, get IO streams
		try {
        	Socket socket = new Socket( "faculty.winthrop.edu", 80 );
       		InputStream instream = socket.getInputStream();
        	BufferedReader fromServer = new BufferedReader( new InputStreamReader( instream ) );
        	OutputStream outstream = socket.getOutputStream();
        	PrintWriter toServer = new PrintWriter( new OutputStreamWriter( outstream ) );

        	// Send the request
        	toServer.print( "GET " + path + fileName + " HTTP/1.0\r\n\r\n" );
        	toServer.flush();

        	// Is it good?
        	String inline = fromServer.readLine();
        	if( inline.indexOf( "200" ) < 0 ) {
        	    retVal = true;
        	} else {
        	    retVal = false;
        	}
		} catch( Exception e ) {
            if( e.getMessage() != null ) {
                System.out.println( e.getMessage() );
				retVal = false;
            }
		}

		return retVal;
	}

	// Public version of isGoodLink
	// Just returns isGood
	public boolean isGoodLink() {
		return isGood;
	}

	// Resets values according to passed address
	public String set( String path, String address ) {
		this.path = path;
		fullAddress = address;

        if( isInternalLink( address ) ) {
            isInternal = true;
            fileName = address;

            if( isGoodLink( address ) ) {
                isGood = true;
            }
        }

		return fullAddress;
    }

	// This one's pretty self-explanatory
	public String getFullAddress() {
		return fullAddress;
	}

	// Also self-explanatory
	public String getFileName() {
		return fileName;
	}
}
