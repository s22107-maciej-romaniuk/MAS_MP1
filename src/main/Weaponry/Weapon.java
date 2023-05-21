package main.Weaponry;


import main.CustomExceptions.NoLinkException;
import main.CustomExceptions.NotAnInstanceException;
import main.ObjectPlusPlus;
import main.Weaponry.Operation.Automatic;
import main.Weaponry.Operation.ManuallyOperated;

import java.util.List;

public class Weapon extends ObjectPlusPlus {
    String name;
    int powerDraw; //watts

    public static void initializeCardinalities(){
//        setRoleCardinality(AutomatL, 1);
//        setRoleCardinality(ManualL, 1);
        setRoleCardinality("FireTypeL", 1);
        setRoleCardinality(LaserL, 1);
        setRoleCardinality(KineticL, 1);
        setRoleCardinality(RocketL, 1);
        setRoleCardinality(LaserR, 1);
        setRoleCardinality(KineticR, 1);
        setRoleCardinality(RocketR, 1);
        setRoleCardinality("WeaponR", 500);
    }

    public Weapon(String name, int powerDraw) {
        this.name = name;
        this.powerDraw = powerDraw;
    }

    public Weapon(String name, int powerDraw, Class fireTypeClass, int... args)
            throws Exception {
        this(name, powerDraw);
        if(fireTypeClass == Kinetic.class) addKineticFireType(args[0], args[1], args[2]);
        else if(fireTypeClass == Laser.class) addLaserFireType(args[0]);
        else if(fireTypeClass == Rocket.class) addRocketFireType(args[0]);
    }

//    public void addAutomaticSteering(String softwareVersion, int powerDraw) throws Exception {
//        for(ObjectPlusPlus link : getLinks(AutomatL)){
//            this.removeLink(AutomatL, AutomatR, link);
//        }
//        Automatic mode = new Automatic(softwareVersion, powerDraw);
//        this.addLink(AutomatL, AutomatR, mode);
//    }
//    public void addManualSteering(int personnelCount) throws Exception {
//        for(ObjectPlusPlus link : getLinks(ManualL)){
//            this.removeLink(ManualL, ManualR, link);
//        }
//        ManuallyOperated mode = new ManuallyOperated(personnelCount);
//        this.addLink(ManualL, ManualR, mode);
//    }

    boolean isFireTypeSet = false;
    private void removeFireTypes() throws Exception {
        try {
            for (ObjectPlusPlus link : getLinks(KineticL)) {
                this.removePart(KineticL, KineticR, link);
            }
        }
        catch(NoLinkException nlexc){
            //do nothing
        }
        try {
            for (ObjectPlusPlus link : getLinks(RocketL)) {
                this.removePart(RocketL, RocketR, link);
            }
        }
        catch(NoLinkException nlexc){
            //do nothing
        }
        try {
            for (ObjectPlusPlus link : getLinks(LaserL)) {
                this.removePart(LaserL, LaserR, link);
            }
        }
        catch(NoLinkException nlexc){
            //do nothing
        }
    }
    public void addKineticFireType(int projectileMass, int projectileSpeed, int rateOfFire) throws Exception {
        if(!isFireTypeSet) {
            this.removeFireTypes();
            Kinetic mode = new Kinetic(projectileMass, projectileSpeed, rateOfFire);
            this.addPart(KineticL, KineticR, mode);
            this.isFireTypeSet = true;
        }
        else{
            throw new Exception("Fire type already set");
        }
    }

    public void addLaserFireType(int frequency) throws Exception {
        if(!isFireTypeSet) {
            this.removeFireTypes();
            Laser mode = new Laser(frequency);
            this.addPart(LaserL, LaserR, mode);
            this.isFireTypeSet = true;
        }
        else{
            throw new Exception("Fire type already set");
        }
    }

    public void addRocketFireType(int warheadMass) throws Exception {
        if(!isFireTypeSet) {
            this.removeFireTypes();
            Rocket mode = new Rocket(warheadMass);
            this.addPart(RocketL, RocketR, mode);
            this.isFireTypeSet = true;
        }
        else{
            throw new Exception("Fire type already set");
        }
    }


    //polimorfizm przy _overlapping
    public int getPowerDraw(){
        return this.powerDraw;
    }

    public Kinetic castKinetic() throws NotAnInstanceException {
        try{
            List<ObjectPlusPlus> obj = this.getLinks(KineticL);
            return ((Kinetic) obj.get(0));
        } catch (Exception e) {
            throw new NotAnInstanceException("This weapon is not of kinetic fire type");
        }
    }
    public Laser castLaser() throws NotAnInstanceException {
        try{
            List<ObjectPlusPlus> obj = this.getLinks(LaserL);
            return ((Laser) obj.get(0));
        } catch (Exception e) {
            throw new NotAnInstanceException("This weapon is not of laser fire type");
        }
    }
    public Rocket castRocket() throws NotAnInstanceException {
        try{
            List<ObjectPlusPlus> obj = this.getLinks(RocketL);
            return ((Rocket) obj.get(0));
        } catch (Exception e) {
            throw new NotAnInstanceException("This weapon is not of rocket fire type");
        }
    }


    //nazwy relacji
    private static String AutomatL = "Automat L";
    private static String AutomatR = "Automat R";
    private static String ManualL = "Manual L";
    private static String ManualR = "Manual R";
    private static String LaserL = "Laser L";
    private static String LaserR = "Laser R";
    private static String KineticL = "Kinetic L";
    private static String KineticR = "Kinetic R";
    private static String RocketL = "Rocket L";
    private static String RocketR = "Rocket R";

    public String getName() {
        return name;
    }

    public class Kinetic extends ObjectPlusPlus {
        public int projectileMass;
        public int projectileSpeed;
        public int rateOfFire;

        public Kinetic(int projectileMass, int projectileSpeed, int rateOfFire) {
            this.projectileMass = projectileMass;
            this.projectileSpeed = projectileSpeed;
            this.rateOfFire = rateOfFire;
        }

        public int getProjectileMass() {
            return projectileMass;
        }

        public int getProjectileSpeed() {
            return projectileSpeed;
        }

        public int getRateOfFire() {
            return rateOfFire;
        }
    }

    public class Laser extends ObjectPlusPlus {
        public int frequency;

        public Laser(int frequency) {
            this.frequency = frequency;
        }

        public int getFrequency() {
            return frequency;
        }
    }

    public class Rocket extends ObjectPlusPlus {
        public int warheadMass;

        public Rocket(int warheadMass) {
            this.warheadMass = warheadMass;
        }

        public int getWarheadMass() {
            return warheadMass;
        }
    }

    @Override
    public String toString(){
        return "Weapon: " + this.getName();
    }
}
