package GUI;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class CentralServer {
	private static final ArrayList<Business> Businesses = new ArrayList<>();

	public static void main(String[] args) {
		int counter = 1;
		ServerSocket serversocket = null;
		
		try {
			serversocket = new ServerSocket(9999);
		}
		catch (IOException e)
		{
		System.err.println("Could not listen on port: 9999.");
		System.exit(1);
		}
		while(true)
		{
			Socket clientSocket = null;	
			try {
				clientSocket = serversocket.accept();
				System.out.println("New client connected: " + counter++ );
				ConnectionHandler handler = new ConnectionHandler(clientSocket,Businesses); 
				new Thread(handler).start();
			} 
			catch (IOException e) {
				System.err.println("Accept failed.");
				System.exit(1);
				}
		}
	}
}
