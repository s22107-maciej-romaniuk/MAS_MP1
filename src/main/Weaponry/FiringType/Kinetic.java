package main.Weaponry.FiringType;

import main.ObjectPlusPlus;

public class Kinetic extends ObjectPlusPlus {
    public int projectileMass;
    public int projectileSpeed;
    public int rateOfFire;

    public Kinetic(int projectileMass, int projectileSpeed, int rateOfFire) {
        this.projectileMass = projectileMass;
        this.projectileSpeed = projectileSpeed;
        this.rateOfFire = rateOfFire;
    }
}