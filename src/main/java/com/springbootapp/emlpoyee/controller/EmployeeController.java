package com.springbootapp.emlpoyee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springbootapp.emlpoyee.entity.Employee;
import com.springbootapp.emlpoyee.service.EmployeeService;

@Controller
@RequestMapping("/Employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/Save")
	public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
		return new ResponseEntity<>(employeeService.save(employee), HttpStatus.CREATED);
	}

	@PostMapping("/SaveAll")
	public List<Employee> saveAllEmployees(@RequestBody List<Employee> employees) {
		return employeeService.saveAll(employees);
	}

	@GetMapping("/GetEmployees")
	public String getEmployees(Model model) {
		model.addAttribute("employees", employeeService.getEmployees());
		return "Employees";
	}

	@GetMapping("/GetEmployeeById/{id}")
	public Employee getEmployees(@PathVariable Long id) {
		return employeeService.getEmployeeById(id);
	}

	@GetMapping("/GetEmployeeByFirstNameAndLastName/{firstName}/{lastName}")
	public List<Employee> getEmployees(@PathVariable String firstName, @PathVariable String lastName) {
		return employeeService.getEmplyeesByFirstNameAndLastNameLike(firstName, lastName);
	}

	@GetMapping("/GetEmployeesOrderByFirstName")
	public List<Employee> getEmployeesOrderByFirstName() {
		return employeeService.getEmployeesOrderByFirstName();
	}

//	@PutMapping("/UpdateEmployee/{id}")
//	public Employee updateEmployee(@RequestBody Employee employee, @PathVariable Long id) {
//		return employeeService.updateEmployee(employee, id);
//	}

	@GetMapping("/DeleteEmployee/{id}")
	public String deleteEmployee(@PathVariable Long id) {
		employeeService.deleteEmployee(id);
		return "redirect:/Employees/GetEmployees";
	}

	@GetMapping("/CreateEmployeeForm")
	public String createEmployee(Model model) {
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "CreateEmployeeForm";
	}

	@PostMapping("/SaveEmployee")
	public String createEmployee(@ModelAttribute Employee employee) {
		employeeService.save(employee);
		return "redirect:/Employees/GetEmployees";
	}

	@GetMapping("/UpdateEmployeeForm/{id}")
	public String updateEmployee(@PathVariable Long id, Model model) {
		model.addAttribute("employee", employeeService.getEmployeeById(id));
		return "UpdateEmployeeForm";
	}

	@PostMapping("/UpdateEmployee/{id}")
	public String updateEmployee(@ModelAttribute Employee employee, @PathVariable Long id) {
		Employee existingEmployee = employeeService.getEmployeeById(id);

		existingEmployee.setFirstName(employee.getFirstName());
		existingEmployee.setLastName(employee.getLastName());
		existingEmployee.setDepartment(employee.getDepartment());
		existingEmployee.setEmail(employee.getEmail());
		existingEmployee.setGender(employee.getGender());
		existingEmployee.setJobTitle(employee.getJobTitle());
		existingEmployee.setSalary(employee.getSalary());

		employeeService.save(existingEmployee);
		return "redirect:/Employees/GetEmployees";
	}
}
