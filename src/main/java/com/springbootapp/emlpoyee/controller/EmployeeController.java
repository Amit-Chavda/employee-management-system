package com.springbootapp.emlpoyee.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springbootapp.emlpoyee.entity.Employee;
import com.springbootapp.emlpoyee.service.EmployeeService;

@Controller
@RequestMapping("/")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping()
	public String basePath() {
		return "index";
	}

	/*
	 * Common public pages
	 */

	@RequestMapping("AccessDenied")
	public String accessDenied() {
		return "common/AccessDenied";
	}

	@RequestMapping("Login")
	public String employeeLogin(Model model) {
		model.addAttribute("employee", new Employee());
		return "common/Login";
	}

	@RequestMapping("/LoginError")
	public String employeeLoginError(HttpServletRequest request, Model model) {
		model.addAttribute("employee", new Employee());

		HttpSession session = request.getSession(false);
		String errorMessage = null;
		if (session != null) {
			AuthenticationException ex = (AuthenticationException) session
					.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
			if (ex != null) {
				errorMessage = ex.getMessage();
			}
		}
		model.addAttribute("error", errorMessage);

		return "common/Login";
	}

	/*
	 * Employee accessible pages
	 */
	@RequestMapping("Employees/Home")
	public String employeeHome(Model model) {
		return "employee/Home";
	}

	/*
	 * Admin accessible pages
	 */
	@PostMapping("/Admin/Save")
	public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
		return new ResponseEntity<>(employeeService.save(employee), HttpStatus.CREATED);
	}

	@PostMapping("/Admin/SaveAll")
	public List<Employee> saveAllEmployees(@RequestBody List<Employee> employees) {
		return employeeService.saveAll(employees);
	}

	@GetMapping("Admin/")
	public String admindashboard(Model model) {
		return "redirect:/Admin/page/1?sortBy=id&order=ASC";
	}

	@GetMapping("Admin/GetEmployees")
	public String getEmployees(Model model) {
		model.addAttribute("employees", employeeService.getEmployees());
		return "Employees";
	}

	@GetMapping("Admin/GetEmployeeById/{id}")
	public Employee getEmployees(@PathVariable Long id) {
		return employeeService.getEmployeeById(id);
	}

	/*
	 * @GetMapping("/GetEmployeeByFirstNameAndLastName/{firstName}/{lastName}")
	 * public List<Employee> getEmployees(@PathVariable String
	 * firstName, @PathVariable String lastName) { return
	 * employeeService.getEmplyeesByFirstNameAndLastNameLike(firstName, lastName); }
	 * 
	 * @GetMapping("/GetEmployeesOrderByFirstName") public List<Employee>
	 * getEmployeesOrderByFirstName() { return
	 * employeeService.getEmployeesOrderByFirstName(); }
	 */
	@GetMapping("Admin/DeleteEmployee/{id}")
	public String deleteEmployee(@PathVariable Long id) {
		employeeService.deleteEmployee(id);
		return "redirect:/Admin/page/1?sortBy=id&order=ASC";
	}

	@GetMapping("Admin/CreateEmployeeForm")
	public String createEmployee(Model model) {
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "admin/CreateEmployeeForm";
	}

	@PostMapping("Admin/SaveEmployee")
	public String createEmployee(@ModelAttribute Employee employee) {
		employeeService.save(employee);
		return "redirect:/Admin/page/1?sortBy=id&order=ASC";
	}

	@GetMapping("Admin/UpdateEmployeeForm/{id}")
	public String updateEmployee(@PathVariable Long id, Model model) {
		model.addAttribute("employee", employeeService.getEmployeeById(id));
		return "admin/UpdateEmployeeForm";
	}

	@PostMapping("Admin/UpdateEmployee/{id}")
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
		return "redirect:/Admin/page/1?sortBy=id&order=ASC";
	}

	@GetMapping("Admin/Search")
	public String search(Model model, @RequestParam String keyword) {
		if (keyword.length() > 3) {
			int pageSize = 10;
			Page<Employee> page = employeeService.searchEmployee(keyword, 0, pageSize);
			model.addAttribute("employees", page.getContent());
			model.addAttribute("totalPages", page.getTotalPages());
			model.addAttribute("totalItems", page.getTotalElements());
			model.addAttribute("keyword", keyword);
			model.addAttribute("currentPage", 1);
			return "admin/AdminHome";
		}
		return "redirect:/Admin/page/1?sortBy=id&order=ASC";
	}

	@GetMapping("Admin/page/{pageNo}")
	public String getSomeEmployees(Model model, @PathVariable int pageNo, @RequestParam("sortBy") String sortBy,
			@RequestParam("order") String order, @RequestParam(defaultValue = "", required = false) String keyword) {
		int pageSize = 10;

		sortBy = sortBy.trim();
		Page<Employee> page = employeeService.findPaginated(pageNo - 1, pageSize, sortBy, order);

		model.addAttribute("employees", page.getContent());
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("sortBy", sortBy);
		model.addAttribute("order", order);
		model.addAttribute("keyword", "");
		model.addAttribute("reverseOrder", order.equalsIgnoreCase("asc") ? "desc" : "asc");
		model.addAttribute("welcomeMsg", "You are logged in as Admin!");

		return "admin/AdminHome";
	}
}
