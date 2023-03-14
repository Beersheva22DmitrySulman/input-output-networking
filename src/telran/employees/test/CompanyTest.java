package telran.employees.test;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.employees.Company;
import telran.employees.CompanyImpl;
import telran.employees.Employee;

class CompanyTest {

	List<Employee> employees;
	Company company;
	private final static String FILE_PATH = "test.data";

	@AfterEach
	void tearDown() throws Exception {
		Files.deleteIfExists(Path.of(FILE_PATH));
	}

	@BeforeEach
	void setUp() throws Exception {
		employees = new ArrayList<>();
		employees.add(new Employee(0, "Emp0", LocalDate.of(2022, 12, 25), "Dep1", 10_000));
		employees.add(new Employee(1, "Emp1", LocalDate.of(2022, 12, 25), "Dep1", 20_000));
		employees.add(new Employee(2, "Emp2", LocalDate.of(2022, 11, 25), "Dep2", 30_000));
		employees.add(new Employee(3, "Emp3", LocalDate.of(2022, 10, 25), "Dep2", 40_000));
		employees.add(new Employee(4, "Emp4", LocalDate.of(2022, 10, 25), "Dep3", 50_000));

		company = new CompanyImpl();
	}

	void addEmployeesToCompany() {
		company.addEmployee(employees.get(0));
		company.addEmployee(employees.get(1));
		company.addEmployee(employees.get(2));
		company.addEmployee(employees.get(3));
		company.addEmployee(employees.get(4));
	}

	@Test
	void addEmployeeTest() {
		assertTrue(company.addEmployee(employees.get(0)));
		assertFalse(company.addEmployee(employees.get(0)));
	}

	@Test
	void getEmployeeTest() {
		addEmployeesToCompany();
		getEmployee();
	}

	@Test
	void getEmployeeAfterRestoreTest() {
		addEmployeesToCompany();
		saveAndRestoreCompany();
		getEmployee();
	}

	@Test
	void getNonExistEmployeeTest() {
		addEmployeesToCompany();
		assertNull(company.getEmployee(5));
	}

	@Test
	void getNonExistEmployeeAfterRestoreTest() {
		addEmployeesToCompany();
		saveAndRestoreCompany();
		assertNull(company.getEmployee(5));
	}

	@Test
	void removeEmployeeAfterRestoreTest() {
		addEmployeesToCompany();
		saveAndRestoreCompany();
		Employee employee = company.removeEmployee(4);
		assertEquals("Emp4", employee.getName());
		assertNull(company.getEmployee(4));
	}

	@Test
	void removeEmployeeTest() {
		addEmployeesToCompany();
		Employee employee = company.removeEmployee(4);
		assertEquals("Emp4", employee.getName());
		assertNull(company.getEmployee(4));
	}

	@Test
	void getAllEmployeesTest() {
		addEmployeesToCompany();
		getAllEmployees();
	}

	@Test
	void getAllEmployeesAfterRestoreTest() {
		addEmployeesToCompany();
		saveAndRestoreCompany();
		getAllEmployees();
	}

	@Test
	void iteratorTest() {
		addEmployeesToCompany();
		iterator();
	}

	@Test
	void iteratorAfterRestoreTest() {
		addEmployeesToCompany();
		saveAndRestoreCompany();
		iterator();
	}

	@Test
	void getEmployeesByMonthBirthTest() {
		addEmployeesToCompany();
		getEmployeesByMonthBirth();
	}

	@Test
	void getEmployeesByDepartmentTest() {
		addEmployeesToCompany();
		getEmployeesByDepartment();
	}

	@Test
	void getEmployeesBySalaryTest() {
		addEmployeesToCompany();
		getEmployeesBySalary();
	}

	@Test
	void getEmployeesByMonthBirthAfterRestoreTest() {
		addEmployeesToCompany();
		saveAndRestoreCompany();
		getEmployeesByMonthBirth();
	}

	@Test
	void getEmployeesByDepartmentAfterRestoreTest() {
		addEmployeesToCompany();
		saveAndRestoreCompany();
		getEmployeesByDepartment();
	}

	@Test
	void getEmployeesBySalaryAfterRestoreTest() {
		addEmployeesToCompany();
		saveAndRestoreCompany();
		getEmployeesBySalary();
	}

	private void getEmployee() {
		Employee employee = company.getEmployee(4);
		assertEquals("Emp4", employee.getName());
	}

	private void iterator() {
		List<Employee> emps = new ArrayList<>();
		company.forEach(emps::add);
		Employee[] expected = employees.toArray(Employee[]::new);
		Employee[] array = emps.stream().sorted(Comparator.comparingLong(Employee::getId)).toArray(Employee[]::new);
		assertArrayEquals(expected, array);
	}

	private void getEmployeesBySalary() {
		Employee[] expected1 = { employees.get(0), employees.get(1), employees.get(2) };
		Employee[] array1 = company.getEmployeesBySalary(10_000, 30_000).stream()
				.sorted(Comparator.comparingLong(Employee::getId)).toArray(Employee[]::new);
		assertArrayEquals(expected1, array1);

		Employee[] expected2 = { employees.get(3) };
		Employee[] array2 = company.getEmployeesBySalary(35_000, 45_000).stream()
				.sorted(Comparator.comparingLong(Employee::getId)).toArray(Employee[]::new);
		assertArrayEquals(expected2, array2);

		Employee[] expected3 = { employees.get(2), employees.get(3), employees.get(4) };
		Employee[] array3 = company.getEmployeesBySalary(30_000, 60_000).stream()
				.sorted(Comparator.comparingLong(Employee::getId)).toArray(Employee[]::new);
		assertArrayEquals(expected3, array3);
	}

	private void getEmployeesByDepartment() {
		Employee[] expected1 = { employees.get(0), employees.get(1) };
		Employee[] array1 = company.getEmployeesByDepartment("Dep1").stream()
				.sorted(Comparator.comparingLong(Employee::getId)).toArray(Employee[]::new);
		assertArrayEquals(expected1, array1);

		Employee[] expected2 = { employees.get(2), employees.get(3) };
		Employee[] array2 = company.getEmployeesByDepartment("Dep2").stream()
				.sorted(Comparator.comparingLong(Employee::getId)).toArray(Employee[]::new);
		assertArrayEquals(expected2, array2);

		Employee[] expected3 = { employees.get(4) };
		Employee[] array3 = company.getEmployeesByDepartment("Dep3").stream()
				.sorted(Comparator.comparingLong(Employee::getId)).toArray(Employee[]::new);
		assertArrayEquals(expected3, array3);

	}

	private void getEmployeesByMonthBirth() {
		Employee[] expected10 = { employees.get(3), employees.get(4) };
		Employee[] array10 = company.getEmployeesByMonthBirth(10).stream()
				.sorted(Comparator.comparingLong(Employee::getId)).toArray(Employee[]::new);
		assertArrayEquals(expected10, array10);

		Employee[] expected11 = { employees.get(2) };
		Employee[] array11 = company.getEmployeesByMonthBirth(11).stream()
				.sorted(Comparator.comparingLong(Employee::getId)).toArray(Employee[]::new);
		assertArrayEquals(expected11, array11);

		Employee[] expected12 = { employees.get(0), employees.get(1) };
		Employee[] array12 = company.getEmployeesByMonthBirth(12).stream()
				.sorted(Comparator.comparingLong(Employee::getId)).toArray(Employee[]::new);
		assertArrayEquals(expected12, array12);
	}

	private void getAllEmployees() {
		Employee[] expected = employees.toArray(Employee[]::new);
		Employee[] array = company.getAllEmployees().stream().sorted(Comparator.comparingLong(Employee::getId))
				.toArray(Employee[]::new);
		assertArrayEquals(expected, array);
	}

	private void saveAndRestoreCompany() {
		company.save(FILE_PATH);
		company = new CompanyImpl();
		company.restore(FILE_PATH);
	}
}
