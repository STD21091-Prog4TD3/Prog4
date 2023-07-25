package com.prog.TD.service;
import com.prog.TD.modele.Employee;
import com.prog.TD.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }
    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }
    public void updateEmployee(Employee employee) {
        employeeRepository.saveAndFlush(employee);
    }
    public List<Employee> getFilteredEmployees(
            String firstName,
            String lastName,
            String gender,
            String jobFunction,
            LocalDate hiringDateFrom,
            LocalDate hiringDateTo,
            LocalDate departureDateFrom,
            LocalDate departureDateTo
    ) {
        return employeeRepository.findFilteredEmployees(
                firstName, lastName, gender, jobFunction,
                hiringDateFrom, hiringDateTo, departureDateFrom, departureDateTo
        );
    }
}

