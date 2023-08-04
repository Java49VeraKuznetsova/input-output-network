package telran.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyFileTransfer implements CopyFile {
 @Override
 public void copy(String pathToSource, String pathToDestibation) {
try (InputStream input = new FileInputStream(pathToSource);
		OutputStream output = new FileOutputStream(pathToDestibation)) {
	
}
	catch(IOException e) {
		throw new RuntimeException(e.toString());
	}
}
}
