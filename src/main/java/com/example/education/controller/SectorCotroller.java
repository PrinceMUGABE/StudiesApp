package com.example.education.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.education.modal.District;
import com.example.education.modal.Sector;
import com.example.education.serviceImplementation.CellServiceImplementation;
import com.example.education.serviceImplementation.DistrictServiceImplementation;
import com.example.education.serviceImplementation.SectorServiceImplementation;
import com.example.education.serviceImplementation.VillageServiceImplementation;

@Controller
public class SectorCotroller {
    @Autowired
    private DistrictServiceImplementation districtServiceImplementation;
    @Autowired
    private SectorServiceImplementation sectorServiceImplementation;
    private CellServiceImplementation cellServiceImplementation;
    private VillageServiceImplementation villageServiceImplementation;

    @GetMapping("/list_sectors")
    public String showAllAvailabeSectors(Model model) {
        List<Sector> sectors = sectorServiceImplementation.displayAllSectors();
        model.addAttribute("sectors", sectors);

        return "manage_all_sectors";

    }

    @GetMapping("/create-new-sector")
    public String showNewSectorCreationPage(Model model) {
        List<District> districts = districtServiceImplementation.getAllDistricts();
        model.addAttribute("districts", districts);
        return "add_new_sectror";
    }

    @PostMapping("/save_new_sector")
    public String saveNewDistrict(Model modal, @RequestParam String name, @RequestParam Long district) {
        Sector savedSector = sectorServiceImplementation.findSectorByName(name);
        if (savedSector != null) {
            // Sector already exists, handle accordingly
            modal.addAttribute("error", "Sector with name " + name + " already exists");
            return "redirect:/list_sectors";
        }

        Optional<District> savedDistrict = districtServiceImplementation.findDistrictById(district);
        if (savedDistrict.isPresent()) {
            // District exists, create and save new sector
            Sector newSector = new Sector();
            newSector.setName(name);
            newSector.setDistrict(savedDistrict.get());
            sectorServiceImplementation.saveSector(newSector);
            modal.addAttribute("success", "Sector saved successfully");
            return "redirect:/list_sectors";
        } else {
            // District does not exist, handle accordingly
            modal.addAttribute("error", "District not found");
            return "redirect:/list_sectors";
        }
    }

    @PostMapping("/delete-sector/{id}")
    public String deleteDistrict(Model model, @PathVariable Long id) {
        try {
            sectorServiceImplementation.deleteSector(id);
            model.addAttribute("message", "Sector deleted successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to delete Sector with id: " + id);
        }
        // Redirect to display all schools after deletion
        return "redirect:/list_sectors";
    }

    @GetMapping("/edit-sector/{id}")
    public String editDistrict(Model model, @PathVariable Long id) {
        List<District> districts = districtServiceImplementation.getAllDistricts();
        model.addAttribute("districts", districts);
            return "edit-sector";
        
    }


    @PostMapping("/update-sector")
    public String updateDistrict(Model model, @RequestParam Long id, @RequestParam String name, @RequestParam Long districtId) {
        Optional<Sector> savedSectorOptional = sectorServiceImplementation.findSectorById(id);
        if (savedSectorOptional.isPresent()) {
            Sector savedSector = savedSectorOptional.get();

            if (!savedSector.getName().equals(name)) {
   
                Sector existingSector = sectorServiceImplementation.findSectorByName(name);
                if (existingSector != null) {
               
                    if (!existingSector.getDistrict().getId().equals(districtId)) {
                        District newDistrict = districtServiceImplementation.findDistrictById(districtId)
                                .orElseThrow(() -> new IllegalArgumentException("District not found with ID: " + districtId));
                        savedSector.setDistrict(newDistrict);
                        sectorServiceImplementation.updateSector(savedSector);
                    }
                    model.addAttribute("error", "Sector with name " + name + " already exists");
                    return "redirect:/list_sectors";
                } else {
                
                    savedSector.setName(name);
                    District newDistrict = districtServiceImplementation.findDistrictById(districtId)
                            .orElseThrow(() -> new IllegalArgumentException("District not found with ID: " + districtId));
                    savedSector.setDistrict(newDistrict);
                    sectorServiceImplementation.updateSector(savedSector);
                    model.addAttribute("success", "Sector updated successfully");
                    return "redirect:/list_sectors";
                }
            } else {
               
                District newDistrict = districtServiceImplementation.findDistrictById(districtId)
                        .orElseThrow(() -> new IllegalArgumentException("District not found with ID: " + districtId));
                if (!savedSector.getDistrict().getId().equals(districtId)) {
                    savedSector.setDistrict(newDistrict);
                    sectorServiceImplementation.updateSector(savedSector);
                }
                model.addAttribute("success", "Sector updated successfully");
                return "redirect:/list_sectors";
            }
        } else {
            model.addAttribute("error", "Sector not found with ID: " + id);
            return "redirect:/list_sectors";
        }
    }

}


