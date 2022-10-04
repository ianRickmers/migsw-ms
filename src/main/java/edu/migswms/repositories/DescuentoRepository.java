package edu.migswms.repositories;

import edu.migswms.entities.DescuentoEntity;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescuentoRepository extends JpaRepository<DescuentoEntity, Long> {
    public ArrayList<DescuentoEntity> findByRut(String rut);
}
