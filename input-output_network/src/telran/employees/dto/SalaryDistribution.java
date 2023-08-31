package telran.employees.dto;

import java.io.Serializable;

public record SalaryDistribution (
		int minSalary,
		int maxsalary,
		int amountEmployees) implements Serializable{

}

