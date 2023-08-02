package telran.performance;

import telran.io.CopyFile;

public class CopyPerformanceTest extends PerformanceTest  {
private String pathToSourse;
private String pathToDestination;
public CopyPerformanceTest(String testName, 
			                   int nRun, 
			                   String pathToSourse,
			                   String pathToDestination,
			                   CopyFile copyFile) {
		super(testName, nRun);
		this.pathToSourse = pathToSourse;
		this.pathToDestination = pathToDestination;
		
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void runTest() {
		//  Auto-generated method stub
		System.out.println("Hi2");
	CopyFile.copy(pathToSourse, pathToDestination);

	}

}
