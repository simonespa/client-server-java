package prova;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	BufferedReader inputSocket;
	PrintWriter outputSocket;
	Socket socket;
	
	/**
	 * Costruttore che alloca il socket di comunicazione
	 * @param host
	 * @param port
	 * @exception UnknownHostException, IOException
	 */
	public Client(String host, int port){
		try {
			socket = new Socket(host,port);
			inputSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outputSocket = new PrintWriter(socket.getOutputStream());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Connette il client al server mediante l'indirizzo e la porta passati al costruttore
	 * @return void
	 * @exception IOException
	 */
	public void start(){
		System.out.println("Client started");
		String msg;
		Scanner console = new Scanner(System.in);
		boolean run = true;
		while(run){
			try {
				// Server says: inserisci la stringa da processare
				msg = inputSocket.readLine();
				System.out.println(msg);
				msg = console.nextLine();
				outputSocket.println(msg);
				outputSocket.flush();
				
				// Server response: <stringa processata>
				msg = inputSocket.readLine();
				System.out.println(msg);
				
				// Server says: vuoi continuare? (SI/NO)
				msg = inputSocket.readLine();
				System.out.println(msg);
				msg = console.nextLine();
				outputSocket.println(msg);
				outputSocket.flush();
				if( msg.equalsIgnoreCase("NO")){
					msg = inputSocket.readLine();
					System.out.println(msg);
					run = false;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args){
		Client client = new Client("localhost",8000);
		client.start();
	}
	
}
