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

import com.example.education.modal.Cell;
import com.example.education.modal.District;
import com.example.education.modal.Employee;
import com.example.education.modal.School;
import com.example.education.modal.Sector;
import com.example.education.modal.Village;
import com.example.education.serviceImplementation.CellServiceImplementation;
import com.example.education.serviceImplementation.DistrictServiceImplementation;
import com.example.education.serviceImplementation.EmployeeServiceImplementation;
import com.example.education.serviceImplementation.SchoolServiceImplementation;
import com.example.education.serviceImplementation.SectorServiceImplementation;
import com.example.education.serviceImplementation.VillageServiceImplementation;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeServiceImplementation serviceImplementation;
    @Autowired
    private SchoolServiceImplementation schoolServiceImplementation;
    @Autowired
    private SchoolServiceImplementation schoolServiceImplementation2;
    @Autowired
    private DistrictServiceImplementation districtServiceImplementation;
    @Autowired
    private SectorServiceImplementation sectorServiceImplementation;
    @Autowired
    private CellServiceImplementation cellServiceImplementation;
    @Autowired
    private VillageServiceImplementation villageServiceImplementation;

    @GetMapping("/list_employees")
    public String showAllAvailabeEmployees(Model model) {
        List<Employee> employees = serviceImplementation.displayAllEmployee();
        model.addAttribute("employees", employees);

        return "manage_all_employees";

    }

    @GetMapping("/create-new-employee")
    public String showNewEmployeeCreationPage(Model model) {
        List<School> schools = schoolServiceImplementation.displayAllSchools();
        model.addAttribute("schools", schools);
        List<District> districts = districtServiceImplementation.getAllDistricts();
  
        model.addAttribute("districts", districts);
        return "add_new_employee";
    }

    @PostMapping("/save_new_employee")
    public String saveNewEmployee(Model modal, @RequestParam String schoolCode, @RequestParam String firstname,
            @RequestParam String lastname, @RequestParam Long districtId, @RequestParam Long sectorId, @RequestParam Long cellId, @RequestParam Long villageId, @RequestParam String phone, @RequestParam String idNo
           ) {


             // Retrieve district, sector, cell, and village based on their IDs
            Optional<District> savedDistrict = districtServiceImplementation.findDistrictById(districtId);
            Optional<Sector> savedSector = sectorServiceImplementation.findSectorById(sectorId);
            Optional<Cell> savedCell = cellServiceImplementation.findCellById(cellId);
            Optional<Village> savedVillage = villageServiceImplementation.findVillageById(villageId);

            // Check if all entities are found
            if (savedDistrict.isEmpty() || savedSector.isEmpty() || savedCell.isEmpty() || savedVillage.isEmpty()) {
                modal.addAttribute("error", "District, sector, cell, or village not found");
                return "redirect:/list_employees";
            }

            try {

                Employee employee = new Employee();

                employee.setFirstname(firstname);
                employee.setLastname(lastname);
                employee.setCell(savedCell.get().getName());
                employee.setDitrict(savedCell.get().getName());
                employee.setPhone(phone);
                employee.setSector(savedSector.get().getName());
                employee.setVillage(savedVillage.get().getName());
                employee.setIdentityNo(idNo);

                Optional<School> savedSchool = schoolServiceImplementation.findSchoolBySchoolCode(schoolCode);
                if (savedSchool!=null) {
                    employee.setSchool(savedSchool.get());
                }else{
                    modal.addAttribute("error", "School not found");
                }

                Employee savedEmployee = serviceImplementation.saveEmployee(employee);
                if (savedEmployee != null) {
                    modal.addAttribute("message", "Employee " + savedEmployee + " saved successfully");
                    return "redirect:/list_employees";
                } else {
                    modal.addAttribute("message", "Failed to register new Employee");
                    return "redirect:/list_employees";
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        return "add_new_employee";

    }

    @PostMapping("/delete-employee/{id}")
    public String deleteEmployee(Model model, @PathVariable Long id) {
        try {
            serviceImplementation.deleteEmployee(id);
            model.addAttribute("message", "Employee deleted successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to delete Employee with id: " + id);
        }
        // Redirect to display all schools after deletion
        return "redirect:/list_employees";
    }

    @GetMapping("/edit-employee/{id}")
    public String editEmployee(Model model, @PathVariable Long id) {
        Optional<Employee> savedEmployee = serviceImplementation.findEmployeeById(id);
        if (savedEmployee.isPresent()) {
            Employee employee = savedEmployee.get();
            List<School> schools = schoolServiceImplementation.displayAllSchools();
            List<District> districts = districtServiceImplementation.getAllDistricts();
            model.addAttribute("schools", schools);
            model.addAttribute("districts", districts);
            model.addAttribute("employee", employee); // Add the employee object to the model
            return "edit-employee";
        } else {
            model.addAttribute("error", "Employee not found with id: " + id);
            // Redirect to a page displaying all employees or handle the error appropriately
            return "redirect:/list_employees";
        }
    }


    @PostMapping("/update-employee")
    public String updateEmployee(Model modal, @RequestParam Long id, @RequestParam String schoolCode, @RequestParam String firstname, @RequestParam String lastname, @RequestParam Long districtId,
        @RequestParam Long sectorId,@RequestParam Long cellId,@RequestParam Long villageId,@RequestParam String phone,@RequestParam String idNo) {
        
             // Retrieve district, sector, cell, and village based on their IDs
             Optional<District> savedDistrict = districtServiceImplementation.findDistrictById(districtId);
             Optional<Sector> savedSector = sectorServiceImplementation.findSectorById(sectorId);
             Optional<Cell> savedCell = cellServiceImplementation.findCellById(cellId);
             Optional<Village> savedVillage = villageServiceImplementation.findVillageById(villageId);
 
             // Check if all entities are found
             if (savedDistrict.isEmpty() || savedSector.isEmpty() || savedCell.isEmpty() || savedVillage.isEmpty()) {
                 modal.addAttribute("error", "District, sector, cell, or village not found");
                 return "redirect:/list_employees";
             }
 
             try {
                
                Optional<Employee> foundEmployee = serviceImplementation.findEmployeeById(id);
                if (foundEmployee.isPresent()) {
                    Employee updateEmployee = foundEmployee.get();
                    updateEmployee.setFirstname(firstname);
                    updateEmployee.setLastname(lastname);
                    updateEmployee.setCell(savedCell.get().getName());
                    updateEmployee.setDitrict(savedCell.get().getName());
                    updateEmployee.setPhone(phone);
                    updateEmployee.setSector(savedSector.get().getName());
                    updateEmployee.setVillage(savedVillage.get().getName());
                    updateEmployee.setIdentityNo(idNo);

                    Optional<School> savedSchool = schoolServiceImplementation.findSchoolBySchoolCode(schoolCode);

                    if (savedSchool!=null) {
                        updateEmployee.setSchool(savedSchool.get());


                        String savedEmployee = serviceImplementation.updateEmployee(updateEmployee);
                        if (savedEmployee != null) {
                            modal.addAttribute("message", "Employee " + savedEmployee + " Updated successfully");
                            return "redirect:/list_employees";
                        } else {
                            modal.addAttribute("message", "Failed to update new Employee");
                            return "redirect:/list_employees";
                        }
                    }else{
                        modal.addAttribute("error", "School not found");
                    }
                }
 
                 
 
                 
 
             } catch (Exception ex) {
                 ex.printStackTrace();
             }
    return "edit-employee";
}
}

