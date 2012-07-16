package graph.transformers;

import automata.State;
import org.apache.commons.collections15.Transformer;

/**
 * Classe responsável por transformar os rótulos dos estados do autômato
 */
public class StateLabeller implements Transformer<State, String> {

    /**
     * Retorna um rótulo amigável para o estado
     *
     * @param state estado que deve ser transformado
     * @return nome do estado
     */
    @Override
    public String transform(State state) {
        return state.getLabel();
    }
}