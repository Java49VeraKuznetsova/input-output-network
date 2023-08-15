package telran.view.console;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConsoleInputOutputTest {

	ConsoleInputOutput inputOutput = new ConsoleInputOutput();
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void readIntTest() {
	
	int res = inputOutput.readInt("Enter integer number:", "Wrong integer number");
	inputOutput.writeLine("integer res = " +res);
	}



@Test
void readDateTestInterval() {
	LocalDate from = LocalDate.parse("1925-04-10");
	LocalDate to = LocalDate.parse("1995-04-10");
	LocalDate res = inputOutput.readDate("Input date in format dd-MM-yyyy", "Wrong date", from, to);
	inputOutput.writeLine("Our date: " + res);
}
}

//LocalDate readDate(String prompt, String errorPrompt, 
	//	  LocalDate from, LocalDate to) 
