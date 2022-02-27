package core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Demone {
	
	ServerSocket socket;
	Socket incoming;
	
	public Demone(int port) throws IOException{
		socket = new ServerSocket(2000);
		incoming = null;
	}
	
	public void start() throws IOException{
		System.out.println("Server started");
		while(true){
			incoming = socket.accept();
			dispatch(incoming);
		}
	}
	
	private void dispatch(Socket incoming) throws IOException{
		System.out.println("New client connected");
		Thread t = new ThreadServer(incoming);
		t.start();
	}
	
	public static void main(String[] args){
		try {
			Demone server = new Demone(2000);
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}