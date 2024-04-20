package com.example.education.serviceImplementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.education.modal.School;
import com.example.education.repository.SchoolRepository;
import com.example.education.service.SchoolService;
@Service
public class SchoolServiceImplementation implements SchoolService{
    @Autowired
    private SchoolRepository repo;
    

    

    @Override
    public School saveSchool(School school) {
        return repo.save(school);
    }

    @Override
    public List<School> displayAllSchools() {
        return repo.findAll();
    }

    @Override
    public School updateSchool(School user) {
        Optional<School> savedSchool = repo.findById(user.getId());
        if (savedSchool.isPresent()) {
            School existingSchool = savedSchool.get();
            existingSchool.setName(user.getName());
            existingSchool.setSchoolCode(user.getSchoolCode());
            existingSchool.setEmployees(user.getEmployees());
            existingSchool.setDepartments(user.getDepartments());
            return repo.save(existingSchool);
        } else {
            throw new RuntimeException("User not found with id: " + user.getId());
        }
    }

    @Override
    public void deleteSchool(Long school) {
        repo.deleteById(school);
    }

    @Override
    public Optional<School> findSchoolBySchoolCode(String schoolCode) {
        return repo.findSchoolBySchoolCode(schoolCode);
    }

    @Override
    public Optional<School> findSchoolById(Long id) {
        return repo.findById(id);
    }

    


}
