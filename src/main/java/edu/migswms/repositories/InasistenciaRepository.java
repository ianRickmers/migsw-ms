package edu.migswms.repositories;

import edu.migswms.entities.InasistenciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InasistenciaRepository extends JpaRepository<InasistenciaEntity, Long> {

} 
