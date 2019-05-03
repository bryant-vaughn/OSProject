import java.util.concurrent.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class ConsumerClient {

	private static boolean done = false;

	private static InetAddress host;
	private static final int PORT = 1234;
	private static Socket link;
	private static BufferedReader in;
	private static PrintWriter out;
	private static BufferedReader keyboard;

	public static void main(String[] args) {
		try {

			//Set host to "localhost"
			host = InetAddress.getLocalHost();

			//Create a socket object using localhost:1234
			link = new Socket(host, PORT);

			//Creates the input and output streams between client and server
			in = new BufferedReader(new InputStreamReader(link.getInputStream()));
			out = new PrintWriter(link.getOutputStream(), true);
			keyboard = new BufferedReader(new InputStreamReader(System.in));

			String message, response;

			//Allows the user to control the number of producer threads
			System.out.print("\nEnter the number of bees desired: ");
			message = keyboard.readLine();
			out.println(message);

			//Allows the user to control the number of consumer threads
			System.out.print("\nEnter the number of bears desired: ");
			message = keyboard.readLine();
			out.println(message);

			//Allow the user to control the buffer size
			System.out.print("\nEnter the size of the bee hive: ");
			message = keyboard.readLine();
			out.println(message);

			//This loop allows the reading of messages from server
			do {
				response = in.readLine();
				System.out.println("\n" + response);
			} while(true);
			
		} catch(UnknownHostException e) {
			System.out.println("Host not found!");
			System.exit(1);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}