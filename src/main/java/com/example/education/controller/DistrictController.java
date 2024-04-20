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
import com.example.education.serviceImplementation.CellServiceImplementation;
import com.example.education.serviceImplementation.DistrictServiceImplementation;
import com.example.education.serviceImplementation.SectorServiceImplementation;
import com.example.education.serviceImplementation.VillageServiceImplementation;

@Controller
public class DistrictController {
    @Autowired
    private DistrictServiceImplementation districtServiceImplementation;
    @Autowired
    private SectorServiceImplementation sectorServiceImplementation;
    private CellServiceImplementation cellServiceImplementation;
    private VillageServiceImplementation villageServiceImplementation;

    @GetMapping("/list_districts")
    public String showAllAvailabeDistricts(Model model) {
        List<District> districts = districtServiceImplementation.getAllDistricts();
        model.addAttribute("districts", districts);

        return "manage_all_districts";

    }

    @GetMapping("/create-new-district")
    public String showNewDistrictCreationPage(Model model) {
        return "add_new_district";
    }

    @PostMapping("/save_new_district")
    public String saveNewDistrict(Model modal, @RequestParam String name) {
        
            District savedDistrict = new District();
            savedDistrict.setName(name);
            districtServiceImplementation.saveDistrict(savedDistrict);
            modal.addAttribute("success", "District updated successfully");
            return "redirect:/list_districts";

    }

    @PostMapping("/delete-district/{id}")
    public String deleteDistrict(Model model, @PathVariable Long id) {
        try {
            districtServiceImplementation.deleteDistrict(id);
            model.addAttribute("message", "District deleted successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to delete District with id: " + id);
        }
        // Redirect to display all schools after deletion
        return "redirect:/list_districts";
    }

    @GetMapping("/edit-district/{id}")
    public String editDistrict(Model model, @PathVariable Long id) {
            return "edit-district";
        
    }


    @PostMapping("/update-district")
    public String updateDistrict(Model model, @RequestParam Long id, @RequestParam String name) {
        Optional<District> savedDistrict = districtServiceImplementation.findDistrictById(id);
        if (savedDistrict.isPresent()) {
            District updateDistrict = savedDistrict.get();
            updateDistrict.setName(name);
            districtServiceImplementation.updateDistrict(updateDistrict);
            model.addAttribute("success", "District updated successfully");
            return "redirect:/list_districts";
        } else {
            model.addAttribute("success", "District saved successfully");
            return "redirect:/list_districts";
        }
    }
}


