/**
 * @author Simone Spaccarotella
 */
package prova;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Questa classe funge da demone in ascolta su una porta.
 * Quando un client si connette a tale porta, il demone
 * delega la gestione del client ad un thread da lui creato
 */
public class Server {

	private ServerSocket socket;
	private int port;
	
	/**
	 * Costruttore
	 * @param port
	 */
	public Server(int port){
		this.port = port;
		try {
			socket = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println("Server (Costruttore): creazione del socket fallita - line 16");
		}
	}
	
	public void start(){
		System.out.println("Server started");
		while(true){
			try {
				Socket incoming;
				incoming = socket.accept();
				System.out.println("New client connected");
				BufferedReader inputSocket;
				inputSocket = new BufferedReader(new InputStreamReader(incoming.getInputStream()));
				PrintWriter outputSocket;
				outputSocket = new PrintWriter(incoming.getOutputStream());
				Thread t = new ThreadServer(incoming,inputSocket,outputSocket);
				t.start();
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Server (metodo start): problema nell'inizializzazione del thread");
			}
		}
	}
	
	public static void main(String[] args){
		Server server = new Server(8000);
		server.start();
	}
	
}