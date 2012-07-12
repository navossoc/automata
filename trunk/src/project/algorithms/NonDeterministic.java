package project.algorithms;

import automata.Automata;
import automata.State;
import automata.Transition;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * ETAPA 2 - Algoritmo de Eliminação de Não Determinísmos
 */
public class NonDeterministic {

    private final Automata automata;

    public NonDeterministic(Automata automata) {
        this.automata = automata.clone();

    }

    public Automata Start() {
        Map<State, Map<String, Set<Transition>>> all = getNonDeterminism();
        Set<State> tempStates = new TreeSet<State>();
        while (all.size() > 0) {
            for (State state : all.keySet()) {
                System.out.println("state = " + state);
                Map<String, Set<Transition>> symbolTransitions = all.get(state);
                for (String symbol : symbolTransitions.keySet()) {
                    System.out.println("for symbol = " + symbol);
                    tempStates.clear();
                    String newStateLabel = "";
                    int newStateType = State.NORMAL;
                    for (Transition t : symbolTransitions.get(symbol)) {
                        System.out.println(t);
                        State tempState = t.getState2();
                        tempStates.add(tempState);

                        // TODO: verificar se os labels vão estar sempre ordenados
                        // TODO: concatenar o novo estado - DONE
                        newStateLabel += tempState.getLabel();
                        // TODO: verificar se o estado é final e modificar se preciso - DONE
                        if (tempState.isFinal()) {
                            newStateType |= tempState.getType();
                        }

                    }
                    // TODO: criar novo estado - DONE
                    State newState = new State(newStateLabel, newStateType);

                    // TODO: adicionar novo estado ao conjunto de estados
                    automata.addState(newState);

                    // TODO: adicionar ao novo estado as transições dos estados usados
                    for (State tempState : tempStates) {
                        for (Transition tempTransitions : getTransitions(tempState)) {
                            automata.addTransition(new Transition(newState,
                                    tempTransitions.getState2(), tempTransitions.getSymbol()));
                        }
                    }

                    // TODO: remover transições antigas (q0 a q1, q0 a q2) - DONE
                    //removeTransitions(symbolTransitions.get(symbol));

                    // TODO: criar nova transição e adiciona-la (q0 a q1q2) - DONE
                    //Transition newTransition = new Transition(state, newState, symbol);
                    //automata.addTransition(newTransition);

                    // TODO: substituir o novo estado nas transições antigas
                    replaceStateInTransitions(tempStates, newState);
                    /* /for (State tempState : tempStates) {
                     * replaceStateInTransitions(tempState, newState);
                     * } */
                    break;
                }
                break;
            }
            all = getNonDeterminism();
        }
        return automata;
    }

    public Map<State, Map<String, Set<Transition>>> getNonDeterminism() {
        Map<State, Map<String, Set<Transition>>> all = new TreeMap<State, Map<String, Set<Transition>>>();

        for (State state : automata.getStates()) {

            Map<String, Set<Transition>> map = new TreeMap<String, Set<Transition>>();
            for (String symbol : automata.getSymbols()) {
                map.put(symbol, new TreeSet<Transition>());
            }
            for (Transition transition : getTransitions(state)) {
                map.get(transition.getSymbol()).add(transition);
            }

            Map<String, Set<Transition>> toBeCopy = new TreeMap<String, Set<Transition>>();
            for (String symbol : map.keySet()) {
                Set<Transition> transitions = map.get(symbol);
                if (transitions.size() > 1) {
                    toBeCopy.put(symbol, transitions);
                }
            }

            if (toBeCopy.size() > 0) {
                all.put(state, toBeCopy);
            }
        }

        return all;
    }

    private Set<Transition> getTransitions(State state) {
        Set<Transition> transitions = new TreeSet<Transition>();
        for (Transition t : automata.getTransitions()) {
            if (t.getState1().equals(state)) {
                transitions.add(t);
            }
        }

        return transitions;
    }

    private void replaceStateInTransitions(Set<State> oldState, State newState) {
        Map<State, Map<String, Set<Transition>>> all = getNonDeterminism();
        for (State state : all.keySet()) {
            Map<String, Set<Transition>> symbolTransitions = all.get(state);
            for (String symbol : symbolTransitions.keySet()) {
                String newDestination = "";
                for (Transition t : symbolTransitions.get(symbol)) {
                    newDestination += t.getState2();
                }
                if (automata.getState(newDestination) != null) {
                    for (Transition t : symbolTransitions.get(symbol)) {
                        automata.removeTransition(t);
                    }

                    automata.addTransition(new Transition(state, newState, symbol));
                }

            }
        }
    }
}
