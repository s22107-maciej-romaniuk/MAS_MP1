package main.Weaponry.Operation;

import main.ObjectPlusPlus;
import main.Weaponry.Weapon;

public class ManuallyOperated extends Weapon {
    public int personnelCount;

    public ManuallyOperated(String name, int powerDraw, int personnelCount,  Class fireTypeClass, int... args)
            throws Exception {
        super(name, powerDraw, fireTypeClass, args);
        this.personnelCount = personnelCount;
    }

    public int getPersonnelCount() {
        return personnelCount;
    }
}
