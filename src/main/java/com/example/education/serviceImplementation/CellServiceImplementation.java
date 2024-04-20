// CellServiceImplementation.java
package com.example.education.serviceImplementation;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.education.modal.Cell;
import com.example.education.modal.Sector;
import com.example.education.repository.CellRepository;
import com.example.education.service.CellService;

@Service
public class CellServiceImplementation implements CellService{

    @Autowired
    private CellRepository repo;

    @Override
    public Cell saveCell(Cell cell) {
        return repo.save(cell);
    }

    @Override
    public List<Cell> getAllCells() {
        return repo.findAll();
    }

    @Override
    public Cell updateCell(Cell cell) {
        Cell savedCell = repo.findById(cell.getId()).orElse(null);
        if (savedCell != null) {
            savedCell.setName(cell.getName());
            savedCell.setSector(cell.getSector());
            return repo.save(savedCell);
        }else{
            return repo.save(cell);
        }
    }

    @Override
    public Cell findCellByName(String name) {
        return repo.findCellByName(name);
    }

    @Override
    public void deleteCell(Long id) {
        repo.deleteById(id);
    }

    public Optional<Cell> findCellById(Long id) {
        return repo.findById(id);
    }

    public List<Cell> findCellsBySector(Optional<Sector> selectedSector) {
        return repo.findCellsBySector(selectedSector);
    }

    @Override
    public List<Cell> findCellsBySector(Sector sector) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findCellsBySector'");
    }
}
