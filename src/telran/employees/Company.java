package telran.employees;

import java.io.Serializable;
import java.util.List;

public interface Company extends Iterable<Employee>, Serializable {
	Employee getEmployee(long id);
	boolean addEmployee(Employee employee);
	Employee removeEmployee(long id);
	List<Employee> getAllEmployees();
	List<Employee> getEmployeesByMonthBirth(int month);
	List<Employee> getEmployeesBySalary(int salaryFrom, int salaryTo);
	List<Employee> getEmployeesByDepartment(String department);
	void save(String pathName);
	void restore(String pathName);
}
