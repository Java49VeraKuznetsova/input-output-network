package telran.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


public class CopyFileStreams implements CopyFile {
private static int bufferLenght;

public CopyFileStreams(int bufferLenght) {
	super();
	CopyFileStreams.bufferLenght = bufferLenght;
}


public static void copy (String pathToSourse, String pathToDestination) throws Exception{
	System.out.println("Hi");
	try (
			InputStream input = new FileInputStream(pathToSourse);
			OutputStream output = new FileOutputStream(pathToDestination))
			 {
		byte buf[] = new byte[bufferLenght];
		
		int lenght = 0;
		while((lenght = input.read(buf)) > 0) {
			output.write(buf, 0, lenght);
					}
			}
	
}


}
