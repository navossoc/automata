package project;

import automata.Automata;
import automata.State;
import automata.Transition;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.util.Relaxer;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import graph.transformers.*;
import java.awt.Dimension;
import project.algorithms.*;

/**
 * Classe usada para desenhar os grafos na tela
 */
public class GraphViewer extends javax.swing.JFrame {

    Automata[] automatas = new Automata[6];
    String filename;
    int step;

    /** Creates new form GraphViewer */
    public GraphViewer() {
        initComponents();
        // Centraliza a janela
        setLocationRelativeTo(null);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelGraph = new javax.swing.JPanel();
        jButtonPrevious = new javax.swing.JButton();
        jButtonNext = new javax.swing.JButton();
        jScrollPane = new javax.swing.JScrollPane();
        jConsole = new project.Console();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Otimizador de Autômatos Finitos");

        jPanelGraph.setLayout(new java.awt.GridLayout(1, 0));

        jButtonPrevious.setText("<");
        jButtonPrevious.setEnabled(false);
        jButtonPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPreviousActionPerformed(evt);
            }
        });

        jButtonNext.setText(">");
        jButtonNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNextActionPerformed(evt);
            }
        });

        jConsole.setColumns(20);
        jConsole.setEditable(false);
        jConsole.setRows(5);
        jScrollPane.setViewportView(jConsole);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelGraph, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonNext, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelGraph, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonNext, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                    .addComponent(jButtonPrevious, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                    .addComponent(jScrollPane))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Botão de voltar
     *
     * @param evt
     */
    private void jButtonPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPreviousActionPerformed
        step--;
        // Executa o algoritmo anterior
        runAlgorithm();
        // Desenha o autômato
        drawAutomata();
        // Habilita o botão de avançar
        jButtonNext.setEnabled(true);
        // Desabilita o botão de voltar
        if (step <= 0) {
            jButtonPrevious.setEnabled(false);
        }
    }//GEN-LAST:event_jButtonPreviousActionPerformed

    /**
     * Botão de avançar
     *
     * @param evt
     */
    private void jButtonNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNextActionPerformed
        step++;
        // Executa o próximo algoritmo
        runAlgorithm();
        // Desenha o autômato
        drawAutomata();
        // Habilita o botão de voltar
        jButtonPrevious.setEnabled(true);
        // Desabilita o botão de avançar
        if (step >= 5) {
            jButtonNext.setEnabled(false);
        }
    }//GEN-LAST:event_jButtonNextActionPerformed

    /**
     * Inicialização do programa
     *
     * @param args
     */
    public static void main(String[] args) {
        // Autômato inicial
        final Automata automata = new Automata();

        // Janela para abrir o arquivo
        /*
         * String filename = FileFormat.open(automata);
         * if (filename == null) {
         * JOptionPane.showMessageDialog(null, "É necessário selecionar um arquivo para continuar", "Erro", JOptionPane.ERROR_MESSAGE);
         * return;
         * }
         */

        // TODO: apenas debug, substituir depois pela janela
        final String filename = "samples\\automata5.txt";
        FileFormat.read(filename, automata);

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                GraphViewer graphFrame = new GraphViewer();
                // Mostra o grafo inicial
                graphFrame.automatas[0] = automata;
                graphFrame.filename = filename;
                graphFrame.drawAutomata();
                // Altera o título da janela
                graphFrame.setWindowTitle();
                graphFrame.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonNext;
    private javax.swing.JButton jButtonPrevious;
    private project.Console jConsole;
    private javax.swing.JPanel jPanelGraph;
    private javax.swing.JScrollPane jScrollPane;
    // End of variables declaration//GEN-END:variables

    /**
     * Desenha o grafo com seus respectivos vértices e arrestas
     */
    private void drawAutomata() {
        // Tamanho do painel
        int width = jPanelGraph.getWidth();
        int height = jPanelGraph.getHeight();

        // Autômato atual a ser desenhado
        Automata automata = automatas[step];

        // Cria um grafo direcional que permite múltiplas arestas
        Graph<State, Transition> graph = new DirectedSparseMultigraph<State, Transition>();

        // Para cada estado do autômato
        for (State state : automata.getStates()) {
            // Adiciona um vértice
            graph.addVertex(state);
        }
        // Para cada transição do autômato
        for (Transition transition : automata.getTransitions()) {
            // Adiciona uma aresta
            graph.addEdge(transition, transition.getState1(), transition.getState2());
        }

        // Layout<V, E>
        Layout<State, Transition> layout = new FRLayout<State, Transition>(graph);
        layout.setSize(new Dimension(width - 50, height - 50));

        // VisualizationComponent<V, E>
        VisualizationViewer<State, Transition> vv = new VisualizationViewer<State, Transition>(layout);

        // RenderContext<V, E>
        RenderContext<State, Transition> renderContext = vv.getRenderContext();

        // Altera o formato da fonte dos vértices
        renderContext.setEdgeFontTransformer(new TransitionFont());
        // Altera o formato da curva das linhas
        renderContext.setEdgeShapeTransformer(new EdgeShape.BentLine<State, Transition>());
        // Colore os vértices (inicial, final, ambos)
        renderContext.setVertexFillPaintTransformer(new StatePainter());
        // Altera o desenho dos vértices (inicial, final, ambos)
        renderContext.setVertexShapeTransformer(new StateShape());
        // Mostra os rótulos dos vértices e arestas
        renderContext.setVertexLabelTransformer(new StateLabeller());
        renderContext.setEdgeLabelTransformer(new TransitionLabeller());

        // Create a graph mouse and add it to the visualization component
        DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
        gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        vv.setGraphMouse(gm);
        vv.setPreferredSize(new Dimension(width, height));

        // Adiciona dicas para os vértices e arestas
        vv.setEdgeToolTipTransformer(new ToStringLabeller<Transition>());
        vv.setVertexToolTipTransformer(new ToStringLabeller<State>());

        // Ajusta a posição dos vértices do grafo na tela
        Relaxer relaxer = vv.getModel().getRelaxer();
        if (relaxer != null) {
            relaxer.prerelax();
            relaxer.relax();
        }

        // Atualiza o painel
        jPanelGraph.removeAll();
        jPanelGraph.add(vv);
        validate();

    }

    /**
     * Executa o algoritmo na etapa atual
     */
    private void runAlgorithm() {
        // Limpa a janela do console
        jConsole.setText(null);

        // Número da etapa
        switch (step) {
            case 1:
                // Algoritmo de eliminação de transições em vazio
                automatas[1] = new EmptyTransitions(automatas[0]).Start();
                FileFormat.write(filename + ".s1.txt", automatas[1]);
                break;
            case 2:
                // Algoritmo de elimininação de não determinísmos
                automatas[2] = new NonDeterministic(automatas[1]).Start();
                FileFormat.write(filename + ".s2.txt", automatas[2]);
                break;
            case 3:
                // Algoritmo de eliminação de estados inacessíveis
                automatas[3] = new InaccessibleStates(automatas[2]).Start();
                FileFormat.write(filename + ".s3.txt", automatas[3]);
                break;
            case 4:
                // Algoritmo de eliminação de estados de inúteis
                automatas[4] = new UselessStates(automatas[3]).Start();
                FileFormat.write(filename + ".s4.txt", automatas[4]);
                break;
            case 5:
                // Algoritmo de minimização de AF
                automatas[5] = new MinimizationFA(automatas[4]).Start();
                FileFormat.write(filename + ".s5.txt", automatas[5]);
                break;
        }

        // Altera o título da janela
        setWindowTitle();
    }

    /**
     * Altera o título da janela
     */
    private void setWindowTitle() {
        final String preffix = "Otimizador de Autômatos Finitos - ";

        // Número da etapa
        switch (step) {
            case 0:
                setTitle(preffix + "Autômato inicial");
                break;
            case 1:
                setTitle(preffix + "Eliminação de transições em vazio");
                break;
            case 2:
                setTitle(preffix + "Eliminação de não determinísmos");
                break;
            case 3:
                setTitle(preffix + "Eliminação de estados inacessíveis");
                break;
            case 4:
                setTitle(preffix + "Eliminação de estados inúteis");
                break;
            case 5:
                setTitle(preffix + "Minimização de AF");
                break;

        }
    }
}
