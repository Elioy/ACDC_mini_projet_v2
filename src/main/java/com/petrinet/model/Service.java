package com.petrinet.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class
 * @author Namoz
 */
public class Service {

    private String nom;
    private List<Etat> etats;
    private List<Transition> transitions = new ArrayList();
    public Service() {
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions(List<Transition> transitions) {
        this.transitions = transitions;
    }

    public Service(String nom, List<Etat> etats) {
        this.nom = nom;
        this.etats = etats;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Etat> getEtats() {
        return etats;
    }

    public void setEtats(List<Etat> etats) {
        this.etats = etats;
    }
}
