package com.example.education.repository;

import com.example.education.modal.Cell;
import com.example.education.modal.Sector;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CellRepository extends JpaRepository<Cell, Long>{
    Cell findCellByName(String name);
    List<Cell> findCellsBySector(Optional<Sector> selectedSector);
    

}

