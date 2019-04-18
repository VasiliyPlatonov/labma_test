package gui;

import javax.swing.*;

public class DrawingBoardFrame extends JFrame {
    public static final int DEFAULT_WIDTH = 900;
    public static final int DEFAULT_HEIGHT = 700;

    public DrawingBoardFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationByPlatform(true);
        setTitle("Drawing board");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}
