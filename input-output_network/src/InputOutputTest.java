import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class InputOutputTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Disabled
	@Test
	void test() {
		Path pathParent = Path.of(".");
		System.out.println(pathParent.toAbsolutePath()
				.normalize().getName(3));
		
	}
 @Test
 void displayDirContentTest() {
	 displayDirContent(Path.of("./."), 3);
	 
	 displayDirContent(Path.of("C:\\Users\\kuzne\\Desktop\\Учеба\\Tel_Ran\\Лекции\\Lesson 83_32.docx"), 7);
	
 }
  private void displayDirContent(Path dirPath, int maxDepth)  {
	  //
	  //Display content directory 
	  // by using method walk of the class Files

	
	
	 try {
		  if(!Files.isDirectory(dirPath)) {
				throw new IllegalArgumentException("Not directory: "+dirPath.toString());
			}
		  
			 Files.walk(dirPath, maxDepth)
			 .filter(Files::isReadable)
			 .forEach(p -> 
			     System.out.printf("%s  %s - %s \n", 
			    		 " ".repeat(p.getNameCount()*2),
			    		 p.toAbsolutePath()
			    		 .normalize()
			    		// .getName(p.getNameCount()-1),
			    		 .getFileName(),
			    	 	Files.isDirectory(p) ? "dir" : "file"));
	 }

	
	 catch (IOException ex){
		 System.out.println(ex.toString());
	 }
		catch (IllegalArgumentException ex){
			System.out.println(ex.toString());
		}
			
			
  }
}
