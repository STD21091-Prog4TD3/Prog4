package com.prog.TD.service;

import com.prog.TD.modele.Employee;
import com.prog.TD.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;


@Service
public class EmployeeService{
    private final EmployeeRepository employeeRepository;
    private final EntityManager entityManager;


    public EmployeeService(EmployeeRepository employeeRepository, EntityManager entityManager) {
        this.employeeRepository = employeeRepository;
        this.entityManager = entityManager;
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

    public List<Employee> customSearch(String firstName, String lastName, String gender, String jobFunction, String hiringDate, String departureDate, String sort) {
        StringBuilder queryString = new StringBuilder("SELECT e FROM Employee e WHERE 1 = 1");
        if (firstName != null && !firstName.isEmpty()) {
            queryString.append(" AND e.firstName LIKE :firstName");
        }
        if (lastName != null && !lastName.isEmpty()) {
            queryString.append(" AND e.lastName LIKE :lastName");
        }
        if (gender != null && !gender.isEmpty()) {
            queryString.append(" AND e.gender LIKE  :gender");
        }
        if (jobFunction != null && !jobFunction.isEmpty()) {
            queryString.append(" AND e.jobFunction LIKE :jobFunction");
        }
        if (hiringDate != null && !hiringDate.isEmpty()) {
            queryString.append(" AND e.hiringDate = :hiringDate");
        }
        if (departureDate != null && !departureDate.isEmpty()) {
            queryString.append(" AND e.departureDate = :departureDate");
        }

        if (!sort.isEmpty()) {
            String[] sortParams = sort.split(",");
            String sortField = sortParams[0];
            String sortOrder = sortParams[1];
            queryString.append(" ORDER BY e.").append(sortField).append(" ").append(sortOrder);
        }

        TypedQuery<Employee> query = entityManager.createQuery(queryString.toString(), Employee.class);

        if (firstName != null && !firstName.isEmpty()) {
            query.setParameter("firstName", "%" + firstName.trim() + "%");
        }
        if (lastName != null && !lastName.isEmpty()) {
            query.setParameter("lastName", "%" + lastName.trim() + "%");
        } if (gender != null && !gender.isEmpty()) {
            query.setParameter("gender", "%" + gender.trim() + "%");
        }
        if (jobFunction != null && !jobFunction.isEmpty()) {
            query.setParameter("jobFunction", "%" + jobFunction.trim() + "%");
        }
        if (hiringDate != null && !hiringDate.isEmpty()) {
            query.setParameter("hiringDate", LocalDate.parse(hiringDate));
        }
        if (departureDate != null && !departureDate.isEmpty()) {
            query.setParameter("departureDate", LocalDate.parse(departureDate));
        }

        return query.getResultList();
    }
}

