package com.grh.entities;

public enum Unite{
        JOURS,MOIS,ANNEE;
        @Override
        public String toString(){
                return this == JOURS ? "jours" :( this == MOIS ? "mois" :"anneé");
        }
}