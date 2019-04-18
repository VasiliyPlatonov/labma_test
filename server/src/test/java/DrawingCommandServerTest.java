import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import server.DrawingCommandServer;
import util.CommandManager;
import util.DrawingCommand;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class DrawingCommandServerTest {
    @Mock
    private CommandManager mockManager;
    private static final int PORT = 29288;
    private ArrayList<DrawingCommand> commands;


    @Before
    public void setCommands() {
        MockitoAnnotations.initMocks(this);
        commands = new ArrayList<>();
        DrawingCommand command1 = new DrawingCommand("60:21:C0:2A:E0:33",
                "start", 0.0, 0.0, -16777216);
        commands.add(command1);

        for (int i = 1; i < 4; i++) {
            commands.add(new DrawingCommand("60:21:C0:2A:E0:33", "move",
                    i * (0.1), i * (0.1), -16777216));
        }
    }


    @Test
    @SuppressWarnings("unchecked")
    public void start() throws IOException, ClassNotFoundException, InterruptedException {
        when(mockManager.getCommands()).thenReturn(this.commands);

        TestServerThread serverThread = new TestServerThread(mockManager, PORT);
        serverThread.start();

        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("localhost", PORT), 10000);
        InputStream in = socket.getInputStream();
        ObjectInputStream objIn = new ObjectInputStream(in);
        ArrayList<DrawingCommand> com = (ArrayList<DrawingCommand>) objIn.readObject();

        // manager has to invoked getCommands 1 times
        verify(mockManager, times(1)).getCommands();
        assertThat(com).isNotNull().hasSize(4);
        assertThat(com.get(0)).isNotNull();
        assertThat(com.get(0).getAction()).isEqualTo("start");
        assertThat(com.get(0).getColor()).isEqualTo(-16777216);

        socket.close();
        serverThread.close();
    }

    private class TestServerThread extends Thread implements Closeable {
        private DrawingCommandServer server;

        private TestServerThread(CommandManager commandManager, int port) {
            server = new DrawingCommandServer(commandManager, port);
        }

        @Override
        public void run() {
            server.start();
        }

        @Override
        public void close() throws IOException {
            for (DrawingCommandServer.DrawingCommandServerThread thread : server.serverList) {
                thread.close();
                this.interrupt();
            }
        }
    }
}
