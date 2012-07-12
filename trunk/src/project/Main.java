package project;

import automata.Automata;
import project.algorithms.EmptyTransitions;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Automata a1 = new Automata();
        FileFormat.read("samples/automata5", a1);
        Automata a2 = new EmptyTransitions(a1).Start();
    }
}
