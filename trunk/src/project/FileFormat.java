package project;

import automata.Automata;
import automata.State;
import automata.Transition;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * Classe usada para leitura/escrita de arquivos
 * contendo os autômatos
 */
public class FileFormat {

    /**
     * Separador usado nos símbolos
     */
    private static final String SYMBOL_SEPARATOR = ",";

    /**
     * Exibe uma janela para selecionar um arquivo
     *
     * @return caminho completo com o nome do arquivo
     */
    public static String open() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new ExtensionFileFilter("Autômato", "txt"));
        // Exibe o diálogo de abrir
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }

        return null;
    }

    /**
     * Valida o arquivo especificado
     *
     * @param filename nome do arquivo
     * @return
     */
    private static boolean parse(String filename) {
        FileReader fr;
        try {
            fr = new FileReader(filename);
        } catch (FileNotFoundException ex) {
            return false;
        }

        BufferedReader br = new BufferedReader(fr);
        try {
            // Tipo da linha
            final String[] errors = new String[]{
                "Símbolos: ",
                "Estados: ",
                "Estado Inicial: ",
                "Estados Finais: "
            };

            // Expressões regulares
            final String[] patterns = new String[]{
                "^\\s*\\w+(\\s+\\w+)*\\s*$",
                "^\\s*\\w+(\\s+\\w+)*\\s*$",
                "^\\s*\\w+\\s*$",
                "^\\s*\\w+(\\s+\\w+)*\\s*$"
            };

            // Linha lida
            String line;
            boolean invalid = false;
            // Símbolos, estados, inicial, finais
            for (int i = 0; i < 4; i++) {
                // Tenta ler a linha
                if ((line = br.readLine()) == null) {
                    System.out.println(errors[i] + "Linha em branco");
                    invalid = true;
                    break;
                }
                // Verifica o formato
                if (!Pattern.matches(patterns[i], line)) {
                    System.out.println(errors[i] + "Formato inválido (" + line + ")");
                    invalid = true;
                    break;
                }
            }

            // Se o arquivo for inválido
            if (invalid) {
                br.close();
                return false;
            }

            // Transições
            Pattern pattern = Pattern.compile("^\\s*(\\w+)\\s+(\\w+(?:,\\w+)*)\\s+(\\w+)\\s*$");
            while ((line = br.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (!matcher.matches()) {
                    System.out.println("Transição: Formato inválido (" + line + ")");
                    invalid = true;
                    break;
                }
            }

            br.close();
            return !invalid;

        } catch (IOException ex) {
            Logger.getLogger(FileFormat.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    /**
     * Lê o arquivo especificado
     *
     * @param filename nome do arquivo
     * @param automata autômato correspondente
     * @return
     */
    public static boolean read(String filename, Automata automata) {
        // Verifica se o formato do arquivo é inválido
        if (!parse(filename)) {
            System.out.println("Arquivo selecionado inválido");
            return false;
        }

        FileReader fr;
        try {
            fr = new FileReader(filename);
        } catch (FileNotFoundException ex) {
            return false;
        }

        BufferedReader br = new BufferedReader(fr);
        try {
            // Linha lida
            String line;

            // Expressão regular
            Pattern pattern, pattern2;
            Matcher matcher;

            // Símbolos
            pattern = Pattern.compile("(\\w+)");
            matcher = pattern.matcher(br.readLine());
            while (matcher.find()) {
                automata.addSymbol(matcher.group(1));
            }

            // Estados
            matcher = pattern.matcher(br.readLine());
            while (matcher.find()) {
                automata.addState(matcher.group(1), State.NORMAL);
            }

            // Estado inicial
            pattern2 = Pattern.compile("^\\s*(\\w+)\\s*$");
            matcher = pattern2.matcher(br.readLine());
            if (matcher.matches()) {
                automata.addState(matcher.group(1), State.INITIAL);
            }

            // Estados finais
            matcher = pattern.matcher(br.readLine());
            while (matcher.find()) {
                automata.addState(matcher.group(1), State.FINAL);
            }

            // Transições
            pattern = Pattern.compile("^\\s*(\\w+)\\s+(\\w+(?:,\\w+)*)\\s+(\\w+)\\s*$");
            while ((line = br.readLine()) != null) {
                matcher = pattern.matcher(line);
                while (matcher.find()) {
                    automata.addTransition(matcher.group(1), matcher.group(3), matcher.group(2).split(SYMBOL_SEPARATOR));
                }
            }

            br.close();
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

            // Estados inicial
            bw.write(automata.getInitialState().toString());
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
