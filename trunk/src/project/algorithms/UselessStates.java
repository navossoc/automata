package project.algorithms;

import automata.Automata;
import automata.State;
import automata.Transition;
import java.util.SortedSet;
import java.util.TreeSet;
import project.algorithms.helpers.StateHelper;

/**
 * ETAPA 4 - Algoritmo de Eliminação de Estados Inúteis
 */
public class UselessStates extends BaseAlgorithm {

    /**
     * Construtor padrão
     *
     * @param automata autômato a ser otimizado
     */
    public UselessStates(Automata automata) {
        super(automata);
    }

    /**
     * Executa o algoritmo de eliminação de estados inúteis
     *
     * @return autômato otimizado sem os estados inúteis
     */
    @Override
    public Automata Start() {
        // Conjunto com os estados que serão analisados
        SortedSet<StateHelper> states = new TreeSet<StateHelper>();

        // Para cada estado do autômato
        for (State state : this.automata.getStates()) {
            StateHelper helper = new StateHelper(state);
            // Se o estado for final
            if (state.isFinal()) {
                // Marca como válido
                helper.setValid();
            }
            // Adiciona na lista
            states.add(helper);
        }

        StateHelper current;
        // Enquanto houver estados válidos ainda não visitados
        while ((current = StateHelper.getNextValidUnvisited(states)) != null) {
            // Para cada transição com destino nesse estado válido
            for (Transition transition : this.automata.getTransitionsFromDestination(current.getState())) {
                // Marca como válido todos os estados de origem
                StateHelper.setStateAsValid(states, transition.getState1());
            }
            // Marca o estado atual como visitado
            current.setVisited();
        }

        // Para cada estado
        for (StateHelper state : states) {
            // Verifica se o estado é inválido
            if (!state.isValid()) {
                // Para cada transição com origem nesse estado inválido
                for (Transition transition : this.automata.getTransitionsFromOrigin(state.getState())) {
                    // Remove sua transição
                    this.automata.removeTransition(transition);
                }
                // Para cada transição com destino nesse estado inválido
                for (Transition transition : this.automata.getTransitionsFromDestination(state.getState())) {
                    // Remove sua transição
                    this.automata.removeTransition(transition);
                }
                // Remove o estado inválido
                this.automata.removeState(state.getState());
            }
        }

        // Remove os símbolos do alfabeto que não estão sendo usados
        removeUnusedSymbols();

        return this.automata;
    }
}
