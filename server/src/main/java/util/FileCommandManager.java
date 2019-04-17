package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a class that helps to read  drawing commands from a file,
 * keep them in a list and deliver them
 */
public class FileCommandManager implements CommandManager<DrawingCommand> {
    private final String CHARSET = "UTF-8";
    private final String COMMAND_FILE;
    private List<DrawingCommand> commands;

    /**
     * Creates a new <tt>FileCommandManager</tt>, given the name of the
     * file to read from.
     *
     * @throws IOException if the commands file cannot be read or if the named file does not
     *                     exist, is a directory rather than a regular file, or for some other reason cannot be opened for reading.
     */
    public FileCommandManager(String commandFile) {
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
    public FileCommandManager(File file) {
        COMMAND_FILE = file.getName();
        setCommands();
    }

    private void setCommands() {
        this.commands = readCommands(COMMAND_FILE);
    }

    @Override
    public List<DrawingCommand> getCommands() {
        return commands;
    }

    /**
     * Read the commands from commands file, given the name of the
     * file to read from.
     *
     * @param fileName the name of the file to read from
     * @throws IOException if the commands file cannot be read
     */
    private List<DrawingCommand> readCommands(String fileName) {
        try (
                FileInputStream fileIn = new FileInputStream(fileName);
                InputStreamReader inReader = new InputStreamReader(fileIn, CHARSET);
                BufferedReader reader = new BufferedReader(inReader)
        ) {
            String comStr;
            while ((comStr = reader.readLine()) != null) {
                DrawingCommand command = getCommandFromString(comStr);
                if (command != null)
                    commands.add(command);
            }
        } catch (IOException e) {
            // TODO: make logging
            System.err.println("Error when trying to read drawing commands from file: " + fileName);
            e.printStackTrace();
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
    private List<DrawingCommand> readCommands(File file) {
        return readCommands(file.getName());
    }

    /**
     * Create and give a new drawing command from a string that has the format:<br></br>
     * <уникальный идентификатор устройства>;<действие>;<относительная координата по оси X>;<относительная координата по оси Y>;<цвет>\n
     */
    private DrawingCommand getCommandFromString(String string) {
        String[] parts = string.split(";");
        if (parts.length != 5) return null;
        else
            return new DrawingCommand(
                    parts[0],
                    parts[1],
                    Double.valueOf(parts[2]),
                    Double.valueOf(parts[3]),
                    Integer.valueOf(parts[4]));
    }
}
