package main;

import main.Personnel.Person;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class CivilianShip extends Ship{
    private static Set<CivilianShip> fleet = new HashSet<>(); //ekstensja klasy
    public CivilianShip(Reactor reactor, String shipName, String prefixName) throws Exception {
        super(reactor, shipName, prefixName);
        CivilianShip.fleet.add(this);
    }

    public static Set<CivilianShip> getCivilianShipFleet(){
        return CivilianShip.fleet;
    }
    //=====================================================
    static final int startingSerialNumber = 0;
    @Override
    public int highestSerialNumber() {
        Integer serialNumber = startingSerialNumber;
        for(CivilianShip civShip : getCivilianShipFleet()){
            if(serialNumber == 0) serialNumber = civShip.getSerialNumber();
            else {
                if(civShip.getSerialNumber() > serialNumber){
                    serialNumber = civShip.getSerialNumber();
                }
            }
        }
        return serialNumber;
    }
    public static int getHighestSerialNumber(){
        try {
            return new ArrayList<>(getCivilianShipFleet()).get(0).highestSerialNumber();
        }
        catch(NullPointerException ex){
            return startingSerialNumber;
        }
    }

    //=====================================================
    //atrybut klasowy
    static Person coordinator;

    @Override
    public Person getCoordinator() {
        return coordinator;
    }
    public static void setCoordinatorStatic(Person coordinator) {
        CivilianShip.coordinator = coordinator;
    }
    public static Person getCoordinatorStatic() {
        return coordinator;
    }

    //=====================================================
    public static String mostFrequentCargoCivilianShip(){ // metoda klasowa
        Map<String, List<String>> cargoCounter = new HashMap<>();
        for(CivilianShip ship : getCivilianShipFleet()){
            for(String cargo : ship.getCargoManifest()){
                if(!cargoCounter.containsKey(cargo)) cargoCounter.put(cargo, new LinkedList<>());
                cargoCounter.get(cargo).add(cargo);
            }
        }
        String mostFrequentCargo = null;
        for(String cargo : cargoCounter.keySet()){
            if(mostFrequentCargo != null){
                if(cargoCounter.get(cargo).size() > cargoCounter.get(mostFrequentCargo).size()){
                    mostFrequentCargo = cargo;
                }
            }
            else{
                mostFrequentCargo = cargo;
            }
        }
        return mostFrequentCargo;
    }
    //=====================================================
    @Override // przesłonięcie
    public String getDescription() {
        return String.format("Civilian ship %s %s", this.prefixName, this.shipName);
    }
    //=====================================================
    //trwałość ekstensji klasy

    public static void writeExtensionToFile() throws IOException {
        ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(new File("civilian.data")));
        stream.writeObject(getCivilianShipFleet());
        stream.flush();
        stream.close();
        ObjectOutputStream streamCoordinator = new ObjectOutputStream(new FileOutputStream(new File("civilianCoordinator.data")));
        streamCoordinator.writeObject(getCoordinatorStatic());
        streamCoordinator.flush();
        streamCoordinator.close();
    }

    public static void readExtensionFromFile() throws IOException, ClassNotFoundException {
        ObjectInputStream stream = new ObjectInputStream(new FileInputStream(new File("civilian.data")));
        CivilianShip.fleet = (Set<CivilianShip>) stream.readObject();
        Ship.getFleet().addAll(CivilianShip.getCivilianShipFleet());
        stream.close();
        ObjectInputStream streamCoordinator = new ObjectInputStream(new FileInputStream(new File("civilianCoordinator.data")));
        CivilianShip.setCoordinatorStatic((Person) streamCoordinator.readObject());
        streamCoordinator.close();
    }

}
