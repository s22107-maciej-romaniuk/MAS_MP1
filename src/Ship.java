import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

public abstract class Ship implements Serializable {
    private static Set<Ship> fleet; //ekstensja klasy
    public Ship(){
        Ship.fleet.add(this);
        this.serialNumber = highestSerialNumber() + 1;
    }
    public static Set<Ship> getFleet() {
        return fleet;
    }
    //=======================================
    //trwałość ekstensji klasy
    public abstract void writeExtensionToFile(ObjectOutputStream stream) throws IOException;
    public abstract void readExtensionFromFile(ObjectInputStream stream) throws IOException, ClassNotFoundException;
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
    abstract int highestSerialNumber(); //atrybut klasowy (implementowany w klasach pochodnych)
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







}
