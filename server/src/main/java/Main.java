import server.DrawingCommandServer;
import util.CommandManager;
import util.FileCommandManager;

import java.util.ResourceBundle;

public class Main {
    private static ResourceBundle resource = ResourceBundle.getBundle("config");
    private static final int PORT = Integer.valueOf(resource.getString("server.port"));

    /**
     * Start server on port given from config.properties file
     * and, by default, with drawing commands from file settled on config.properties
     * or with drawing commands file given in args if they`re been given
     * */
    public static void main(String[] args) {
        String fileName;
        if (args.length > 0 && args[0] != null)
            fileName = args[0];
        else
            fileName = resource.getString("command-file");

        CommandManager comManager = new FileCommandManager(fileName);
        DrawingCommandServer server = new DrawingCommandServer(comManager, PORT);
        server.start();
    }
}
