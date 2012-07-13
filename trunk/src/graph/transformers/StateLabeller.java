package graph.transformers;

import automata.State;
import org.apache.commons.collections15.Transformer;

public class StateLabeller implements Transformer<State, String> {

    @Override
    public String transform(State state) {
        return state.getLabel();
    }

}