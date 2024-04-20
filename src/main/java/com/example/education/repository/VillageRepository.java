package com.example.education.repository;

import org.springframework.stereotype.Repository;

import com.example.education.modal.Cell;
import com.example.education.modal.Village;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;


@Repository
public interface VillageRepository extends JpaRepository<Village, Long>{
    Village findVillageByName(String name);
    List<Village> findByCell(Optional<Cell> cell);

}
