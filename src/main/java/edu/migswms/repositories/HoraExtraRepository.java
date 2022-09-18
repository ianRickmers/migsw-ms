package edu.migswms.repositories;

import edu.migswms.entities.HoraExtraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoraExtraRepository extends JpaRepository<HoraExtraEntity, Long> {
    public HoraExtraEntity findByRut(String rut);
}