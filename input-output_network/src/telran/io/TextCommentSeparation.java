package telran.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class TextCommentSeparation {

	public static void main(String[] args) {
		//  Auto-generated method stub
	
		
		if(args.length < 3) {
			System.out.println("Usage: must be three arguments (source, destinationLine and destinationComment)");
		} else {
			try (BufferedReader reader = 
					new BufferedReader(new FileReader(args[0]));
					PrintWriter outputLine = 
							new PrintWriter(args[1]);
					PrintWriter outputComment = 
						new PrintWriter(args[2])){
		  
				reader
				.lines()
				.forEach(line -> {
					if(line.contains("//")) {
						 outputComment.println(line);
					} else {
						 outputLine.println(line); 
						
					}
				});
						
					
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			
		}

	}

}
