package project.algorithms;

import automata.Automata;
import automata.State;
import automata.Transition;
import java.util.*;
import project.algorithms.helpers.StatePair;

/**
 * ETAPA 5 - Algoritmo de Minimização de AF
 */
public class MinimizationFA extends BaseAlgorithm {

    private State totalState;

    /**
     * Construtor padrão
     *
     * @param automata autômato a ser otimizado
     */
    public MinimizationFA(Automata automata) {
        super(automata);
    }

    /**
     * Executa o algoritmo de minimização
     *
     * @return autômato equivalente com estados minimizados
     */
    @Override
    public Automata Start() {
        convertTotal();

        State[] arrrayStates = this.automata.getStates().toArray(new State[0]);
        SortedSet<StatePair> statesPair = new TreeSet<StatePair>();

        // Construção da tabela
        for (int i = 0; i < arrrayStates.length - 1; i++) {   // horizontal (finais)
            State state1 = arrrayStates[i];
            for (int j = i + 1; j < arrrayStates.length; j++) {    // vertical (não finais)
                State state2 = arrrayStates[j];
                StatePair pair = new StatePair(state1, state2);
                statesPair.add(pair);
                // Marcação dos estados trivialmente não equivalente

                // Se o par é (F, 'F) ou ('F, F)
                if ((state1.isFinal() != state2.isFinal())) {
                    // Marcar o par
                    pair.setMarked();
                }
            }
        }

        SortedSet<StatePair> nonMarked = getNonMarked(statesPair);
        Iterator<StatePair> iterator = nonMarked.iterator();
        // Para cada par não marcado
        while (iterator.hasNext()) {
            StatePair pair = iterator.next();

            // Certifica que o par não foi marcado
            if (!pair.isMarked()) {
                // Verificação do par
                if (checkPair(statesPair, pair)) {
                    iterator.remove();
                }
            }
        }

        // Unificação dos estados equivalentes
        nonMarked = getNonMarked(statesPair);
        System.out.println(nonMarked);
        List<SortedSet<State>> mergedStates = merge(nonMarked);

        for (SortedSet<State> states : mergedStates) {
            String newLabel = "";
            for (State s : states) {
                newLabel += s.getLabel();
            }

            State newState = new State(newLabel, states.first().getType());
            replaceState(states, newState);
            this.automata.addState(newState);
        }
        removeTotal();

        return this.automata;
    }

    /**
     * Analísa se o par específicado deve ser marcado como não equivalente
     *
     * @param statesPair conjunto com todos os pares não marcados
     * @param pair par a ser analisado
     * @return
     */
    private boolean checkPair(SortedSet<StatePair> statesPair, StatePair pair) {
        SortedMap<String, State> s1T = new TreeMap<String, State>();
        SortedMap<String, State> s2T = new TreeMap<String, State>();
        boolean marked = false;

        for (Transition t : this.automata.getTransitionsFromOrigin(pair.getState1())) {
            for (String symbol : this.automata.getSymbols()) {
                if (t.getSymbol().equals(symbol)) {
                    s1T.put(symbol, t.getState2());
                }
            }
        }
        for (Transition t : this.automata.getTransitionsFromOrigin(pair.getState2())) {
            for (String symbol : this.automata.getSymbols()) {
                if (t.getSymbol().equals(symbol)) {
                    s2T.put(symbol, t.getState2());
                }
            }
        }

        // Para cada símbolo
        for (String symbol : this.automata.getSymbols()) {
            State state1 = s1T.get(symbol); // pu
            State state2 = s2T.get(symbol); // pv
            if (state1 != null && state2 != null) {


                // Se pu = pv (caso a), o par não deve ser marcado
                if (state1.equals(state2)) {
                } // Se pu != pv (caso b e c)
                else {
                    // Obtém a referência do par
                    StatePair temp = new StatePair(state1, state2);
                    for (StatePair tempState : statesPair) {
                        if (tempState.equals(temp)) {
                            temp = tempState;
                            break;
                        }
                    }

                    // Se o par {pu,pv} não estiver marcado
                    if (!temp.isMarked()) {
                        // O par {qu,qv} é incluido em uma lista de {pu,pv}
                        temp.getList().add(pair);
                    } else { // se {pu,pv} está marcado
                        // {qu,qv} não é equivalente e é marcado
                        pair.setMarked();
                        // É marcado todos os pares na lista de {qu,qv}
                        pair.markAll();
                        marked = true;
                    }
                }
            }
        }
        return marked;
    }

    /**
     * Verifica e converse se necessário o autômato para função total
     */
    private void convertTotal() {
        if (this.automata.getSymbols().size() * this.automata.getStates().size() > this.automata.getTransitions().size()) {
            System.out.println("nao é total, deve converter");
            totalState = new State(" ", State.NORMAL);
            this.automata.addState(totalState);
            for (State state : this.automata.getStates()) {
                SortedSet<Transition> transitions = this.automata.getTransitionsFromOrigin(state);
                if (transitions.size() < this.automata.getSymbols().size()) {
                    for (String symbol : this.automata.getSymbols()) {
                        boolean used = false;
                        for (Transition transition : transitions) {
                            if (transition.getSymbol().equals(symbol)) {
                                used = true;
                                break;
                            }
                        }
                        if (!used) {
                            this.automata.addTransition(new Transition(state, totalState, symbol));
                        }
                    }
                }
            }
        }
    }

    /**
     * Faz a unificação de todos os pares de estados não marcados
     *
     * @param states conjunto de pares não marcados
     * @return lista contendo conjunto com os estados que serão unificados
     */
    private List<SortedSet<State>> merge(SortedSet<StatePair> states) {
        // Lista com o conjunto dos estados a serem unificados
        List<SortedSet<State>> mergedStates = new ArrayList<SortedSet<State>>();

        // Para cada par dos pares a serem unificados
        Iterator<StatePair> pairIterator = states.iterator();
        while (pairIterator.hasNext()) {
            StatePair pair = pairIterator.next();

            // Conjunto dos estados a serem unificados
            SortedSet<State> newState = new TreeSet<State>();

            State origin = pair.getState1();
            State destination = pair.getState2();

            System.out.println(origin + "(" + origin.getType() + ") " + destination + "(" + destination.getType() + ")");

            boolean add = false;
            if (origin.isInitial()) {
                destination.setType(destination.getType() | State.INITIAL);
                add = true;
            }
            if (destination.isInitial()) {
                origin.setType(origin.getType() | State.INITIAL);
                add = true;
            }

            if (origin.isNormal() && destination.isNormal()) {
                add = true;
            } else if (origin.isFinal() && destination.isFinal()) {
                add = true;
            }

            if (add) {
                // Estado de origem
                newState.add(origin);
                // Estado de destino
                newState.add(destination);
            }

            // Para cada outro par dos pares a serem unificados
            Iterator<StatePair> pairIterator2 = states.iterator();
            while (pairIterator2.hasNext()) {
                StatePair pair2 = pairIterator2.next();

                // Se os pares forem transitivos
                if (newState.contains(pair2.getState1()) || newState.contains(pair2.getState2())) {
                    // Adiciona os estados para gerar o novo estado unificado
                    newState.add(pair2.getState1());
                    newState.add(pair2.getState2());
                    pairIterator2.remove();
                }

            }

            // Adiciona na lista de conjuntos dos estados unificados
            mergedStates.add(newState);

            // Recria o iterator para evitar bugs
            pairIterator = states.iterator();
        }

        return mergedStates;
    }

    /**
     * Obtém todos os pares de estados não marcados
     *
     * @param states conjunto de todos os pares de estados
     * @return um novo conjunto contendo os pares não marcados
     */
    private SortedSet<StatePair> getNonMarked(SortedSet<StatePair> states) {
        SortedSet<StatePair> statesPair = new TreeSet<StatePair>();
        // Para cada par
        for (StatePair pair : states) {
            // Se o par não estiver marcado
            if (!pair.isMarked()) {
                // Adiciona ao novo conjunto
                statesPair.add(pair);
            }
        }
        return statesPair;
    }

    /**
     * Verifica e remove se necessário as transições e estado extra devido a conversão da função total
     */
    private void removeTotal() {
        if (totalState != null) {
            Iterator<Transition> transitions = this.automata.getTransitions().iterator();
            while (transitions.hasNext()) {
                Transition transition = transitions.next();

                if (transition.getState1().equals(totalState) || transition.getState2().equals(totalState)) {
                    transitions.remove();
                }
            }
            this.automata.removeState(totalState);
        }
    }

    /**
     * Substitui os estados antigos pelo novo estado unificado em todas as transições
     *
     * @param allStates conjunto contendo todos os estados antigos
     * @param newState novo estado a ser inserido
     */
    private void replaceState(SortedSet<State> allStates, State newState) {

        Set<Transition> newTransitions = new TreeSet<Transition>();

        Iterator<Transition> transitions = this.automata.getTransitions().iterator();
        while (transitions.hasNext()) {
            Transition transition = transitions.next();

            if (allStates.contains(transition.getState1()) && allStates.contains(transition.getState2())) {
                transitions.remove();
                newTransitions.add(new Transition(newState, newState, transition.getSymbol()));
            } else if (allStates.contains(transition.getState1())) {
                transitions.remove();
                newTransitions.add(new Transition(newState, transition.getState2(), transition.getSymbol()));
            } else if (allStates.contains(transition.getState2())) {
                transitions.remove();
                newTransitions.add(new Transition(transition.getState1(), newState, transition.getSymbol()));
            }
        }

        // Adiciona as novas transições
        for (Transition transition : newTransitions) {
            this.automata.addTransition(transition);
        }

        // Remove os estados antigos
        for (State state : allStates) {
            this.automata.removeState(state);
        }
    }
}
