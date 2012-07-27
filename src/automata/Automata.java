package automata;

import java.util.*;

/**
 * Classe que representa os autômatos
 */
public class Automata {

    /**
     * Conjunto que contém os símbolos
     */
    private SortedSet<String> symbols;
    /**
     * Mapa que contém os estados
     */
    private SortedMap<String, State> states;
    /**
     * Conjunto que contém as transições
     */
    private SortedSet<Transition> transitions;

    /**
     * Construtor padrão
     */
    public Automata() {
        this.symbols = new TreeSet<String>();
        this.states = new TreeMap<String, State>();
        this.transitions = new TreeSet<Transition>();
    }

    /**
     * Adiciona um símbolo
     *
     * @param symbol símbolo a ser adicionado
     */
    public void addSymbol(String symbol) {
        this.symbols.add(symbol);
    }

    /**
     * Adiciona um novo estado
     *
     * @param state estado a ser adicionado
     */
    public void addState(State state) {
        System.out.println("Adicionado o estado:\t" + state);
        this.states.put(state.getLabel(), state);
    }

    /**
     * Adiciona um estado
     *
     * @param label rótulo do estado
     * @param type tipo do estado (normal, inicial, final ou ambos)
     */
    public void addState(String label, int type) {
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

    /**
     * Adiciona uma transição
     *
     * @param transition transição
     */
    public void addTransition(Transition transition) {
        System.out.println("Adicionada a transição:\t" + transition);
        this.transitions.add(transition);
    }

    /**
     * Adiciona uma transição
     *
     * @param state1 rótulo do estado de origem
     * @param state2 rótulo do estado de destino
     * @param symbol símbolo
     */
    public void addTransition(String state1, String state2, String symbol) {
        this.transitions.add(new Transition(this.states.get(state1),
                this.states.get(state2), symbol));
    }

    /**
     * Adiciona transições
     *
     * @param state1 rótulo do estado de origem
     * @param state2 rótulo do estado de destino
     * @param symbols array de símbolos
     */
    public void addTransition(String state1, String state2, String[] symbols) {
        State origin = this.states.get(state1);
        if (origin == null) {
            this.addState(state1, State.NORMAL);
            origin = this.states.get(state1);
        }

        State destination = this.states.get(state2);
        if (destination == null) {
            this.addState(state2, State.NORMAL);
            destination = this.states.get(state2);
        }

        // Para cada símbolo
        for (String symbol : symbols) {
            this.symbols.add(symbol);
            this.transitions.add(new Transition(origin, destination, symbol));
        }
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
    public SortedSet<String> getSymbols() {
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
     * Retorna o estado inicial
     *
     * @return
     */
    public State getInitialState() {
        // Para cada estado
        for (State state : this.states.values()) {
            // Verifica se o estado contém o tipo inicial
            if (state.isInitial()) {
                return state;
            }
        }
        return null;
    }

    /**
     * Retorna um conjunto contendo estados finais
     *
     * @return
     */
    public SortedSet<State> getFinalStates() {
        SortedSet<State> finals = new TreeSet<State>();
        // Para cada estado
        for (State state : this.states.values()) {
            // Verifica se o estado contém o tipo final
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
    public SortedSet<Transition> getTransitions() {
        return this.transitions;
    }

    /**
     * Retorna um conjunto com todas as transições que tem origem no estado específicado
     *
     * @param origin estado de origem
     * @return
     */
    public SortedSet<Transition> getTransitionsFromOrigin(State origin) {
        SortedSet<Transition> tempTransitions = new TreeSet<Transition>();
        for (Transition t : this.getTransitions()) {
            if (t.getState1().equals(origin)) {
                tempTransitions.add(t);
            }
        }
        return tempTransitions;
    }

    /**
     * Retorna um conjunto com todas as transições cujo destino seja o estado específicado
     *
     * @param destination estado de destino
     * @return
     */
    public SortedSet<Transition> getTransitionsFromDestination(State destination) {
        SortedSet<Transition> tempTransitions = new TreeSet<Transition>();
        for (Transition t : this.getTransitions()) {
            if (t.getState2().equals(destination)) {
                tempTransitions.add(t);
            }
        }
        return tempTransitions;
    }

    /**
     * Remove um estado
     *
     * @param state estado a ser removido
     */
    public void removeState(State state) {
        System.out.println("Removido o estado:\t" + state);
        this.states.remove(state.getLabel());
    }

    /**
     * Remove um conjunto de estados
     *
     * @param states conjuntos de estados a serem removidos
     */
    public void removeStates(Set<State> states) {
        for (State state : states) {
            this.removeState(state);
        }
    }

    /**
     * Remove uma transição
     *
     * @param transition transição a ser removida
     * @return
     */
    public boolean removeTransition(Transition transition) {
        System.out.println("Removida a transição:\t" + transition);
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
            temp.addTransition(transition.getState1().getLabel(),
                    transition.getState2().getLabel(),
                    transition.getSymbol());
        }

        return temp;
    }
}
