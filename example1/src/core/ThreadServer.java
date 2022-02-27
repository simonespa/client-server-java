package core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ThreadServer extends ClientServerModel {

	private InputStream in;
	private OutputStream out;
	Socket socket;
	
	public ThreadServer(Socket incoming) throws IOException{
		socket = incoming;
		in = socket.getInputStream();
		out = socket.getOutputStream();
	}

	@Override
	public void run() {
		try{
			String stringa = readFromSocket(socket);
			while(!stringa.equalsIgnoreCase(">QUIT")){
				System.out.println(stringa);
				readFromSocket(socket);
				stringa = readFromSocket(socket);
			}
			try {
				socket.close();
				System.out.println("Client disconnected");
			} catch (IOException e) {}
		}catch( NullPointerException e ){
			try {
				socket.close();
			} catch (IOException e1) {
			}
		}
	}
	
}