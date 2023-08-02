package telran.performance;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import telran.io.CopyFile;
import telran.io.CopyFileStreams;

public class CopyFilePerformanceAppl {

	private static final int [] BUFFER_LENGTH_TEST = {10_000, 100_000, 1_000_000, 100_000_000};
	private static final int N_RUN = 1;
	//private static final String  pathToSourse= "input1.txt";
	private static final String  pathToSourse= "\"C:\\Users\\kuzne\\Desktop\\קורס דרור ו 16.mp4\"";
	private static final String pathToDestination = "output1";
	
	public static void main(String[] args) {
		//  Auto-generated method stub
		/*
		try {	
		long fileSize = getBigFile(pathToSourse);
		System.out.printf("File size = %l\n", fileSize);
		System.out.println("Hi3");
		}
		catch (Exception ex){
			
		}
		*/
		
			for (int i = 0; i < BUFFER_LENGTH_TEST.length; i++) {
				String testName = getTestName(BUFFER_LENGTH_TEST[i]);
				CopyFile fileToCopy = new CopyFileStreams(BUFFER_LENGTH_TEST[i]);
				CopyPerformanceTest test = 
						new CopyPerformanceTest(
								testName, 
								N_RUN,
								pathToSourse,
								pathToDestination, 
								fileToCopy);
								
					test.run();
			}
	

	}
	
     private static long getBigFile(String pathToSourse) throws Exception {
    	 try(OutputStream output = new FileOutputStream(pathToSourse)) {
 			
 			for (int i = 0; i < 4; i++) {
 				StringBuilder builder = new StringBuilder(500_000_000);
 				for (int j = 0; j < 50_000_000; j++) {
 					builder.append("HelloWorld");

 				}
 				output.write(builder.toString().getBytes());
 			}
 			
 			return Files.size(Path.of(pathToSourse));
 		}
		
	}
	


	private static String getTestName(int bufferLenghtTest) {
		
		return String.format("BufferLength: %d", bufferLenghtTest);
	}
	

}
