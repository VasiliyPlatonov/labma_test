package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileCommandManager implements CommandManager {
    private final String CHARSET = "UTF-8";
    private final String COMMAND_FILE;
    private List<String> commands;

    /**
     * Creates a new <tt>FileCommandManager</tt>, given the name of the
     * file to read from.
     *
     * @throws IOException if the commands file cannot be read or if the named file does not
     *                     exist, is a directory rather than a regular file, or for some other reason cannot be opened for reading.
     */
    public FileCommandManager(String commandFile) throws IOException {
        COMMAND_FILE = commandFile;
        commands = new ArrayList<>();
        setCommands();
    }

    /**
     * Creates a new <tt>FileCommandManager</tt>, given the <tt>File</tt> to read from.
     *
     * @throws IOException if the commands file cannot be read or if the named file does not
     *                     exist, is a directory rather than a regular file, or for some other reason cannot be opened for reading.
     */
    public FileCommandManager(File file) throws IOException {
        COMMAND_FILE = file.getName();
        setCommands();
    }


    private void setCommands() throws IOException {
        this.commands = readCommands(COMMAND_FILE);
    }

    @Override
    public List<String> getCommands() throws IOException {
        return commands;
    }

    /**
     * Read the commands from commands file, given the name of the
     * file to read from.
     *
     * @param fileName the name of the file to read from
     * @throws IOException if the commands file cannot be read
     */
    private List<String> readCommands(String fileName) throws IOException {

        try (
                FileInputStream fileIn = new FileInputStream(fileName);
                InputStreamReader inReader = new InputStreamReader(fileIn, CHARSET);
                BufferedReader reader = new BufferedReader(inReader)
        ) {
            String command;
            while ((command = reader.readLine()) != null) {
                //todo: make check
                // if (checkCommand(command))
                commands.add(command);
            }
        } catch (IOException e) {
            // TODO: make logging
            System.err.println(e);
            throw e;
        }
        return commands;
    }

    /**
     * Read the commands from given the <tt>File</tt>
     * to read from.
     *
     * @param file the <tt>File</tt> to read from
     * @throws IOException if the commands file cannot be read
     */
    private List<String> readCommands(File file) throws IOException {
        return readCommands(file.getName());
    }

    /**
     * Check command for compliance with the command format:<br></br>
     * <tt><уникальный идентификатор устройства>;<действие>;<относительная координата по оси X>;<относительная координата по оси Y>;<цвет>\n</tt>
     */
    private boolean checkCommand(String command) {
        return false;
    }

}
