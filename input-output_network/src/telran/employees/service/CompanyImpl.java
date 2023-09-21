package telran.employees.service;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

import telran.employees.dto.DepartmentSalary;
import telran.employees.dto.Employee;
import telran.employees.dto.Monitor;
import telran.employees.dto.SalaryDistribution;

import java.time.LocalDate;
import java.util.*;

public class CompanyImpl implements Company {
	LinkedHashMap<Long, Employee> employees = new LinkedHashMap<>();
	TreeMap<Integer, Collection<Employee>> employeesSalary = new TreeMap<>();
	TreeMap<LocalDate, Collection<Employee>> employeesAge = new TreeMap<>();
	HashMap<String, Collection<Employee>> employeesDepartment = new HashMap<>();

	ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
	Lock readLock = rwLock.readLock();
	Lock writeLock = rwLock.writeLock();
	Monitor monitor = new Monitor(readLock, writeLock);
	
	@Override
	public boolean addEmployee(Employee empl) {
		boolean res;
		try {monitor.write().lock();
			res = false;
			Employee emplRes = employees.putIfAbsent(empl.id(), empl);
			if (emplRes == null) {
				res = true;
				addEmployeeSalary(empl);
				addEmployeeAge(empl);
				addEmployeeDepartment(empl);
				
			} 
			return res;
		} finally {
			monitor.write().unlock();
		}
	
	}

	private <T> void addToIndex(Employee empl, T key, Map<T, Collection<Employee>> map) {
		map.computeIfAbsent(key, k -> new HashSet<>()).add(empl);
	}

	private void addEmployeeSalary(Employee empl) {
		addToIndex(empl, empl.salary(), employeesSalary);

	}

	private void addEmployeeAge(Employee empl) {
		addToIndex(empl, empl.birthDate(), employeesAge);

	}

	private void addEmployeeDepartment(Employee empl) {
		addToIndex(empl, empl.department(), employeesDepartment);

	}

	@Override
	public Employee removeEmployee(long id) {
		Employee res;
		try { monitor.write().lock();
			res = employees.remove(id);
			if (res != null) {
				removeEmployeeSalary(res);
				removeEmployeeAge(res);
				removeEmployeeDepartment(res);
			} 
			return res;
		} finally {
			monitor.write().lock();
		}
		
	}

	private <T> void removeFromIndex(Employee empl, T key, Map<T, Collection<Employee>> map) {

		Collection<Employee> employeesCol = map.get(key);
		employeesCol.remove(empl);
		if (employeesCol.isEmpty()) {
			map.remove(key);
		}
	}

	private void removeEmployeeSalary(Employee empl) {
		int salary = empl.salary();
		removeFromIndex(empl, salary, employeesSalary);

	}

	private void removeEmployeeAge(Employee empl) {
		removeFromIndex(empl, empl.birthDate(), employeesAge);

	}

	private void removeEmployeeDepartment(Employee empl) {
		removeFromIndex(empl, empl.department(), employeesDepartment);

	}

	@Override
	public Employee getEmployee(long id) {

		try {monitor.read().lock();
			return employees.get(id);
		} finally {
			monitor.read().unlock();
		}
	}

	@Override
	public List<Employee> getEmployees() {

		try {monitor.read().lock();
			return new ArrayList<>(employees.values());
		} finally {
			monitor.read().unlock();
		}
	}

	@Override
	public List<DepartmentSalary> getDepartmentSalaryDistribution() {

		try {monitor.read().lock();
			return employees.values().stream()
					.collect(Collectors.groupingBy(Employee::department, Collectors.averagingInt(Employee::salary)))
					.entrySet().stream().map(e -> new DepartmentSalary(e.getKey(), e.getValue())).toList();
		} finally {
			monitor.read().unlock();
		}
	}

	@Override
	public List<SalaryDistribution> getSalaryDistribution(int interval) {

		try {monitor.read().lock();
			return employees.values().stream()
					.collect(Collectors.groupingBy(e -> e.salary() / interval, Collectors.counting())).entrySet()
					.stream()
					.map(e -> new SalaryDistribution(e.getKey() * interval, e.getKey() * interval + interval - 1,
							e.getValue().intValue()))
					.sorted((sd1, sd2) -> Integer.compare(sd1.minSalary(), sd2.minSalary())).toList();
		} finally {
			monitor.read().unlock();
		}
	}

	@Override
	public List<Employee> getEmployeesByDepartment(String department) {
		ArrayList<Employee> res;
		try {monitor.read().lock();
			Collection<Employee> employeesCol = employeesDepartment.get(department);
			res = new ArrayList<>();
			if (employeesCol != null) {
				res.addAll(employeesCol);
			} 
			return res;
		} finally {
			monitor.read().unlock();
		}
		
	}

	@Override
	public List<Employee> getEmployeesBySalary(int salaryFrom, int salaryTo) {

		try {monitor.read().lock();
			return employeesSalary.subMap(salaryFrom, true, salaryTo, true).values().stream()
					.flatMap(col -> col.stream().sorted((empl1, empl2) -> Long.compare(empl1.id(), empl2.id())))

					.toList();
		} finally {
			monitor.read().unlock();
		}
	}

	@Override
	public List<Employee> getEmployeesByAge(int ageFrom, int ageTo) {
		try {monitor.read().lock();
			LocalDate dateTo = LocalDate.now().minusYears(ageFrom);
			LocalDate dateFrom = LocalDate.now().minusYears(ageTo);
			return employeesAge.subMap(dateFrom, true, dateTo, true).values().stream()
					.flatMap(col -> col.stream().sorted((empl1, empl2) -> Long.compare(empl1.id(), empl2.id())))
					.toList();
		} finally {
			monitor.read().unlock();
		}
	}

	@Override
	public Employee updateSalary(long id, int newSalary) {
		Employee empl;
		try {monitor.write().lock();
			empl = removeEmployee(id);
			if (empl != null) {
				Employee newEmployee = new Employee(id, empl.name(), empl.department(), newSalary, empl.birthDate());
				addEmployee(newEmployee);
			} 
			return empl;
		} finally {
			monitor.write().unlock();
		}
		
	}

	@Override
	public Employee updateDepartment(long id, String newDepartment) {
		Employee empl;
		try {monitor.write().lock();
			empl = removeEmployee(id);
			if (empl != null) {
				Employee newEmployee = new Employee(id, empl.name(), newDepartment, empl.salary(), empl.birthDate());
				addEmployee(newEmployee);
				
			} 
			return empl;
		} finally {
			monitor.write().unlock();
		}
		
	}

}