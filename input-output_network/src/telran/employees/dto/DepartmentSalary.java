package telran.employees.dto;

import java.io.Serializable;

public record DepartmentSalary(
		String departmnet,
		double salary) 
implements Serializable {

}
