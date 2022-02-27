package prova;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ThreadServer extends Thread {

	private Socket socket;
	private BufferedReader inputSocket;
	private PrintWriter outputSocket;
	
	public ThreadServer(Socket incoming, BufferedReader input, PrintWriter output){
		super();
		this.socket = incoming;
		this.inputSocket = input;
		this.outputSocket = output;
	}
	
	public void run(){
		boolean run = true;
		Scanner console = new Scanner(System.in);
		String msg;
		try {
			while( run ){
				outputSocket.println("Server says: Inserisci la stringa da processare");
				outputSocket.flush();
				System.out.println("Server says: Inserisci la stringa da processare");
				msg = inputSocket.readLine();
				System.out.println("Client response: "+msg);
				
				outputSocket.println("Server response: "+msg.toUpperCase());
				outputSocket.flush();
				System.out.println("Server response: "+msg.toUpperCase());
				
				outputSocket.println("Server says: Vuoi continuare? (SI/NO)");
				outputSocket.flush();
				System.out.println("Server says: Vuoi continuare? (SI/NO)");
				msg = inputSocket.readLine();
				System.out.println("Client response: "+msg);
				
				if(msg.equalsIgnoreCase("NO")){
					run = false;
					outputSocket.println("Server says: Goodbye");
					outputSocket.flush();
					System.out.println("Server says: Goodbye");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
