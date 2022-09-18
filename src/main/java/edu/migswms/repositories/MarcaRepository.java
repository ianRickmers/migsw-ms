package edu.migswms.repositories;

import edu.migswms.entities.MarcaEntity;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MarcaRepository extends JpaRepository<MarcaEntity, Long> {
    //findallbyrut
    /* @Query("SELECT m FROM MarcaEntity m WHERE m.rut = :rut")
    public ArrayList<MarcaEntity> findAllByRut(@Param("rut") String rut); */
    @Query("SELECT m from MarcaEntity m WHERE m.rut = :rut")
    ArrayList<MarcaEntity> findByRutCustomQuery(@Param("rut") String rut);
}