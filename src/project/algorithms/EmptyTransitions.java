package project.algorithms;

import automata.Automata;
import automata.State;
import automata.Transition;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * ETAPA 1 - Algoritmo de Eliminação de Transições em Vazio
 */
public class EmptyTransitions extends BaseAlgorithm {

    /**
     * Construtor padrão
     *
     * @param automata autômato a ser otimizado
     */
    public EmptyTransitions(Automata automata) {
        super(automata);
    }

    /**
     * Executa o algoritmo de eliminação de transições em vazio
     *
     * @return autômato equivalente sem as transições em vazio
     */
    @Override
    public Automata Start() {
        SortedSet<Transition> empty = getEmptyTransitions();
        while (empty.size() > 0) {
            for (Transition t : empty) {

                State origin = t.getState1();
                State destination = t.getState2();
                // Verifica se o estado de origem é diferente do estado de destino
                if (!origin.equals(destination)) {

                    // Verifica se o estado de destino é final
                    if (destination.isFinal()) {
                        // Adiciona ao estado de origem o atributo final
                        origin.setType(origin.getType() | State.FINAL);
                    }

                    // Obtém todas as transições que partem do estado destino
                    SortedSet<Transition> transitions = this.automata.getTransitionsFromOrigin(destination);

                    for (Transition beingAnalyzed : transitions) {
                        // Adiciona uma nova transição
                        this.automata.addTransition(new Transition(origin, beingAnalyzed.getState2(), beingAnalyzed.getSymbol()));
                    }
                }

                // Remove a transição que estava sendo analisada
                this.automata.removeTransition(t);
            }

            empty = getEmptyTransitions();
        }

        // Remove o símbolo de vazio do alfabeto
        this.automata.getSymbols().remove(BaseAlgorithm.EMPTY_SYMBOL);

        return this.automata;
    }

    /**
     * Verifica se a transição consome o símbolo vazio
     *
     * @param transition transição a ser verificada
     * @return
     */
    private boolean isEmptySymbol(Transition transition) {
        // Compara o símbolo da transição
        return transition.getSymbol().equalsIgnoreCase(BaseAlgorithm.EMPTY_SYMBOL);
    }

    /**
     * Retorna um conjunto com todas as transições que consomem o símbolo vazio do autômato
     *
     * @return
     */
    private SortedSet<Transition> getEmptyTransitions() {
        // Conjunto temporário de transições em vazio
        SortedSet<Transition> emptyTransitions = new TreeSet<Transition>();

        // Para cada transição do autômato
        for (Transition originalTransition : this.automata.getTransitions()) {
            // Verifica se o símbolo é o vazio
            if (isEmptySymbol(originalTransition)) {
                emptyTransitions.add(originalTransition);
            }
        }

        return emptyTransitions;
    }
}