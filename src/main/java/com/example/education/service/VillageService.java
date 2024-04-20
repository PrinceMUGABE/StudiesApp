package com.example.education.service;

import com.example.education.modal.Cell;
import com.example.education.modal.District;
import com.example.education.modal.Sector;
import com.example.education.modal.Village;
import java.util.List;
import java.util.Optional;

public interface VillageService {
    Village saveVillage(Village village);
    List<Village> getAllVillages();
    Village updateVillage(Village village);
    Village findVillageByName(String name);
    void deleteVillage(Long id);
    // List<Village> getAllVillagesByCell(Cell cell);
    // List<Village> getVillagesBySector(Sector sector);
  
    Optional<Village> findVillageById(Long id);
    List<Village> findByCell(Optional<Cell> cell);
}
