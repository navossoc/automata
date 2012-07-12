package automata;

import java.util.*;

/**
 * Classe que representa os autômatos
 */
public class Automata {

    /**
     * Conjunto que contém os símbolos
     */
    private Set<String> symbols;
    /**
     * Mapa que contém os estados
     */
    private Map<String, State> states;
    /**
     * Conjunto que contém as transições
     */
    private Set<Transition> transitions;

    /**
     * Construtor padrão
     */
    public Automata() {
        this.symbols = new TreeSet<String>();
        this.states = new TreeMap<String, State>();
        this.transitions = new TreeSet<Transition>();
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
     * Retorna um conjunto de símbolos
     *
     * @return
     */
    public Set<String> getSymbols() {
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
            // Verifica se o estado contém o atributo inicial
            if (state.isInitial()) {
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
            // Verifica se o estado contém o atributo final
            if (state.isFinal()) {
                finals.add(state);
            }
        }
        return finals;
    }

    /**
     * Retorna um conjunto de transições
     *
     * @return
     */
    public Set<Transition> getTransitions() {
        return this.transitions;
    }

    /**
     * Remove uma transição
     *
     * @param transition transição a ser removida
     * @return
     */
    public boolean removeTransition(Transition transition) {
        return this.transitions.remove(transition);
    }

    @Override
    public Automata clone() {
        Automata temp = new Automata();
        temp.symbols.addAll(this.symbols);
        for (State state : this.states.values()) {
            temp.states.put(state.getLabel(), new State(state));
        }
        for (Transition transition : this.transitions) {
            temp.addTransition(new Transition(
                    temp.getState(transition.getState1().getLabel()),
                    temp.getState(transition.getState2().getLabel()),
                    transition.getSymbol()));
        }

        return temp;
    }
}