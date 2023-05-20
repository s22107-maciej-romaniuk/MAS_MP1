package main.Weaponry.Operation;

import main.ObjectPlusPlus;

public class Automatic extends ObjectPlusPlus {
    public String softwareVersion;
    public int powerDraw;

    public Automatic(String softwareVersion, int powerDraw) {
        this.softwareVersion = softwareVersion;
        this.powerDraw = powerDraw;
    }
}
