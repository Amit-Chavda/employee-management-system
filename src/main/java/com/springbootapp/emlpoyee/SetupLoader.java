package com.springbootapp.emlpoyee;

import com.springbootapp.emlpoyee.entity.Employee;
import com.springbootapp.emlpoyee.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class SetupLoader implements ApplicationListener<ContextRefreshedEvent> {


    private final EmployeeRepository employeeRepository;

    public SetupLoader(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (employeeRepository.findByEmail("admin@gmail.com").isPresent()) {
            return;
        }
        Employee employee = new Employee();
        employee.setDepartment("ADMIN");
        employee.setEmail("admin@gmail.com");
        //Test!@#123
        employee.setPassword("$2a$10$9MI91AWrKaVHmSY5OZuDT.y1AGFhB.VgSaaelHxcQMqTnxtBtIZdC");
        employee.setGender("M");
        employee.setFirstName("admin");
        employee.setLastName("admin");
        employee.setJobTitle("ADMIN");
        employee.setRoles("ROLE_ADMIN");
        employeeRepository.save(employee);


        Employee employee2 = new Employee();
        employee2.setDepartment("Employee");
        employee2.setEmail("employee@gmail.com");
        //Test!@#123
        employee2.setPassword("$2a$10$9MI91AWrKaVHmSY5OZuDT.y1AGFhB.VgSaaelHxcQMqTnxtBtIZdC");
        employee2.setGender("M");
        employee2.setFirstName("employee");
        employee2.setLastName("employee");
        employee2.setJobTitle("Employee");
        employee2.setRoles("ROLE_USER");
        employeeRepository.save(employee2);
    }
}