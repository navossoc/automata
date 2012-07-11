package project;

import automata.Automata;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Automata a1 = new Automata();
        FileFormat.read("samples/automata1a", a1);


    }
}
