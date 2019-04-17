import org.junit.Test;
import util.DrawingCommand;
import util.FileCommandManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static org.assertj.core.api.Assertions.assertThat;

public class FileCommandManagerTest {
    private static ResourceBundle resource = ResourceBundle.getBundle("test-config");
    private FileCommandManager commandReader = new FileCommandManager(resource.getString("command-file"));

    public FileCommandManagerTest() throws IOException {
    }

    @Test
    public void getCommandsTest() throws IOException {
        int commandsSize = 87;
        ArrayList<DrawingCommand> commands = (ArrayList<DrawingCommand>) commandReader.getCommands();

        assertThat(commands)
                .isNotNull()
                .hasSize(commandsSize);
    }
}
