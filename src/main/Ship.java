package main;

import main.Modularity.Module;
import main.Personnel.Person;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class Ship extends ObjectPlusPlus {
    private static Set<Ship> fleet = new HashSet<>(); //ekstensja klasy
    public Ship(Reactor reactor, String shipName, String prefixName) throws Exception {
        this.shipName = shipName;
        this.prefixName = prefixName;
        Ship.fleet.add(this);
        this.setShipReactor(reactor);
    }

    static Set<Ship> getFleet() {
        return Ship.fleet;
    }

    //======================================
    static String mostFrequentCargoShip() { // metoda klasowa
        Map<String, List<String>> cargoCounter = new HashMap<>();
        for (Ship ship : getFleet()) {
            for (String cargo : ship.getCargoManifest()) {
                if (!cargoCounter.containsKey(cargo)) {
                    cargoCounter.put(cargo, new LinkedList<>());
                }
                cargoCounter.get(cargo).add(cargo);
            }
        }
        String mostFrequentCargo = null;
        for (String cargo : cargoCounter.keySet()) {
            if (mostFrequentCargo != null) {
                if (cargoCounter.get(cargo).size() > cargoCounter.get(mostFrequentCargo).size()) {
                    mostFrequentCargo = cargo;
                }
            } else {
                mostFrequentCargo = cargo;
            }
        }
        return mostFrequentCargo;
    }

    //===========================================================================
    //ustawianie liczności
    static void initializeCardinalities() {
        ObjectPlusPlus.setRoleCardinality("Składa się z", Integer.MAX_VALUE);
        ObjectPlusPlus.setRoleCardinality("Zatrudnia", Integer.MAX_VALUE);
        ObjectPlusPlus.setRoleCardinality("Brał udział w L", Integer.MAX_VALUE);

        ObjectPlusPlus.setRoleCardinality("Wchodzi w skład", 1);
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
    //atrybut klasowy
    //protected abstract Person getCoordinator();
    public String getDescription() throws Exception {
        return this.toString();
    }

    //=======================================

    public int getCargoQuantity() {
        return cargoManifest.size();
    } //atrybut pochodny
    //=======================================
    public String shipName;
    public String prefixName;


    public void setNewName(String nameWithPrefix){ //przeciążenie
        String[] x = nameWithPrefix.split(" ");
        this.shipName = x[1];
        this.prefixName = x[0];
    }

    public void setNewName(String name, String prefixName){
        this.shipName = name;
        this.prefixName = prefixName;
    }


    @Override
    public String toString(){
        return this.prefixName + " " + this.shipName;
    }

    //===========================================================================
    //kompozycja

//    public void createModule(String description) throws Exception {
//        this.addModule(new Module(description));
//    }

    private void addModule(Module module) throws Exception {
        this.addLink("Składa się z", "Wchodzi w skład", module);
    }

    public void removeModule(Module module) throws Exception {
        this.removeLink("Składa się z", "Wchodzi w skład", module);
    }

    public List<Module> getModules() throws Exception {
        return getLinksInType("Składa się z");
    }


    public void showModules(PrintStream stream) throws Exception {
        this.showLinks("Składa się z", stream);
    }

//    public class Module extends ObjectPlusPlus{ //nie trzeba tutaj asocjacji w drugą stronę implementować bo to klasa wewnętrzna, i sam ten fakt już nam to zapewnia
//        String moduleName;
//
//        public Module(String name) {
//            this.moduleName = name;
//        }
//
//        @Override
//        public String toString(){
//            return "Module: " + this.moduleName;
//        }
//    }

    //============================================================================
    //asocjacja kwalifikowana

    public void addCrewman(Person crewman, String jobName) throws Exception {
        this.addLink("Zatrudnia", "Jest zatrudniony w", crewman, jobName);
    }


    public void removeCrewman(Person crewman, String jobName){
        if(crewman != null && jobName != null){
            this.removeLink("Zatrudnia", "Jest zatrudniony w", crewman, jobName);
        }
    }

    public void removeCrewman(String jobName) throws Exception {
        if(jobName != null){
            Person crewman = (Person) this.getLinkedObject("Zatrudnia", jobName);
            this.removeLink("Zatrudnia", "Jest zatrudniony w", crewman, jobName);
        }
    }


    public Person getCrewman(String jobName) throws Exception {
        return (Person) this.getLinkedObject("Zatrudnia", jobName);
    }


    public List<Person> getCrewmenList() throws Exception {
        return getLinksInType("Zatrudnia");
    }


    public void showCrewmen(PrintStream stream) throws Exception {
        this.showLinks("Zatrudnia", stream);
    }

    //============================================================================
    //asocjacja z atrybutem

    public void addIncident(Incident incident, Activity activity) throws Exception {
        this.addLink("Brał udział w L", "Zrzeszał L", activity);
        activity.addLink("Brał udział w R", "Zrzeszał R", incident);
    }


    public void removeIncident(Incident incident) throws Exception {
        for (ObjectPlusPlus activity : getLinks("Brał udział w L")) {
            if(activity.getLinkedObject("Brał udział w R", incident) == incident){
                this.removeLink("Brał udział w L", "Zrzeszał L", activity);
                activity.removeLink("Brał udział w R", "Zrzeszał R", incident);
            }
        }
    }


    public Map<Incident, Activity> getActivityToIncidentsMap() throws Exception {
        List<Activity> activities = this.getLinksInType("Brał udział w L");
        Map<Activity, Incident> activityToIncident = new HashMap<>();

        for(Activity activity : activities){
            activityToIncident.put(activity, (Incident) activity.getLinksInType("Brał udział w R").get(0));
        }
        return invertMapUsingStreams(activityToIncident);
    }


    public void showIncidentsWithActivityDescription(PrintStream stream) throws Exception {
        Map<Incident, Activity> incidentActivityMap = this.getActivityToIncidentsMap();
        for(Incident incident : incidentActivityMap.keySet()){
            stream.println(incident);
            stream.println("   " + incidentActivityMap.get(incident));
        }
    }
}
