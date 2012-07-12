package project;

import automata.Automata;
import automata.State;
import automata.Transition;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.DefaultEdgeLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.DefaultVertexLabelRenderer;
import java.awt.Color;
import javax.swing.JFrame;

public class GraphViewer {

    public static void showAutomata(Automata automata) {
        DirectedSparseMultigraph g = new DirectedSparseMultigraph();

        for (State state : automata.getStates()) {
            g.addVertex(state.getLabel());
        }
        for (Transition transition : automata.getTransitions()) {
            g.addEdge(new Edge(transition.getSymbol()), transition.getState1().getLabel(), transition.getState2().getLabel());
        }
        VisualizationViewer vv = new VisualizationViewer(new FRLayout(g));
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        JFrame jf = new JFrame();
        jf.getContentPane().add(vv);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.pack();
        jf.setVisible(true);
    }
    
    static class Edge
    {
        String label;
        
        public Edge(String label)
        {
            this.label = label;
        }

        @Override
        public boolean equals(Object obj) {
            return false;
        }

        @Override
        public String toString() {
            return label;
        }
    }
}
