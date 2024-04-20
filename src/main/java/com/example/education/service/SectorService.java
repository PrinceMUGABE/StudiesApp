package com.example.education.service;

import com.example.education.modal.Sector;
import java.util.List;
import java.util.Optional;

public interface SectorService {
    Sector saveSector(Sector sector);
    List<Sector> displayAllSectors();
    Sector updateSector(Sector sector);
    Sector findSectorByName(String name);
    void deleteSector(Long id);
    Optional<Sector> findSectorById(Long id);
    List<Sector> findSectorsByDistrictId(Long id);

}
