package com.example.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.education.modal.District;
import com.example.education.modal.Sector;
import java.util.List;
import java.util.Optional;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long>{
    Sector findSectorByName(String name);
    List<Sector> getAllSectorsByDistrictName(String districtname);
    List<Sector> findSectorsByDistrict(Optional<District> savedDistrict);
}