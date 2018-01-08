package com.petrinet.model;

import java.util.ArrayList;
import java.util.List;


/**
 * Use to display all the state having a token at the end of the program
 * @author Namoz
 */
public class RuntimeEndThread extends Thread {

    List<Service> services = new ArrayList<>();
    
    public RuntimeEndThread(List<Service> services){
        this.services = services;
    }
    
    /**
     * Start the thread, printing all the states with token
     */
    @Override
    public void start() {
        System.out.println("Program ended | states with token : ");
        this.services.forEach(s -> {
            s.getEtats().forEach(e -> {
                if (e.getToken() > 0)
                {
                    System.out.println(e);
                }
            });
        });
    }
}
