package com.petrinet.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * This class stored dependancy on other service
 * @author Namoz
 */
public class DependanceExterne {

    private Etat pere;
    private Transition fils;

    public DependanceExterne(Etat pere, Transition fils) {
        this.pere = pere;
        this.fils = fils;
    }

    public DependanceExterne() {
    }

    public Etat getPere() {
        return pere;
    }

    public void setPere(Etat pere) {
        this.pere = pere;
    }

    public Transition getFils() {
        return fils;
    }

    public void setFils(Transition fils) {
        this.fils = fils;
    }

}
