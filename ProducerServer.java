import java.util.*;
import java.io.*;
import java.util.concurrent.*;
import java.net.*;

public class ProducerServer {

	private static ServerSocket servSocket;
	private static final int PORT = 1234;
	private static LinkedList<Integer> beeHive;
	private static BufferedReader in;
	private static PrintWriter out;
	private static Random rand;
	private static int full;
	private static int level;
	private static int empty;

	//The producer portion of the problem
	static class Bee extends Thread {

		//called with .start()
		public void run() {

			try {

				while(true) {
					//Only allow one thread access at a time
					synchronized(ProducerServer.beeHive) {

						//Producer thread waits if buffer full
						while(ProducerServer.beeHive.size() == ProducerServer.full) {
							ProducerServer.beeHive.wait();
						}

						rand = new Random();
						Integer honeyProduced = rand.nextInt(1000);
						ProducerServer.beeHive.add(honeyProduced); //Add item to buffer
						ProducerServer.level++; //Increment item counter
						String message = "Produced some honey; Bees produced " + honeyProduced +
										  "; Bee hive size: " + ProducerServer.beeHive.size();
						ProducerServer.out.println(message); //Send message through socket to client
						ProducerServer.beeHive.notifyAll(); //Notify other sleeping threads
						int sleep = rand.nextInt(1000); //Get Random sleep time
						Thread.sleep(sleep); //Go to sleep for .5 second
					}
				}
				
			} catch(InterruptedException e) {
				System.out.println("Bees interrupted.");
			}
		}
	}

	//The consumer portion of the problem
	static class Bear extends Thread {

		//Called with .start()
		public void run() {

			try {

				while(true) {
					//Allow one thread access at a time
					synchronized(ProducerServer.beeHive) {

						//Consumer thread waits if buffer empty
						while(ProducerServer.beeHive.size() == 0) {
							ProducerServer.beeHive.wait();
						}

						Integer honeyConsumed = ProducerServer.beeHive.removeFirst(); //Pop first item
						ProducerServer.level--;
						String message = "Consuming honey; Bear ate " + honeyConsumed +
										  "; Bee hive size: " + ProducerServer.beeHive.size();
						ProducerServer.out.println(message); //Send message through socket to client
						ProducerServer.beeHive.notifyAll(); //Notify other sleeping threads
						int sleep = rand.nextInt(1000); //Get random sleep time
						Thread.sleep(sleep); //Go to sleep for 1 second
					}
				}
			} catch(InterruptedException e) {
				System.out.println("Bears interrupted.");
			}
		}
	}


	public static void main(String[] args) {

		System.out.println("Opening port...\n");
		try {

			servSocket = new ServerSocket(PORT); //Create server socket with PORT
			Socket link = servSocket.accept(); //Accept client connection
			System.out.println("New client connected\n");

			in = new BufferedReader(new InputStreamReader(link.getInputStream()));
			out = new PrintWriter(link.getOutputStream(), true);

			String beesIn = in.readLine(); //Read in user request for bees
			int numBees = Integer.parseInt(beesIn);

			String bearsIn = in.readLine(); //Read in user request for bears
			int numBears = Integer.parseInt(bearsIn);

			String hiveSize = in.readLine(); //Read in user request for buffer size
			int beeHiveSize = Integer.parseInt(hiveSize);

			beeHive = new LinkedList<Integer>(); //Create new Bounded Buffer
			full = beeHiveSize; //Max size of buffer
			level = 0; //Item counter
			empty = 0; //Zero holder

			//Generates the desired number of bee threads
			for(int i = 0; i < numBees; i++) {
				new Thread(new Bee()).start();
			}

			//Generates the desired number of bear threads
			for(int j = 0; j < numBears; j++) {
				new Thread(new Bear()).start();
			}

		} catch(IOException e) {
			System.out.println("Unable to listen to port: " + PORT);
			System.exit(1);
		}
	}
}