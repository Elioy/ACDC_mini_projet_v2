package com.petrinet.model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Transitions of our Petri Network
 * @author Namoz
 */
public class Transition implements Runnable {

    private String nom;
    private List<Etat> parents = new ArrayList<>();
    private List<Etat> enfants = new ArrayList<>();
    private int duree;
    private boolean isRunning;
    private PetriParser parser;
    
    public Transition() {
        isRunning = false;
        duree = 4000;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public PetriParser getParser() {
        return parser;
    }

    public void setParser(PetriParser parser) {
        this.parser = parser;
    }


    public boolean isIsRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Etat> getParents() {
        return parents;
    }

    public void setParents(List<Etat> parents) {
        this.parents = parents;
    }

    public List<Etat> getEnfants() {
        return enfants;
    }

    public void setEnfants(List<Etat> enfants) {
        this.enfants = enfants;
    }

    /**
     * Notify the parser of a change
     */
    public void notifier() {
        this.parser.update(this);
    }

    /**
     * Will check if the transition can be done
     * If a parent is not in the service, it must have been done one time
     * Otherwise, it needs at least a token
     * @param s the service of the state
     * @return boolean true if can be done, false otherwise
     */
    public boolean canBeDone(Service s) {
        for (Etat p : this.parents)
        {
            if (!s.getEtats().contains(p) && !p.wasDone())
            {
                return false;
            }
            else if (s.getEtats().contains(p) && p.getToken() <= 0)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Start the thread
     * Sleep for the required duration then notify the Petri Network
     */
    @Override
    public void run() {
        try {
            this.isRunning = true;
            Thread.sleep(this.duree);
            this.isRunning = false;
            this.notifier();

        } catch (InterruptedException ex) {
            Logger.getLogger(Etat.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}
