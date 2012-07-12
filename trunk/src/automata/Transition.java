package automata;

/**
 * Classe que representa a transição
 */
public class Transition implements Comparable<Transition> {

    /**
     * Estado origem
     */
    private State state1;
    /**
     * Estado destino
     */
    private State state2;
    /**
     * Símbolo consumido
     */
    private String symbol;

    /**
     * Construtor padrão
     *
     * @param state1 estado origem
     * @param state2 estado destino
     * @param symbol símbolo consumido
     */
    public Transition(State state1, State state2, String symbol) {
        this.state1 = state1;
        this.state2 = state2;
        this.symbol = symbol;
    }

    /**
     * Retorna o estado de origem
     *
     * @return
     */
    public State getState1() {
        return state1;
    }

    /**
     * Retorna o estado de destino
     *
     * @return
     */
    public State getState2() {
        return state2;
    }

    /**
     * Retorna o símbolo
     *
     * @return
     */
    public String getSymbol() {
        return symbol;
    }

    @Override
    public int compareTo(Transition transition) {
        return this.toString().compareTo(transition.toString());
    }

    @Override
    public String toString() {
        return state1 + " " + symbol + " " + state2;
    }
}
