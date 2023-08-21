package telran.employees.controller;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

import telran.employees.dto.DepartmentSalary;
import telran.employees.dto.Employee;
import telran.employees.dto.SalaryDistribution;
import telran.employees.service.Company;
import telran.view.InputOutput;
import telran.view.Item;


public class CompanyController {
   private static final long MIN_ID = 100000;
private static final long MAX_ID = 999999;
private static final int MIN_SALARY = 6000;
private static final int MAX_SALARY = 50000;
private static final int MAX_AGE = 75;
private static final int MIN_AGE = 20;
static Company company;
	public static ArrayList<Item> getCompanyItems(Company company) {
		CompanyController.company = company;
		ArrayList<Item> res = new ArrayList<>(Arrays.asList(
				getItems()));
		return res;
	}
	private static Item[] getItems() {
		
		return new Item[] {
				Item.of("Add new Employee", CompanyController::addEmployeeItem),
				Item.of("Remove Employee", CompanyController::removeEmployeeItem),
				Item.of("All Employees", CompanyController::getEmployeesItem),
				Item.of("Data about Employee", CompanyController::getEmployeeItem),
				Item.of(" Employees by Salary", CompanyController::getEmployeesBySalaryItem),
				Item.of("Employees by Department", CompanyController::getEmployeesByDepartmentItem),
				Item.of("Update salary", CompanyController::updateSalaryItem),
				Item.of("Departments and Salary", CompanyController::getDepartmentSalaryDistributionItem),
				Item.of("Distribution by Salary", CompanyController::getSalaryDistributionItem),
				Item.of("Employees by Age", CompanyController::getEmployeesByAgeItem),
				Item.of("Update Department", CompanyController::updateDepartmentItem)
		};
	}
	static private Set<String> departments = new HashSet<>(Arrays.asList(new String[] {
			"QA", "Development", "Audit", "Management", "Accounting"
	}));
	static void addEmployeeItem(InputOutput io) {
		long id = io.readLong("Enter Employee identity", "Wrong identity value", MIN_ID, MAX_ID);
		String name = io.readString("Enter name", "Wrong name",
				str -> str.matches("[A-Z][a-z]+"));
		String department = io.readString("Enter department", "Wrong department", departments );
		int salary = io.readInt("Enter salary", "Wrong salary", MIN_SALARY, MAX_SALARY);
		LocalDate birthDate = io.readDate("Enter birth data", "Wrong birth date entered",
				getBirthdate(MAX_AGE), getBirthdate(MIN_AGE));
		boolean res = company.addEmployee(new Employee(id, name, department, salary, birthDate));
		io.writeLine(res ? String.format("Employee with id %d has been added", id) : 
			String.format("Employee with id %d already exists", id));
	}
	private static LocalDate getBirthdate(int age) {
		
		return LocalDate.now().minusYears(age);
	}
	static void removeEmployeeItem(InputOutput io) {
		//TODO
		long id = io.readLong("Enter ID", "Wrong ID");
		Employee res = company.removeEmployee(id);
		io.writeLine(res != null ? String.format("Employee with id %d has been removed", res.id()) : 
			String.format("No employee with id %d ", id));

		
	}
	static void getEmployeeItem(InputOutput io) {
		//TODO !!!!!!PRINT
		long id = io.readLong("Enter ID", "Wrong ID");
		Employee res = company.getEmployee(id);
		
		if(res != null) {
			io.writeLine(res);  
		} else {
			io.writeLine(String.format("No employee with id %d ", id));
		}
	}
	static void getEmployeesItem(InputOutput io) {
		//
		
		company.getEmployees().forEach(io::writeLine);
		
	}
	static void getDepartmentSalaryDistributionItem(InputOutput io) {
		company.getDepartmentSalaryDistribution().forEach(io::writeLine);
		
	}
	static void getSalaryDistributionItem(InputOutput io) {
		//
		int interval = io.readInt("Enter interval", "Wrong interval" );
		company.getSalaryDistribution(interval).forEach(io::writeLine);
	}
	static void getEmployeesByDepartmentItem(InputOutput io) {
		//
		
		String department = io.readString("Enter department", "Wrong department", departments );
		company.getEmployeesByDepartment(department).forEach(io::writeLine);
		
	}
	static void getEmployeesBySalaryItem(InputOutput io) {
		//
		int salaryFrom = io.readInt("Enter salary from", "Wrong salary", MIN_SALARY, MAX_SALARY);
		int salaryTo = io.readInt("Enter salary to", "Wrong salary", MIN_SALARY, MAX_SALARY);
		company.getEmployeesBySalary(salaryFrom, salaryTo).forEach(io::writeLine);
	}
	static void getEmployeesByAgeItem(InputOutput io) {
		//TODO
		int ageFrom = io.readInt("Enter age from", "Wrong salary", MIN_AGE, MAX_AGE);
		int ageTo = io.readInt("Enter age to", "Wrong salary", MIN_AGE, MAX_AGE);
		company.getEmployeesByAge(ageFrom, ageTo).forEach(io::writeLine);
	}
	static void updateSalaryItem(InputOutput io) {
		//TODO ??? CHECK SALARY?
		long id = io.readLong("Enter ID", "Wrong ID");
		int salary = io.readInt("Enter new salary", "Wrong salary");
		Employee empl = company.updateSalary(id, salary);
		io.writeLine(empl != null ? String.format("Salary for Employee with id %d has been update", empl.id(), empl) : 
			String.format("No employee with id %d ", id));
	}
	static void updateDepartmentItem(InputOutput io) {
		//
		String newDepartment = io.readString("Enter new department", "Wrong department", departments);
		long id = io.readLong("Enter ID", "Wrong ID");
		Employee empl = company.updateDepartment(id, newDepartment);
		io.writeLine(empl != null ? String.format("Department for Employee with id %d has been update", empl.id(), empl) : 
			String.format("No employee with id %d ", id));
		
	}
	
	

}