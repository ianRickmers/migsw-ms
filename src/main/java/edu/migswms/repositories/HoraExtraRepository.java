package edu.migswms.repositories;

import edu.migswms.entities.HoraExtraEntity;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoraExtraRepository extends JpaRepository<HoraExtraEntity, Long> {
    public ArrayList<HoraExtraEntity> findByRut(String rut);
}