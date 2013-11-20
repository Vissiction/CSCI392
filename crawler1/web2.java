import java.net.*;
import java.io.*;

public class web2
{
   public static void main(String[] args)
   {
     System.out.println("Starting Web tester");

     if (args.length == 0)
     {
       System.out.println("please specify the file to retrieve, exiting...\n\n");
       System.exit(1);
     }

     try
     {
       // connect to the server
       Socket sock = new Socket ("faculty.winthrop.edu",80);

       // get the reading and writing streams
       InputStream sin = sock.getInputStream();
       BufferedReader fromServer = new BufferedReader(new InputStreamReader(sin));
       OutputStream sout = sock.getOutputStream();
       PrintWriter toServer = new PrintWriter (new OutputStreamWriter(sout));

       // build the request message
       String outmsg = "GET ";
       outmsg += args[0];
       outmsg += " HTTP/1.0\r\n\r\n";

       // send the request to the server
       toServer.print (outmsg);
       toServer.flush();

       // read the response
       String inputline = fromServer.readLine();
       while (inputline != null)
       {
         System.out.println (inputline);
         inputline = fromServer.readLine();
       } 
     }
     catch (Exception e)
     {
        System.out.println(e.getMessage());
     }
     System.out.println("Done with Web Tester");
   }
}
