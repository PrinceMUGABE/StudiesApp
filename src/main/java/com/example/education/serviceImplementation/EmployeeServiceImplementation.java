package com.example.education.serviceImplementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.education.modal.Employee;
import com.example.education.repository.EmployeeRepository;
import com.example.education.service.Employeeservice;

@Service
public class EmployeeServiceImplementation implements Employeeservice{

    @Autowired
    private EmployeeRepository repo; 

    @Override
    public Employee saveEmployee(Employee employee) {
        return repo.save(employee);
    }

    @Override
    public List<Employee> displayAllEmployee() {
        return repo.findAll();    }

    @Override
    public Optional<Employee> findEmployeeById(Long id) {
        return repo.findById(id);
    }

    // @Override
    // public Optional<Employee> findEmployeeByPhone(String phone) {
    //     return repo.findEmployeebyPhone(phone);
    // }

    @Override
    public String updateEmployee(Employee employee) {
        Optional<Employee> savedEmployee = repo.findById(employee.getId());
        if (savedEmployee.isPresent()) {
            Employee updateEmployee = savedEmployee.get();
            updateEmployee.setFirstname(employee.getFirstname());
            updateEmployee.setLastname(employee.getLastname());
            updateEmployee.setPhone(employee.getPhone());
            updateEmployee.setSchool(employee.getSchool());
            updateEmployee.setIdentityNo(employee.getIdentityNo());
            updateEmployee.setDitrict(employee.getDitrict());
            updateEmployee.setSector(employee.getSector());
            updateEmployee.setCell(employee.getCell());
            
            Employee updatedEmployee = repo.save(updateEmployee);
            if (updatedEmployee != null) {

                return "Employee updated successfully";
            }else{
                return "Failed to update empoyee";
            }
        }else{
            return "Employee not found";
        }
    }

    @Override
    public void deleteEmployee(Long id) {
        repo.deleteById(id);
    }

}
