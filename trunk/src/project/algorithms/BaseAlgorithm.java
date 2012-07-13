package project.algorithms;

import automata.Automata;
import automata.Transition;
import java.util.Iterator;

/**
 * Classe comum para todos os algoritmos
 */
public abstract class BaseAlgorithm {

    /**
     * Caractere que representa o símbolo de vazio
     */
    public static final String EMPTY_SYMBOL = "~";
    /**
     * Mantém uma cópia do autômato que será modificado
     */
    protected Automata automata;

    /**
     * Construtor padrão
     *
     * @param automata autômato a ser otimizado
     */
    public BaseAlgorithm(Automata automata) {
        this.automata = automata.clone();
    }

    /**
     * Executa o algoritmo que for implementado pela classe
     *
     * @return autômato equivalente ou otimizado
     */
    public abstract Automata Start();

    /**
     * Remove símbolos não consumidos pelo autômato
     */
    protected void removeUnusedSymbols() {
        Iterator<String> iterator = this.automata.getSymbols().iterator();
        // Para cada símbolo do autômato
        while (iterator.hasNext()) {
            // Símbolo
            String symbol = iterator.next();
            boolean used = false;

            // Verifica em todas as transições se o símbolo é usado pelo menos uma vez
            for (Transition transition : this.automata.getTransitions()) {
                if (transition.getSymbol().equals(symbol)) {
                    used = true;
                    break;
                }
            }

            // Se não for usado, remove ele
            if (!used) {
                iterator.remove();
            }
        }
    }
}
