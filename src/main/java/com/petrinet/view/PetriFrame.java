/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.petrinet.view;

import com.petrinet.controller.*;
import com.petrinet.model.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

/**
 *
 * @author eliot
 */
public class PetriFrame extends JFrame {

    /**
     * Parseur contenant la liste des services
     */
    private PetriParser parser;

    private List<Viewer> viewers;

    /**
     * Constructeur
     */
    public PetriFrame() {
        this.viewers = new ArrayList<Viewer>();
        initComponents();
    }

    /**
     * créé la fenetre et y ajout les différents composants
     */
    private void initComponents() {
        JFileChooser dialogue = new JFileChooser();
        dialogue.showOpenDialog(null);
        this.parser = Petri.launch(dialogue.getSelectedFile().getName());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setMinimumSize(new Dimension(1280, 720));
        this.add(this.creerPanelReseauxEtNoms(this.creerPanelReseaux(), this.creerPanelNoms()), BorderLayout.CENTER);
        this.add(this.creerPanelBas(), BorderLayout.SOUTH);
        this.repaint();
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(PetriFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        new PetriFrame();
    }

    /**
     * Creer le graphe correspondant au réseau de Petri d'un service
     *
     * @param s le Service pour lequel le graphe sera créé
     * @return le craphe créé
     */
    public Graph creerGraphe(Service s) {
        System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        Graph graph = new SingleGraph(s.getNom());
        this.ajouterEtats(graph, s);
        this.ajouterTransitions(graph, s);
        graph.addAttribute("ui.stylesheet", "url('style.css')");
        return graph;
    }

    /**
     * ajoute les etats au graphe
     *
     * @param g le graphe
     * @param s le service du graphe
     */
    public void ajouterEtats(Graph g, Service s) {
        SpriteManager sman = new SpriteManager(g);
        for (Etat e : s.getEtats()) {
            g.addNode(e.getNom());
            Node n = g.getNode(e.getNom());
            n.addAttribute("ui.class", "etat0");
            Sprite sprite = sman.addSprite("s" + e.getNom());
            sprite.attachToNode(e.getNom());
            sprite.setPosition(0.4, 0, 0);
            sprite.addAttribute("ui.label", e.getNom());
            if (e.getToken() == 0) {
                n.addAttribute("ui.label", "");
            } else if (e.getToken() == 1) {
                n.addAttribute("ui.label", "●");
                n.removeAttribute("ui.class");
                n.addAttribute("ui.class", "etat");
            } else {
                n.addAttribute("ui.label", e.getToken());
                n.removeAttribute("ui.class");
                n.addAttribute("ui.class", "etat");
            }
            EtatObserver eo = new EtatObserver(e, g, parser);
            e.addObserver(eo);
        }
    }

    /**
     * ajouter les transitions du graphe, ainsi que les liens entre les etats et
     * les transitions
     *
     * @param g le graphe
     * @param s le service du graphe
     */
    public void ajouterTransitions(Graph g, Service s) {
        SpriteManager sman = new SpriteManager(g);
        for (Transition t : s.getTransitions()) {
            g.addNode(t.getNom());
            Node n = g.getNode(t.getNom());
            n.addAttribute("ui.class", "transition");
            Sprite sprite = sman.addSprite("s" + t.getNom());
            sprite.attachToNode(t.getNom());
            sprite.setPosition(0.4, 0, 0);
            sprite.addAttribute("ui.label", t.getNom());
            for (Etat e : t.getEnfants()) {
                g.addEdge(t.getNom() + e.getNom(), t.getNom(), e.getNom(), true);
            }
            for (Etat e : t.getParents()) {
                if (s.getEtats().contains(e)) {
                    g.addEdge(e.getNom() + t.getNom(), e.getNom(), t.getNom(), true);
                }
            }
            for(DependanceExterne de: parser.getDependancesExternes()){
                if(de.getFils().equals(t)){
                    Sprite sprite2 = sman.addSprite("s" + t.getNom()+1);
                    sprite2.attachToNode(t.getNom());
                    sprite2.setPosition(0.4, 0, 345);
                    sprite2.addAttribute("ui.label", de.getPere().getNom());
                    sprite2.addAttribute("ui.class", "dependance");
                }
            }
        }
    }

    /**
     * créé le panel contenant les graphes
     *
     * @return le panel contenant les graphes
     */
    public JPanel creerPanelReseaux() {
        JPanel jp1 = new JPanel();
        jp1.setLayout(new BoxLayout(jp1, BoxLayout.X_AXIS));
        for (Service s : this.parser.getServices()) {
            Graph graph = creerGraphe(s);
            Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
            ViewPanel view = viewer.addDefaultView(false);
            viewer.enableAutoLayout();
            this.viewers.add(viewer);
            JPanel jp2 = new JPanel();
            jp2.setLayout(new BorderLayout());
            jp2.add(view, BorderLayout.CENTER);
            jp2.setBorder(new javax.swing.border.BevelBorder(BevelBorder.RAISED));
            jp1.add(jp2);
        }

        return jp1;
    }

    /**
     * créé le panel contenant le nom des graphes
     *
     * @return
     */
    public JPanel creerPanelNoms() {
        JPanel jp1 = new JPanel();
        jp1.setLayout(new BoxLayout(jp1, BoxLayout.X_AXIS));
        for (Service s : this.parser.getServices()) {
            JPanel jp2 = new JPanel();
            jp2.add(new JLabel(s.getNom()));
            jp1.add(jp2);
        }
        return jp1;
    }

    /**
     * creer le panel contenant les graphes ainsi que leurs noms
     *
     * @param panelReseaux le panel contenant les graphes
     * @param panelNoms le panel contenant les noms
     * @return le panel contenant les graphes ainsi que leurs noms
     */
    public JPanel creerPanelReseauxEtNoms(JPanel panelReseaux, JPanel panelNoms) {
        JPanel jp1 = new JPanel();
        jp1.setLayout(new BorderLayout());
        jp1.add(panelReseaux, BorderLayout.CENTER);
        jp1.add(panelNoms, BorderLayout.SOUTH);
        return jp1;
    }

    /**
     * creer le panel du bas (contenant le bouton pour lancer la simulation)
     *
     * @return
     */
    public JPanel creerPanelBas() {
        JPanel jp1 = new JPanel();
        jp1.setLayout(new BoxLayout(jp1, BoxLayout.X_AXIS));
        for (int i = 0; i < this.parser.getServices().size(); i++) {
            JPanel jp2 = new JPanel();
            jp2.setLayout(new BoxLayout(jp2, BoxLayout.Y_AXIS));
            jp2.add(new JPanel());
            if (i == 0) {
                JButton jb = new JButton("Lancer");
                jb.addActionListener(new BoutonListener(jb, parser));
                jp2.add(jb);
            }
            jp1.add(jp2);
        }
        return jp1;
    }

    public PetriParser getParser() {
        return parser;
    }

}
