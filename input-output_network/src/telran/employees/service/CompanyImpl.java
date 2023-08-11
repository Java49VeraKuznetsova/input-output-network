package telran.employees.service;

import java.util.List;
import java.util.stream.Collectors;

import telran.employees.dto.DepartmentSalary;
import telran.employees.dto.Employee;
import telran.employees.dto.SalaryDistribution;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
public class CompanyImpl implements Company {
  LinkedHashMap<Long, Employee> employees = new LinkedHashMap<>();
	TreeMap<Integer, Collection<Employee>> employeesSalary = new TreeMap<>();
	TreeMap<Integer, Collection<Employee>> employeesAge = new TreeMap<>();
	HashMap<String, Collection<Employee>> employeesDepartment = new HashMap<>();
		
  @Override
	public boolean addEmployee(Employee empl) {
		boolean res = false;
	  Employee emplRes = employees.putIfAbsent(empl.id(), empl);
		if (emplRes == null) {
			res = true;
			addEmployeeSalary(empl);
			addEmployeeAge(empl);
			addEmployeeDepartment(empl);
		}
	  return res;
	}

	private void addEmployeeDepartment(Employee empl) {
	// TODO Auto-generated method stub
		String department = empl.department();
		employeesDepartment
		.computeIfAbsent(department, k -> new HashSet<>())
		.add(empl);
	
}

	private void addEmployeeAge(Employee empl) {
	// TODO Auto-generated method stub
		LocalDate dateBirth = empl.birthDate();
		int age = getAge(dateBirth);
		employeesAge
		.computeIfAbsent(age, k -> new HashSet<>())
		.add(empl);

}

	private int getAge(LocalDate dateBirth) {
		// TODO Auto-generated method stub
		final LocalDate today = LocalDate.now();
	
		return Period.between(dateBirth, today).getYears();
	}

	private void addEmployeeSalary(Employee empl) {
		int salary = empl.salary();
	employeesSalary
	.computeIfAbsent(salary, k -> new HashSet<>())
	.add(empl);
	
}

	@Override
	public Employee removeEmployee(long id) {
		Employee res = employees.remove(id);
		if (res != null) {
			removeEmployeeSalary(res);
			removeEmployeeAge(res);
			removeEmployeeDepartment(res);
		}
		
		return res;
	}

	private void removeEmployeeDepartment(Employee empl) {
		// TODO Auto-generated method stub
		String department = empl.department();
		Collection<Employee> employeesCol = 
				employeesDepartment.get(department);
		employeesCol.remove(empl);
		if (employeesCol.isEmpty()) {
			employeesDepartment.remove(department);
		}
		
	}

	private void removeEmployeeAge(Employee empl) {
		// TODO Auto-generated method stub
		LocalDate dateBirth = empl.birthDate();
		int age = getAge(dateBirth);
		Collection<Employee> employeesCol = 
				employeesAge.get(age);
		employeesCol.remove(empl);
		if(employeesCol.isEmpty()) {
			employeesAge.remove(age);
		}
		
	}

	private void removeEmployeeSalary(Employee empl) {
		int salary = empl.salary();
		Collection<Employee> employeesCol = 
				employeesSalary.get(salary);
		employeesCol.remove(empl);
		if(employeesCol.isEmpty()) {
			employeesSalary.remove(salary);
		}
	}

	@Override
	public Employee getEmployee(long id) {
		
		return employees.get(id);
	}

	@Override
	public List<Employee> getEmployees() {
		
		return new ArrayList<>(employees.values());
	}

	@Override
	public List<DepartmentSalary> getDepartmentSalaryDistribution() {
		
		return employees.values().stream()
				.collect(Collectors.groupingBy(Employee::department,
						Collectors.averagingInt(Employee::salary)))
				.entrySet().stream().map(e -> new DepartmentSalary(e.getKey(),
						e.getValue())).toList();
	}

	@Override
	public List<SalaryDistribution> getSalaryDistribution(int interval) {
		
		return employees.values().stream()
				.collect(Collectors.groupingBy(e -> e.salary()/interval,
						Collectors.counting()))
				.entrySet().stream()
				.map(e -> new SalaryDistribution(e.getKey() * interval,
						e.getKey() * interval + interval - 1, e.getValue().intValue()))
				.sorted((sd1, sd2) -> Integer.compare(sd1.minSalary(), sd2.minSalary()))
						.toList();
	}

	@Override
	public List<Employee> getEmployeesByDepartment(String department) {
		// TODO Auto-generated method stub
		
			if (employeesDepartment.get(department) == null) {
			
				throw new NullPointerException("now such department");
							
		} 
		return employeesDepartment
				.get(department)
				.stream()
				.sorted((empl1, empl2) ->
				Long.compare(empl1.id(), empl2.id()))
						
						.toList();
	}

	@Override
	public List<Employee> getEmployeesBySalary(int salaryFrom, int salaryTo) {
		
		return employeesSalary
				.subMap(salaryFrom, true, salaryTo, true)
				.values()
				.stream()
				.flatMap(col -> col.stream()
						.sorted((empl1, empl2) ->
						      Long.compare(empl1.id(), empl2.id())))
				
				.toList();
	}

	@Override
	public List<Employee> getEmployeesByAge(int ageFrom, int ageTo) {
		// TODO Auto-generated method stub
		return employeesAge
				.subMap(ageFrom, true, ageTo, true)
				.values()
				.stream()
				.flatMap(col -> col.stream()
						.sorted((empl1, empl2) ->
						    Long.compare(empl1.id(), empl2.id())))
				.toList();
	}

	@Override
	public Employee updateSalary(long id, int newSalary) {
		// TODO Auto-generated method stub
	
		Employee empl = employees.get(id);
		if (empl != null && empl.salary() != newSalary) {
			removeEmployee(id);
			Employee newEmpl =
            new Employee(empl.id(), empl.name(), empl.department(), 
            		newSalary, empl.birthDate());
			addEmployee(newEmpl);
						
		} else {
			empl= null;
		}

		return empl;
	}

	@Override
	public Employee updateDepartment(long id, String newDepartment) {
		// TODO Auto-generated method stub
		Employee empl = employees.get(id);
		if (empl != null && empl.department() != newDepartment) {
			removeEmployee(id);
			Employee newEmpl =
            new Employee(empl.id(), empl.name(), newDepartment, 
            		empl.salary(), empl.birthDate());
			addEmployee(newEmpl);
				} else {
					empl= null;
				}
		
		return empl;
	}

	

	

}