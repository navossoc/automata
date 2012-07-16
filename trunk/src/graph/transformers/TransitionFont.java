package graph.transformers;

import automata.Transition;
import java.awt.Font;
import org.apache.commons.collections15.Transformer;

/**
 * Classe responsável por alterar a fonte das transições do autômato
 */
public class TransitionFont implements Transformer<Transition, Font> {

    /**
     * Nome da fonte
     */
    private static final String name = "Helvetica";
    /**
     * Tamanho da fonte
     */
    private static final int size = 12;

    /**
     * Altera a fonte da transição
     *
     * @param transition transição a ser alterada
     * @return nova fonte
     */
    @Override
    public Font transform(Transition transition) {
        return new Font(name, Font.BOLD, size);
    }
}
