import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class CivilianShip extends Ship{
    private static Set<CivilianShip> fleet; //ekstensja klasy
    public CivilianShip(){
        super();
        CivilianShip.fleet.add(this);
    }

    public static Set<CivilianShip> getCivilianShipFleet(){
        return CivilianShip.fleet;
    }
    //=====================================================
    @Override
    public int highestSerialNumber() {
        Integer serialNumber = null;
        for(CivilianShip civShip : getCivilianShipFleet()){
            if(serialNumber == null) serialNumber = civShip.getSerialNumber();
            else {
                if(civShip.getSerialNumber() > serialNumber){
                    serialNumber = civShip.getSerialNumber();
                }
            }
        }
        return serialNumber;
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
    public static String mostFrequentCargo(){ // metoda klasowa
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

    @Override
    public void writeExtensionToFile(ObjectOutputStream stream) throws IOException {
        stream.writeObject(getCivilianShipFleet());
        stream.flush();
        stream.close();
    }

    @Override
    public void readExtensionFromFile(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        CivilianShip.fleet = (Set<CivilianShip>) stream.readObject();
        Ship.getFleet().addAll(CivilianShip.getCivilianShipFleet());
    }

}
