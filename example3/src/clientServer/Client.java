package clientServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	private BufferedReader socketInput;
	private PrintWriter socketOutput;
	private Socket socket;
	
	public Client(String inetAddress, int port) {
		try {
			socket = new Socket(inetAddress, port);
		} catch (UnknownHostException e) {
			System.err.println(e.getMessage());
			System.err.println("Messaggio: impossibile contattare l'host specificato");
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.err.println("Messaggio: problemi di connessione");
		}
		try {
			socketInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		try {
			socketOutput = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void start() {
		try {
			String msg = socketInput.readLine();
			System.out.println(msg);
			Scanner scanner = new Scanner(System.in);
			msg = scanner.nextLine();
			socketOutput.println(msg);
			socketOutput.flush();
			msg = socketInput.readLine();
			System.out.println(msg);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		Client client = new Client("localhost", 9000);
		client.start();
	}
	
}
