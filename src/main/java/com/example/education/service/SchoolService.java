package com.example.education.service;

import java.util.List;
import java.util.Optional;

import com.example.education.modal.School;

public interface SchoolService {
    School saveSchool(School school);
    List<School> displayAllSchools();
    School updateSchool(School user);
    void deleteSchool(Long school);
    Optional<School> findSchoolBySchoolCode(String schoolCode);
    Optional<School> findSchoolById(Long id);
    
}
