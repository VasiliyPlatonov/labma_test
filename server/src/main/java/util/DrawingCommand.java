package util;

import java.io.Serializable;

public class DrawingCommand implements Serializable{
    private String mac;
    private String action;
    private double x;
    private double y;
    private int color;

    public DrawingCommand(String mac, String action, double x, double y, int color) {
        this.mac = mac;
        this.action = action;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
