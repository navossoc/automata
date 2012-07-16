package graph.transformers;

import automata.State;
import java.awt.Color;
import java.awt.Paint;
import org.apache.commons.collections15.Transformer;

/**
 * Classe responsável por alterar a cor dos estados do autômato
 */
public class StatePainter implements Transformer<State, Paint> {

    /**
     * Retorna uma nova cor de acordo com o tipo do estado
     *
     * @param state estado que deve ser colorido
     * @return cor do estado
     */
    @Override
    public Paint transform(State state) {
        switch (state.getType()) {
            case State.INITIAL:
                return Color.green;
            case State.FINAL:
                return Color.red;
            case State.BOTH:
                return Color.yellow;
            default:
                return Color.white;
        }
    }
}
