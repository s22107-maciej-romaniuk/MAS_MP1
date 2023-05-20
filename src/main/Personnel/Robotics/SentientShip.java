package main.Personnel.Robotics;

import main.Activity;
import main.CustomExceptions.NotAnInstanceException;
import main.CustomExceptions.UnfitForServiceException;
import main.IShip;
import main.Incident;
import main.ObjectPlusPlus;
import main.Personnel.IPerson;
import main.Personnel.Person;
import main.Reactor;
import main.Ship;
import main.Ship.Module;

import java.io.PrintStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class SentientShip extends ObjectPlusPlus implements IShip, IPerson {
    private static String PersonSentientL = "PersonSentientL";
    private static String PersonSentientR = "PersonSentientR";
    private static String ShipSentientL = "ShipSentientL";
    private static String ShipSentientR = "ShipSentientR";
    public static void initializeCardinalities(){
        setRoleCardinality(PersonSentientL, 1);
        setRoleCardinality(PersonSentientR, 1);
        setRoleCardinality(ShipSentientL, 1);
        setRoleCardinality(ShipSentientR, 1);
    }
    public SentientShip(Ship ship) throws Exception {
        Person person = new Person(ship.prefixName, ship.shipName);
        this.addLink(PersonSentientL, PersonSentientR, person, PersonSentientL);
        this.addLink(ShipSentientL, ShipSentientR, ship, ShipSentientL);
    }

    private Ship getShip() throws Exception {
        return (Ship) this.getLinkedObject(ShipSentientL, ShipSentientL);
    }
    private Person getPerson() throws Exception {
        return (Person) this.getLinkedObject(PersonSentientL, PersonSentientL);
    }
    @Override
    public Reactor getShipReactor() throws Exception {
        return this.getShip().getShipReactor();
    }

    @Override
    public void setShipReactor(Reactor shipReactor) throws Exception {
        this.getShip().setShipReactor(shipReactor);
    }

    @Override
    public int getSerialNumber() throws Exception {
        return this.getShip().getSerialNumber();
    }

    @Override
    public Date getLastMaintenanceDate() throws Exception {
        return this.getShip().getLastMaintenanceDate();
    }

    @Override
    public void setLastMaintenanceDate(Date lastMaintenanceDate) throws Exception {
        this.getShip().setLastMaintenanceDate(lastMaintenanceDate);
    }

    @Override
    public List<String> getCargoManifest() throws Exception {
        return this.getShip().getCargoManifest();
    }

    @Override
    public void setCargoManifest(List<String> cargoManifest) throws Exception {
        this.getShip().setCargoManifest(cargoManifest);
    }

    @Override
    public int highestSerialNumber() throws Exception {
        return this.getShip().highestSerialNumber();
    }

    @Override
    public int getCargoQuantity() throws Exception {
        return this.getShip().getCargoQuantity();
    }

    @Override
    public void setNewName(String nameWithPrefix) throws Exception {
        this.getShip().setNewName(nameWithPrefix);
        this.getPerson().firstName = this.getShip().shipName;
        this.getPerson().lastName = this.getShip().prefixName;
    }

    @Override
    public void setNewName(String name, String prefixName) throws Exception {
        this.getShip().setNewName(name, prefixName);
        this.getPerson().firstName = this.getShip().shipName;
        this.getPerson().lastName = this.getShip().prefixName;
    }

    @Override
    public String getDescription() throws Exception {
        return this.getShip().getDescription();
    }

    @Override
    public void createModule(String description) throws Exception {
        this.getShip().createModule(description);
    }

    @Override
    public void removeModule(Module module) throws Exception {
        this.getShip().removeModule(module);
    }

    @Override
    public List<Module> getModules() throws Exception {
        return this.getShip().getModules();
    }

    @Override
    public void showModules(PrintStream stream) throws Exception {
        this.getShip().showModules(stream);
    }

    @Override
    public void addCrewman(Person crewman, String jobName) throws Exception {
        this.getShip().addCrewman(crewman, jobName);
    }

    @Override
    public void removeCrewman(Person crewman, String jobName) throws Exception {
        this.getShip().removeCrewman(crewman, jobName);
    }

    @Override
    public void removeCrewman(String jobName) throws Exception {
        this.getShip().removeCrewman(jobName);
    }

    @Override
    public Person getCrewman(String jobName) throws Exception {
        return this.getShip().getCrewman(jobName);
    }

    @Override
    public List<Person> getCrewmenList() throws Exception {
        return this.getShip().getCrewmenList();
    }

    @Override
    public void showCrewmen(PrintStream stream) throws Exception {
        this.getShip().showCrewmen(stream);
    }

    @Override
    public void addIncident(Incident incident, Activity activity) throws Exception {
        this.getShip().addIncident(incident, activity);
    }

    @Override
    public void removeIncident(Incident incident) throws Exception {
        this.getShip().removeIncident(incident);
    }

    @Override
    public Map<Incident, Activity> getActivityToIncidentsMap() throws Exception {
        return this.getShip().getActivityToIncidentsMap();
    }

    @Override
    public void showIncidentsWithActivityDescription(PrintStream stream) throws Exception {
        this.getShip().showIncidentsWithActivityDescription(stream);
    }

    @Override
    public void addService(Ship ship, String jobName) throws UnfitForServiceException {
        throw new UnfitForServiceException("Sentient ship cannot serve as a crewmember");
    }

    @Override
    public void removeService(Ship ship, String jobName) throws UnfitForServiceException {
        throw new UnfitForServiceException("Sentient ship cannot serve as a crewmember");
    }

    @Override
    public void changeToCivilian(String employerName) throws Exception {
        this.getPerson().changeToCivilian(employerName);
    }

    @Override
    public void changeToMilitary(String rank) throws Exception {
        this.getPerson().changeToMilitary(rank);
    }

    @Override
    public String getEmployerName() throws Exception {
        return this.getPerson().getEmployerName();
    }

    @Override
    public String getRank() throws Exception {
        return this.getPerson().getRank();
    }
}
