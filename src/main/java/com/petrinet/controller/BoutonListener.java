/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petrinet.controller;

import com.petrinet.model.PetriParser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author eliot
 */
public class BoutonListener implements ActionListener{
    /**
     * le bouton de l'écouteur
     */
    private JButton bouton;
    
    /**
     * le petri parser
     */
    private PetriParser parser;

    /**
     * constructeur
     * @param bouton
     * @param parser 
     */
    public BoutonListener(JButton bouton, PetriParser parser){
        this.bouton = bouton;
        this.parser = parser;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        parser.start();
    }
}
