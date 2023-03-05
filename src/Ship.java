import java.util.Date;
import java.util.List;

public abstract class Ship {
    private static List<Ship> fleet; //ekstensja klasy

    private Reactor shipReactor; //atrybut złożony (nieopcjonalny)
    private String serialNumber; //atrybut prosty
    abstract String highestSerialNumber();

    private List<String> cargoManifest; //atrybut powtarzalny
    private Date lastMaintenanceDate; //atrybut opcjonalny

    //private static int cargoQuantity; //atrybut pochodny zaimplementowany przez metodę getCargoQuantity


    public static List<Ship> getFleet() {
        return fleet;
    }
    public Reactor getShipReactor() {
        return shipReactor;
    }
    public void setShipReactor(Reactor shipReactor) {
        if(shipReactor == null) throw new NullPointerException("Ship must have a reactor");
        this.shipReactor = shipReactor;
    }
    public String getSerialNumber() {
        return serialNumber;
    }
    public List<String> getCargoManifest() {
        return cargoManifest;
    }
    public void setCargoManifest(List<String> cargoManifest) {
        this.cargoManifest = cargoManifest;
    }
    public Date getLastMaintenanceDate() {
        return lastMaintenanceDate;
    }
    public void setLastMaintenanceDate(Date lastMaintenanceDate) {
        this.lastMaintenanceDate = lastMaintenanceDate;
    }
    public int getCargoQuantity() {
        return cargoManifest.size();
    }


}
