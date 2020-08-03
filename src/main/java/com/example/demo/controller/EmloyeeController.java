package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.module.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:4201"})
@RequestMapping("api/v1")
  public class EmloyeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return (List<Employee>) employeeRepository.findAll();
    }
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) throws ResourceNotFoundException {
      Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Empoyee not found " +
              "for this id ::" + id));
      return ResponseEntity.ok().body(employee);
    }
    @PostMapping("/employees")
  public Employee createEmployee(@Valid @RequestBody Employee employee ) {
      return employeeRepository.save(employee);
    }
    @PutMapping("/employees/{id}")
  public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,@Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
      Employee employee = employeeRepository.findById(id)
              .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id ::"+id));
      employee.setEmail(employeeDetails.getEmail());
      employee.setFistName(employeeDetails.getFistName());
      employee.setLastName(employee.getLastName());
      final Employee updatedEmployee= employeeRepository.save(employee);
      return ResponseEntity.ok(updatedEmployee);

    }
    @DeleteMapping("/employees/{id}")
  public Map<String, Boolean> deleteEmployeeById (@PathVariable Long id) throws ResourceNotFoundException {
      Employee employee = employeeRepository.findById(id)
              .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id ::"+id));
      employeeRepository.deleteById(id);
      Map<String, Boolean> response = new HashMap<>();
      response.put("deleted",true);
      return response;
    }
}