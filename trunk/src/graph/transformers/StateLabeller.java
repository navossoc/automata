package graph.transformers;

import automata.State;
import org.apache.commons.collections15.Transformer;

/**
 * Classe responsável por transformar os rótulos dos estados do autômato
 */
public class StateLabeller implements Transformer<State, String> {

    /**
     * Transforma um estado em um rótulo amigável de se ler
     *
     * @param state estado que deve ser transformado
     * @return nome do estado
     */
    @Override
    public String transform(State state) {
        return state.getLabel();
    }
}