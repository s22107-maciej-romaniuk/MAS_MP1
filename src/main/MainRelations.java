package main;

import main.Personnel.Person;

import java.time.LocalDate;

public class MainRelations {
    public static void main(String[] args) throws Exception {
        Ship.initializeCardinalities();
        Person.initializeCardinalities();
        Incident.initializeCardinalities();
        Activity.initializeCardinalities();
        Ship kaczka = new CivilianShip(new Reactor(), "Kaczka", "KWA");
        try {
            kaczka.createModule("Dziób");
            kaczka.createModule("Mesa");
            kaczka.showModules(System.out);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        Person person1 = new Person("Maciej", "Romaniuk");
        Person person2 = new Person("Adrianna", "Gabryś");

        try {
            kaczka.addCrewman(person1, "Pierwszy oficer");
            person2.addService(kaczka, "Kapitan");
            kaczka.showCrewmen(System.out);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        Incident incident1 = new Incident("Bitwa pod Jowiszem", LocalDate.now());
        Incident incident2 = new Incident("Bitwa pod Saturnem", LocalDate.now());

        try {
            kaczka.addIncident(incident1, new Activity("Tchórzliwa ucieczka"));
            kaczka.addIncident(incident2, new Activity("Brawurowa ucieczka"));
            kaczka.showIncidentsWithActivityDescription(System.out);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

    }
}
