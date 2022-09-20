package edu.migswms.repositories;

import edu.migswms.entities.SueldoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SueldoRepository extends JpaRepository<SueldoEntity, Long> {
    SueldoEntity findByRut(String rut);
}