package com.springbootapp.emlpoyee.service;

import com.springbootapp.emlpoyee.entity.Employee;
import com.springbootapp.emlpoyee.entity.EmployeeDetails;
import com.springbootapp.emlpoyee.exception.ResourceNotFound;
import com.springbootapp.emlpoyee.repository.EmployeeRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmployeeDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    public EmployeeDetailsService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFound("Employee", "email", email));
        return new EmployeeDetails(employee);
    }

}
