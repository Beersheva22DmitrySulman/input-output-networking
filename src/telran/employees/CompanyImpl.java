package telran.employees;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

public class CompanyImpl implements Company {
	private static final long serialVersionUID = 1L;

	private Map<Long, Employee> employees = new HashMap<>();
	private Map<Integer, Set<Employee>> employeesByMonthBirth = new HashMap<>();
	private Map<String, Set<Employee>> employeesByDepartment = new HashMap<>();
	private NavigableMap<Integer, Set<Employee>> employeesBySalary = new TreeMap<>();

	@Override
	public Iterator<Employee> iterator() {
		return employees.values().iterator();
	}

	@Override
	public Employee getEmployee(long id) {
		return employees.get(id);
	}

	@Override
	public boolean addEmployee(Employee employee) {
		Long id = employee.getId();
		boolean res = !employees.containsKey(id);
		if (res) {
			employees.put(id, employee);
			employeesByMonthBirth.computeIfAbsent(employee.getBirthDate().getMonthValue(), k -> new HashSet<>())
				.add(employee);
			employeesByDepartment.computeIfAbsent(employee.getDepartment(), k -> new HashSet<>())
				.add(employee);
			employeesBySalary.computeIfAbsent(employee.getSalary(), k -> new HashSet<>())
				.add(employee);
		}

		return res;
	}

	private void removeEmployeeFromIndex(Map<? extends Object, Set<Employee>> index, Object key, Employee employee) {
		Set<Employee> set = index.get(key);
		set.remove(employee);
		if (set.isEmpty()) {
			index.remove(key);
		}
	}

	@Override
	public Employee removeEmployee(long id) {
		Employee employee = employees.remove(id);
		if (employee != null) {
			removeEmployeeFromIndex(employeesByMonthBirth, employee.getBirthDate().getMonthValue(), employee);
			removeEmployeeFromIndex(employeesByDepartment, employee.getDepartment(), employee);
			removeEmployeeFromIndex(employeesBySalary, employee.getSalary(), employee);
		}

		return employee;
	}

	@Override
	public List<Employee> getAllEmployees() {
		return employees.values().stream().toList();
	}

	@Override
	public List<Employee> getEmployeesByMonthBirth(int month) {
		return employeesByMonthBirth.get(month).stream().toList();
	}

	@Override
	public List<Employee> getEmployeesBySalary(int salaryFrom, int salaryTo) {
		return employeesBySalary.subMap(salaryFrom, true, salaryTo, true).values().stream()
				.flatMap(Set::stream)
				.toList();
	}

	@Override
	public List<Employee> getEmployeesByDepartment(String department) {
		return employeesByDepartment.get(department).stream().toList();
	}

	@Override
	public void save(String pathName) {
		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(pathName))) {
			output.writeObject(this);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void restore(String pathName) {
		try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(pathName))) {
			CompanyImpl company = (CompanyImpl) input.readObject();
			employees = company.employees;
			employeesByMonthBirth = company.employeesByMonthBirth;
			employeesByDepartment = company.employeesByDepartment;
			employeesBySalary = company.employeesBySalary;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
