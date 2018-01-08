/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petrinet.controller;

import com.petrinet.model.DependanceExterne;
import com.petrinet.model.Etat;
import com.petrinet.model.PetriParser;
import com.petrinet.model.Service;
import com.petrinet.model.Transition;
import com.petrinet.view.PetriFrame;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

/**
 *
 * @author eliot
 */
public class EtatObserver implements Observer {

    /**
     * l'etat observé
     */
    private Etat etat;
    /**
     * le graphe
     */
    private Graph graphe;
    
    /**
     * le petri parser
     */
    private PetriParser parser;

    /**
     * Constructeur
     * @param etat
     * @param graphe
     * @param parser 
     */
    public EtatObserver(Etat etat, Graph graphe, PetriParser parser) {
        this.etat = etat;
        this.graphe = graphe;
        this.parser = parser;
    }

    @Override
    public void update(Observable o, Object o1) {
        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            Logger.getLogger(EtatObserver.class.getName()).log(Level.SEVERE, null, ex);
        }
        Node n = graphe.getNode(etat.getNom());
        if (etat.getToken() == 0) {
            n.setAttribute("ui.label", "");
        } else if (etat.getToken() == 1) {
            n.setAttribute("ui.label", "●");
        } else {
            n.setAttribute("ui.label", etat.getToken());
        }
        if (etat.wasDone()) {
            n.removeAttribute("ui.class");
            n.addAttribute("ui.class", "etat");
        } 
    }
}
