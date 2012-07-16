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

        for (int i = 1; i <= 20; i++) {
            Automata automata = new Automata();
            FileFormat.read("samples/" + file + i + ".txt", automata);
            automata = new EmptyTransitions(automata).Start();
            FileFormat.write("samples/" + file + i + ".s1.txt", automata);
            automata = new NonDeterministic(automata).Start();
            FileFormat.write("samples/" + file + i + ".s2.txt", automata);
            automata = new InaccessibleStates(automata).Start();
            FileFormat.write("samples/" + file + i + ".s3.txt", automata);
            automata = new UselessStates(automata).Start();
            FileFormat.write("samples/" + file + i + ".s4.txt", automata);
            automata = new MinimizationFA(automata).Start();
            FileFormat.write("samples/" + file + i + ".s5.txt", automata);
        }

    }
}
