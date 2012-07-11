package automata;

import java.util.*;

/**
 * Classe que representa os autômatos
 */
public class Automata {

    /**
     * Lista que contém os símbolos
     */
    private List<String> symbols;
    /**
     * Mapa que contém os estados
     */
    private Map<String, State> states;
    /**
     * Lista que contém as transições
     */
    private List<Transition> transitions;

    /**
     * Construtor padrão
     */
    public Automata() {
        this.symbols = new ArrayList<String>();
        this.states = new TreeMap<String, State>();
        this.transitions = new ArrayList<Transition>();
    }

    /**
     * Adiciona símbolos
     *
     * @param symbols array de símbolos
     */
    public void addSymbols(String[] symbols) {
        this.symbols.addAll(Arrays.asList(symbols));
    }

    /**
     * Adiciona estados
     *
     * @param states array de estados
     * @param type tipo do estado (normal, inicial, final ou ambos)
     */
    public void addStates(String[] states, int type) {
        for (String label : states) {
            // Verifica se o estado não existe
            if (!this.states.containsKey(label)) {
                // Adiciona
                State state = new State(label, type);
                this.states.put(label, state);
            } else {
                // Altera o tipo
                State state = this.states.get(label);
                state.setType(state.getType() | type);
            }
        }
    }

    /**
     * Adiciona uma transição
     *
     * @param transition transição
     */
    public void addTransition(Transition transition) {
        this.transitions.add(transition);
    }

    /**
     * Retorna um estado dado o seu nome
     *
     * @param label nome do estado
     * @return
     */
    public State getState(String label) {
        return this.states.get(label);
    }

    /**
     * Retorna uma lista de símbolos
     *
     * @return
     */
    public List<String> getSymbols() {
        return this.symbols;
    }

    /**
     * Retorna uma lista de estados
     *
     * @return
     */
    public Collection<State> getStates() {
        return this.states.values();
    }

    /**
     * Retorna uma lista contendo estados iniciais
     *
     * @return
     */
    public Collection<State> getInitialStates() {
        List<State> initials = new ArrayList<State>();
        for (State state : this.states.values()) {
            // Verifica se o tipo do estado contém o atributo inicial
            if ((state.getType() & State.INITIAL) == State.INITIAL) {
                initials.add(state);
            }
        }
        return initials;
    }

    /**
     * Retorna uma lista contendo estados finais
     *
     * @return
     */
    public Collection<State> getFinalStates() {
        List<State> finals = new ArrayList<State>();
        for (State state : this.states.values()) {
            // Verifica se o tipo do estado contém o atributo final
            if ((state.getType() & State.FINAL) == State.FINAL) {
                finals.add(state);
            }
        }
        return finals;
    }

    /**
     * Retorna uma lista de transições
     *
     * @return
     */
    public List<Transition> getTransitions() {
        return this.transitions;
    }
}
