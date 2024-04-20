package com.example.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.education.modal.District;
import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long>{

    Optional<District> findByName(String name);
}
