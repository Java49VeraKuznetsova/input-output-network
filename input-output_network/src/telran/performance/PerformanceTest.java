package telran.performance;

public abstract class PerformanceTest {
private String testName;
	private int nRun;
	
	
	public PerformanceTest(String testName, 
            int nRun) {
		super();
		this.testName = testName;
		this.nRun = nRun;
	
		
	}
	
	

	protected abstract void runTest();
	
	public  void run () {
		//
		// printing information about results
		long start = System.currentTimeMillis();
		for (int i=0; i<nRun; i++) {
			runTest();
				}
		displayInfo(start, System.currentTimeMillis());
	}
	private  void displayInfo (long start, long finish) {
		
		System.out.printf("\ntest %s;  Number of the runs: %d; Running time: %dMs\n",
				testName,  nRun, finish - start);


	}
}

