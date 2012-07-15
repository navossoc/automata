package project.algorithms;

import automata.Automata;
import automata.State;
import automata.Transition;
import java.util.*;
import project.FileFormat;
import project.algorithms.helpers.StatePair;

/**
 * ETAPA 5 - Algoritmo de Minimização de AF
 */
public class MinimizationFA extends BaseAlgorithm {

    private State totalState;

    /**
     * Construtor padrão
     *
     * @param automata autômato a ser otimizado
     */
    public MinimizationFA(Automata automata) {
        super(automata);
    }

    /**
     * Executa o algoritmo de minimização
     *
     * @return autômato mínimo equivalente
     */
    @Override
    public Automata Start() {
        convertTotal();

        // Array com todos os estados
        State[] arrrayStates = this.automata.getStates().toArray(new State[0]);

        // Tabela com os pares de estados
        SortedSet<StatePair> statesPair = new TreeSet<StatePair>();

        // Construção da tabela
        for (int i = 0; i < arrrayStates.length - 1; i++) {   // eixo horizontal
            State stateX = arrrayStates[i];
            for (int j = i + 1; j < arrrayStates.length; j++) {    // eixo vertical
                State stateY = arrrayStates[j];

                // Cria e adiciona um par de estados
                StatePair pair = new StatePair(stateX, stateY);
                statesPair.add(pair);

                // Marcação dos estados trivialmente não equivalente
                // Se o par é (F, 'F) ou ('F, F)
                if ((stateX.isFinal() != stateY.isFinal())) {
                    // Marca o par
                    pair.setMarked();
                }
            }
        }


        // Conjunto com todos os pares não marcados
        SortedSet<StatePair> nonMarked;

        // Obtém todos os pares de estados não marcados
        nonMarked = getNonMarked(statesPair);

        // Para cada par não marcado
        Iterator<StatePair> iterator = nonMarked.iterator();
        while (iterator.hasNext()) {
            StatePair quqv = iterator.next();

            // Verifica se o par não foi marcado
            if (!quqv.isMarked()) {
                // Verificação do par
                if (checkPair(statesPair, quqv)) {
                    iterator.remove();
                }
            }
        }

        // Unificação dos estados equivalentes

        // Obtém todos os pares de estados não marcados
        nonMarked = getNonMarked(statesPair);

        // Lista com o conjunto dos estados que serão unificados
        List<SortedSet<State>> mergedStates = merge(nonMarked);

        // Para cada conjunto de estados
        for (SortedSet<State> states : mergedStates) {
            // Nome do rótulo do estado unificado
            String mergedLabel = "";
            for (State s : states) {
                mergedLabel += s.getLabel();
            }

            // Cria o novo estado
            State mergedState = new State(mergedLabel, states.first().getType());
            // Substituí todas as referências dos estados antigos pelo estado unificado
            replaceState(states, mergedState);
            // Adiciona o novo estado
            this.automata.addState(mergedState);
        }

        // Remove as transições que foram inseridas ao tornar o autômato total
        removeTotal();

        return this.automata;
    }

    /**
     * Analisa se o par específicado deve ser marcado como não equivalente
     *
     * @param statesPair conjunto com todos os pares não marcados
     * @param quqv par a ser analisado
     * @return
     */
    private boolean checkPair(SortedSet<StatePair> statesPair, StatePair quqv) {
        // Mapa com símbolos e estados de destino para qu
        SortedMap<String, State> quStates = new TreeMap<String, State>();
        // Para cada transição a partir da origem de qu
        for (Transition transition : this.automata.getTransitionsFromOrigin(quqv.getState1())) {
            // Para cada símbolo
            for (String symbol : this.automata.getSymbols()) {
                // Se o símbolo da transição for igual
                if (transition.getSymbol().equals(symbol)) {
                    // Associa o símbolo e o estado no mapa
                    quStates.put(symbol, transition.getState2());
                }
            }
        }

        // Mapa com símbolos e seus respectivos estados de destino para qv
        SortedMap<String, State> qvStates = new TreeMap<String, State>();
        // Para cada transição a partir da origem de qv
        for (Transition transition : this.automata.getTransitionsFromOrigin(quqv.getState2())) {
            // Para cada símbolo
            for (String symbol : this.automata.getSymbols()) {
                // Se o símbolo da transição for igual
                if (transition.getSymbol().equals(symbol)) {
                    // Associa o símbolo e o estado no mapa
                    qvStates.put(symbol, transition.getState2());
                }
            }
        }

        // Flag para indicar se o par foi marcado
        boolean marked = false;

        // Para cada símbolo
        for (String symbol : this.automata.getSymbols()) {
            State pu = quStates.get(symbol); // pu
            State pv = qvStates.get(symbol); // pv

            // Se pu = pv (caso a)
            if (pu.equals(pv)) {
                // o par não deve ser marcado
                continue;
            } else { // Se pu != pv (caso b e c)
                // Obtém a referência do par
                StatePair pupv = new StatePair(pu, pv);
                for (StatePair tempState : statesPair) {
                    if (tempState.equals(pupv)) {
                        pupv = tempState;
                        break;
                    }
                }

                // Se o par {pu,pv} não estiver marcado
                if (!pupv.isMarked()) {
                    // O par {qu,qv} é incluido em uma lista de {pu,pv}
                    pupv.getList().add(quqv);
                } else { // se {pu,pv} está marcado
                    // {qu,qv} não é equivalente e é marcado
                    quqv.setMarked();
                    // É marcado todos os pares na lista de {qu,qv}
                    quqv.markAll();
                    marked = true;
                }
            }
        }

        return marked;
    }

    /**
     * Se necessário converte o autômato para função total
     */
    private void convertTotal() {
        // Verifica se esse autômato é total
        if (this.automata.getTransitions().size() == this.automata.getSymbols().size() * this.automata.getStates().size()) {
            return; // Não é necessário converter
        }

        // Cria e adiciona um novo estado não final (total)
        totalState = new State(FileFormat.SEPARATOR, State.NORMAL);
        this.automata.addState(totalState);

        // Para cada estado do autômato atual
        for (State state : this.automata.getStates()) {
            // Obtém todas as transições a partir da origem desse estado
            SortedSet<Transition> transitions = this.automata.getTransitionsFromOrigin(state);
            // Se o número de transições for menor que o número de símbolos
            if (transitions.size() < this.automata.getSymbols().size()) {
                // Procura quais símbolos ainda precisam ser usados
                for (String symbol : this.automata.getSymbols()) {
                    boolean used = false;
                    for (Transition transition : transitions) {
                        if (transition.getSymbol().equals(symbol)) {
                            used = true;
                            break;
                        }
                    }

                    // Se o símbolo não foi usado
                    if (!used) {
                        // Adiciona uma transição para o estado complementar (total)
                        this.automata.addTransition(new Transition(state, totalState, symbol));
                    }
                }
            }
        }
    }

    /**
     * Faz a unificação de todos os pares de estados não marcados
     *
     * @param states conjunto de pares não marcados
     * @return lista contendo conjunto com os estados que serão unificados
     */
    private List<SortedSet<State>> merge(SortedSet<StatePair> states) {
        // Lista com o conjunto dos estados a serem unificados
        List<SortedSet<State>> mergedStates = new ArrayList<SortedSet<State>>();

        // Para cada par dos pares a serem unificados
        Iterator<StatePair> pairIterator = states.iterator();
        while (pairIterator.hasNext()) {
            StatePair pair = pairIterator.next();

            // Conjunto dos estados a serem unificados
            SortedSet<State> newState = new TreeSet<State>();

            State origin = pair.getState1();
            State destination = pair.getState2();

            // Flag para indicar se o par deve ser unificado
            boolean merged = false;

            // Se o estado de origem for inicial
            if (origin.isInitial()) {
                // Adiciona ao estado de destino o tipo inicial
                destination.addInitial();
                merged = true;
            }
            // Se o estado de destino for inicial
            if (destination.isInitial()) {
                // Adiciona ao estado de origem o tipo inicial
                origin.addInitial();
                merged = true;
            }

            // Se os estados de origem e destino forem normais
            if (origin.isNormal() && destination.isNormal()) {
                merged = true;
            } // Se os estados de origem e destino forem finais
            else if (origin.isFinal() && destination.isFinal()) {
                merged = true;
            }

            // Deve ser unificado
            if (merged) {
                // Estado de origem
                newState.add(origin);
                // Estado de destino
                newState.add(destination);
            }

            // Para cada outro par dos pares a serem unificados
            Iterator<StatePair> pairIterator2 = states.iterator();
            while (pairIterator2.hasNext()) {
                StatePair pair2 = pairIterator2.next();

                // Se os pares forem transitivos
                if (newState.contains(pair2.getState1()) || newState.contains(pair2.getState2())) {
                    // Adiciona os estados para gerar o novo estado unificado
                    newState.add(pair2.getState1());
                    newState.add(pair2.getState2());
                    pairIterator2.remove();
                }

            }

            // Adiciona na lista de conjuntos dos estados unificados
            mergedStates.add(newState);

            // Recria o iterator para evitar bugs
            pairIterator = states.iterator();
        }

        return mergedStates;
    }

    /**
     * Obtém todos os pares de estados não marcados
     *
     * @param states conjunto de todos os pares de estados
     * @return um novo conjunto contendo os pares não marcados
     */
    private SortedSet<StatePair> getNonMarked(SortedSet<StatePair> states) {
        // Conjunto com todos os pares não marcados
        SortedSet<StatePair> nonMarkedPairs = new TreeSet<StatePair>();

        // Para cada par
        for (StatePair pair : states) {
            // Se o par não estiver marcado
            if (!pair.isMarked()) {
                // Adiciona ao novo conjunto
                nonMarkedPairs.add(pair);
            }
        }

        return nonMarkedPairs;
    }

    /**
     * Remove se necessário as transições e o estado extra adicionados devido a conversão para função total
     */
    private void removeTotal() {
        // Verifica se o estado complementar não foi criado
        if (totalState == null) {
            return; // ignora
        }

        // Para cada transição
        Iterator<Transition> transitions = this.automata.getTransitions().iterator();
        while (transitions.hasNext()) {
            Transition transition = transitions.next();

            // Se a transição tiver origem ou destino ao estado complementar (total)
            if (transition.getState1().equals(totalState) || transition.getState2().equals(totalState)) {
                // Remove a transição
                transitions.remove();
            }
        }

        // Remove o estado complementar (total)
        this.automata.removeState(totalState);
    }

    /**
     * Substituí os estados antigos pelo novo estado unificado em todas as transições
     *
     * @param oldStates conjunto contendo todos os estados antigos
     * @param newState novo estado a ser substituído
     */
    private void replaceState(SortedSet<State> oldStates, State newState) {

        // Conjunto com todas as novas transições a serem adicionadas
        Set<Transition> newTransitions = new TreeSet<Transition>();

        // Para cada transição
        Iterator<Transition> transitions = this.automata.getTransitions().iterator();
        while (transitions.hasNext()) {
            Transition transition = transitions.next();

            // Se a origem e o destino da transição for de algum dos estados antigos
            if (oldStates.contains(transition.getState1()) && oldStates.contains(transition.getState2())) {
                // Remove essa transição
                transitions.remove();
                // Cria uma nova transição com o estado a ser substituído
                newTransitions.add(new Transition(newState, newState, transition.getSymbol()));
            } // Se a origem da transição for de algum dos estados antigos
            else if (oldStates.contains(transition.getState1())) {
                // Remove essa transição
                transitions.remove();
                // Cria uma nova transição com o estado a ser substituído
                newTransitions.add(new Transition(newState, transition.getState2(), transition.getSymbol()));
            } // Se o destino da transição for de algum dos estados antigos
            else if (oldStates.contains(transition.getState2())) {
                // Remove essa transição
                transitions.remove();
                // Cria uma nova transição com o estado a ser substituído
                newTransitions.add(new Transition(transition.getState1(), newState, transition.getSymbol()));
            }
        }

        // Adiciona as novas transições
        for (Transition transition : newTransitions) {
            this.automata.addTransition(transition);
        }

        // Remove os estados antigos
        for (State state : oldStates) {
            this.automata.removeState(state);
        }
    }
}
