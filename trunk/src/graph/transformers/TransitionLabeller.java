package graph.transformers;

import automata.Transition;
import org.apache.commons.collections15.Transformer;

public class TransitionLabeller implements Transformer<Transition, String> {

    @Override
    public String transform(Transition transition) {
        return transition.getSymbol();
    }

}