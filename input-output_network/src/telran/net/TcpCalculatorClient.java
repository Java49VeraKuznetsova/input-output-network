package telran.net;

import java.io.*;
import java.net.*;
import java.util.*;


import telran.view.*;

public class TcpCalculatorClient {

	
		private static final String HOST = "localHost";
		private static final int PORT = 5500;
		private static final String[] mathAct = {"+", "-", "*", "/"};

		public static void main(String[] args) throws Exception{
			try (Socket socket = new Socket(HOST, PORT);
					PrintStream writer = new  PrintStream(socket.getOutputStream());
					BufferedReader reader =
							new BufferedReader(new InputStreamReader(socket.getInputStream()))){
				    InputOutput io = new ConsoleInputOutput();
				    Menu menu = new Menu ("TCP client calculator", Item.of("Send request", io1 -> {
				    	HashSet<String> requests = new HashSet<>(Arrays.asList(mathAct));
				    	String requestType = io1.readString("Enter request type " + requests, HOST, requests);
				    	double operand1 = io1.readDouble("enter first number", "this isn't double a number");
				    	double operand2 = io1.readDouble("enter second number", "this isn't double a number");
				    	
				    	writer.println(String.format("%s#%s#%s", requestType, Double.toString(operand1), Double.toString(operand2)));
				    	try {
				    		String response = reader.readLine();
				    		io1.writeLine(response);
				    	} catch (IOException e) {
				    		throw new RuntimeException(e.toString());
				    	}
				    	
				    }), Item.ofExit());
				    menu.perform(io);
			}
			
			

		
	}

}
