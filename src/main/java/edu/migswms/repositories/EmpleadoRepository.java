package edu.migswms.repositories;

import edu.migswms.entities.EmpleadoEntity;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<EmpleadoEntity, Long> {
    public EmpleadoEntity findByRut(String rut);
    @Query("select distinct (e.rut) from EmpleadoEntity as e")
    ArrayList<EmpleadoEntity> findAllRut();
}