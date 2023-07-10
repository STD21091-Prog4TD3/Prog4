package com.prog.TD.controller;

import com.prog.TD.modele.Employee;
import com.prog.TD.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/")
    public String getEmployees(Model model) {
        List<Employee> employees = employeeRepository.findAll();
        model.addAttribute("employees", employees);
        return "listEmployee";
    }

    @GetMapping("/add")
    public String showAddEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "addEmployee";
    }
    @PostMapping("/add")
    public String addEmployee(@ModelAttribute("employee") Employee employee,
                              @RequestParam("photoFile") MultipartFile photoFile) throws IOException {
        if (!photoFile.isEmpty()) {
            employee.setPhoto(photoFile.getBytes());
        }
        employeeRepository.save(employee);
        return "redirect:/";
    }

    @GetMapping("/employee/{id}")
    public String getEmployeeDetails(@PathVariable("id") Long id, Model model) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee id: " + id));
        model.addAttribute("employee", employee);
        return "detailsEmployee";
    }
}