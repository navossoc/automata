package graph.transformers;

import automata.Transition;
import org.apache.commons.collections15.Transformer;

/**
 * Classe responsável por transformar os rótulos das transições do autômato
 */
public class TransitionLabeller implements Transformer<Transition, String> {

    /**
     * Transforma uma transição em um rótulo amigável de se ler
     *
     * @param transition transição que deve ser transformada
     * @return símbolo consumido na transição
     */
    @Override
    public String transform(Transition transition) {
        return transition.getSymbol();
    }
}