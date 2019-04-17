package server;

import util.CommandManager;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class DrawingCommandServer {
    private final int PORT;
    private CommandManager commandManager;
    public static LinkedList<DrawingCommandServerThread> serverList = new LinkedList<>();


    public DrawingCommandServer(CommandManager commandManager, int port) {
        PORT = port;
        this.commandManager = commandManager;
    }


    public void start() {
        try (ServerSocket serverSoc = new ServerSocket(PORT)) {
            System.out.println("Drawing server has started. Waiting for connection.");
            while (true) {
                Socket clientSoc = serverSoc.accept();
                serverList.add(new DrawingCommandServerThread(clientSoc));
            }
        } catch (IOException e) {
            // TODO: 15.04.2019 - add logging
            System.err.println("Error when trying to make a server socket");
            e.printStackTrace();
        }
        System.out.println("The drawing command server is closed.");
    }


    private class DrawingCommandServerThread implements Runnable {
        private Socket clientSoc;

        public DrawingCommandServerThread(Socket clientSoc) {
            this.clientSoc = clientSoc;
            run();
        }


        @Override
        public void run() {
            try (
                    BufferedOutputStream out = new BufferedOutputStream(clientSoc.getOutputStream());
                    ObjectOutputStream objOut = new ObjectOutputStream(out)
            ) {
                objOut.writeObject(commandManager.getCommands());
                objOut.flush();
            } catch (IOException e) {
                // TODO: 15.04.2019 - add logging
                System.err.println("Error when trying to send commands to client");
                e.printStackTrace();
                try {
                    clientSoc.close();
                } catch (IOException e1) {
                    System.err.println("Error when trying to close client socket");
                    e1.printStackTrace();
                }

            }
        }
    }
}
