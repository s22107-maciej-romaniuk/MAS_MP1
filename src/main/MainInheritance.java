package main;

import main.CustomExceptions.NotAnInstanceException;
import main.Modularity.Module;
import main.Personnel.IPerson;
import main.Personnel.Person;
import main.Personnel.Person.Civilian;
import main.Personnel.Robotics.SentientShip;
import main.Weaponry.Operation.Automatic;
import main.Weaponry.Operation.ManuallyOperated;
import main.Weaponry.Weapon;
import main.Weaponry.Weapon.Kinetic;
import main.Weaponry.Weapon.Laser;

import java.util.LinkedList;
import java.util.List;

public class MainInheritance {
    public static void main(String[] args) throws Exception {
        Ship.initializeCardinalities();
        Person.initializeCardinalities();
        SentientShip.initializeCardinalities();
        Weapon.initializeCardinalities();
        Module.initializeCardinalities();


        //multiinheritance
        System.out.println("-------------Multiinheritance-------------");
        List<Ship> shipList = new LinkedList<>();
        List<IPerson> personList = new LinkedList<>();

        SentientShip sentientShip = new SentientShip(new Reactor(), "Test", "HMS", "Maciej", Civilian.class, "Transport Inc.");
        shipList.add(sentientShip);
        personList.add(sentientShip);
        for (Ship ship:
             shipList) {
            System.out.println(ship.toString());
        }
        for (IPerson person:
                personList) {
            System.out.println(person.getName());
        }

        //multiaspect
        System.out.println("\n\n\n-------------Multiaspect-------------");
        Weapon manualLaserWeapon = new ManuallyOperated("Manualny laser", 50, 2, Laser.class, 100);
        Weapon automaticLaserWeapon = new Automatic("Automatyczny laser", 50, 50, "123.45beta", Laser.class, 200);
        Weapon manualKineticWeapon = new ManuallyOperated("Manualna armata", 50, 2, Kinetic.class, 300, 400, 500);
        Weapon automaticKineticWeapon = new Automatic("Automatyczna armata", 50, 50, "456.alfa", Kinetic.class, 600, 700, 800);
        System.out.println("Częstotliwość manualnego lasera: " + manualLaserWeapon.castLaser().getFrequency());
        System.out.println("Częstotliwość automatycznego lasera: " + automaticLaserWeapon.castLaser().getFrequency());
        System.out.println("Prędkość pocisku z manualnej armaty: " + manualKineticWeapon.castKinetic().getProjectileSpeed());
        System.out.println("Prędkość pocisku z automatycznej armaty: " + automaticKineticWeapon.castKinetic().getProjectileSpeed());
        System.out.println("Ilość operatorów manualnego lasera: " + ((ManuallyOperated)manualLaserWeapon).getPersonnelCount());
        System.out.println("Wersja oprogramowania automatycznego lasera: " + ((Automatic)automaticLaserWeapon).getSoftwareVersion());
        System.out.println("Ilość operatorów manualnej armaty: " + ((ManuallyOperated)manualKineticWeapon).getPersonnelCount());
        System.out.println("Wersja oprogramowania automatycznej armaty: " + ((Automatic)automaticKineticWeapon).getSoftwareVersion());

        //dynamic
        System.out.println("\n\n\n-------------Dynamic-------------");
        IPerson person = sentientShip;
        System.out.println("Zatrydniony przez: " + person.castCivilian().getEmployerName());
        try{
            System.out.println("Ranga: " + person.castMilitary().getRank());
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        person.changeToMilitary("Generał");
        System.out.println("Ranga: " + person.castMilitary().getRank());
        try{
            System.out.println("Zatrydniony przez: " + person.castCivilian().getEmployerName());
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        //overlapping
        System.out.println("\n\n\n-------------Overlapping-------------");
        List<String> scientificEquipment = new LinkedList<>();
        scientificEquipment.add("Skaner");
        scientificEquipment.add("Teleskop");
        Module scientificModule = new Module("Moduł badawczy", 500, 34, 66666, scientificEquipment);
        List<Weapon> weaponsList = new LinkedList<>();
        weaponsList.add(manualLaserWeapon);
        weaponsList.add(manualKineticWeapon);
        weaponsList.add(automaticKineticWeapon);
        weaponsList.add(automaticLaserWeapon);
        Module combatModule = new Module(weaponsList, "Uzbrojony moduł", 500, 34, 66666);
        Module combatScientificModule = new Module(weaponsList, "Uzbrojony moduł", 500, 34, 66666, scientificEquipment);
        System.out.println(scientificModule.getScientificEquipment().toString());
        try {
            System.out.println(scientificModule.getWeapons().toString());
        }
        catch(NotAnInstanceException ex){
            System.out.println(ex.getMessage());
        }
        System.out.println(combatModule.getWeapons().toString());
        try {
            System.out.println(combatModule.getScientificEquipment().toString());
        }
        catch(NotAnInstanceException ex){
            System.out.println(ex.getMessage());
        }
        System.out.println(combatScientificModule.getScientificEquipment().toString());
        System.out.println(combatScientificModule.getWeapons().toString());

        //klasa abstrakcyjna / polimorfizm
        System.out.println("\n\n\n-------------Abstract/polimorfizm-------------");
        Ship ship = new CivilianShip(new Reactor(), "Eustachy", "ORP");
        System.out.println(ship.getDescription());
    }
}
