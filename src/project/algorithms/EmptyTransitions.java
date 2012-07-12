package project.algorithms;

import automata.Automata;
import automata.State;
import automata.Transition;
import java.util.ArrayList;
import java.util.List;

/**
 * ETAPA 1 - Algoritmo de Eliminação de Transições em Vazio
 */
public class EmptyTransitions {

    private Automata original;
    private Automata newAutomata;
    List<Transition> emptyTransitions;

    public EmptyTransitions(Automata automata) {
        this.original = automata;
        this.newAutomata = this.original.clone();
        this.emptyTransitions = new ArrayList<Transition>();
    }

    public Automata Start() {
        List<Transition> empty = getEmpty();
        do {
            for (Transition t : empty) {
                List<Transition> transitions = getTransitions(t.getState2());
                if (!t.getState1().equals(t.getState2())) {
                    for (Transition beingAnalyzed : transitions) {
                        State newState = getState(t.getState1().getLabel());
                        newState.setType(newState.getType() | beingAnalyzed.getState1().getType());
                        this.newAutomata.addTransition(new Transition(newState, beingAnalyzed.getState2(), beingAnalyzed.getSymbol()));
                    }
                }
                 this.newAutomata.removeTransition(t);
            }

            empty = getEmpty();
        } while (empty.size() > 0);

        return this.newAutomata;
    }

    public boolean isEmpty(Transition transition) {
        return transition.getSymbol().equalsIgnoreCase(Transition.EMPTY_SYMBOL);
    }

    public void removeEmptyTransition(Transition transition) {
        this.newAutomata.removeTransition(transition);
    }

    public List<Transition> getEmpty() {
        emptyTransitions.clear();
        for (Transition originalTransition : this.newAutomata.getTransitions()) {
            if (isEmpty(originalTransition)) {
                emptyTransitions.add(originalTransition);
            }
        }

        return emptyTransitions;
    }

    public List<Transition> getTransitions(State state) {
        List<Transition> transitions = new ArrayList<Transition>();
        for (Transition t : this.newAutomata.getTransitions()) {
            if (t.getState1().equals(state)) {
                transitions.add(t);
            }
        }

        return transitions;
    }

    public State getState(String label) {
        return this.newAutomata.getState(label);
    }
}
