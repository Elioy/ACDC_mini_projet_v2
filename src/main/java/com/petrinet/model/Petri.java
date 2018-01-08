package com.petrinet.model;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.petrinet.model.RuntimeEndThread;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Class containing the main, will start the program by asking for a file Will
 * parse it and start the behavior of the application
 *
 * @author Namoz
 */
public class Petri {

    /**
     * Ask for a valid file
     *
     * @return File the file to read
     */
    public static File askFile() {
        File file;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("Entrez un nom de fichier valide (stocker à la racine du projet) :");
            String fileName = sc.nextLine();
            file = new File(fileName);
        } while (!file.exists() || file.isDirectory());
        return file;
    }

    public static void main(String[] args) {
        try {
            File file = Petri.askFile();

            // Parsing
            YamlReader reader = new YamlReader(new FileReader(file));
            PetriParser parser = reader.read(PetriParser.class);
            parser.reparse();

            // Adding a shutdown hook to display the states having a token
            Runtime.getRuntime().addShutdownHook(new RuntimeEndThread(parser.getServices()));

            // Start the program
            parser.start();

        } catch (IOException e) {
            System.out.println(e.toString());
        }

    }

    public static PetriParser launch(String fichier) {
        try {
            File file = new File(fichier);

            // Parsing
            YamlReader reader = new YamlReader(new FileReader(file));
            PetriParser parser = reader.read(PetriParser.class);
            parser.reparse();
            // Adding a shutdown hook to display the states having a token
            Runtime.getRuntime().addShutdownHook(new RuntimeEndThread(parser.getServices()));
            return parser;
        } catch (IOException e) {
            System.out.println(e.toString());
            return null;
        }
    }
}
