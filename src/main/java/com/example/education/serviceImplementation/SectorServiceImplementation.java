package com.example.education.serviceImplementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.education.modal.District;
import com.example.education.modal.Sector;
import com.example.education.repository.SectorRepository;
import com.example.education.service.SectorService;

@Service
public class SectorServiceImplementation implements SectorService{

    @Autowired
    private SectorRepository repo;
    @Override
    public Sector saveSector(Sector sector) {
        return repo.save(sector);
    }

    @Override
    public List<Sector> displayAllSectors() {
        return repo.findAll();
    }

    @Override
    public Sector updateSector(Sector sector) {
        Sector savedSector = repo.findById(sector.getId()).orElse(null);
        if (savedSector!=null) {
            savedSector.setName(sector.getName());
            savedSector.setDistrict(sector.getDistrict());
            return repo.save(savedSector);
        }else{
            return repo.save(sector);
        }
    }

    @Override
    public Sector findSectorByName(String name) {
        return repo.findSectorByName(name);
    }

    @Override
    public void deleteSector(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Optional<Sector> findSectorById(Long id) {
        return repo.findById(id);
    }

    public List<Sector> findSectorsByDistrict(Optional<District> savedDistrict) {
        return repo.findSectorsByDistrict(savedDistrict);
    }

    @Override
    public List<Sector> findSectorsByDistrictId(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findSectorsByDistrictId'");
    }


}
