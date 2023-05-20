package main.Weaponry;


import main.CustomExceptions.NoLinkException;
import main.CustomExceptions.NotAnInstanceException;
import main.ObjectPlusPlus;
import main.Weaponry.FiringType.Kinetic;
import main.Weaponry.FiringType.Laser;
import main.Weaponry.FiringType.Rocket;
import main.Weaponry.Operation.Automatic;
import main.Weaponry.Operation.ManuallyOperated;

import java.util.List;

public class Weapon extends ObjectPlusPlus {
    String name;
    int powerDraw; //watts

    public static void initializeCardinalities(){
        setRoleCardinality(AutomatL, 1);
        setRoleCardinality(ManualL, 1);
        setRoleCardinality(LaserL, 1);
        setRoleCardinality(KineticL, 1);
        setRoleCardinality(RocketL, 1);
    }

    public Weapon(String name, int powerDraw) {
        this.name = name;
        this.powerDraw = powerDraw;
    }

    public void addAutomaticSteering(String softwareVersion, int powerDraw) throws Exception {
        for(ObjectPlusPlus link : getLinks(AutomatL)){
            this.removeLink(AutomatL, AutomatR, link);
        }
        Automatic mode = new Automatic(softwareVersion, powerDraw);
        this.addLink(AutomatL, AutomatR, mode);
    }
    public void addManualSteering(int personnelCount) throws Exception {
        for(ObjectPlusPlus link : getLinks(ManualL)){
            this.removeLink(ManualL, ManualR, link);
        }
        ManuallyOperated mode = new ManuallyOperated(personnelCount);
        this.addLink(ManualL, ManualR, mode);
    }

    boolean isFireTypeSet = false;
    private void removeFireTypes() throws NoLinkException {
        for (ObjectPlusPlus link : getLinks(KineticL)) {
            this.removeLink(KineticL, KineticR, link);
        }
        for (ObjectPlusPlus link : getLinks(RocketL)) {
            this.removeLink(RocketL, RocketR, link);
        }
        for (ObjectPlusPlus link : getLinks(LaserL)) {
            this.removeLink(LaserL, LaserR, link);
        }
    }
    public void addKineticFireType(int projectileMass, int projectileSpeed, int rateOfFire) throws Exception {
        if(!isFireTypeSet) {
            this.removeFireTypes();
            Kinetic mode = new Kinetic(projectileMass, projectileSpeed, rateOfFire);
            this.addLink(KineticL, KineticR, mode);
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
            this.addLink(LaserL, LaserR, mode);
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
            this.addLink(RocketL, RocketR, mode);
            this.isFireTypeSet = true;
        }
        else{
            throw new Exception("Fire type already set");
        }
    }



    public String getSoftwareVersion() throws NotAnInstanceException {
        try{
            List<ObjectPlusPlus> obj = this.getLinks(AutomatL);
            return ((Automatic) obj.get(0)).softwareVersion;
        } catch (Exception e) {
            throw new NotAnInstanceException("This weapon is not automatic");
        }
    }

    public int getPersonnelCount() throws NotAnInstanceException {
        try{
            List<ObjectPlusPlus> obj = this.getLinks(ManualL);
            return ((ManuallyOperated) obj.get(0)).personnelCount;
        } catch (Exception e) {
            throw new NotAnInstanceException("This weapon is not manually operated");
        }
    }

    //polimorfizm przy _overlapping
    public int getPowerDraw(){
        try{
            List<ObjectPlusPlus> obj = this.getLinks(AutomatL);
            return ((Automatic) obj.get(0)).powerDraw + this.powerDraw;
        } catch (Exception e) {
            return this.powerDraw;
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
}
