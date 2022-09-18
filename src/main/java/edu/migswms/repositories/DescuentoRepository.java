package edu.migswms.repositories;

import edu.migswms.entities.DescuentoEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescuentoRepository extends JpaRepository<DescuentoEntity, Long> {
    public Optional<DescuentoEntity> findByRut(String rut);
}
