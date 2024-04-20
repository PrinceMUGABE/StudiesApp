package com.example.education.service;

import com.example.education.modal.District;
import java.util.List;
import java.util.Optional;

public interface DistrictService {

    District saveDistrict(District district);
    List<District> getAllDistricts();
    void deleteDistrict(Long districtName);
    District updateDistrict(District district);
    Optional<District> findDistrictByName(String districtName);
    Optional<District> findDistrictById(Long id);
    
    
}
