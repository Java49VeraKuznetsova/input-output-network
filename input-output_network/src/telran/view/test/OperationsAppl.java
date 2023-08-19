package telran.view.test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.function.BinaryOperator;

import telran.view.*;

public class OperationsAppl {

	public static void main(String[] args) {
		// Auto-generated method stub
		InputOutput io = new ConsoleInputOutput();
		Menu mainMenu = new Menu("Operations", getItemsMainMenu());
		mainMenu.perform(io);
		io.writeLine("This is the end");
		
	}

	static Item[] getItemsMainMenu() {
		 Item[] items = {
				 Item.of("Number Operations",io -> callNumbersMenu(io)),
				 Item.of("Date Operators", io -> callDatesMenu(io)),
		 Item.ofExit()
		 	 };
		 
		 return items;
	}
	static void callNumbersMenu(InputOutput io) {
		Menu calculateMenu = new Menu("Number Operations", getItemsCalculate());
		calculateMenu.perform(io);
	
	}
	static void callDatesMenu(InputOutput io) {
		Menu datesMenu = new Menu("Date Operations", getItemsDateMenu());
		datesMenu.perform(io);
	
		
	}
	static Item[] getItemsDateMenu() {
		Item [] items = {
				Item.of("Date after from your date", io -> addDays(io)),
				Item.of("Date before from your date", io -> subDays(io)),
				Item.of("Days between two days", io -> betweenDays(io)),
				Item.ofExit()
		};
		return items;
	}
	 static Item[] getItemsCalculate() {
		 Item[] items = {
			Item.of("Add numbers", io -> calculate(io, (a,b) ->(a+b))) ,	
			Item.of("Subtract numbers", io -> calculate(io, (a,b) ->(a-b))),
			Item.of("Multiply numbers", io -> calculate(io, (a,b) ->(a*b))) ,
			Item.of("Divide numbers", io -> calculate(io, (a,b) ->(a/b))),
			Item.ofExit() };
		 return items;
	 }
	 
	 
	 static void calculate(InputOutput io, BinaryOperator<Double> operator) {
		 double first = 
				 io.readDouble("Enter first number", 
						        "Must be any number");
		 double second = 
				 io.readDouble("Enter second number", 
						        "Must be any number");
		io.writeLine(operator.apply(first, second));
		  }
	 
	 static void addDays(InputOutput io) {
		 LocalDate inputDate =
				 io.readDate("Enter date in format yyyy-MM-dd", "Must be a date in format yyyy-MM-dd");
		 long numberDates = io.readInt("Enter any number", "Must be any number");
		 io.writeLine(inputDate.plusDays(numberDates));
	 }
	 
	 static void subDays(InputOutput io) {
		 LocalDate inputDate =
				 io.readDate("Enter date in format yyyy-MM-dd", "Must be a date in format yyyy-MM-dd");
		 long numberDates = io.readInt("Enter any number", "Must be any number");
		 io.writeLine(inputDate.minusDays(numberDates));
	 }
	 
	 static void betweenDays (InputOutput io) {
			 LocalDate firstDate =
					 io.readDate("Enter first date in format yyyy-MM-dd", "Must be a date in format yyyy-MM-dd");
			 LocalDate secondDate =
					 io.readDate("Enter second date in format yyyy-MM-dd", "Must be a date in format yyyy-MM-2dd");
			 io.writeLine(ChronoUnit.DAYS.between(firstDate, secondDate));
	 }


}
