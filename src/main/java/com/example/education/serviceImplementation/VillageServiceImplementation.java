package com.example.education.serviceImplementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.education.modal.Cell;
import com.example.education.modal.Village;
import com.example.education.repository.CellRepository;
import com.example.education.repository.DistrictRepository;
import com.example.education.repository.SectorRepository;
import com.example.education.repository.VillageRepository;
import com.example.education.service.VillageService;



@Service
public class VillageServiceImplementation implements VillageService{

    @Autowired
    private VillageRepository repo;
    @Autowired
    private CellRepository cellRepository;

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Override
    public Village saveVillage(Village village) {
        return repo.save(village);
    }

    @Override
    public List<Village> getAllVillages() {
        return repo.findAll();
    
    }

    @Override
    public Village updateVillage(Village village) {
       Village savedVillage = repo.findById(village.getId()).orElse(null);
       if (savedVillage!=null) {
            savedVillage.setName(village.getName());
            savedVillage.setCell(village.getCell());
            return repo.save(savedVillage);
       }else{
        return repo.save(village);
       }
    }

    @Override
    public Village findVillageByName(String name) {
        return repo.findVillageByName(name);
    }

    @Override
    public void deleteVillage(Long id) {
        repo.deleteById(id);
    }



@Override
public Optional<Village> findVillageById(Long id) {
    return repo.findById(id);
}

public List<Village> findByCell(Optional<Cell> selectedCell) {
    return repo.findByCell(selectedCell);
}


}
