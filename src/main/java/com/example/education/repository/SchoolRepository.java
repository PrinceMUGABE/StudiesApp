package com.example.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

import com.example.education.modal.School;
@Repository
public interface SchoolRepository extends JpaRepository<School, Long>{
    Optional<School> findSchoolBySchoolCode(String schoolCode);
    
}
