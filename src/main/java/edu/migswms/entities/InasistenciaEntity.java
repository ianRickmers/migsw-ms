package edu.migswms.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "inasistencias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InasistenciaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    
    private String rut;
    private String fecha;
    private Integer justificada = 0;
}
