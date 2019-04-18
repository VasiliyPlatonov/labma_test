import gui.DrawingBoardFrame;
import gui.DrawingBoardPanel;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

public class DrawingBoardApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DrawingBoardFrame drawBoard = new DrawingBoardFrame();
            drawBoard.add(new DrawingBoardPanel(fetchToCommands()));
            drawBoard.setVisible(true);
        });
    }

    @SuppressWarnings("unchecked")
    private static <T> ArrayList<T> fetchToCommands() {
        ArrayList<T> commands = new ArrayList<>();

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("localhost", 29288), 10000);
            try (
                    InputStream in = socket.getInputStream();
                    ObjectInputStream objIn = new ObjectInputStream(in)
            ) {
                commands = (ArrayList<T>) objIn.readObject();
            }
            return commands;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println(e);
            e.printStackTrace();
        }
        return commands;
    }
}
