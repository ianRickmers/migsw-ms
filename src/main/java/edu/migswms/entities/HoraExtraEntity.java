package edu.migswms.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "horas_extras")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HoraExtraEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private Long idEmpleado;
    private String rut;
    private String fecha;
    private Integer cantidadHoras;
    private Integer autorizada = 0;
}