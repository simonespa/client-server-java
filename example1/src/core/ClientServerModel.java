package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class ClientServerModel extends Thread {

	InputStream in;
	OutputStream out;
	BufferedReader input;
	PrintWriter output;
	
	public String readFromSocket(Socket incoming){
		try {
			in = incoming.getInputStream();
			input = new BufferedReader(new InputStreamReader(in));
			return input.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public void writeToSocket(Socket incoming, String msg){
		try {
			out = incoming.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		output = new PrintWriter(out);
		output.write(msg);
	}

}
