package main.Modularity;

import main.CustomExceptions.NotAnInstanceException;
import main.ObjectPlusPlus;
import main.Weaponry.Weapon;

import java.util.LinkedList;
import java.util.List;

public class Module extends ObjectPlusPlus {
    String name;
    int powerDraw;
    int crewCapacity;
    int basePrice;

    private Module(String name, int powerDraw, int crewCapacity, int basePrice){
        this.name = name;
        this.powerDraw = powerDraw;
        this.crewCapacity = crewCapacity;
        this.basePrice = basePrice;
    }
    public Module(String name, int powerDraw, int crewCapacity, int basePrice, List<String> scientificEquipmentList)
            throws Exception {
        this(name, powerDraw, crewCapacity, basePrice);
        this.addScientificSubmodule(scientificEquipmentList);
    }

    public Module(List<Weapon> weaponList, String name, int powerDraw, int crewCapacity, int basePrice)
            throws Exception {
        this(name, powerDraw, crewCapacity, basePrice);
        this.addCombatSubmodule(weaponList);
    }

    public Module(List<Weapon> weaponList, String name, int powerDraw, int crewCapacity, int basePrice, List<String> scientificEquipmentList)
            throws Exception {
        this(name, powerDraw, crewCapacity, basePrice);
        this.addCombatSubmodule(weaponList);
        this.addScientificSubmodule(scientificEquipmentList);
    }

    public static void initializeCardinalities(){
        setRoleCardinality("ScientificL", 1);
        setRoleCardinality("CombatL", 1);
        setRoleCardinality("CombatR", 1);
        setRoleCardinality("WeaponL", 500);
        setRoleCardinality("ScientificR", 1);
    }

    private void addScientificSubmodule(List<String> scientificEquipmentList) throws Exception {
        this.addPart("ScientificL", "ScientificR", new Scientific(scientificEquipmentList));
    }

    private void addCombatSubmodule(List<Weapon> weaponList) throws Exception {
        this.addPart("CombatL", "CombatR", new Combat(weaponList));
    }

    public String getName() {
        return name;
    }

    public int getPowerDraw() {
        return powerDraw;
    }

    public int getCrewCapacity() {
        return crewCapacity;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPowerDraw(int powerDraw) {
        this.powerDraw = powerDraw;
    }

    public void setCrewCapacity(int crewCapacity) {
        this.crewCapacity = crewCapacity;
    }

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    public List<String> getScientificEquipment() throws NotAnInstanceException {
        try{
            List<ObjectPlusPlus> obj = this.getLinks("ScientificL");
            return ((Scientific) obj.get(0)).getScientificEquipment();
        } catch (Exception e) {
            throw new NotAnInstanceException("This is not a scientific module");
        }
    }

    public List<Weapon> getWeapons() throws NotAnInstanceException {
        try{
            List<ObjectPlusPlus> obj = this.getLinks("CombatL");
            return ((Combat) obj.get(0)).getWeapons();
        } catch (Exception e) {
            throw new NotAnInstanceException("This is not a combat module");
        }
    }

    public class Combat extends ObjectPlusPlus {

        public Combat(List<Weapon> weapons) throws Exception {
            for (Weapon weapon:
                    weapons) {
                this.addWeapon(weapon);
            }
        }
        public void addWeapon(Weapon weapon) throws Exception {
            this.addLink("WeaponL", "WeaponR", weapon);
        }

        public List<Weapon> getWeapons() throws Exception {
            return this.getLinksInType("WeaponL");
        }
    }

    public class Scientific extends ObjectPlusPlus {
        List<String> scientificEquipment;

        public Scientific(List<String> scientificEquipment) {
            this.scientificEquipment = scientificEquipment;
        }


        public List<String> getScientificEquipment() {
            return scientificEquipment;
        }
    }
}
