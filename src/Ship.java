import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class Ship implements Serializable {
    private static Set<Ship> fleet = new HashSet<>(); //ekstensja klasy
    public Ship(Reactor reactor, String shipName, String prefixName){
        this.shipName = shipName;
        this.prefixName = prefixName;
        Ship.fleet.add(this);
        this.serialNumber = highestSerialNumber() + 1;
        this.setShipReactor(reactor);
    }
    public static Set<Ship> getFleet() {
        return fleet;
    }

    //=======================================
    //trwałość ekstensji klasy
//    public static void writeExtensionToFile() throws IOException {
//    }
//
//    public static void readExtensionFromFile() throws IOException, ClassNotFoundException {
//    }

    //=======================================
    private Reactor shipReactor; //atrybut złożony (nieopcjonalny)
    public Reactor getShipReactor() {
        return shipReactor;
    }
    public void setShipReactor(Reactor shipReactor) {
        if(shipReactor == null) throw new NullPointerException("Ship must have a reactor");
        this.shipReactor = shipReactor;
    }
    //=======================================
    private int serialNumber; //atrybut prosty
    public int getSerialNumber() {
        return serialNumber;
    }
    //=======================================
    private Date lastMaintenanceDate; //atrybut opcjonalny
    public Date getLastMaintenanceDate() {
        return lastMaintenanceDate;
    }
    public void setLastMaintenanceDate(Date lastMaintenanceDate) {
        this.lastMaintenanceDate = lastMaintenanceDate;
    }
    //=======================================
    private List<String> cargoManifest; //atrybut powtarzalny
    public List<String> getCargoManifest() {
        return cargoManifest;
    }
    public void setCargoManifest(List<String> cargoManifest) {
        this.cargoManifest = cargoManifest;
    }
    //=======================================
    public abstract int highestSerialNumber();
    //=======================================
    //atrybut klasowy
    abstract Person getCoordinator();


    //=======================================
    public int getCargoQuantity() {
        return cargoManifest.size();
    } //atrybut pochodny
    //=======================================
    String shipName;
    String prefixName;

    public void setNewName(String nameWithPrefix){ //przeciążenie
        String[] x = nameWithPrefix.split(" ");
        this.shipName = x[1];
        this.prefixName = x[0];
    }
    public void setNewName(String name, String prefixName){
        this.shipName = name;
        this.prefixName = prefixName;
    }
    //======================================
    public abstract String getDescription();
    //======================================
    public static String mostFrequentCargoShip(){ // metoda klasowa
        Map<String, List<String>> cargoCounter = new HashMap<>();
        for(Ship ship : getFleet()){
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


    @Override
    public String toString(){
        return this.prefixName + " " + this.shipName;
    }




}
