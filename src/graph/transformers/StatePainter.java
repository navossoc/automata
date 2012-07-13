package graph.transformers;

import automata.State;
import java.awt.Color;
import java.awt.Paint;
import org.apache.commons.collections15.Transformer;

// TODO: tentar modificar o formato da figura
public class StatePainter implements Transformer<State, Paint> {

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
