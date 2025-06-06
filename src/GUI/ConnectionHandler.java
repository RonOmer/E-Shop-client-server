package GUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;


public class ConnectionHandler implements Runnable{
	private Socket socket;
	private final ArrayList<Business>Businesses;
	
	private String nameInput = "",itemInput = "",numberInput = "",amountInput = "";
 
	
	
	public ConnectionHandler(Socket socket, ArrayList<Business>Businesses) {
		this.socket = socket;
		this.Businesses = Businesses;
	}
	
	@Override
	public void run() {
		try {
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String inputLine;
			int outputLine;
			
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.equals("q")) break;
				
				splitData(inputLine);
				outputLine = processInput();
				out.println(outputLine);
			}
			out.close();
			in.close();
			socket.close();

		} catch (IOException e) {
			System.err.println("Problem connecting to server");
		}
	}
	
	public synchronized int processInput() {
		if(nameInput.isEmpty() || itemInput.isEmpty() || numberInput.isEmpty() || amountInput.isEmpty()) {
			return 200;
		}
		if(!isNumeric(numberInput) || !isNumeric(amountInput)) {
			return 202;
		}
		
		int businessNum = Integer.parseInt(numberInput);
		if(!(businessNum <= 99999 && businessNum >= 10000)) {
			return 202;
		}
		if(WrongName()) {
			return 201;
		}
		else {
			if(!SearchBusiness() && businessIndex() == -1) {
				Business b1 = new Business(businessNum,nameInput);
				Businesses.add(b1);
			}
			updateDetails();
			return 100;
		}
	}
	
	public void splitData(String input) {
		String [] inputs = input.split(",");
		nameInput = inputs[0].trim();
		numberInput = inputs[1].trim();
		itemInput = inputs[2].trim();
		amountInput = inputs[3].trim();
	}
	
	public boolean SearchBusiness() {
		if(!isNumeric(numberInput)) {
			return false;
		}
		int num = Integer.parseInt(numberInput);
		for(int i =0;i<Businesses.size();i++) {
			if(Businesses.get(i).getBusinessName().equals(nameInput) && Businesses.get(i).getBusinessNum() == num){
				return true;
			}
		}
		return false;
	}

	public boolean WrongName() {
		if(!isNumeric(numberInput)) {
			return false;
		}
		int num = Integer.parseInt(numberInput);
		for(int i =0;i<Businesses.size();i++) {
			if(!(Businesses.get(i).getBusinessName().equals(nameInput)) && Businesses.get(i).getBusinessNum() == num){
				return true;
			}
		}
		return false;
	}


	public int businessIndex() {
		if(!isNumeric(numberInput)) {
			return -1;
		}
		int num = Integer.parseInt(numberInput);
		
		for(int i =0;i<Businesses.size();i++) {
			if(Businesses.get(i).getBusinessNum() ==  num){
				return i;
			}
		}
		return -1;
	}
	
	public boolean isNumeric(String str) {
		 try {
			 Integer.parseInt(str.trim());
			 return true;
		 }catch(NumberFormatException e) {
			 return false;
		 }
	}
	
	public synchronized void updateDetails() {
		int index = businessIndex();
		int amount = Integer.parseInt(amountInput);
		
		if(itemInput.equals("Scarf")) {
			Businesses.get(index).setScarfCounter(amount);
			return;
		}
		if(itemInput.equals("Sun glasses")) {
			Businesses.get(index).setSunGlassCounter(amount);
			return;
		}
		if(itemInput.equals("Belt")) {
			Businesses.get(index).setBeltCounter(amount);
		}
	}
}
