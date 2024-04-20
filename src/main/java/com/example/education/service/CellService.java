package com.example.education.service;

import com.example.education.modal.Cell;
import com.example.education.modal.Sector;

import java.util.List;
import java.util.Optional;


public interface CellService {
    Cell saveCell(Cell cell);
    List<Cell> getAllCells();
    Cell updateCell(Cell cell);
    Cell findCellByName(String name);
    void deleteCell(Long id);
    List<Cell> findCellsBySector(Sector sector);


}
