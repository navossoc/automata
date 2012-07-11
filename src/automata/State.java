package automata;

/**
 * Classe que representa os estados
 */
public class State {

    /**
     * Estado normal
     */
    public static final int NORMAL = 0;
    /**
     * Estado inicial
     */
    public static final int INITIAL = 1;
    /**
     * Estado final
     */
    public static final int FINAL = 2;
    /**
     * Estado inicial e final
     */
    public static final int BOTH = 3;
    /**
     * Nome do estado
     */
    private String label;
    /**
     * Tipo do estado
     */
    private int type;

    /**
     * Construtor padrão
     * @param label nome
     * @param type tipo
     */
    public State(String label, int type) {
        this.label = label;
        this.type = type;
    }

    /**
     * Retorna o nome
     * @return
     */
    public String getLabel() {
        return label;
    }

    /**
     * Retorna o tipo
     */
    public int getType() {
        return type;
    }

    /**
     * Altera o tipo
     * @param type novo tipo
     */
    public void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof State) {
            State state = (State) obj;
            return this.label.equals(state.label);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.label != null ? this.label.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return label;
    }
}
