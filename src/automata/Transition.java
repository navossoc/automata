package automata;

/**
 * Classe que representa a transição
 */
public class Transition implements Comparable<Transition> {

    /**
     * Caractere que representa o símbolo de vazio
     */
    public static final String EMPTY_SYMBOL = "~";
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
     * Constrói uma transição a partir dos valores de uma transição existente
     *
     * @param transition transição a ser copiada
     */
    public Transition(Transition transition) {
        this.state1 = transition.state1;
        this.state2 = transition.state2;
        this.symbol = transition.symbol;
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

    /**
     * Altera o estado de origem
     *
     * @param state1 novo estado de origem
     */
    public void setState1(State state1) {
        this.state1 = state1;
    }

    /**
     * Altera o estado de destino
     *
     * @param state2 novo estado de destino
     */
    public void setState2(State state2) {
        this.state2 = state2;
    }

    /**
     * Altera o símbolo
     *
     * @param symbol novo símbolo
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public int compareTo(Transition transition) {
        return this.toString().compareTo(transition.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Transition) {
            return this.toString().equals(obj.toString());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.state1 != null ? this.state1.hashCode() : 0);
        hash = 29 * hash + (this.state2 != null ? this.state2.hashCode() : 0);
        hash = 29 * hash + (this.symbol != null ? this.symbol.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return state1 + " " + symbol + " " + state2;
    }
}
