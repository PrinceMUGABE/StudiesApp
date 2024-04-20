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
import com.example.education.modal.School;
import com.example.education.modal.Sector;
import com.example.education.modal.Village;
import com.example.education.serviceImplementation.CellServiceImplementation;
import com.example.education.serviceImplementation.DistrictServiceImplementation;
import com.example.education.serviceImplementation.SchoolServiceImplementation;
import com.example.education.serviceImplementation.SectorServiceImplementation;
import com.example.education.serviceImplementation.VillageServiceImplementation;


@Controller
public class SchoolController {
    @Autowired
    private SchoolServiceImplementation serviceImplementation;
    @Autowired
    private DistrictServiceImplementation districtServiceImplementation;
    @Autowired
    private SectorServiceImplementation sectorServiceImplementation;
    @Autowired
    private CellServiceImplementation cellServiceImplementation;
    @Autowired
    private VillageServiceImplementation villageServiceImplementation;
    @GetMapping("/list_schools")
    public String showAllAvailabeSchools(Model model){
        List<School> schools = serviceImplementation.displayAllSchools();
        model.addAttribute("schools", schools);

        return "manage_all_schools";

    }

    @GetMapping("/create-new-school")
    public String showNewSchoolCreationPage(Model model)
    {
        List<District> districts = districtServiceImplementation.getAllDistricts();
  
        model.addAttribute("districts", districts);
        
        return "add_new_school";
    }



    @PostMapping("/save_new_school")
    public String saveNewSchool(Model model, @RequestParam String name, @RequestParam String code, @RequestParam Long districtId, @RequestParam Long sectorId, @RequestParam Long cellId, @RequestParam Long villageId) {
    // Check if the school with the given code already exists
    Optional<School> existingSchool = serviceImplementation.findSchoolBySchoolCode(code);
    if (existingSchool != null) {
        model.addAttribute("error", "School with code '" + code + "' already exists");
        return "redirect:/list_schools";
    }

    // Retrieve district, sector, cell, and village based on their IDs
    Optional<District> savedDistrict = districtServiceImplementation.findDistrictById(districtId);
    Optional<Sector> savedSector = sectorServiceImplementation.findSectorById(sectorId);
    Optional<Cell> savedCell = cellServiceImplementation.findCellById(cellId);
    Optional<Village> savedVillage = villageServiceImplementation.findVillageById(villageId);

    // Check if all entities are found
    if (savedDistrict.isEmpty() || savedSector.isEmpty() || savedCell.isEmpty() || savedVillage.isEmpty()) {
        model.addAttribute("error", "District, sector, cell, or village not found");
        return "redirect:/list_schools";
    }

    // Create a new school with the retrieved entities
    School newSchool = new School();
    newSchool.setName(name);
    newSchool.setSchoolCode(code);
    newSchool.setDistrict(savedDistrict.get().getName());
    newSchool.setSector(savedSector.get().getName());
    newSchool.setCell(savedCell.get().getName());
    newSchool.setVillage(savedVillage.get().getName());

    // Save the new school
    serviceImplementation.saveSchool(newSchool);

    // Redirect to the list of schools with success message
    model.addAttribute("success", "School saved successfully");
    return "redirect:/list_schools";
}

    @PostMapping("/delete-school/{id}")
    public String deleteSchool(Model model, @PathVariable Long id) {
        try {
            serviceImplementation.deleteSchool(id);
            model.addAttribute("message", "School deleted successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to delete school with id: " + id);
        }
        // Redirect to display all schools after deletion
        return "redirect:/list_schools";
    }


    @GetMapping("/edit-school/{id}")
    public String editSchool(Model model, @PathVariable Long id) {
        Optional<School> savedSchool = serviceImplementation.findSchoolById(id);
        if (savedSchool.isPresent()) {
            School school = savedSchool.get();
            model.addAttribute("school", school);
            return "edit-school";
        } else {
            model.addAttribute("error", "School not found with id: " + id);
            // Redirect to a page displaying all schools or handle the error appropriately
            return "redirect:/display-all-schools";
        }
    }


    @PostMapping("/update-school")
    public String updateSchool(Model model, @RequestParam Long id, @RequestParam String name, @RequestParam String code, @RequestParam String district, @RequestParam String sector, @RequestParam String cell) {
        Optional<School> savedSchool = serviceImplementation.findSchoolById(id);
        if (savedSchool.isPresent()) {
            School school = savedSchool.get();
            school.setName(name);
            school.setSchoolCode(code);
            // school.setDistrict(district);
            // school.setSector(sector);
            // school.setCell(cell);
            serviceImplementation.updateSchool(school);
            model.addAttribute("message", "School updated successfully!");
        } else {
            model.addAttribute("error", "School not found with id: " + id);
        }
        return "redirect:/list_schools";
    }



    

    @GetMapping("/getSectorsByDistrict")
    @ResponseBody
    public List<Sector> getSectorsByDistrict(@RequestParam Long districtId) {
        // Call service method to get sectors by districtId
        Optional<District> foundDistrict = districtServiceImplementation.findDistrictById(districtId);
        List<Sector> sectors = sectorServiceImplementation.findSectorsByDistrict(foundDistrict);
        return sectors;
    }



    @GetMapping("/getCellsBySector")
    @ResponseBody
    public List<Cell> getCellsBySector(@RequestParam Long sectorId) {
        // Call service method to get cells by sectorId
        Optional<Sector> foundSector = sectorServiceImplementation.findSectorById(sectorId);
        List<Cell> cells = cellServiceImplementation.findCellsBySector(foundSector);
        return cells;
    }



    @GetMapping("/getVillagesByCell")
    @ResponseBody
    public List<Village> getVillagesByCell(@RequestParam Long cellId) {
        // Call service method to get villages by cell
        Optional<Cell> foundCell = cellServiceImplementation.findCellById(cellId);
        System.out.println("Selected cell is: "+foundCell.get() );
        List<Village> villages = villageServiceImplementation.findByCell(foundCell);
        return villages;
    }


}
