package main.Weaponry.Operation;

import main.ObjectPlusPlus;
import main.Weaponry.Weapon;

public class Automatic extends Weapon {
    public String softwareVersion;
    public int powerDraw;

    public Automatic(String name, int powerDraw, int computerPowerDraw, String computerSoftwareVersion, Class fireTypeClass, int... args)
            throws Exception {
        super(name, powerDraw, fireTypeClass, args);
        this.softwareVersion = computerSoftwareVersion;
        this.powerDraw = computerPowerDraw;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    @Override
    public int getPowerDraw() {
        return powerDraw + super.getPowerDraw();
    }
}
