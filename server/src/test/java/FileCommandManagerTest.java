import org.junit.Test;
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
    public void readCommandsTest() throws IOException {
        int commandsSize = 86;
        ArrayList<String> commands = (ArrayList<String>) commandReader.getCommands();

        assertThat(commands)
                .isNotNull()
                .hasSize(commandsSize);
    }
}
