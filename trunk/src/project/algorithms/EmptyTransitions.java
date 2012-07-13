package project.algorithms;

import automata.Automata;
import automata.State;
import automata.Transition;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * ETAPA 1 - Algoritmo de Eliminação de Transições em Vazio
 */
public class EmptyTransitions {

    /**
     * Mantêm uma cópia do autômato que será modificado
     */
    private Automata automata;
    /**
     * Conjunto temporário de transições em vazio
     */
    private SortedSet<Transition> emptyTransitions;

    /**
     * Construtor padrão
     *
     * @param automata automato a ser otimizado
     */
    public EmptyTransitions(Automata automata) {
        this.automata = automata.clone();
        this.emptyTransitions = new TreeSet<Transition>();
    }

    /**
     * Executa o algoritmo de eliminação de transições em vazio
     *
     * @return autômato equivalente sem as transições em vazio
     */
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

                    // Obtêm todas as transições que partem do estado destino
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
        Iterator<String> iterator = this.automata.getSymbols().iterator();
        while (iterator.hasNext()) {
            String symbol = iterator.next();
            if (symbol.equalsIgnoreCase(Transition.EMPTY_SYMBOL)) {
                this.automata.getSymbols().remove(symbol);
                break;
            }
        }

        return this.automata;
    }

    /**
     * Verifica se a transição consome símbolo vazio
     *
     * @param transition transição a ser verificada
     * @return
     */
    private boolean isEmptySymbol(Transition transition) {
        return transition.getSymbol().equalsIgnoreCase(Transition.EMPTY_SYMBOL);
    }

    /**
     * Retorna um conjunto com todas as transições que consomem símbolo vazio do autômato
     *
     * @return
     */
    private SortedSet<Transition> getEmptyTransitions() {
        emptyTransitions.clear();
        for (Transition originalTransition : this.automata.getTransitions()) {
            if (isEmptySymbol(originalTransition)) {
                emptyTransitions.add(originalTransition);
            }
        }

        return emptyTransitions;
    }
}