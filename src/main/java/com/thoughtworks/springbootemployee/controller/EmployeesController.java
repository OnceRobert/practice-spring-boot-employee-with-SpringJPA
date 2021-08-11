package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employees;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    @Autowired
    private EmployeeService employeeService;
    private static List<Employees> employeesList = new ArrayList<>();

    public EmployeesController(EmployeeService employeeService) {
        employeesList.add(new Employees(1, "Angelo", 23, "male", 1000, 1));
        employeesList.add(new Employees(2, "Angela", 26, "female", 900, 1));
    }


    @GetMapping()
    public List<Employees> getAllEmployees() {
        return employeeService.getEmployeesList();
    }


    @GetMapping("/{employeeId}")
    public Employees getEmployeeById(@PathVariable Integer employeeId) {
        return employeesList.stream()
                .filter(employee -> employee.getId().equals(employeeId))
                .findFirst()
                .orElse(null);
    }

    @GetMapping(params = {"gender"})
    public List<Employees> getEmployeesByGender(@RequestParam("gender") String givenGender) {
        return employeesList.stream()
                .filter(employee -> employee.getGender().equals(givenGender))
                .collect(Collectors.toList());
    }

    @GetMapping(params = {"index", "size"})
    public List<Employees> getEmployeesByPagination(@RequestParam int index, @RequestParam int size) {
        return employeesList.stream().skip((long) (index - 1) * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    @PostMapping
    public void addEmployee(@RequestBody Employees newEmployee) {
        Employees employeesToBeAdded = new Employees(employeesList.size() + 1,
                newEmployee.getName(),
                newEmployee.getAge(),
                newEmployee.getGender(),
                newEmployee.getSalary(),
                newEmployee.getCompanyId());
        employeesList.add(employeesToBeAdded);

    }

    @PutMapping(path = "/{employeeId}")
    public Employees updateEmployee(@PathVariable Integer employeeId, @RequestBody Employees employeesToBeUpdated) {
        return employeesList.stream().filter(employees1 -> employees1.getId().equals(employeeId))
                .map(employees1 -> updateEmployeesInfo(employees1, employeesToBeUpdated))
                .findFirst().get();
    }

    private Employees updateEmployeesInfo(Employees employees1, Employees employeesToBeUpdated) {

        if (employeesToBeUpdated.getGender() != null) {
            employees1.setGender(employeesToBeUpdated.getGender());
        }
        if (employeesToBeUpdated.getAge() != null) {
            employees1.setAge(employeesToBeUpdated.getAge());
        }
        if (employeesToBeUpdated.getName() != null) {
            employees1.setName(employeesToBeUpdated.getName());
        }
        if (employeesToBeUpdated.getSalary() != null) {
            employees1.setSalary(employeesToBeUpdated.getSalary());
        }
        return employees1;
    }

    @DeleteMapping(path = "/{employeeId}")
    public void deleteEmployee(@PathVariable Integer employeeId) {
        Employees employeesToRemove = employeesList.stream()
                .filter(employees -> employees.getId().equals(employeeId))
                .findFirst().get();

        employeesList.remove(employeesToRemove);
    }
}
