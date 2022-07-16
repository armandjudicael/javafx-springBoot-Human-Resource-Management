package com.grh.grh.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AutorisationAbsence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private typeAutorisation typeAutorisation;
    private int duree;
    private String lieuDeJouissance;
}
