package project;

import automata.Automata;
import project.algorithms.*;

/**
 * Classe principal
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String file = "automata";

        for (int i = 1; i <= 5; i++) {
            Automata a1 = new Automata();
            FileFormat.read("samples/" + file + i, a1);
            Automata a2 = new EmptyTransitions(a1).Start();
            FileFormat.write("samples/" + file + i + ".r1", a2);
        }
        for (int i = 6; i <= 10; i++) {
            Automata a1 = new Automata();
            FileFormat.read("samples/" + file + i, a1);
            Automata a2 = new NonDeterministic(a1).Start();
            FileFormat.write("samples/" + file + i + ".r2", a2);
        }
        for (int i = 11; i <= 12; i++) {
            Automata a1 = new Automata();
            FileFormat.read("samples/" + file + i, a1);
            Automata a2 = new InaccessibleStates(a1).Start();
            FileFormat.write("samples/" + file + i + ".r3", a2);
        }
        for (int i = 13; i <= 14; i++) {
            Automata a1 = new Automata();
            FileFormat.read("samples/" + file + i, a1);
            Automata a2 = new UselessStates(a1).Start();
            FileFormat.write("samples/" + file + i + ".r4", a2);
        }
        for (int i = 15; i <= 19; i++) {
            Automata a1 = new Automata();
            FileFormat.read("samples/" + file + i, a1);
            Automata a2 = new MinimizationFA(a1).Start();
            FileFormat.write("samples/" + file + i + ".r5", a2);
        }
    }
}
