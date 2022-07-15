package com.grh.grh.entities;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Personnel{
    private String nom;
    private String prenom;
    private String matricule;
    private String classe;
    private String echelon;
    private String solde;
    private LocalDate dateEntre;
    private LocalDate dateNaissance;
    private String lieuDeNaissance;
    private Sexe sexe;
    private String position;
}
