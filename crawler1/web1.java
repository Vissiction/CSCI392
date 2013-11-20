import java.net.*;
import java.io.*;

public class web1 
{
   public static void main(String[] args)
   {
     System.out.println("Starting Web tester");

     try
     {
       // connect to the server
       Socket sock = new Socket ("faculty.winthrop.edu",80);

       // get the reading and writing streams
       InputStream sin = sock.getInputStream();
       BufferedReader fromServer = new BufferedReader(new InputStreamReader(sin));
       OutputStream sout = sock.getOutputStream();
       PrintWriter toServer = new PrintWriter (new OutputStreamWriter(sout));

       // send the request to the server
       toServer.print ("GET /dannellys/default.htm HTTP/1.0\r\n\r\n");
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
