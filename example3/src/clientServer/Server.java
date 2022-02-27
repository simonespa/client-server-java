package clientServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	ServerSocket socket;
	boolean alive;
	
	public Server(int port) {
		try {
			socket = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.err.println("Messaggio: impossibile istanziare il socket");
		}
		alive = true;
	}
	
	public void start() {
		System.out.println("Server started");
		Socket incoming = null;
		while( alive ) {
			try {
				incoming = socket.accept();
				System.out.println("New client accepted");
			} catch (IOException e) {
				e.printStackTrace();
			}
			Thread service = new ThreadServer(incoming);
			service.start();		
		}
	}
	
	public static void main(String[] args) {
		Server server = new Server(9000);
		server.start();
	}
	
	private class ThreadServer extends Thread {
		
		private BufferedReader socketInput;
		private PrintWriter socketOutput;
		private Socket socket;
		
		public ThreadServer(Socket socket) {
			this.socket = socket;
			try {
				socketInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			} catch (IOException e) {
				System.err.println(e.getMessage());
				System.err.println("Messaggio: impossibile aprire uno stream di input sul socket");
			}
			try {
				socketOutput = new PrintWriter(socket.getOutputStream());
			} catch (IOException e) {
				System.err.println(e.getMessage());
				System.err.println("Messaggio: impossibile aprire uno stream di output sul socket");
			}
		}

		@Override
		public void run() {
			boolean serviceContinue = true;
			String msg = "Inserisci una stringa";
			socketOutput.println(msg);
			socketOutput.flush();
			System.out.println("<Server says>");
			System.out.println(msg);
			System.out.println();
			String stringa = null;
			try {
				stringa = socketInput.readLine();
			} catch (IOException e) {
				System.err.println(e.getMessage());
				System.err.println("Errore durante la lettura sul socket");
			}
			System.out.println("<Client says>");
			System.out.println(stringa);
			System.out.println();
			stringa = stringa.toUpperCase();
			msg = "La stringa modificata e': " + stringa;
			socketOutput.print(msg);
			System.out.println("<Server says>");
			System.out.println(msg);
			System.out.println();
			socketOutput.flush();
		}
	}
	
}
