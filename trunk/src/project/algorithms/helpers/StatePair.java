package project.algorithms.helpers;

import automata.State;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Classe para auxiliar o algoritmo de minimização
 */
public class StatePair implements Comparable<StatePair> {

    /**
     * Primeiro estado
     */
    private final State state1;
    /**
     * Segundo estado
     */
    private final State state2;
    /**
     * Indica se esse par está marcado como não equivalente
     */
    private boolean marked;
    // Lista de pares encabeçados nesse par
    SortedSet<StatePair> list;

    /**
     * Construtor padrão
     *
     * @param state1 primeiro estado
     * @param state2 segundo estado
     */
    public StatePair(State state1, State state2) {
        this.state1 = state1;
        this.state2 = state2;
        this.list = new TreeSet<StatePair>();
    }

    /**
     * Retorna a lista de pares encabeçados nesse par
     *
     * @return
     */
    public SortedSet<StatePair> getList() {
        return list;
    }

    /**
     * Retorna o primeiro estado do par ordenado
     *
     * @return
     */
    public State getState1() {
        return state1;
    }

    /**
     * Retorna o esgundo estado do par ordenado
     *
     * @return
     */
    public State getState2() {
        return state2;
    }

    /**
     * Retorna se o par está marcado
     *
     * @return
     */
    public boolean isMarked() {
        return marked;
    }

    /**
     * Marca todos os pares que estão na lista (recursivamente)
     */
    public void markAll() {
        if (list.size() > 0) {
            // Para cada par da lista
            for (StatePair other : this.list) {
                // Se não for ele mesmo
                if (other != this) {
                    // Marca o par
                    other.setMarked();
                    // Marca todos da lista
                    other.markAll();
                }
            }
        }
    }

    /**
     * Marca esse par
     */
    public void setMarked() {
        this.marked = true;
    }

    @Override
    public int compareTo(StatePair o) {
        return this.toString().compareTo(o.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StatePair) {
            StatePair other = (StatePair) obj;
            return (this.state1.equals(other.state1) && this.state2.equals(other.state2))
                    || (this.state1.equals(other.state2) && this.state2.equals(other.state1));
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (this.state1 != null ? this.state1.hashCode() : 0);
        hash = 79 * hash + (this.state2 != null ? this.state2.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "{" + state1 + "," + state2 + "}";
    }
}