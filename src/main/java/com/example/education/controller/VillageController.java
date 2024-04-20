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
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.education.modal.Cell;
import com.example.education.modal.District;
import com.example.education.modal.Sector;
import com.example.education.modal.Village;
import com.example.education.serviceImplementation.CellServiceImplementation;
import com.example.education.serviceImplementation.DistrictServiceImplementation;
import com.example.education.serviceImplementation.SectorServiceImplementation;
import com.example.education.serviceImplementation.VillageServiceImplementation;

@Controller
public class VillageController {
    @Autowired
    private DistrictServiceImplementation districtServiceImplementation;
    @Autowired
    private SectorServiceImplementation sectorServiceImplementation;
    @Autowired
    private CellServiceImplementation cellServiceImplementation;
    @Autowired
    private VillageServiceImplementation villageServiceImplementation;

    @GetMapping("/list_villages")
    public String showAllAvailableVillages(Model model) {
        List<Village> villages = villageServiceImplementation.getAllVillages();
        model.addAttribute("villages", villages);
        return "manage_all_villages";
    }

    @GetMapping("/create-new-village")
    public String showNewVillageCreationPage(Model model) {
        List<District> districts = districtServiceImplementation.getAllDistricts();
        model.addAttribute("districts", districts);
        return "add_new_village";
    }

    @PostMapping("/save_new_village")
    public String saveNewVillage(Model model, @RequestParam String name, @RequestParam Long districtId, @RequestParam Long sectorId, @RequestParam Long cellId) {
        Village existingVillage = villageServiceImplementation.findVillageByName(name);
        if (existingVillage != null) {
            model.addAttribute("error", "Village with name '" + name + "' already exists");
            return "redirect:/list_villages";
        }

        Optional<District> savedDistrict = districtServiceImplementation.findDistrictById(districtId);
        if (savedDistrict.isEmpty()) {
            model.addAttribute("error", "District not found");
            return "redirect:/list_villages";
        }

        List<Sector> savedSectors = sectorServiceImplementation.findSectorsByDistrict(savedDistrict);
        if (savedSectors.isEmpty()) {
            model.addAttribute("error", "No sectors found for the selected district");
            return "redirect:/list_villages";
        }

        Optional<Sector> selectedSector = savedSectors.stream()
                .filter(sector -> sector.getId().equals(sectorId))
                .findFirst();

        if (selectedSector.isEmpty()) {
            model.addAttribute("error", "Selected sector not found for the selected district");
            return "redirect:/list_villages";
        }

        List<Cell> savedCells = cellServiceImplementation.findCellsBySector(selectedSector);
        if (savedCells.isEmpty()) {
            model.addAttribute("error", "No cells found for the selected sector");
            return "redirect:/list_villages";
        }

        Optional<Cell> selectedCell = savedCells.stream()
                .filter(cell -> cell.getId().equals(cellId))
                .findFirst();

        if (selectedCell.isEmpty()) {
            model.addAttribute("error", "Selected cell not found for the selected sector");
            return "redirect:/list_villages";
        }

        Village newVillage = new Village();
        newVillage.setName(name);
        newVillage.setCell(selectedCell.get()); // Set the selected cell
        villageServiceImplementation.saveVillage(newVillage);

        model.addAttribute("success", "Village saved successfully");
        return "redirect:/list_villages";
    }

    @PostMapping("/delete-village/{id}")
    public String deleteVillage(Model model, @PathVariable Long id) {
        try {
            villageServiceImplementation.deleteVillage(id);
            model.addAttribute("message", "Village deleted successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to delete Village with id: " + id);
        }

        return "redirect:/list_villages";
    }

    


    

}
