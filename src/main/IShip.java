package main;

import main.Modularity.Module;
import main.Personnel.Person;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IShip extends Serializable {


    Reactor getShipReactor() throws Exception;

    void setShipReactor(Reactor shipReactor) throws Exception;

    Date getLastMaintenanceDate() throws Exception;

    void setLastMaintenanceDate(Date lastMaintenanceDate) throws Exception;

    List<String> getCargoManifest() throws Exception;

    void setCargoManifest(List<String> cargoManifest) throws Exception;


    //=======================================
    int getCargoQuantity() throws Exception //atrybut pochodny
    ;

    void setNewName(String nameWithPrefix) throws Exception;

    void setNewName(String name, String prefixName) throws Exception;

    //======================================
    String getDescription() throws Exception;

    @Override
    String toString();

    //===========================================================================
    //
    //void createModule(String description) throws Exception;

    void removeModule(Module module) throws Exception;

    List<Module> getModules() throws Exception;

    void showModules(PrintStream stream) throws Exception;

    //============================================================================
    //asocjacja kwalifikowana
    void addCrewman(Person crewman, String jobName) throws Exception;

    void removeCrewman(Person crewman, String jobName) throws Exception;

    void removeCrewman(String jobName) throws Exception;

    Person getCrewman(String jobName) throws Exception;

    List<Person> getCrewmenList() throws Exception;

    void showCrewmen(PrintStream stream) throws Exception;

    //============================================================================
    //asocjacja z atrybutem
    void addIncident(Incident incident, Activity activity) throws Exception;

    void removeIncident(Incident incident) throws Exception;

    Map<Incident, Activity> getActivityToIncidentsMap() throws Exception;

    void showIncidentsWithActivityDescription(PrintStream stream) throws Exception;
}
