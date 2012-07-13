package project.algorithms.helpers;

import automata.State;
import java.util.SortedSet;

/**
 * Classe para auxiliar os algoritmos de eliminação de estados inacessíveis e inúteis
 */
public class StateHelper implements Comparable<StateHelper> {

    /**
     * Estado sendo analisado
     */
    private State state;
    /**
     * Indica se o estado está marcado como válido (acessível/útil)
     */
    private boolean valid;
    /**
     * Indica se o estado está marcado como visitado (considerado)
     */
    private boolean visited;

    /**
     * Construtor padrão
     *
     * @param state estado sendo analisado
     */
    public StateHelper(State state) {
        this.state = state;
    }

    /**
     * Retorna o estado que está sendo analisado
     *
     * @return
     */
    public State getState() {
        return state;
    }

    /**
     * Retorna se o estado foi marcado como válido (acessível/útil)
     *
     * @return
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Retorna se o estado foi marcado como visitado (considerado)
     *
     * @return
     */
    public boolean isVisited() {
        return visited;
    }

    /**
     * Define o estado como válido (acessível/útil)
     */
    public void setValid() {
        this.valid = true;
    }

    /**
     * Define o estado como visitado (considerado)
     */
    public void setVisited() {
        this.visited = true;
    }

    
    /**
     * Obtém o próximo estado válido porém não visitado
     *
     * @param states conjunto de estados que estão sendo analisados
     * @return
     */
    public static StateHelper getNextValidUnvisited(SortedSet<StateHelper> states) {
        // Para cada estado do conjunto
        for (StateHelper tempState : states) {
            // Verifica se o estado é válido e não foi visitado
            if (tempState.isValid() && !tempState.isVisited()) {
                return tempState;
            }
        }
        return null;
    }

    /**
     * Marca o estado específicado como válido
     *
     * @param states conjunto de estados sendo analisados
     * @param validState estado a ser marcado como válido
     */
    public static void setStateAsValid(SortedSet<StateHelper> states, State validState) {
        // Para cada estado do conjunto
        for (StateHelper tempState : states) {
            // Verifica se o estado atual é igual ao específicado
            if (tempState.getState().equals(validState)) {
                // Marca o estado como válido
                tempState.setValid();
            }
        }
    }   
    
    @Override
    public int compareTo(StateHelper o) {
        return this.state.compareTo(o.state);
    }
}