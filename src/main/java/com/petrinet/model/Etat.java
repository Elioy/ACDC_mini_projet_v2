package com.petrinet.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * State class
 * @author Namoz
 */
public class Etat extends Observable{

    private String nom;
    private int token = 0;
    private boolean wasDone = false;
    
    public Etat(){
    }
    
    @Override
    public String toString() {
        return "name : " +  this.nom + " | token number :" + this.token;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        if (token >0)
        {
            this.wasDone = true;
        }
        this.token = token;
    }
    
    public void addToken() {
        this.wasDone = true;
        this.token++;
        setChanged();
        notifyObservers();
    }
    
    public void removeToken() {
        this.token--;
        setChanged();
        notifyObservers();
    }

    public boolean wasDone() {
        return wasDone;
    }

    public void setWasDone(boolean wasDone) {
        this.wasDone = wasDone;
    }
    
}
