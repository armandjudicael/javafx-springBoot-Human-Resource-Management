package com.grh.grh.entities;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Personnel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String matricule;
    private String classe;
    private String echelon;
    private Integer solde;
    private String corps;
    private LocalDate dateEntre;
    private LocalDate dateSortie;
    private String motifSortie;
    private LocalDate dateNaissance;
    private String lieuDeNaissance;
    private CategorieEmploye categorieEmploye;
    private Sexe sexe;
    private String position;
    private String fonction;
    private Regime regime;

    @OneToMany
    List<Conge> conges;
}
