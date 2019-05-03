import java.util.concurrent.*;
import java.util.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;

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

			host = InetAddress.getLocalHost();

			link = new Socket(host, PORT);

			in = new BufferedReader(new InputStreamReader(link.getInputStream()));
			out = new PrintWriter(link.getOutputStream(), true);
			keyboard = new BufferedReader(new InputStreamReader(System.in));

			String message, response;
			//Allows the user to control the number of producer threads

			// System.out.print("Enter the number of bees desired: ");
			// message = keyboard.readLine();
			// out.println(message);
			//
			// //Allows the user to control the number of consumer threads
			// System.out.print("Enter the number of bears desired: ");
			// message = keyboard.readLine();
			// out.println(message);
			//
			// //Allow the user to control the buffer size
			// System.out.print("Enter the size of the bee hive: ");
			// message = keyboard.readLine();
			// out.println(message);

			ClientInterface ci = new ClientInterface();

			ci.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                out.println(ci.returnBees());
								out.println(ci.returnBears());
								out.println(ci.returnHives());
            }
        });

			do {
				response = in.readLine();
				ci.appendMessage(response);
				//ci.setCaretPosition(ci.getDocument().getLength());
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
