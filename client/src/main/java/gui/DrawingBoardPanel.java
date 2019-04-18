package gui;

import util.DrawingCommand;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;

public class DrawingBoardPanel extends JPanel {
    private ArrayList<DrawingCommand> commands;

    public DrawingBoardPanel(ArrayList<DrawingCommand> commands) {
        this.commands = commands;
    }

    @Override
    protected void paintComponent(Graphics g) {
        int width = getSize().width;
        int height = getSize().height;
        Graphics2D g2d = (Graphics2D) g;
        Path2D.Double path = new Path2D.Double();

        for (int i = 0; i < commands.size(); i += 3) {
            if (commands.get(i).getAction().equals("start")) {
                g2d.setColor(new Color(commands.get(i).getColor())); // set new color for new element
                path.moveTo(commands.get(i).getX() * width, commands.get(i).getY() * height);
            } else if (commands.get(i).getAction().equals("move")) {
                path.curveTo(
                        commands.get(i).getX() * width, commands.get(i).getY() * height,
                        commands.get(i + 1).getX() * width, commands.get(i + 1).getY() * height,
                        commands.get(i + 2).getX() * width, commands.get(i + 2).getY() * height
                );
            }/*if command action is not "move" or "start" just continue*/
        }
        g2d.draw(path);
    }
}
