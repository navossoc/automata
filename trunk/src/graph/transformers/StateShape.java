package graph.transformers;

import automata.State;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import org.apache.commons.collections15.Transformer;

/**
 * Classe responsável por alterar o formato (desenho) do estado do autômato
 */
public class StateShape implements Transformer<State, Shape> {

    /**
     * Tamanho do círculo
     */
    private final static float size = 30;
    /**
     * Deslocamento do círculo (centralizar)
     */
    private final static float offset = -size / 2;
    /**
     * Escala do tamanho do círculo e da seta
     */
    private final static float scale = 0.75f;

    /**
     * Retorna uma figura de acordo com o tipo do estado
     *
     * @param state estado que deve ser transformado
     * @return figura do estado
     */
    @Override
    public Shape transform(State state) {

        // Círculo
        Ellipse2D circle = new Ellipse2D.Float(offset, offset, size, size);
        if (state.isNormal()) {
            return circle;
        }

        // Nova figura
        Path2D path = new Path2D.Float(circle);

        // Se o estado for do tipo inicial
        if (state.isInitial()) {
            double angle = (135 * Math.PI) / 180;
            float x = (float) (Math.sin(angle) * offset) - ((size * scale) / 2);
            float y = (float) -(Math.cos(angle) * offset) - ((size * scale) / 2);
            // Seta
            path.append(new Arc2D.Float(x, y, size * scale, size * scale, 112.5f, 45, Arc2D.PIE), false);
        }
        // Se o estado for do tipo final
        if (state.isFinal()) {
            // Círculo
            path.append(new Ellipse2D.Float(offset * scale, offset * scale, size * scale, size * scale), false);
        }

        return path;
    }
}
