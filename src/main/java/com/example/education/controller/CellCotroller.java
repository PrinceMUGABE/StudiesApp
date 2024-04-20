package com.example.education.controller;

import java.util.List;
import java.util.Optional;

import org.hibernate.internal.util.collections.ConcurrentReferenceHashMap.Option;
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
import com.example.education.serviceImplementation.CellServiceImplementation;
import com.example.education.serviceImplementation.DistrictServiceImplementation;
import com.example.education.serviceImplementation.SectorServiceImplementation;

@Controller
public class CellCotroller {
    @Autowired
    private DistrictServiceImplementation districtServiceImplementation;
    @Autowired
    private SectorServiceImplementation sectorServiceImplementation;
    @Autowired
    private CellServiceImplementation cellServiceImplementation;


    @GetMapping("/list_cells")
    public String showAllAvailableCells(Model model) {
        List<Cell> cells = cellServiceImplementation.getAllCells();
        model.addAttribute("cells", cells);
        return "manage_all_cells";
    }


    @GetMapping("/create-new-cell")
    public String showNewCellCreationPage(Model model) {

        List<District> district = districtServiceImplementation.getAllDistricts();
        List<Sector> sectors = sectorServiceImplementation.displayAllSectors();
        model.addAttribute("districts", district);
        model.addAttribute("sectors", sectors);

        return "add_new_cell";
    }


    @PostMapping("/save_new_cell")
    public String saveNewCell(Model model, @RequestParam String name, @RequestParam Long districtId, @RequestParam Long sectorId) {

        Cell existingCell = cellServiceImplementation.findCellByName(name);
        if (existingCell != null) {
            model.addAttribute("error", "Cell with name '" + name + "' already exists");
            return "redirect:/list_cells";
        }

        Optional<District> savedDistrict = districtServiceImplementation.findDistrictById(districtId);
        if (savedDistrict.isEmpty()) {
            model.addAttribute("error", "District not found");
            return "redirect:/list_cells";
        }

        List<Sector> savedSectors = sectorServiceImplementation.findSectorsByDistrict(savedDistrict);
        if (savedSectors.isEmpty()) {
            model.addAttribute("error", "No sectors found for the selected district");
            return "redirect:/list_cells";
        }

        Optional<Sector> selectedSector = savedSectors.stream()
                .filter(sector -> sector.getId().equals(sectorId))
                .findFirst();

        if (selectedSector.isEmpty()) {
            model.addAttribute("error", "Selected sector not found for the selected district");
            return "redirect:/list_cells";
        }

        Cell newCell = new Cell();
        newCell.setName(name);
        newCell.setSector(selectedSector.get());
        cellServiceImplementation.saveCell(newCell);

        model.addAttribute("success", "Cell saved successfully");
        return "redirect:/list_cells";
    }

    @PostMapping("/delete-cell/{id}")
    public String deleteCell(Model model, @PathVariable Long id) {
        try {
            cellServiceImplementation.deleteCell(id);
            model.addAttribute("message", "Cell deleted successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to delete Cell with id: " + id);
        }

        return "redirect:/list_cells";
    }

    @GetMapping("/edit-cell/{id}")
    public String editCell(Model model, @PathVariable Long id,@PathVariable Long districtId,@PathVariable Long sectorId,@PathVariable String name) {
        List<District> districts = districtServiceImplementation.getAllDistricts();
        List<Sector> sectors = sectorServiceImplementation.displayAllSectors();
        model.addAttribute("districts", districts);
        model.addAttribute("sectors", sectors);
        return "edit-cell";
        
    }


    @PostMapping("/update-cell")
    public String updateCell(Model model, @RequestParam Long id, @RequestParam String name, @RequestParam Long districtId, @RequestParam Long sectorId) {
        
        Optional<Cell> existingCell = cellServiceImplementation.findCellById(id);

        if (existingCell.isPresent()) {
            Optional<District> savedDistrict = districtServiceImplementation.findDistrictById(districtId);
            if (savedDistrict.isEmpty()) {
                model.addAttribute("error", "District not found");
                return "redirect:/list_cells";
            }

            List<Sector> savedSectors = sectorServiceImplementation.findSectorsByDistrict(savedDistrict);
            if (savedSectors.isEmpty()) {
                model.addAttribute("error", "No sectors found for the selected district");
                return "redirect:/list_cells";
            }

            Optional<Sector> selectedSector = savedSectors.stream()
                    .filter(sector -> sector.getId().equals(sectorId))
                    .findFirst();

            if (selectedSector.isEmpty()) {
                model.addAttribute("error", "Selected sector not found for the selected district");
                return "redirect:/list_cells";
            }

            Cell newCell = new Cell();
            newCell.setName(name);
            newCell.setSector(selectedSector.get());
            cellServiceImplementation.saveCell(newCell);

            model.addAttribute("success", "Cell saved successfully");
            return "redirect:/list_cells";
        }else{
            model.addAttribute("error", "Cell not exist");
            return "redirect:/list_cells";
        }

    }
    


}


