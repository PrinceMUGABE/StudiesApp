package com.example.education.serviceImplementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.education.modal.Department;
import com.example.education.modal.School;
import com.example.education.repository.DepartmentRepository;
import com.example.education.service.DepartmentService;

@Service
public class DepartmentServiceImplementation implements DepartmentService{

    @Autowired
    private DepartmentRepository repo;
    @Override
    public Department saveDepartment(Department department) {
        return repo.save(department);
    }

    @Override
    public List<Department> getAllDepartments() {
        return repo.findAll();
    }

    @Override
    public String updateDepartment(Department department) {
        Optional<Department> savedDepartment = repo.findById(department.getId());
        if (savedDepartment.isPresent()) {
            Department updateDepartment = savedDepartment.get();
            updateDepartment.setName(department.getName());
            updateDepartment.setSchool(department.getSchool());
            updateDepartment.setTotalStudent(department.getTotalStudent());

           Department updatedDepartment = repo.save(updateDepartment);
           if (updatedDepartment!=null) {
             return "Department updated successfully";
           }else{
            return "Failed to update department";
           }

            
        }{
            return "Department not found";
        }
        
    }

    @Override
    public String deleteDepartmentById(Long id) {
        Optional<Department> foundDepartment = repo.findById(id);
        if (foundDepartment.isEmpty()) {
            return "Department not found";
        }

        repo.deleteById(foundDepartment.get().getId());
        return "Department deleted successfully";
    }

    // @Override
    // public List<Department> findDepartmentsByName(String name) {
    //     return repo.findDepartmentsByDepartmentName(name);
    // }

    @Override
    public Optional<Department> findDepartmentByName(String name) {
        return repo.findDepartmentByName(name);
    }

    @Override
    public Optional<Department> findDepartmentById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Optional<Department> findDepartmentBySchool(Optional<School> school) {
        return repo.findDepartmentBySchool(school);
    }

    @Override
    public List<Department> findDepartmentsBySchool(School school) {
        return repo.findDepartmentsBySchool(school);
    }

    

}
