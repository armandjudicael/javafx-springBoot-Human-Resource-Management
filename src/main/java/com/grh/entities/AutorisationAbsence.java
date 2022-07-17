package com.grh.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class AutorisationAbsence{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nature;
    private typeAutorisation typeAutorisation;
    @OneToOne(cascade = CascadeType.ALL)
    private Durer durer;
    private String motif;
    private String lieuDeJouissance;
}
