package project;

import automata.Automata;
import automata.State;
import automata.Transition;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * Classe usada para leitura/escrita de arquivos
 * contendo os autômatos
 */
public class FileFormat {

    /**
     * Separador padrão
     */
    public static final String SEPARATOR = " ";
    /**
     * Separado usado nos símbolos
     */
    private static final String SYMBOL_SEPARATOR = ",";

    /**
     * Exibe uma janela para selecionar um arquivo
     *
     * @param automata autômato lido do arquivo
     * @return caminho completo com o nome do arquivo
     */
    public static String open(Automata automata) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new ExtensionFileFilter("Autômato", "txt"));
        // Exibe o diálogo de abrir
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getAbsolutePath();
            // Lê o arquivo
            if (read(filename, automata)) {
                return filename;
            }
        }

        return null;
    }

    /**
     * Lê o arquivo especificado
     *
     * @param filename nome do arquivo
     * @param automata autômato correspondente
     * @return
     */
    public static boolean read(String filename, Automata automata) {
        FileReader fr;
        try {
            fr = new FileReader(filename);
        } catch (FileNotFoundException ex) {
            return false;
        }

        BufferedReader br = new BufferedReader(fr);
        try {
            // Símbolos
            String[] symbols = br.readLine().trim().split(SEPARATOR);
            automata.addSymbols(symbols);

            // Estados
            String[] states = br.readLine().trim().split(SEPARATOR);
            automata.addStates(states, State.NORMAL);

            // Estados iniciais
            String[] initials = br.readLine().trim().split(SEPARATOR);
            automata.addStates(initials, State.INITIAL);

            // Estados finais
            String[] finals = br.readLine().trim().split(SEPARATOR);
            automata.addStates(finals, State.FINAL);

            // Transições
            String transition;
            while ((transition = br.readLine()) != null) {
                String[] temp = transition.trim().split(SEPARATOR);

                for (String symbol : temp[1].trim().split(SYMBOL_SEPARATOR)) {
                    State state1 = automata.getState(temp[0]);
                    State state2 = automata.getState(temp[2]);
                    automata.addTransition(new Transition(state1, state2, symbol));
                }
            }

            br.close();
            fr.close();

            return true;
        } catch (IOException ex) {
            Logger.getLogger(FileFormat.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    /**
     * Escreve o autômato no arquivo
     *
     * @param filename nome do arquivo
     * @param automata autômato a ser escrito
     * @return
     */
    public static boolean write(String filename, Automata automata) {
        FileWriter fw;
        try {
            fw = new FileWriter(filename);
        } catch (IOException ex) {
            return false;
        }

        BufferedWriter bw = new BufferedWriter(fw);
        try {

            // Símbolos
            String symbols = "";
            for (String symbol : automata.getSymbols()) {
                symbols += symbol + " ";
            }
            bw.write(symbols.trim());
            bw.newLine();

            // Estados
            String states = "";
            for (State state : automata.getStates()) {
                states += state + " ";
            }
            bw.write(states.trim());
            bw.newLine();

            // Estados iniciais
            String initials = "";
            for (State initial : automata.getInitialStates()) {
                initials += initial + " ";
            }
            bw.write(initials.trim());
            bw.newLine();

            // Estados finais
            String finals = "";
            for (State finalz : automata.getFinalStates()) {
                finals += finalz + " ";
            }
            bw.write(finals.trim());
            bw.newLine();

            // Transições
            for (Transition transition : automata.getTransitions()) {
                bw.write(transition.toString());
                bw.newLine();
            }

            bw.close();
            fw.close();

            return true;
        } catch (IOException ex) {
            Logger.getLogger(FileFormat.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    /**
     * FileFilter for file dialog
     * http://www.java2s.com/Code/JavaAPI/javax.swing/JFileChoosersetFileFilterFileFilterfilter.htm
     */
    static class ExtensionFileFilter extends FileFilter {

        String description;
        String extensions[];

        public ExtensionFileFilter(String description, String extension) {
            this(description, new String[]{extension});
        }

        public ExtensionFileFilter(String description, String extensions[]) {
            if (description == null) {
                this.description = extensions[0];
            } else {
                this.description = description;
            }
            this.extensions = (String[]) extensions.clone();
            toLower(this.extensions);
        }

        private void toLower(String array[]) {
            for (int i = 0, n = array.length; i < n; i++) {
                array[i] = array[i].toLowerCase();
            }
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public boolean accept(File file) {
            if (file.isDirectory()) {
                return true;
            } else {
                String path = file.getAbsolutePath().toLowerCase();
                for (int i = 0, n = extensions.length; i < n; i++) {
                    String extension = extensions[i];
                    if ((path.endsWith(extension) && (path.charAt(path.length() - extension.length() - 1)) == '.')) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
