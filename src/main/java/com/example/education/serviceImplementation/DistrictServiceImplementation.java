package com.example.education.serviceImplementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.education.modal.District;
import com.example.education.repository.DistrictRepository;
import com.example.education.service.DistrictService;

@Service
public class DistrictServiceImplementation  implements DistrictService{

    @Autowired
    private DistrictRepository repo;
    @Override
    public District saveDistrict(District district) {
        return repo.save(district);
    }

    @Override
    public List<District> getAllDistricts() {
        return repo.findAll();
    }

    @Override
    public void deleteDistrict(Long id) {
        repo.deleteById(id);
    }

    @Override
    public District updateDistrict(District district) {
        District savedDistrict = repo.findById(district.getId()).orElse(null);
        if (savedDistrict!=null) {
            savedDistrict.setName(district.getName());
            return repo.save(savedDistrict);
        }else{
            return repo.save(savedDistrict);
        }
    }

    @Override
    public Optional<District> findDistrictByName(String districtName) {
        return repo.findByName(districtName);
    }

    @Override
    public Optional<District> findDistrictById(Long id) {
        return repo.findById(id);
    }

}
