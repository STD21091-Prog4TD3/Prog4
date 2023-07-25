package com.prog.TD.controller;

import com.opencsv.CSVWriter;
import com.prog.TD.modele.Employee;
import com.prog.TD.modele.SearchParams;
import com.prog.TD.service.EmployeeService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller

public class EmployeeController {
    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @GetMapping("/")
    public String showEmployeeList(@RequestParam(value = "firstName", required = false) String firstName,
                                   @RequestParam(value = "lastName", required = false) String lastName,
                                   @RequestParam(value = "gender", required = false) String gender,
                                   @RequestParam(value = "jobFunction", required = false) String jobFunction,
                                   @RequestParam(value = "hiringDateFrom", required = false) LocalDate hiringDateFrom,
                                   @RequestParam(value = "hiringDateTo", required = false) LocalDate hiringDateTo,
                                   @RequestParam(value = "departureDateFrom", required = false) LocalDate departureDateFrom,
                                   @RequestParam(value = "departureDateTo", required = false) LocalDate departureDateTo,
                                   @RequestParam(value = "sort", required = false) String sort,
                                   Model model) {

        List<Employee> employees = employeeService.getAllEmployees();

        if (firstName != null && !firstName.isEmpty()) {
            employees = employees.stream()
                    .filter(employee -> employee.getFirstName().contains(firstName))
                    .collect(Collectors.toList());
        }
        if (lastName != null && !lastName.isEmpty()) {
            employees = employees.stream()
                    .filter(employee -> employee.getFirstName().contains(lastName))
                    .collect(Collectors.toList());
        }
        if (gender != null && !gender.isEmpty()) {
            employees = employees.stream()
                    .filter(employee -> employee.getGender().equals(gender))
                    .collect(Collectors.toList());
        }
        if (jobFunction != null && !jobFunction.isEmpty()) {
            employees = employees.stream()
                    .filter(employee -> employee.getJobFunction().contains(jobFunction))
                    .collect(Collectors.toList());
        }
        if (hiringDateFrom != null) {
            employees = employees.stream().filter(employee -> employee.getHiringDate().isAfter(hiringDateFrom.minusDays(1)))
                    .collect(Collectors.toList());
        }
        if (hiringDateTo != null) {
            employees = employees.stream()
                    .filter(employee -> employee.getHiringDate().isBefore(hiringDateTo.plusDays(1)))
                    .collect(Collectors.toList());
        }
        if (departureDateFrom != null) {
            employees = employees.stream()
                    .filter(employee -> employee.getDepartureDate().isAfter(departureDateFrom.minusDays(1)))
                    .collect(Collectors.toList());
        }
        if (departureDateTo != null) {
            employees = employees.stream()
                    .filter(employee -> employee.getDepartureDate().isBefore(departureDateTo.plusDays(1)))
                    .collect(Collectors.toList());
        }
        if (sort != null && !sort.isEmpty()) {
            String[] sortParams = sort.split(",");
            if (sortParams.length == 2) {
                String sortField = sortParams[0];
                String sortOrder = sortParams[1];
                employees = employees.stream()
                        .sorted((e1, e2) -> {
                            int result = 0;
                            if ("asc".equals(sortOrder)) {
                                switch (sortField) {
                                    case "firstName":
                                        result = e1.getFirstName().compareTo(e2.getFirstName());
                                        break;
                                    case "lastName":
                                        result = e1.getLastName().compareTo(e2.getLastName());
                                        break;
                                    case "gender":
                                        result = e1.getGender().compareTo(e2.getGender());
                                        break;
                                    case "jobFunction":
                                        result = e1.getJobFunction().compareTo(e2.getJobFunction());
                                        break;
                                    case "hiringDate":
                                        result = e1.getHiringDate().compareTo(e2.getHiringDate());
                                        break;
                                    case "departureDate":
                                        result = e1.getDepartureDate().compareTo(e2.getDepartureDate());
                                        break;
                                }} else if ("desc".equals(sortOrder)) {
                                switch (sortField) {
                                    case "firstName":
                                        result = e2.getFirstName().compareTo(e1.getFirstName());
                                        break;
                                    case "lastName":
                                        result = e2.getLastName().compareTo(e1.getLastName());
                                        break;
                                    case "gender":
                                        result = e2.getGender().compareTo(e1.getGender());
                                        break;
                                    case "jobFunction":
                                        result = e2.getJobFunction().compareTo(e1.getJobFunction());
                                        break;
                                    case "hiringDate":
                                        result = e2.getHiringDate().compareTo(e1.getHiringDate());
                                        break;
                                    case "departureDate":
                                        result = e2.getDepartureDate().compareTo(e1.getDepartureDate());
                                        break;
                                }
                            }
                            return result;
                        })
                        .collect(Collectors.toList());
            }
        }

        SearchParams searchParams = new SearchParams();
        searchParams.setFirstName(firstName);
        searchParams.setLastName(lastName);
        searchParams.setGender(gender);
        searchParams.setJobFunction(jobFunction);
        searchParams.setHiringDateFrom(hiringDateFrom);
        searchParams.setHiringDateTo(hiringDateTo);
        searchParams.setDepartureDateFrom(departureDateFrom);
        searchParams.setDepartureDateTo(departureDateTo);

        model.addAttribute("searchParams", searchParams);
        model.addAttribute("employees", employees);
        return "listEmployee";
    }
    @GetMapping("/employee/{id}")
    public String showEmployee(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "detailsEmployee";
    }

    @GetMapping("/add")
    public String showAddEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "addEmployee";
    }
    @PostMapping("/add")
    public String addEmployee(@ModelAttribute Employee employee,
                              @RequestParam("photoFile") MultipartFile photoFile) {
        try {
            if (!photoFile.isEmpty()) {
                employee.setPhoto(photoFile.getBytes());
            }
            employeeService.saveEmployee(employee);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }
    @GetMapping("/employee/{id}/edit")
    public String showEditEmployeeForm(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "editEmployee";
    }

    @PostMapping("employee/{id}")
    public String updateEmployee(@PathVariable Long id,
                                 @ModelAttribute Employee updatedEmployee,
                                 @RequestParam("photoFile") MultipartFile photoFile) {
        Employee existingEmployee = employeeService.getEmployeeById(id);
        if (existingEmployee != null) {
            existingEmployee.setEmployeeNumber(updatedEmployee.getEmployeeNumber());
            existingEmployee.setLastName(updatedEmployee.getLastName());
            existingEmployee.setFirstName(updatedEmployee.getFirstName());
            existingEmployee.setBirthdate(updatedEmployee.getBirthdate());
            existingEmployee.setGender(updatedEmployee.getGender());
            existingEmployee.setAddress(updatedEmployee.getAddress());
            existingEmployee.setPhoneNumber(updatedEmployee.getPhoneNumber());
            existingEmployee.setPersonalEmail(updatedEmployee.getPersonalEmail());
            existingEmployee.setWorkEmail(updatedEmployee.getWorkEmail());
            existingEmployee.setCinNumber(updatedEmployee.getCinNumber());
            existingEmployee.setCinDeliveryDate(updatedEmployee.getCinDeliveryDate());
            existingEmployee.setCinDeliveryPlace(updatedEmployee.getCinDeliveryPlace());
            existingEmployee.setJobFunction(updatedEmployee.getJobFunction());
            existingEmployee.setNumberOfChildren(updatedEmployee.getNumberOfChildren());
            existingEmployee.setHiringDate(updatedEmployee.getHiringDate());
            existingEmployee.setDepartureDate(updatedEmployee.getDepartureDate());
            existingEmployee.setSocioProfessionalCategory(updatedEmployee.getSocioProfessionalCategory());
            existingEmployee.setCnapsNumber(updatedEmployee.getCnapsNumber());

            try {
                if (!photoFile.isEmpty()) {
                    existingEmployee.setPhoto(photoFile.getBytes());
                }
                employeeService.updateEmployee(existingEmployee);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "redirect:/";
        }
        return "not_found";
    }
    @GetMapping("/download-employees-csv")
    public void downloadEmployeesCSV(
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "gender", required = false) String gender,
            @RequestParam(value = "jobFunction", required = false) String jobFunction,
            @RequestParam(value = "hiringDateFrom", required = false) LocalDate hiringDateFrom,
            @RequestParam(value = "hiringDateTo", required = false) LocalDate hiringDateTo,
            @RequestParam(value = "departureDateFrom", required = false) LocalDate departureDateFrom,
            @RequestParam(value = "departureDateTo", required = false) LocalDate departureDateTo,
            HttpServletResponse response
    ) throws IOException {

        List<Employee> employees = employeeService.getAllEmployees();

        if (firstName != null && !firstName.isEmpty()) {
            employees = employees.stream()
                    .filter(employee -> employee.getFirstName().contains(firstName))
                    .collect(Collectors.toList());
        }
        if (lastName != null && !lastName.isEmpty()) {
            employees = employees.stream()
                    .filter(employee -> employee.getLastName().contains(lastName))
                    .collect(Collectors.toList());
        }
        if (gender != null && !gender.isEmpty()) {
            employees = employees.stream()
                    .filter(employee -> employee.getGender().equals(gender))
                    .collect(Collectors.toList());
        }
        if (jobFunction != null && !jobFunction.isEmpty()) {
            employees = employees.stream()
                    .filter(employee -> employee.getJobFunction().contains(jobFunction))
                    .collect(Collectors.toList());
        }
        if (hiringDateFrom != null) {
            employees = employees.stream().filter(employee -> employee.getHiringDate().isAfter(hiringDateFrom.minusDays(1)))
                    .collect(Collectors.toList());
        }
        if (hiringDateTo != null) {
            employees = employees.stream()
                    .filter(employee -> employee.getHiringDate().isBefore(hiringDateTo.plusDays(1)))
                    .collect(Collectors.toList());
        }
        if (departureDateFrom != null) {
            employees = employees.stream()
                    .filter(employee -> employee.getDepartureDate().isAfter(departureDateFrom.minusDays(1)))
                    .collect(Collectors.toList());
        }
        if (departureDateTo != null) {
            employees = employees.stream()
                    .filter(employee -> employee.getDepartureDate().isBefore(departureDateTo.plusDays(1)))
                    .collect(Collectors.toList());
        }

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"employees.csv\"");
        try (CSVWriter csvWriter = new CSVWriter(response.getWriter())) {
            String[] header = {"ID", "Nom","Date de naissance","Adress","CIN","Lieu du CIN","CNAPS","Poste","Genre","Email","Date d'embauche","Date de depart"};
            csvWriter.writeNext(header);

            for (Employee employee : employees) {
                String[] data = {
                        String.valueOf(employee.getId()),
                        employee.getFirstName() + " " + employee.getLastName(),
                        String.valueOf(employee.getBirthdate()),
                        employee.getAddress(),
                        employee.getCinNumber(),
                        employee.getPhoneNumber(),
                        employee.getCinDeliveryPlace(),
                        employee.getCnapsNumber(),
                        employee.getJobFunction(),
                        employee.getGender(),
                        employee.getPersonalEmail(),
                        String.valueOf(employee.getDepartureDate()),
                        String.valueOf(employee.getHiringDate()),
                        String.valueOf(employee.getCinDeliveryDate()),
                };
                csvWriter.writeNext(data);
            }
        }
    }

}
