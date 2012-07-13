package project.algorithms;

import automata.Automata;
import automata.State;
import automata.Transition;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * ETAPA 2 - Algoritmo de Eliminação de Não Determinísmos
 */
public class NonDeterministic extends BaseAlgorithm {

    /**
     * Construtor padrão
     *
     * @param automata autômato a ser otimizado
     */
    public NonDeterministic(Automata automata) {
        super(automata);
    }

    /**
     * Executa o algoritmo de eliminação de não determinísmos
     *
     * @return autômato equivalente determinístico
     */
    @Override
    public Automata Start() {
        // Obtém todos os não determinísmos
        SortedMap<State, SortedMap<String, SortedSet<Transition>>> all = getNonDeterminism();
        SortedSet<State> tempStates = new TreeSet<State>();

        // Enquanto houver pelo menos um não determinísmo
        while (all.size() > 0) {
            State state = all.firstKey();

            SortedMap<String, SortedSet<Transition>> symbolTransitions = all.get(state);

            String symbol = symbolTransitions.firstKey();

            tempStates.clear();
            String newStateLabel = "";
            int newStateType = State.NORMAL;

            // Para cada transição do não determinísmo para dado símbolo
            for (Transition t : symbolTransitions.get(symbol)) {
                State tempState = t.getState2();
                tempStates.add(tempState);

                // Concatena o nome dos estados para gerar o novo estado
                newStateLabel += tempState.getLabel();

                // Se o estado de destino for final
                if (tempState.isFinal()) {
                    // O novo estado também será final
                    newStateType |= State.FINAL;
                }

            }
            // Cria o novo estado
            State newState = new State(newStateLabel, newStateType);

            // Adiciona o novo estado ao conjunto de estados do autômato
            automata.addState(newState);

            // Copia todas as transições dos estados que tinham origem nos estados de destino
            for (State tempState : tempStates) {
                for (Transition tempTransitions : this.automata.getTransitionsFromOrigin(tempState)) {
                    // Adiciona uma nova transição com origem no novo estado e destino
                    automata.addTransition(new Transition(newState,
                            tempTransitions.getState2(), tempTransitions.getSymbol()));
                }
            }

            // Substitui as referências dos estados de destino antigas para o novo estado
            replaceStateInTransitions(newState);

            // Obtém uma nova relação de não determinísmo
            all = getNonDeterminism();
        }

        return automata;
    }

    /**
     * Retorna um mapa contendo todos os não determinísmos dado um estado
     *
     * @return
     */
    private SortedMap<State, SortedMap<String, SortedSet<Transition>>> getNonDeterminism() {
        SortedMap<State, SortedMap<String, SortedSet<Transition>>> all = new TreeMap<State, SortedMap<String, SortedSet<Transition>>>();

        // Para cada estado do autômato
        for (State state : automata.getStates()) {

            /*
             * Cria um mapa contendo a relação de um símbolo para todas as transições
             * que o consomem com origem no estado que está sendo analisado
             */
            SortedMap<String, SortedSet<Transition>> map = new TreeMap<String, SortedSet<Transition>>();
            for (String symbol : automata.getSymbols()) {
                map.put(symbol, new TreeSet<Transition>());
            }
            for (Transition transition : this.automata.getTransitionsFromOrigin(state)) {
                map.get(transition.getSymbol()).add(transition);
            }

            SortedMap<String, SortedSet<Transition>> toBeCopy = new TreeMap<String, SortedSet<Transition>>();
            for (String symbol : map.keySet()) {
                SortedSet<Transition> transitions = map.get(symbol);

                /*
                 * Verifica se ocorre não determinísmo para um símbolo a partir
                 * do estado que está sendo analisado
                 */
                if (transitions.size() > 1) {
                    // Copia todas as transições se ocorrer
                    toBeCopy.put(symbol, transitions);
                }
            }

            // Se existir pelo menos um não determinísmo para um símbolo no estado analisado
            if (toBeCopy.size() > 0) {
                // Adiciona ele
                all.put(state, toBeCopy);
            }
        }

        return all;
    }

    /**
     * Substituí o estado de destino das transições pelo novo específicado
     *
     * @param newState novo estado de destino
     */
    private void replaceStateInTransitions(State newState) {
        // Obtém um mapa contendo todos os não determinísmos
        SortedMap<State, SortedMap<String, SortedSet<Transition>>> all = getNonDeterminism();

        // Para cada um dos estados com não determinísmo
        for (State state : all.keySet()) {
            SortedMap<String, SortedSet<Transition>> symbolTransitions = all.get(state);
            for (String symbol : symbolTransitions.keySet()) {
                // Nome do novo estado de destino (concatenado)
                String newDestination = "";

                /*
                 * Gera um estado temporário a partir das transições com origem
                 * no estado que está sendo analisado e para o símbolo
                 */
                for (Transition t : symbolTransitions.get(symbol)) {
                    newDestination += t.getState2();
                }

                // Se o novo estado já existir
                if (automata.getState(newDestination) != null) {
                    // Remove as transições antigas
                    for (Transition t : symbolTransitions.get(symbol)) {
                        automata.removeTransition(t);
                    }

                    /*
                     * Adiciona a nova transição com origem no estado analisado
                     * e destino no novo estado gerado
                     */
                    automata.addTransition(new Transition(state, newState, symbol));
                }
            }
        }
    }
}
