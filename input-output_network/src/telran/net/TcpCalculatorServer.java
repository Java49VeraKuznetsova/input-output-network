package telran.net;

import java.io.*;
import java.net.*;
import java.util.*;



import telran.view.*;

public class TcpCalculatorServer {

	static final int PORT = 5500;

	public static void main(String[] args) throws Exception{
		ServerSocket serverSocket = new ServerSocket(PORT);
		System.out.println("Server is listening to port " + PORT);
		while (true) {
			Socket socket = serverSocket.accept();
			clientRun(socket);
		}
	}

	private static void clientRun(Socket socket) {
		try (BufferedReader reader =
				new BufferedReader
				(new InputStreamReader(socket.getInputStream()));
				PrintStream writer = new PrintStream(socket.getOutputStream()))	{
			while(true) {
				String line = reader.readLine();
				if (line == null) {
					System.out.println("client closed normally connection");
					break;
				}
				String response = getResponse(line);
				writer.println(response);
			}
		} catch (Exception e) {
			System.out.println("client closed abnormally connestion");
		}
				
			
	}

	private static String getResponse(String line) {
		// <request type> +#2.5#2.5
		// request type: + - * /
		
		String response = "wrong !!!!!!";
		String [] tokens = line.split("#");
		
		if(tokens.length == 3) {
			Double operand1 = Double.parseDouble(tokens[1]);
			Double operand2 = Double.parseDouble(tokens[2]);
			
			response = switch(tokens[0]) {
			case "+" -> Double.toString(operand1 + operand2);
			case "-" -> Double.toString(operand1 - operand2);
			case "*" -> Double.toString(operand1 * operand2);
			case "/" -> Double.toString(operand1 / operand2);
		
			default -> "wrong request type";
			};
		}
		
		return "Response is: " +response;
	}

}
