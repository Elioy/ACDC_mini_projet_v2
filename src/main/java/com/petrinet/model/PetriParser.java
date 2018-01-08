package com.petrinet.model;


import com.petrinet.model.DependanceExterne;
import com.petrinet.model.Etat;
import com.petrinet.model.Service;
import com.petrinet.model.Transition;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Main class of the program, will trigger all the events
 *
 * @author Namoz
 */
public class PetriParser {


    /*
    * Liste des services générés
     */
    private List<Service> services = new ArrayList<>();
    List<Etat> toRemove = Collections.synchronizedList(new ArrayList());
    List<Runnable> runnable = Collections.synchronizedList(new ArrayList());
    List<DependanceExterne> dependancesExternes = new ArrayList();

    public List<DependanceExterne> getDependancesExternes() {
        return dependancesExternes;
    }

    public void setDependancesExternes(List<DependanceExterne> dependancesExternes) {
        this.dependancesExternes = dependancesExternes;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    /**
     * make changes required to work fine
     */
    public void reparse() {
        this.dependancesExternes.forEach(de -> {
            de.getFils().getParents().add(de.getPere());
        });
        this.services.forEach(s -> {
            s.getTransitions().forEach(t -> {
                t.setParser(this);
            });
        });
    }

    /**
     * Will add the token to the child of the transition Will start the next
     * threads
     *
     * @see #start()
     * @param t the transition updating the state
     */
    public void update(Transition t) {
        t.getEnfants().forEach(e -> {
            e.addToken();
            System.out.println("added token to " + e);
        });
        this.start();
    }

    /**
     * Check for the startable transitions Start the thread of those Remove
     * tokens from parents
     */
    public void start() {

        // Add the transitions that can be done to runnable
        // Add the parents from the same service to "toRemove"
        this.services.forEach(s -> {
            s.getTransitions().forEach(t -> {
                if (t.canBeDone(s) && !t.isIsRunning()) {
                    synchronized (runnable) {
                        if (!runnable.contains(t)) {
                            runnable.add(t);

                            t.getParents().forEach(p -> {
                                synchronized (toRemove) {
                                    if (!toRemove.contains(p) && s.getEtats().contains(p)) {
                                        toRemove.add(p);
                                    }
                                }
                            });
                        }
                    }
                }
            });
        });

        // Remove a token from parents
        synchronized (toRemove) {

            if (!toRemove.isEmpty()) {
                Iterator<Etat> itr = toRemove.iterator();

                while (itr.hasNext()) {
                    Etat e = itr.next();
                    e.removeToken();
                    System.out.println("removed token from " + e);
                    itr.remove();
                }
            }
        }

        // Start the transitions
        synchronized (runnable) {

            if (!runnable.isEmpty()) {
                Iterator<Runnable> itr = runnable.iterator();

                while (itr.hasNext()) {
                    Runnable r = itr.next();
                    new Thread(r).start();
                    itr.remove();
                }
            }
        }
    }

}
